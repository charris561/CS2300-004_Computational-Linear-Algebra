/*
P04_Plane represents plane for programming assignment 4
 */

package Assignment4;

public class P04_Plane {

    //private data fields
    private double[][] q; //point q on plane
    private double[][] n; //vector normal to plane
    private double[][] x; //a point x

    //constructor
    public P04_Plane(double[][] q, double[][] n, double[][] x){

        this.q = q;
        this.n = n;
        this.x = x;

    }//end constructor

    //getters
    public double[][] getQ() {
        return q;
    }

    public double[][] getN() {
        return n;
    }

    public void setX(double[][] x) {
        this.x = x;
    }

    //public methods
    public double calcDistXFromPlane(){

        double dist = 0;

        //eq => d = (c + n * x) / n * n
        //where c = -n * q

        //make negative n vector
        double[][] negN = new double[3][1];
        for (int r = 0; r < n.length; r++){
            for (int c = 0; c < n[0].length; c++){
                negN[r][c] = (-1) * n[r][c];
            }
        }

        //define c
        double c = charris_pa04_partB.calcDotProd(negN, q);

        //find distance from plane
        dist = (c + charris_pa04_partB.calcDotProd(n, x) ) / charris_pa04_partB.calcDotProd(n, n);

        return dist;

    }//end calcDistXFromPlane()

}//end P04_Plane
