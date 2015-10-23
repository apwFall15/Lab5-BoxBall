import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class BallDemo   
{
    private Canvas myCanvas;
    private Dimension canvasDims;
    private boolean hasBox = false;
    private BoxDims theDims;
    private ArrayList<BoxBallGravity> boxBallGs= new ArrayList<BoxBallGravity>();
    private ArrayList<BoxBall> boxBalls = new ArrayList<BoxBall>();
    private final int boxOffset=30;
    private int pauseLength = 50; //how long to pause before redrawing/recalculating ball movement
    
    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
    }
    
    /**
     * Simulate a bouncing ball placed into a box, accepts a quantity of balls to make, with gravity!
     * accepts a duration argument in seconds will will let the program run for that long
     */
    public void boxBounce(int numBalls, int duration){
        this.hasBox = true;
        this.theDims = new BoxDims(boxOffset,(int)myCanvas.getSize().getWidth()-boxOffset,
                                   boxOffset,(int)myCanvas.getSize().getHeight()-boxOffset);
        
        myCanvas.setVisible(true);
        myCanvas.drawBox(this.theDims);
        // Make the required number of balls
        for(int ballsMade = 0;ballsMade < numBalls; ballsMade++){
            boxBalls.add(new BoxBall(10,30,this.theDims,myCanvas));
        }
        
        boolean finished =  false;
        duration *= 1000;  //convert seconds to miliseconds
        while(!finished) {
            myCanvas.wait(pauseLength);           // small delay
              for(BoxBall ball : boxBalls){   //move each ball in the dynamic array
                ball.move(boxBalls); 
            }
              
            // this condition will never happen as we never retard their speed, instead have
            // an animation duration
            if(duration <= 0){
                finished = true; // if all the balls are on the ground 
            }
            duration -= pauseLength;
        }
       
        myCanvas.erase();
        boxBalls = new ArrayList<BoxBall>();
    }
    
     /**
     * Simulate a bouncing ball placed into a box, accepts a quantity of balls to make
     */
    public void boxBounceGravity(int numBalls){
        this.hasBox = true;
        this.theDims = new BoxDims(boxOffset,(int)myCanvas.getSize().getWidth()-boxOffset,
                                   boxOffset,(int)myCanvas.getSize().getHeight()-boxOffset);
        
        myCanvas.setVisible(true);
        myCanvas.drawBox(this.theDims);
        // Make the required number of balls
        for(int ballsMade = 0;ballsMade < numBalls; ballsMade++){
            boxBallGs.add(new BoxBallGravity(10,30,this.theDims,myCanvas));
        }
        
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(pauseLength);           // small delay
            boolean areBallsMoving = false;
            for(BoxBallGravity ball : boxBallGs){   //move each ball in the dynamic array
                ball.move(); 
                if(ball.hasSpeed())  // check to see if its stopped, update the status array
                    areBallsMoving = true;
            }
              
            // stop once all balls have no horizontal velocity
            if(!areBallsMoving){
                finished = true; // if all the balls are on the ground and stopped
            }
        }
        myCanvas.erase();
        boxBallGs = new ArrayList<BoxBallGravity>();
    }
  
    /**
     * adds an additional ball to the box, boxBounce has to have be called first. This also
     * won't work yet because of how BoxBall is implemented; execution is halted while the original 
     * balls move, this I don't think you can call this one, but eventually could be implemented.
     * 
     */
    public void addBoxBallGravity()
    {
       if(this.hasBox){
            boxBallGs.add(new BoxBallGravity(10,30,this.theDims,myCanvas));           
       }else{
            System.out.println("Please call boxBounce() before using addBoxBall()");
       }
    }

    /**
     * Simulate two bouncing balls
     */
    public void bounce()
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.drawLine(50, ground, 550, ground);

        // crate and show the balls
        BouncingBall ball = new BouncingBall(50, 50, 16, Color.BLUE, ground, myCanvas);
        ball.draw();
        BouncingBall ball2 = new BouncingBall(70, 80, 20, Color.RED, ground, myCanvas);
        ball2.draw();

        // make them bounce
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(pauseLength);           // small delay
            ball.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
}
