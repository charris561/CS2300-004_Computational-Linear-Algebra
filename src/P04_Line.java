//line class represents a line object
public class P04_Line {

    //private data fields
    private final double[][] point1;
    private final double[][] point2;
    private final double[][] vectorV;
    private double[][] variableMat;

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

        variableMat = new double[3][1];

    }//end line constructor

    //getters/setters
    public double[][] getPoint1() {
        return point1;
    }

    public double[][] getPoint2() {
        return point2;
    }

    public double[][] getVectorV() {
        return vectorV;
    }

    public double[][] getVariableMat() {
        return variableMat;
    }

    public void setVariableMat(double[][] variableMat){
        this.variableMat = variableMat;
    }

    //public methods
    public boolean isIntersecting(P04_Triangle triangle){

        boolean isIntersecting = false;

        //eq = x + tv = p1 + u1(p2 - p1) + u2(p3 - p1)
        //let w = p2 - p1 && z = p3 - p1
        //p1, p2, and p3 are vertices of triangle
        /*
        Applying the above yields:
        [ {w1, z1, -v1}, {w2, z2, -v2}, {w3, z3, -v3} ] [ {u1, u2, t} ] = [ {a, b, c} ]
        where a = x1 - p1, b = x2 - p2, c = x3 - p3
         */

        //define w, z, a, b, and c
        double[][] vertex1 = triangle.getP();
        double[][] vertex2 = triangle.getQ();
        double[][] vertex3 = triangle.getR();
        double a = point1[0][0] - vertex1[0][0];
        double b = point1[1][0] - vertex1[1][0];
        double c = point1[2][0] - vertex1[2][0];
        double[][] w = charris_pa04_partB.subPointsOrVectors(vertex2, vertex1);
        double[][] z = charris_pa04_partB.subPointsOrVectors(vertex3, vertex1);

        double[][] mat = {
                {w[0][0], z[0][0], -vectorV[0][0]},
                {w[1][0], z[1][0], -vectorV[1][0]},
                {w[2][0], z[2][0], -vectorV[2][0]}
        };

        double[][] variableMat = new double[3][1];//holds u1, u2, and t
        double[][] answerMat = {
                {a},
                {b},
                {c}
        };

        variableMat = gaussianElim(mat, answerMat);
        setVariableMat(variableMat);

        //see if intersecting
        if((0 < variableMat[0][0] && variableMat[0][0] < 1) && (0 < variableMat[1][0] && variableMat[1][0] < 1)
        && (variableMat[0][0] + variableMat[1][0] < 1)){
            isIntersecting = true;
        }

        return isIntersecting;

    }//end isIntersecting

    public double[][] findIntersection(P04_Triangle triangle){

        double[][] intersectionPoint = new double[3][1];

        intersectionPoint[0][0] = point1[0][0] + variableMat[2][0] * vectorV[0][0];
        intersectionPoint[1][0] = point1[1][0] + variableMat[2][0] * vectorV[1][0];
        intersectionPoint[2][0] = point1[2][0] + variableMat[2][0] * vectorV[2][0];

        return intersectionPoint;

    }//end findIntersection

    private double[][] gaussianElim(double[][] mat, double[][] answerMat){

        double[][] variableMat = new double[3][1];
        double[][] inverseMat = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        //get 1 for first element
        if (mat[0][0] != 0){

            //divide first element by itself to get 1
            double tmp = mat[0][0];
            mat[0][0] /= mat[0][0];

            //divide each element of row by value
            for (int c = 1; c < mat.length; c++){
                mat[0][c] /= tmp;

                //do same to inverseMat
                inverseMat[0][c] /= tmp;
            }
        }

        //get 0 for the element below -> add to row 1
        double tmp = -mat[1][0];

        //multiply tmp by row 1, then add to row 2
        for (int c = 0; c < mat.length; c++){
            mat[1][c] = tmp * mat[0][c] + mat[1][c];

            //do same to inverseMat
            inverseMat[1][c] = tmp * inverseMat[0][c] + inverseMat[1][c];
        }

        //get 0 for element below -> add to row 1
        tmp = -mat[2][0];

        //multiply tmp by row 1, then add to row 3
        for (int c = 0; c < mat.length; c++){
            mat[2][c] = tmp * mat[0][c] + mat[2][c];

            //do same to inverseMat
            inverseMat[2][c] = tmp * inverseMat[0][c] + inverseMat[2][c];
        }

        //get 1 in center
        if (mat[1][1] != 0){

            //divide element by itself to get 1
            tmp = mat[1][1];

            //divide each element of row by value
            for (int c = 0; c < mat.length; c++){
                mat[1][c] /= tmp;

                //do same to inverseMat
                inverseMat[1][c] /= tmp;
            }
        }

        //get 0 in center right -> add row 3 to send row
        tmp = -mat[2][1];

        //multiply tmp by row 2, then add to row 3
        for (int c = 0; c < mat.length; c++){
            mat[2][c] = tmp * mat[1][c] + mat[2][c];

            //do same to inverseMat
            inverseMat[2][c] = tmp * inverseMat[1][c] + inverseMat[2][c];
        }

        //get 1 on bottom right
        if (mat[2][2] != 0){

            //divide element by itself to get 1
            tmp = mat[2][2];

            //divide each element of row by value
            for (int c = 0; c < mat.length; c++){
                mat[2][c] /= tmp;

                //do same to inverseMat
                inverseMat[2][c] /= tmp;
            }
        }

        //get 0 on right -> add with row 3
        tmp = -mat[1][2];

        //multiply tmp by row 3, then add to row 2
        for (int c = 0; c < mat.length; c++){
            mat[1][c] = tmp * mat[2][c] + mat[1][c];

            //do same to inverseMat
            inverseMat[1][c] = tmp * inverseMat[2][c] + inverseMat[1][c];
        }

        //get 0 in top right
        tmp = -mat[0][2];

        for (int c = 0; c < mat.length; c++){
            mat[0][c] = tmp * mat[2][c] + mat[0][c];

            //do same to inverseMat
            inverseMat[0][c] = tmp * inverseMat[2][c] + inverseMat[0][c];
        }

        //get 0 in top center
        tmp = -mat[0][1];

        //multiply tmp by row 2, then add to row 1
        for (int c = 0; c < mat.length; c++){
            mat[0][c] = tmp * mat[1][c] + mat[0][c];

            //do same to inverseMat
            inverseMat[0][c] = tmp * inverseMat[1][c] + inverseMat[0][c];
        }

        /*
        Gaussian elim complete -> multiply inverse by answer to get variables
         */
        variableMat = multiplyMats(inverseMat, answerMat);

        return variableMat;

    }//end gaussianElim

    public static double[][] multiplyMats(double[][] matA, double[][] matB){

        //allocate memory for result mat
        double[][] resultMat = new double[matA.length][matB[0].length];

        double sum = 0; // used to store the sum or row * col
        int i = 0; // used to track rows in matA
        int j = 0; // used to track columns in matB
        int k = 0; // used to track rows in matB

        //multiplies each row by columns
        for (i = 0; i < matA.length; i++){

            for (j = 0; j < matB[0].length; j++){

                for (k = 0; k < matB.length; k++){

                    sum += matA[i][k] * matB[k][j];

                }//end matB row for loop

                //assigns the sum value to result matrix then resets sum
                resultMat[i][j] = sum;
                sum = 0;

            }//end matB col for loop

        }//end matA row for loop

        return resultMat;

    }//end multiplyMats

}//end Line
