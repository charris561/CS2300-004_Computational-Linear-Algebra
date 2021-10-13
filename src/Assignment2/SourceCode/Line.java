package Assignment2.SourceCode;

//line class represents a line object
public class Line {

    //private data fields
    private final int[] startingPoint;
    private final int[] endingPoint;
    private final double[] midpoint;
    private final double slope;

    //constructor
    public Line (int[] startingPoint, int[] endingPoint){

        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;

        //calculate midpoint based on points passed in
        double[] midpoint = new double[2];
        midpoint[0] = ((double)startingPoint[0] / 2) + ((double)endingPoint[0] / 2);
        midpoint[1] = ((double)startingPoint[1] / 2) + ((double)endingPoint[1] / 2);
        this.midpoint = midpoint;

        //calculate slope based on points passed in
        slope = (double)(endingPoint[1] - startingPoint[1]) / (endingPoint[0] - startingPoint[0]);

    }//end line constructor

    //getters
    public int[] getStartingPoint() {
        return startingPoint;
    }

    public int[] getEndingPoint() {
        return endingPoint;
    }

    public double[] getMidpoint() {
        return midpoint;
    }

    public double getSlope() {
        return slope;
    }

}//end Line
