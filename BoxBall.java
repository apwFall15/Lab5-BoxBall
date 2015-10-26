import java.awt.*;
import java.awt.geom.*;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;

/**
 * Class BoxBall - a graphical ball that observes Moves randomly, restricted by walls. The ball
 * has the ability to move. Details of movement are determined by the ball itself.
 *
 * This movement can be initiated by repeated calls to the "move" method.
 * 
 * @author Andrew Worthington
 *
 * @version 2015.10.22
 */

public class BoxBall
{
    private Ellipse2D.Double circle;
    private Color color;
    private int diameter;
    private int xPosition;
    private int yPosition;
    private Canvas canvas;
    private int yVelocity = 0;              
    private int xVelocity = 0;
    private Random rnd = new Random();
    private BoxDims theBoxDims;
    private float ID = rnd.nextFloat();
    
    /**
     * Constructor for objects of class BouncingBall
     *
     * @param diameterMin  the minimum diameter for the ball
     * @param diameterMax  the maximum diamter for the ball
     * @param theBoxDims the dimensions for the arena the balls move in
     * @param drawingCanvas  the canvas to draw this ball on
     */
    public BoxBall(int diameterMin, int diameterMax, BoxDims theBoxDims, Canvas drawingCanvas)
    {
        this.diameter = rnd.nextInt(diameterMax - diameterMin) + diameterMin;
        this.theBoxDims = theBoxDims;
        xPosition = rnd.nextInt(theBoxDims.getXMax()-theBoxDims.getXMin()) + theBoxDims.getXMin();
        yPosition = rnd.nextInt(theBoxDims.getYMax()-theBoxDims.getYMin()-this.diameter) + theBoxDims.getYMin(); // wanted to make sure they start a little off the ground
        color = new Color(rnd.nextFloat(),rnd.nextFloat(),rnd.nextFloat());
        int directionModifier = 0;
        canvas = drawingCanvas;
        while(xVelocity==0){  // set random initial x velocity, can't be 0
            directionModifier = (rnd.nextInt(2)==1)?1:-1;
            xVelocity = (rnd.nextInt(6)+1) * directionModifier;
        }
        
         while(yVelocity==0){ // set random initial y velocity, can't be 0
            directionModifier = (rnd.nextInt(2)==1) ? 1 : -1;
            yVelocity = (rnd.nextInt(6)+1) * directionModifier;
        }
        
    }

    /**
     * Draw this ball at its current position onto the canvas.
     **/
    public void draw()
    {
        canvas.setForegroundColor(color);
        canvas.fillCircle((int)xPosition, (int)yPosition, diameter);
    }

    /**
     * Erase this ball at its current position.
     **/
    public void erase()
    {
        canvas.eraseCircle((int)xPosition, (int)yPosition, diameter);
    }    

    /**
     * Move this ball according to its position and speed and redraw.
     * Check for collisions with the walls of the arena and if the ball has hit another ball
     * @param theBalls is the arrow of otherballs, balldemo keeps this.
     **/
    public void move(ArrayList<BoxBall> theBalls)
    {
        // remove from canvas at the current position
        erase();
        this.canvas.drawBox(this.theBoxDims);
        int directionModifier;
        // compute new position
        
        while(xVelocity==0){  // set random x velocity, can't be 0
            directionModifier = (rnd.nextInt(2)==1)?1:-1;
            xVelocity = (rnd.nextInt(6)+1) * directionModifier;
        }
         while(yVelocity==0){ // set random y velocity, can't be 0
            directionModifier = (rnd.nextInt(2)==1)?1:-1;
            yVelocity = (rnd.nextInt(6)+1) * directionModifier;
        }
        
        xPosition += xVelocity;
        yPosition += yVelocity;

        // check for wall collision
        if(yPosition >= (theBoxDims.getYMax() - diameter)){
            yPosition = (int)(theBoxDims.getYMax() - diameter);
            yVelocity *= -1;
            xVelocity = (xVelocity > 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1;
        }else if(yPosition <=theBoxDims.getYMin()) {
            yPosition = (int)(theBoxDims.getYMin());
            yVelocity *= -1;
            xVelocity = (xVelocity > 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1;
        }else if(xPosition<= (theBoxDims.getXMin())) {
            xPosition = (int)(theBoxDims.getXMin());
            xVelocity *= -1;
            yVelocity = (yVelocity > 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1;
        }else if(xPosition >= (theBoxDims.getXMax() - diameter)){
            xPosition = (int)(theBoxDims.getXMax() - diameter);
            xVelocity *= -1;
            yVelocity = (yVelocity > 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1;
        }
        
        //check for ball collision
        for(BoxBall ball : theBalls){
            if( Math.pow(xPosition - ball.getXPosition(),2) + 
                Math.pow(yPosition - ball.getYPosition(),2) 
                < Math.pow((float)ball.getDiameter() / 2,2) && ball.getID()!=this.ID){
                // redirect this ball randomly in an inverted director from where it was headed    
                xVelocity = (xVelocity < 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1;
                yVelocity = (yVelocity < 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1;
                // redirect the ball it collided with randomly in an inverted director from where it was headed    
                ball.setYVelocity((yVelocity < 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1);
                ball.setXVelocity((xVelocity < 0) ? rnd.nextInt(6) + 1 : (rnd.nextInt(6) + 1) * -1);
            }
        }

        // draw again at new position
        draw();
    }    

    /**
     * return the horizontal position of this ball
     */
    public int getXPosition()
    {
        return xPosition;
    }

    /**
     * return the vertical position of this ball
     */
    public int getYPosition()
    {
        return yPosition;
    }
    
    /**
     * setters for velocity, so during ball collisions, they can move each other
     * @param the new velocity to set to
     */
    public void setYVelocity(int yVelocity){
        this.yVelocity = yVelocity;
    }
    
    public void setXVelocity(int xVelocity){
        this.xVelocity = xVelocity;
    }
    

    /**
     * get the diameter of the ball for checking on collisions
     */
    public int getDiameter()
    {
        return diameter;
    }

    /**
     * return randomly generated ID, in an attempt to 
     * allow balls to not have to calculate colliding with themselves
     */
    public float getID(){
        return this.ID;
    }
}
