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
import java.util.Scanner;

public class charris_p1 {

    public static void main(String[] args) throws IOException {

        //allocate memory for matrix 1
        int[][] mat1 = new int [5][6];

        //iterate through rows
        int u = 0; //used to assign variables to indices
        for (int row = 0; row < mat1.length; row++){

            //iterate through columns
            for (int col = 0; col < mat1[row].length; col++){

                //assigns values to arrays
                mat1[row][col] = u;
                u++;

            }//end for

        }//end for

        //print matrix to file
        printMatrix(mat1, "charris_p1_mat1.txt");

        //allocate memory for matrix 2
        int[][] mat2 = new int[6][5];

        //fill mat2
        fillArray(mat2, 2,3);

        //print mat2 to file
        printMatrix(mat2, "charris_p1_mat2.txt");

        //allocate memory for array 3
        double[][] mat3 = new double[6][5];

        //iterate through columns
        double j = 0.6; //used to assign variables to indices
        for (int col = 0; col < mat2[col].length; col++){

            //iterate through rows
            for (int row = 0; row < mat2.length; row++){

                //assigns values to arrays
                mat3[row][col] = j;
                j += 0.2;

            }//end for

        }//end for

        //print mat3 to file
        printMatrix(mat3, "charris_p1_mat3.txt");

//        //allocate memory for array 4
//        int[][] mat4 = new int[5][9];
//
//        //iterate through columns
//        u = 3; //used to assign variables to indices
//        for (int col = 0; col < mat2[col].length; col++){
//
//            //iterate through rows
//            for (int row = 0; row < mat2.length; row++){
//
//                //assigns values to arrays
//                mat4[row][col] = u;
//                u += 2;
//
//            }//end for
//
//        }//end for
//
//        //print mat4 to file
//        printMatrix(mat4, "charris_p1_mat4.txt");

    }//end main

    //Method printMatrix prints out the passed in matrix
    public static void printMatrix(int mat[][], String file) throws IOException{

        //open file for printing
        File fileName = new File(file);
        PrintWriter outputFile = new PrintWriter(fileName);

            //iterate through rows and columns and prints out data
            for (int row = 0; row < mat.length; row++) {

                for (int col = 0; col < mat[row].length; col++) {

                    outputFile.print(mat[row][col] + " ");

                }//end col for

                //goes to next line
                outputFile.println("");

            }//end row for

        //close outputFile
        outputFile.close();

    }//end printMatrix

    //overloads the printMatrix method in case there is a double
    public static void printMatrix(double mat[][], String file) throws IOException{

        //open file for printing
        File fileName = new File(file);
        PrintWriter outputFile = new PrintWriter(fileName);

        //iterate through rows and columns and prints out data
        for (int row = 0; row < mat.length; row++) {

            for (int col = 0; col < mat[row].length; col++) {

                outputFile.printf("%.1f ", mat[row][col]);

            }//end col for

            //goes to next line
            outputFile.println("");

        }//end row for

        //close outputFile
        outputFile.close();

    }//end overloaded printMatrix method

    //passes in the starting value, the iteration value, then increments over columns then rows
    public static void fillArray(int[][] mat, int startVal, int incrmntVal){

        //iterate through columns
        for (int col = 0; col < mat[col].length; col++){

            //iterate through rows
            for (int row = 0; row < mat.length; row++){

                //assigns values to arrays
                mat[row][col] = startVal;
                startVal += incrmntVal;

            }//end for

        }//end for

    }//end fillArray method

}//end charris_p1
