//line class represents a line object
public class P04_Line {

    //private data fields
    private final double[][] point1;
    private final double[][] point2;
    private final double[][] vectorV;

    //constructor
    public P04_Line (double[][] point1, double[][] point2){

        this.point1 = point1;
        this.point2 = point2;

        //calculate vector v = point2 - point1
        double[][] vectorV = new double[3][1];
        vectorV[0][0] = point2[0][0] - point1[0][0];
        vectorV[1][0] = point2[1][0] - point1[1][0];
        vectorV[2][0] = point2[2][0] - point1[2][0];
        this.vectorV = vectorV;

    }//end line constructor

    //getters
    public double[][] getPoint1() {
        return point1;
    }

    public double[][] getPoint2() {
        return point2;
    }

    public double[][] getVectorV() {
        return vectorV;
    }

    //public methods
    public boolean isIntersecting(P04_Triangle triangle){

        boolean isIntersecting = false;

        //eq = x + tv = p1 + u1(p2 - p1) + u2(p3 - p1)
        //let w = p2 - p1 && z = p3 - p1
        //p1, p2, and p3 are vertices of triangle

        return isIntersecting;

    }//end isIntersecting

}//end Line
