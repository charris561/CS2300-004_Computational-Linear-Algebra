/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 9/13
Assignment: PA 1 - Part 2b

Description:
This program will take 2 of the matrices defined in part 1 and
output either the result of the matrix multiplication of the
two matrices, or output an error
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_p2b {

    public static void main(String[] args) {

        double[][] testMatA = new double[1][3];
        double[][] testMatB = new double[3][2];

    }//end main

    /*
    Methods
     */

    //calcAddition method calculates the matrix addition and prints the output to a file
    public static void calcMultiplication(double[][] matA, double[][]matB, String fileToPrint) throws IOException {

        //add matrices if possible
        if (isPossible(matA, matB)){

            //allocate memory for matrix via [rows in matA][cols in matB]
            double[][] mat12 = new double[matA.length][matB[0].length];
            mat12 = multiplyMats(matA, matB);
            printMatrix(mat12, fileToPrint);

        }

        else {
            printError(fileToPrint);
        }

    }//end calcMultiplication

    //addMatrices method takes two matrices and adds them or returns an error
    public static double[][] multiplyMats(double[][] matA, double[][] matB){

        double[][] resultMat = new double[matA.length][matA[0].length];

        //iterate through the rows, then the columns adding each index
        for (int row = 0; row < matA.length; row++) {

            //iterate through columns
            for (int col = 0; col < matA[0].length; col++) {

                //write code for multiplication

            }//end col for

        }//end row for

        return resultMat;

    }//end multiplyMats

    //isPossible method sees if matrix multiplication is possible
    public static boolean isPossible(double[][] matA, double[][] matB){

        //if columns in matA equal rows in matB
        if (matA[0].length == matB.length){

            return true;

        }//end if

        else {
            return false;
        }

    }//end isPossible

    //mallocMat method allocates memory for matrix based on lines and columns in input file
    public static double[][] mallocMat(String fileToRead) throws IOException {

        //initialize rows and cols
        int rows = 0;
        int cols = 0;

        //assign rows and cols via findCols and findRows method
        rows = findRows(fileToRead);
        cols = findCols(fileToRead);

        System.out.printf("The memory allocated is = mat[%d][%d]\n", rows, cols);

        //allocate memory and return it
        double mat[][] = new double[rows][cols];

        return mat;

    }//end mallocMat

    //readMatrix method reads the matrices from files and fills the passed in arrays with the values
    public static double[][] readMatrix(String fileToRead, double[][] mat) throws IOException{

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //fill matrix
        for (int row = 0; row < mat.length; row++){

            for (int col = 0; col < mat[0].length; col++){

                mat[row][col] = inputFile.nextDouble();

            }//end col for

        }//end row for

        //close file
        inputFile.close();

        return mat;

    }//end readMatrix

    //finds the amount of rows in the file passed in
    public static int findRows(String fileToRead) throws IOException{

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //initialize rows
        int rows = 0;

        //count the rows
        while (inputFile.hasNextLine()){

            //iterate rows
            rows++;

            //goes to next line
            inputFile.nextLine();

        }//end while

        inputFile.close();

        return rows;

    }//end findRows

    //finds the amount of columns in the file passed in
    public static int findCols(String fileToRead) throws IOException{

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //initialize cols
        int cols = 0;

        //counts total numbers in file
        while (inputFile.hasNextDouble()){

            //goes to next double
            inputFile.nextDouble();

            //iterate cols
            cols++;

        }//end while

        //divide cols by rows
        cols = cols/ findRows(fileToRead);

        inputFile.close();

        return cols;

    }//end findCols

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

    //printError method prints an error if addition is impossible
    public static void printError(String file) throws IOException {

        //open file for printing
        File fileName = new File(file);
        PrintWriter outputFile = new PrintWriter(fileName);

        //print error to file
        outputFile.println("Error: Addition is impossible");

        //close file for printing
        outputFile.close();

    }//end printError


}//end charris_p2b
