import java.awt.*;
import java.awt.geom.*;
import java.awt.Color;
import java.util.Random;
import java.aw

/**
 * Class BoxBall - a graphical ball that observes the effect of gravity. The ball
 * has the ability to move. Details of movement are determined by the ball itself. It
 * will fall downwards, accelerating with time due to the effect of gravity, and bounce
 * upward again when hitting the ground.  Now included are Walls.
 *
 * This movement can be initiated by repeated calls to the "move" method.
 * 
 * @author Andrew Worthington
 *
 * @version 2015.10.22
 */

public class BoxBall
{
    private static final int GRAVITY = 3;  // effect of gravity

    private int ballDegradation = 2;
    private Ellipse2D.Double circle;
    private Color color;
    private int diameter;
    private double xPosition;
    private double yPosition;
    private Canvas canvas;
    private int ySpeed = 1;                // initial downward speed
    private Random rnd = new Random();
    private int direction = 1;
    private double hVelocity;
    private double friction = .04;
    private boolean onTheGround = false;
    
    /**
     * Constructor for objects of class BouncingBall
     *
     * @param xPos  the horizontal coordinate of the ball
     * @param yPos  the vertical coordinate of the ball
     * @param ballDiameter  the diameter (in pixels) of the ball
     * @param ballColor  the color of the ball
     * @param groundPos  the position of the ground (where the wall will bounce)
     * @param drawingCanvas  the canvas to draw this ball on
     */
    public BoxBall(int diameterMin, int diameterMax, BoxDims theBoxDims, Canvas drawingCanvas)
    {
        this.theBoxDims = theBoxDims;
        xPosition = rnd.nextInt(theBoxDims.getXMax()-theBoxDims.getXMin()) + theBoxDims.getXMin();
        yPosition = rnd.nextInt(theBoxDims.getYMax()-theBoxDims.getYMin()) + theBoxDims.getYMin();
        color = new Color(rnd.nextFloat(),rnd.nextFloat(),rnd.nextFloat());
        diameter = rnd.nextInt(diameterMax - diameterMin) + diameterMin;
        direction = (rnd.nextInt(1)==1) ? 1 : -1;
        canvas = drawingCanvas;
        hVelocity = rnd.nextInt(5)+2;
        onTheGround = false;
        
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
     **/
    public void move()
    {
        // remove from canvas at the current position
        erase();
            
        // compute new position
        ySpeed += GRAVITY;
        yPosition += ySpeed;
        xPosition += (hVelocity * direction);
        
        //slow the ball's horizontal momentum if it's hit the floor
       if(onTheGround){
            hVelocity -= friction;
            if(hVelocity < 0)
                hVelocity = 0;
        }
        
        // check if it has hit the ground
       if(yPosition >= (theBoxDims.getYMax() - diameter) && ySpeed > 0) {
            yPosition = (int)(theBoxDims.getYMax() - diameter);
            ySpeed = -ySpeed + ballDegradation; 
            if(ySpeed <=1 && ySpeed>=-1){
               ySpeed = 0;
               yPosition = (int)(theBoxDims.getYMax() - diameter);
               onTheGround = true;
            }
        }
        
        // check if it has hit a wall
        if(xPosition<= (theBoxDims.getXMin())) {
            xPosition = (int)(theBoxDims.getXMin());
            if(!onTheGround)ySpeed = ySpeed + ballDegradation; // don't worry about the ySpeed
                                                               // if we're on the ground
            hVelocity -= friction * 3; // slow the ball's horizontal momentum
            direction = 1;  // change direction on the wall hit
        }else if(xPosition >= (theBoxDims.getXMax() - diameter)){
            xPosition = (int)(theBoxDims.getXMax() - diameter);
            if(!onTheGround)ySpeed = ySpeed + ballDegradation;// don't worry about the ySpeed
                                                               // if we're on the ground
            hVelocity -= friction * 3; // slow the ball's horizontal momentum
            direction = -1;  // change direction on the wall hit
        }
        // draw again at new position
        draw();
    }    

    /**
     * return the horizontal position of this ball
     */
    public double getXPosition()
    {
        return xPosition;
    }

    /**
     * return the vertical position of this ball
     */
    public double getYPosition()
    {
        return yPosition;
    }
    
    /**
     * returns the speed, necessary to determine if the ball came to rest
     */
    public boolean hasSpeed(){
        return ((hVelocity!=0));
    }
}
