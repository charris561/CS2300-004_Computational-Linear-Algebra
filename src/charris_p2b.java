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

    public static void main(String[] args) throws IOException {

        //create constants for input file names
        final String P1_MAT1 = "charris_p1_mat1.txt";
        final String P1_MAT2 = "charris_p1_mat2.txt";
        final String P1_MAT3 = "charris_p1_mat3.txt";
        final String P1_MAT4 = "charris_p1_mat4.txt";
        final String P1_MAT5 = "charris_p1_mat5.txt";

        //create constants for output file names
        //combos for mat 1
        final String P2B_OUT11 = "charris_p2b_out11.txt";
        final String P2B_OUT12 = "charris_p2b_out12.txt";
        final String P2B_OUT13 = "charris_p2b_out13.txt";
        final String P2B_OUT14 = "charris_p2b_out14.txt";
        final String P2B_OUT15 = "charris_p2b_out15.txt";
        //combos for mat2
        final String P2B_OUT21 = "charris_p2b_out21.txt";
        final String P2B_OUT22 = "charris_p2b_out22.txt";
        final String P2B_OUT23 = "charris_p2b_out23.txt";
        final String P2B_OUT24 = "charris_p2b_out24.txt";
        final String P2B_OUT25 = "charris_p2b_out25.txt";
        //combos for mat3
        final String P2B_OUT31 = "charris_p2b_out31.txt";
        final String P2B_OUT32 = "charris_p2b_out32.txt";
        final String P2B_OUT33 = "charris_p2b_out33.txt";
        final String P2B_OUT34 = "charris_p2b_out34.txt";
        final String P2B_OUT35 = "charris_p2b_out35.txt";
        //combos for mat4
        final String P2B_OUT41 = "charris_p2b_out41.txt";
        final String P2B_OUT42 = "charris_p2b_out42.txt";
        final String P2B_OUT43 = "charris_p2b_out43.txt";
        final String P2B_OUT44 = "charris_p2b_out44.txt";
        final String P2B_OUT45 = "charris_p2b_out45.txt";
        //combos for mat5
        final String P2B_OUT51 = "charris_p2b_out51.txt";
        final String P2B_OUT52 = "charris_p2b_out52.txt";
        final String P2B_OUT53 = "charris_p2b_out53.txt";
        final String P2B_OUT54 = "charris_p2b_out54.txt";
        final String P2B_OUT55 = "charris_p2b_out55.txt";


        //allocate memory for matrices from part 1
        double[][] mat1 = mallocMat(P1_MAT1);
        double[][] mat2 = mallocMat(P1_MAT2);
        double[][] mat3 = mallocMat(P1_MAT3);
        double[][] mat4 = mallocMat(P1_MAT4);
        double[][] mat5 = mallocMat(P1_MAT5);

        //fill matrices from files
        readMatrix(P1_MAT1, mat1);
        readMatrix(P1_MAT2, mat2);
        readMatrix(P1_MAT3, mat3);
        readMatrix(P1_MAT4, mat4);
        readMatrix(P1_MAT5, mat5);

        //calculate the addition of each file
        //combos for mat1
        calcMultiplication(mat1, mat1, P2B_OUT11);
        calcMultiplication(mat1, mat2, P2B_OUT12);
        calcMultiplication(mat1, mat3, P2B_OUT13);
        calcMultiplication(mat1, mat4, P2B_OUT14);
        calcMultiplication(mat1, mat5, P2B_OUT15);
        //combos for mat2
        calcMultiplication(mat2, mat1, P2B_OUT21);
        calcMultiplication(mat2, mat2, P2B_OUT22);
        calcMultiplication(mat2, mat3, P2B_OUT23);
        calcMultiplication(mat2, mat4, P2B_OUT24);
        calcMultiplication(mat2, mat5, P2B_OUT25);
        //combos for mat3
        calcMultiplication(mat3, mat1, P2B_OUT31);
        calcMultiplication(mat3, mat2, P2B_OUT32);
        calcMultiplication(mat3, mat3, P2B_OUT33);
        calcMultiplication(mat3, mat4, P2B_OUT34);
        calcMultiplication(mat3, mat5, P2B_OUT35);
        //combos for mat4
        calcMultiplication(mat4, mat1, P2B_OUT41);
        calcMultiplication(mat4, mat2, P2B_OUT42);
        calcMultiplication(mat4, mat3, P2B_OUT43);
        calcMultiplication(mat4, mat4, P2B_OUT44);
        calcMultiplication(mat4, mat5, P2B_OUT45);
        //combos for mat5
        calcMultiplication(mat5, mat1, P2B_OUT51);
        calcMultiplication(mat5, mat2, P2B_OUT52);
        calcMultiplication(mat5, mat3, P2B_OUT53);
        calcMultiplication(mat5, mat4, P2B_OUT54);
        calcMultiplication(mat5, mat5, P2B_OUT55);

    }//end main

    /*
    Methods
     */

    //calcAddition method calculates the matrix addition and prints the output to a file
    public static void calcMultiplication(double[][] matA, double[][]matB, String fileToPrint) throws IOException {

        //add matrices if possible
        if (isPossible(matA, matB)){

            //allocate memory for matrix via [rows in matA][cols in matB]
            double[][] matAB = multiplyMats(matA, matB);;
            printMatrix(matAB, fileToPrint);

        }

        else {
            printError(fileToPrint);
        }

    }//end calcMultiplication

    //addMatrices method takes two matrices and adds them or returns an error
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
        outputFile.println("Error: Multiplication is impossible");

        //close file for printing
        outputFile.close();

    }//end printError


}//end charris_p2b
