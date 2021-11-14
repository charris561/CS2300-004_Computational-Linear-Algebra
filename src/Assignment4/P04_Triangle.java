/*
Triangle class represents a triangle with points p, q, and r points in 3D
 */

package Assignment4;

public class P04_Triangle {

    //private data fields
    private final double[][] p;
    private final double[][] q;
    private final double[][] r;
    private final double[][] centroid;
    private final double[][] viewVector;
    private final double[][] normalVector;

    //constructor
    public P04_Triangle(double[][] p, double[][] q, double[][] r){

        //assign points p, q, and r to private data fields
        this.p = p;
        this.q = q;
        this.r = r;

        //calculate and store centroid of triangle
        double[][] centroid = new double[3][1];
        for (int row = 0; row < centroid.length; row++){

            for (int col = 0; col < centroid[0].length; col++){

                centroid[row][col] = (p[row][col] + q[row][col] + r[row][col]) / 3;

            }//end iterating columns

        }//end iterating rows

        this.centroid = centroid;

        //create memory for additional data structures
        viewVector = new double[3][1];
        normalVector = new double[3][1];

    }//end constructor

    //getters
    public double[][] getP() {
        return p;
    }

    public double[][] getQ() {
        return q;
    }

    public double[][] getR() {
        return r;
    }

    public double[][] getCentroid() {
        return centroid;
    }

    public double[][] getViewVector() {
        return viewVector;
    }

    public double[][] getNormalVector() {
        return normalVector;
    }

    //setters
    public void setViewVector(double[][] eyeLocation){

        //eq =  e - c/ ||e - c||
        double[][] eSubC = new double[3][1];

        //calculate e - c
        for (int r = 0; r < eSubC.length; r++){

            for (int c = 0; c < eSubC[0].length; c++){

                eSubC[r][c] = eyeLocation[r][c] - centroid[r][c];

            }//end iterating columns

        }//end iterating rows

        //calculate the magnitude of eSubC
        double lengthOfESubC = Math.sqrt( Math.pow(eSubC[0][0], 2) + Math.pow(eSubC[1][0], 2) + Math.pow(eSubC[2][0], 2));

        //set indices of viewVector to cSubE/ lengthOfESubC
        for (int r = 0; r < viewVector.length; r++){

            for (int c = 0; c < viewVector[0].length; c++){

                viewVector[r][c] = eSubC[r][c] / lengthOfESubC;

            }//end iterating columns

        }//end iterating rows

        //set normal vector
        setNormalVector();

    }//end set viewVector


    //public methods
    public boolean isCulling(){

        //returns true if dotProd < 0, else returns false
        return (calcDotProd(normalVector, viewVector) < 0);

    }//end isCulling

    //calculates the light intensity
    public double calcLightIntensity(double[][] lightDirection){

        //eq. light intensity = cos(theta) = (d * n) / (||n|| ||d||)
        // => sin(theta) = sqrt( 1 - cos^2(theta) )
        double lightIntensity = calcDotProd(lightDirection, normalVector) / (calcLength(normalVector) * calcLength(lightDirection));

        //if negative output 0
        if (lightIntensity < 0){
            lightIntensity = 0;
        }

        return lightIntensity;

    }//end calcLightIntensity

    //combined approach of culling + shading to simulate shading
    public String shade(double[][] lightDirection){

        String shadeResult = "";

        //if culling, then back facing and no shading. Else output 1 for not culling
        if (isCulling()){

            shadeResult += "0 ";

        }
        else {

            shadeResult += "1->";
            shadeResult += String.format("Intensity:%.2f ", calcLightIntensity(lightDirection));

        }

        return shadeResult;

    }//end shade


    //private methods
    private void setNormalVector(){

        //let u = q - p and w = r - p
        //then n = ( u ^ w ) / || u ^ w||

        //calculate u and w
        double[][] u = subPointsOrVectors(q, p);
        double[][] w = subPointsOrVectors(r, p);

        //calculate n
        double[][] crossProdUW = calcCrossProd(u, w);
        double lengthOfCrossUW = calcLength(crossProdUW);
        for (int r = 0; r < normalVector.length; r++){

            for (int c = 0; c < normalVector[0].length; c++){

                normalVector[r][c] = crossProdUW[r][c] / lengthOfCrossUW;

            }//end iterating columns

        }//end iterating rows

    }//end setNormalVector

    //subtracts to vectors/points
    private double[][] subPointsOrVectors(double[][] point1, double[][] point2){

        double[][] result = new double[3][1];

        for (int r = 0; r < result.length; r++){

            for (int c = 0; c < result[0].length; c++){

                result[r][c] = point1[r][c] - point2[r][c];

            }//end iterating columns

        }//end iterating rows

        return result;

    }//end subPointsOrVectors

    //calculates cross product of v and w
    private double[][] calcCrossProd(double[][] v, double[][] w){

        double[][] crossProd = new double[3][1];

        crossProd[0][0] = (v[1][0] * w[2][0]) - (w[1][0] * v[2][0]);
        crossProd[1][0] = (v[2][0] * w[0][0]) - (w[2][0] * v[0][0]);
        crossProd[2][0] = (v[0][0] * w[1][0]) - (w[0][0] * v[1][0]);

        return crossProd;

    }//end calcCrossProd

    //calculate length of vector v
    private double calcLength(double[][] v){

        return Math.sqrt( Math.pow(v[0][0], 2) + Math.pow(v[1][0], 2) + Math.pow(v[2][0], 2));

    }//end calcLength

    //calculate dot product of v and w
    private double calcDotProd(double[][] v, double[][] w){

        double dotProd = 0;

        for (int r = 0; r < v.length; r++){

            for (int c = 0; c < v[0].length; c++){

                dotProd += v[r][c] * w[r][c];

            }//end iterating columns

        }//end iterating rows

        return dotProd;

    }//end calcCrossProd

}//end P04_Triangle
