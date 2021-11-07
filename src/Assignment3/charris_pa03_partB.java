package Assignment3;
/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 10/25
Assignment: PA 3 - Part B

Description:
This program will calculate the following given a 2x2 matrix A:
- Capitol Lambda -> the diagonal matrix whose diagonal elements are corresponding
    eigenvalues cLambda_i,1 = lambda_i for i = 1,2
-R -> the 2x2 matrix whose ith column vec tor is the eigenvector corresponding to
    lambda_i = 1,2
-R*capitalLambda*R^T -> the eigen decomposition of the matrix
-1/0: the comparison between A and R*capitalLambda*R^T (is A = eigenDecomposition?)
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_pa03_partB {

    public static void main(String[] args) throws IOException {

        //declare constants for input/output file names
        final String INPUT_FILE1 = "test_input_1.txt";
        final String INPUT_FILE2 = "test_input_2.txt";
        final String INPUT_FILE3 = "test_input_3.txt";
        final String INPUT_FILE4 = "test_input_4.txt";
        final String OUTPUT_FILE1 = "charris_partB_test_output_1.txt";
        final String OUTPUT_FILE2 = "charris_partB_test_output_2.txt";
        final String OUTPUT_FILE3 = "charris_partB_test_output_3.txt";
        final String OUTPUT_FILE4 = "charris_partB_test_output_4.txt";

        //make calculations and output to output file
        partBCalculations(INPUT_FILE1, OUTPUT_FILE1);
        partBCalculations(INPUT_FILE2, OUTPUT_FILE2);
        partBCalculations(INPUT_FILE3, OUTPUT_FILE3);
        partBCalculations(INPUT_FILE4, OUTPUT_FILE4);

    }//end main

    //partBCalculations method finds calculates eigenvalues, R, eigenDecomposition, and comparison then outputs to file
    public static void partBCalculations(String inputFile, String outputFile) throws IOException {

        //create necessary variables
        double[][] A = new double[2][2]; //stores read in matrix from file
        double[][] lambda = new double[2][2]; //matrix with eigenvalues along its diagonal
        double[][] R = new double[2][2]; //stores the 2x2 matrix holding eigenvectors
        double[][] R_T = new double[2][2]; //stores transpose of R
        double[][] eigenDecomp = new double[2][2]; //stores the eigen decomposition of matrix
        double[][] r_1 = new double[2][1]; //stores eigenvector 1
        double[][] r_2 = new double[2][1]; //stores eigenvector 2
        String output = ""; // stores output

        //read matrix A from file
        readFile(inputFile, A);

        //calculate eigen values
        output = calcEigenVals(A, lambda);

        //check to see if there are any eigenvalues
        if (!output.equals("No eigenvalues\n")) {

            //find eigenvectors
            r_1 = findEigenVector(lambda[0][0], A);
            R[0][0] = r_1[0][0];
            R[1][0] = r_1[1][0];
            r_2 = findEigenVector(lambda[1][1], A);
            R[0][1] = r_2[0][0];
            R[1][1] = r_2[1][0];

            //print R to output ** put in method for calculating eigenvectors
            output += String.format("%.4f %.4f\n%.4f %.4f\n",
                    R[0][0], R[0][1],
                    R[1][0], R[1][1]);

            //calculate the transpose of R
            transpose(R, R_T);

            //calculate eigenDecomposition
            eigenDecomp = calcEigenDecomp(R, lambda, R_T);

            //print eigenDecomp to output
            output += String.format("%.4f %.4f\n%.4f %.4f\n",
                    eigenDecomp[0][0], eigenDecomp[0][1],
                    eigenDecomp[1][0], eigenDecomp[1][1]);

            //compare eigenDecomp w/ A to see if they're the same or not
            output += String.format("%d\n", compare(A, eigenDecomp));

        }

        //print output to file
        fileOut(outputFile, output);

    }//end partBCalculations

    //readFile method reads the matrix A and vector b from files and fills the passed in arrays with the values
    public static void readFile(String fileToRead, double[][] A) throws IOException {

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //fill matrix
        for (int row = 0; row < 2; row++) {

            for (int col = 0; col < 3; col++) {

                //if col 1 or col 2 assign to A, else assign to b
                if (col != 2) {
                    A[row][col] = inputFile.nextDouble();
                } else {
                    inputFile.nextDouble();
                }

            }//end col for

        }//end row for

        //close file
        inputFile.close();

    }//end readFile

    //calcEigenVals method calculates the eigenvalues for a given matrix A
    public static String calcEigenVals(double[][] A, double[][] lambda) {

        String output = "";

        /*
        Calculate eigen values and place them in lambda along diagonal
         */
        //assign each index of A to letter
        double a = A[0][0];
        double b = A[0][1];
        double c = A[1][0];
        double d = A[1][1];

        //find eigenvalues

        if (Math.pow((a + d), 2) - (4 * ((a * d) - (b * c))) >= 0) {
            double sqrt = Math.sqrt(Math.pow((a + d), 2) - (4 * ((a * d) - (b * c))));
            double lambda_1 = (a + d + sqrt) / 2;
            double lambda_2 = (a + d - sqrt) / 2;

            //assign eigenvalues to lambda
            lambda[0][0] = lambda_1;
            lambda[1][1] = lambda_2;

            //print to output
            output = String.format("%.4f %.4f\n%.4f %.4f\n",
                    lambda[0][0], lambda[0][1],
                    lambda[1][0], lambda[1][1]);
        }
        else {
            output = "No eigenvalues\n";
        }

        return output;

    }//calcEigenVals

    //findEigenVector method finds the eigenvector for the linear system
    public static double[][] findEigenVector(double lambda, double[][] matA) {

        /*
        To find eigenvectors, we need to solve the following:
        {(a-lambda_1), b}, {c, (d-lambda_2)} {r_1, r_2} = {0,0}
         */
        //assign each index of A to letter
        double a = matA[0][0];
        double b = matA[0][1];
        double c = matA[1][0];
        double d = matA[1][1];

        double[][] r = new double[2][1]; //stores eigenvector
        boolean colPivot = false; //flag to see if column pivot was performed
        boolean isNonTrivial = true; //flag to see if there are any non trivial eigenvectors

        //let A = a - lambda and D = d - lambda
        double A = a - lambda;
        double D = d - lambda;

        //if A == 0 and c != 0, do row pivot
        if ((A == 0) && (c != 0)) {

            matA[0][0] = c;
            matA[0][1] = D;
            matA[1][0] = A;
            matA[1][1] = b;

        }//end A==0 & c!= 0

        //if A == 0, c == 0, and b != 0, do column pivot
        if ((A == 0) && (b != 0) && (c == 0)) {

            matA[0][0] = b;
            matA[0][1] = A;
            matA[1][0] = D;
            matA[1][1] = c;
            colPivot = true;

        }//end if A=0, c=0, b!=0

        //if A,c,b == 0, no non trivial eigenvector
        if ((A == 0) && (c == 0) && (b == 0)) {

            isNonTrivial = false;

        }//end A,b,c = 0

        //if real eigenvector
        if (isNonTrivial) {

            if (!colPivot) {
                if ((-b * c / A) + D == 0) {

                    double lengthR = Math.sqrt(Math.pow((b / A), 2) + 1);

                    r[1][0] = 1 / lengthR;
                    r[0][0] = -b / (A * lengthR);

                }
            }//end !colpivot

            if(colPivot && ((-A * D / b) + c == 0)){

                double lengthR = Math.sqrt(Math.pow((A / b), 2) + 1);

                r[0][0] = 1 / lengthR;
                r[1][0] = -A / (b * lengthR);

                if (r[0][0] == -0){r[0][0] = 0;}
                if (r[1][0] == -0){r[1][0] = 0;}

            }

        }// isNonTrivial
        else r = null;

        //return matA to original state
        matA[0][0] = a;
        matA[0][1] = b;
        matA[1][0] = c;
        matA[1][1] = d;

        return r;

    }//end findEigenVector

    //transpose method calculates the transpose of a matrix passed in
    public static void transpose(double[][] R, double[][] R_T){

        //assign indices of R to variables
        double a = R[0][0];
        double b = R[0][1];
        double c = R[1][0];
        double d = R[1][1];

        //assign to transpose of r
        R_T[0][0] = a;
        R_T[1][0] = b;
        R_T[0][1] = c;
        R_T[1][1] = d;

    }//end transpose

    //calc eigenDecomp calculates the eigenDecomposition
    public static double[][] calcEigenDecomp(double[][] R, double[][] lambda, double[][] R_T){

        //multiply R*lambda*R_T
        double[][] tmp = multiply(R, lambda);

        return multiply(tmp, R_T);

    }//end calcEigenDecomp

    //addMatrices method takes two matrices and multiplies them
    public static double[][] multiply(double[][] matA, double[][] matB){

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

    //compare method compares 2 matrices and returns a 1 if they are equal
    public static int compare(double[][] matA, double[][] matB){

        int result = 1;

        //iterate through rows and columns and prints out data
        for (int row = 0; row < matA.length; row++) {

            for (int col = 0; col < matA[row].length; col++) {

                if (matA[row][col] != matB[row][col]){
                    result = 0;
                }

            }//end col for

        }//end row for

        return result;

    }//end compare

    //fileOut method writes the outputs of the calculations to the corresponding output file
    public static void fileOut(String fileToOutput, String output) throws IOException{

        //open file for printing
        File fileName = new File(fileToOutput);
        PrintWriter outputFile = new PrintWriter(fileName);

        outputFile.print(output);

        //close outputFile
        outputFile.close();

    }//end fileOut

}//end charris_pa03_partB
