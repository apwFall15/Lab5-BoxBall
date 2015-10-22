import java.awt.Color;
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
    private boolean hasBox = false;
    private BoxDims theDims;
    private ArrayList<BoxBall> boxBalls= new ArrayList<BoxBall>();

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
    }

    /**
     * Simulate a bouncing ball placed into a box, accepts a quantity of balls to make
     */
    public void boxBounce(int numBalls){
        this.hasBox = true;
        this.theDims = new BoxDims(25,550,25,450);
        
        myCanvas.setVisible(true);
        
        myCanvas.drawLine(theDims.getXMin(), theDims.getYMin(), theDims.getXMax(), theDims.getYMin());
        myCanvas.drawLine(theDims.getXMax(), theDims.getYMin(), theDims.getXMax(), theDims.getYMax());
        myCanvas.drawLine(theDims.getXMax(), theDims.getYMax(), theDims.getXMin(), theDims.getYMax());
        myCanvas.drawLine(theDims.getXMin(), theDims.getYMax(), theDims.getXMin(), theDims.getYMin());
        
        // Make the required number of balls
        for(int ballsMade = 0;ballsMade < numBalls; ballsMade++){
            boxBalls.add(new BoxBall(10,30,this.theDims,myCanvas));
        }
        
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(50);           // small delay
            boolean areBallsMoving = false;
            for(BoxBall ball : boxBalls){   //move each ball in the dynamic array
                ball.move(); 
                if(ball.hasSpeed())  // check to see if its stopped, update the status array
                    areBallsMoving = true;
            }
              
            // stop once all balls have no vertical velocity
            if(!areBallsMoving){
                finished = true; // if all the balls are on the ground 
            }
        }
        myCanvas.erase();
        boxBalls = new ArrayList<BoxBall>();
    }
    
    /**
     * adds an additional ball to the box, boxBounce has to have be called first. This also
     * won't work yet because of how BoxBall is implemented; execution is halted while the original 
     * balls move, this I don't think you can call this one, but eventually could be implemented.
     * 
     */
    public void addBoxBall()
    {
       if(this.hasBox){
            boxBalls.add(new BoxBall(10,30,this.theDims,myCanvas));           
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
            myCanvas.wait(50);           // small delay
            ball.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
}
