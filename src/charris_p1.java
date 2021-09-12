/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 9/13
Assignment: PA 1

Description:
This program will create 5 matrices based on certain criteria.
It will then write these matrices to files to be used in the
next parts of this assignment.
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class charris_p1 {

    public static void main(String[] args) throws IOException {

        //allocate memory for matrix 1
        double[][] mat1 = new double[5][6];

        //fill mat1
        fillArrayRowFirst(mat1, 1, 1);

        //print matrix to file
        printMatrix(mat1, "charris_p1_mat1.txt");

        //allocate memory for matrix 2
        double[][] mat2 = new double[6][5];

        //fill mat2
        fillArrayColFirst(mat2, 2, 3);

        //print mat2 to file
        printMatrix(mat2, "charris_p1_mat2.txt");

        //allocate memory for array 3
        double[][] mat3 = new double[6][5];

        //fill mat3
        fillArrayColFirst(mat3, 0.6, 0.2);

        //print mat3 to file
        printMatrix(mat3, "charris_p1_mat3.txt");

        //allocate memory for array 4
        double[][] mat4 = new double[5][9];

        //fill mat4
        fillArrayColFirst(mat4, 3, 2);

        //print mat4 to file
        printMatrix(mat4, "charris_p1_mat4.txt");

        //allocate memory for array 5
        double[][] mat5 = new double[5][9];

        //fill array
        fillArrayRowFirst(mat5, -7,1);

        //print matrix
        printMatrix(mat5, "charris_p1_mat5.txt");

    }//end main


    /*
    Methods:
     */

    //printMatrix method prints out the passed in matrix
    public static void printMatrix(double mat[][], String file) throws IOException {

        //open file for printing
        File fileName = new File(file);
        PrintWriter outputFile = new PrintWriter(fileName);

        //iterate through rows and columns and prints out data
        for (int row = 0; row < mat.length; row++) {

            for (int col = 0; col < mat[row].length; col++) {

                outputFile.printf("%.1f", mat[row][col]);

                //prints a space unless it's the last column
                if (col < (mat[row].length - 1)){
                    outputFile.print(" ");
                }

            }//end col for

            //goes to next line unless last row
            if (row < (mat.length - 1)) {
                outputFile.println("");
            }

        }//end row for

        //close outputFile
        outputFile.close();

    }//end printMatrix method

    //overloads fillArrayColFirst to handle doubles
    public static void fillArrayColFirst(double[][] mat, double startVal, double incrmntVal) {

        //iterate through columns
        for (int col = 0; col < mat[0].length; col++) {

            //iterate through rows
            for (int row = 0; row < mat.length; row++) {

                //assigns values to arrays
                mat[row][col] = startVal;
                startVal += incrmntVal;

            }//end for

        }//end for

    }//end overloaded fillArrayColFirst

    //fill array row first-> fills value based on start val, iteration val
    public static void fillArrayRowFirst(double[][] mat, double startVal, double incrmntVal) {

        //iterate through rows
        for (int row = 0; row < mat.length; row++) {

            //iterate through columns
            for (int col = 0; col < mat[0].length; col++) {

                //assigns values to arrays
                mat[row][col] = startVal;
                startVal += incrmntVal;

            }//end for

        }//end for

    }//end fill array row first

}//end charris_p1
