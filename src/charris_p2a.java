/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 9/13
Assignment: PA 1

Description:
This program will read two of the 2D Matrices created in part1,
add the two matrices, and either output the resultant matrix or
output an error
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class charris_p2a {

    public static void main(String[] args) throws IOException {

        //allocate memory for matrix
        int[][] matA = mallocMat("charris_p1_mat1.txt");

//        //read charris_p1_mat1.txt and fill into matrix
//        readArray("charris_p1_mat1.txt", matA);
//
//        //print array to output file
//        charris_p1.printMatrix(matA, "charris_p2a_out12.txt");

    }//end main


    /*
    Methods:
     */
    //addMatrix method will take in two matrices and add them
    public static void addMatrix(int[][] mat){
        //write code here
    }//end addMatrix

    //overloaded addMatrix method will handle doubles
    public static void addMatrix(double[][] mat){
        //write code here
    }//end overloaded addMatrix

    //readArray reads in the values from the matrix and returns them
    public static void readArray(String file, int[][] mat) throws IOException {

        //open file for writing
        File fileName = new File(file);
        Scanner inputFile = new Scanner(fileName);

        //import matrix into matrix passed into method
        //iterate through rows
        for (int row = 0; row < mat.length; row++) {

            //iterate through columns
            for (int col = 0; col < mat[0].length; col++) {

                //assigns values to arrays
                mat[row][col] = inputFile.nextInt();

            }//end for

        }//end for

        //close file
        inputFile.close();

    }//end readArray

    //overloads the readArray method to handle doubles
    public static void readArray(String file, double[][] mat) throws IOException{
        //write code here
    }//end readArray

    //mallocMat method allocates memory for matrix based on lines
    //and columns in matrix
    public static int[][] mallocMat(String fileToRead) throws FileNotFoundException {

        //get the amount of rows and columns in the file
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //read in the amount of rows and then the columns to
        //find how much memory is needed for matrix
        int rows = 0;
        int cols = 0;
        boolean endOfData = false;

//        while (!endOfData){
//
//            if (inputFile.next().charAt(0) != '\n'){
//                cols++;
//            }//end if
//            else if (inputFile.next().charAt(0) == '\n'){
//                rows++;
//            }//end if
//
//            //checks to see if end of data reached
//            else if (inputFile.next() == null){
//                endOfData = true;
//            }//eof reached
//
//        }//end while

        //close the file
        inputFile.close();

        //allocate memory and return it
        int mat[][] = new int[rows][cols];

        return mat;

    }//end mallocMat

}//end charris_p2a
