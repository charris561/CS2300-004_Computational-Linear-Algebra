//line class represents a line object
public class Line {

    //private data fields
    private final double[] point1;
    private final double[] point2;
    private double[] vector_v;

    //constructor
    public Line (double[] point1, double[] point2){

        this.point1 = point1;
        this.point2 = point2;

        //calculate vector v with passed in points
        double[] vector_v = new double[2];
        vector_v[0] = point2[0] - point1[0];
        vector_v[1] = point2[1] - point1[1];
        this.vector_v = vector_v;

    }//end line constructor

    //getters
    public double[] getPoint1() {
        return point1;
    }

    public double[] getPoint2() {
        return point2;
    }

    public double[] getVector_v() { return vector_v; }

}//end Line
