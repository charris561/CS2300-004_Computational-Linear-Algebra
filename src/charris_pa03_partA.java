/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 10/25
Assignment: PA 3 - Part A

Description:
This program will read in a matrix A and a vector B and compute
x in the equation Ax = b for a general A.
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_pa03_partA {

    public static void main(String[] args) throws IOException {

        //declare constants for input/output file names
        final String INPUT_FILE1 = "test_input_1.txt";
        final String INPUT_FILE2 = "test_input_2.txt";
        final String INPUT_FILE3 = "test_input_3.txt";
        final String INPUT_FILE4 = "test_input_4.txt";
        final String OUTPUT_FILE1 = "charris_partA_test_output_1.txt";
        final String OUTPUT_FILE2 = "charris_partA_test_output_2.txt";
        final String OUTPUT_FILE3 = "charris_partA_test_output_3.txt";
        final String OUTPUT_FILE4 = "charris_partA_test_output_4.txt";

        //make calculations and output to output file
        partACalculations(INPUT_FILE1, OUTPUT_FILE1);
        partACalculations(INPUT_FILE2, OUTPUT_FILE2);
        partACalculations(INPUT_FILE3, OUTPUT_FILE3);
        partACalculations(INPUT_FILE4, OUTPUT_FILE4);

    }//end main

    //partACalculations function either finds x or states system is inconsistent/ underdetermined
    public static void partACalculations(String inputFile, String outputFile) throws IOException{

        //initialize necessary variables
        double[][] A = new double[2][2]; //stored matrix A from file
        double[] b = new double[2]; //stores vector b from file
        double[][] inverseA = new double[2][2]; //stores the inverse matrix of A
        double[] x = new double[2]; //stores x
        double detA = 0; //stores the determinant of A
        String output = ""; //variable to store results or any error messages

        //store matrix from file in A
        readFile(inputFile, A, b);

        //calculate the determinant of A
        detA = calcDet(A);

        //if detA not zero, find x
        if (detA != 0){

            //calculate inverse A
            calcInvA(A, inverseA);

            //find x
            findX(inverseA, x, b);

            //assign values in x to output in String format
            output = xToString(x);

        }//end if detA not zero

        //else determine if inconsistent or underdetermined
        else {

            output = chkIncVSUnd(A, b, x);

        }

        fileOut(outputFile, output);

    }//end partACalculations

    //readFile method reads the matrix A and vector b from files and fills the passed in arrays with the values
    public static void readFile(String fileToRead, double[][] A, double[] b) throws IOException {

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        int bIndex = 0; //stores the index for b vector

        //fill matrix
        for (int row = 0; row < 2; row++){

            for (int col = 0; col < 3; col++){

                //if col 1 or col 2 assign to A, else assign to b
                if (col != 2) {
                    A[row][col] = inputFile.nextDouble();
                }
                else{
                    b[bIndex] = inputFile.nextDouble();
                    bIndex++;
                }

            }//end col for

        }//end row for

        //close file
        inputFile.close();

    }//end readFile

    //fileOut method writes the outputs of the calculations to the corresponding output file
    public static void fileOut(String fileToOutput, String output) throws IOException{

        //open file for printing
        File fileName = new File(fileToOutput);
        PrintWriter outputFile = new PrintWriter(fileName);

        outputFile.print(output);

        //close outputFile
        outputFile.close();

    }//end fileOut

    //calcDet calculates the determinant of the passed in 2x2 matrix
    public static double calcDet(double[][] A){

        //initialize necessary variables
        double det = 0;

        //calculate determinant -> eq: (a1,1*a2,2) - (a1,2*a2,1) = det of A
        det = (A[0][0] * A[1][1]) - (A[0][1] * A[1][0]);

        return det;

    }//end calcDet

    //calcInvA calculates the inverse of matrix A
    public static void calcInvA(double[][] A, double[][] invA){

        /*
        calculate inverse of A -> let A = {a,b}, {c,d}
        eq: (1/det(a)) * {d, -b},{-c, a}
         */
        //set each index of A to variable
        double a = A[0][0];
        double b = A[0][1];
        double c = A[1][0];
        double d = A[1][1];

        //store determinant of A
        double detA = calcDet(A);

        //calculate each index of invA
        invA[0][0] = d / detA;
        invA[0][1] = (-1 * b) / detA;
        invA[1][0] = (-1 * c) / detA;
        invA[1][1] = a / detA;

    }//end calcInvA

    //findX finds X given Matrix A and vector b
    public static void findX(double[][] invA, double[] x, double[] b){

        /*
        Find x for Ax = b -> let invA = {e, f},{g, h}
        eq: x_1 = e * b_1 + f * b_2
        x_2 = g * b_1 + h * b_2
         */
        //assign each index of invA to letter in equation
        double e = invA[0][0];
        double f = invA[0][1];
        double g = invA[1][0];
        double h = invA[1][1];

        //solve for x_1 and x_2
        x[0] = (e * b[0]) + (f * b[1]);
        x[1] = (g * b[0]) + (h * b[1]);

    }//end findX

    //xToString formats x in a 2x1 matrix in string format
    public static String xToString(double[] x){

        String output = ""; //stores output

        //assign first row of output to x_1 and second row of output to x_2
        output = String.format("%.4f\n%.4f", x[0], x[1]);

        return output;

    }//end xToString

    //chkIncVSUnd checks to see if the matrix is inconsistent or underdetermined and returns the appropriate string
    public static String chkIncVSUnd(double[][] A, double[] B, double[] x){

        String result = ""; //stores either underdetermined or inconsistent
        /*
        see if inconsistent or underdetermined. Let A = {a, b},{c, d} & b = {b_1, b_2} & x = {x_1, x_2}

        Equations to be used below:

        b_1 = a * x_1 + b * x_2
        b_2 = c * x_1 + b * x_2

         */
        //assign indices of A to variables
        double a = A[0][0];
        double b = A[0][1];
        double c = A[1][0];
        double d = A[1][1];

        if (b != 0){

            x[0] = 1;
            x[1] = (B[0] - a) / b;

            if ((c * x[0]) + (d * x[1]) != B[1]){
                result = "System Inconsistent";
            }
            else if ((c * x[0]) + (d * x[1]) == B[1]){
                result = "System Underdetermined";
            }
            else {
                result = xToString(x);
            }

        }

        if ((b == 0) && (a != 0)){

            x[1] = 1;
            x[0] = B[0] / a;

            if ((c * x[0]) + (d * x[1]) != B[1]){
                result = "System Inconsistent";
            }
            else if ((c * x[0]) + (d * x[1]) == B[1]){
                result = "System Underdetermined";
            }
            else {
                result = xToString(x);
            }

        }

        if ((b == 0) && (a == 0) && (B[0] != 0)){
           result = "System Inconsistent";
        }

        if ((a == 0) && (b == 0) && (B[0] == 0)){
            result = "System Underdetermined";
        }

        return result;

    }//end chkIncVSUnd

}//end charris_pa03
