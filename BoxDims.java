
/**
 * A Quick Custom Object to hold dimensions of the box boundaries, felt better than having 4 ints 
 */
public class BoxDims
{
    // instance variables - replace the example below with your own
    private int xMin;
    private int xMax;
    private int yMin;
    private int yMax;

    /**
     * Constructor for objects of class BoxDims
     */
    public BoxDims()
    {

    }
    /**
     * Overloaded constructor for when you want to create it set
     */
    public BoxDims(int xMin, int xMax, int yMin, int yMax)
    {
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;

    }
    
    /**
     * setters for dims
     */
    public void setXMin(int xMin){
        this.xMin = xMin;
    }
    public void setXMax(int xMax){
        this.xMax = xMax;    
    }
    public void setYMin(int yMin){
        this.yMin = yMin;
    }
    public void setYMax(int yMax){
        this.yMax = yMax;
    }
    
    /**
     * getters for dims
     */
    public int getXMin(){
        return this.xMin;
    }
    public int getXMax(){
        return this.xMax;
    }
    public int getYMin(){
        return this.yMin;
    }
    public int getYMax(){
        return this.yMax;
    }
}