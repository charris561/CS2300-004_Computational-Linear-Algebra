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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_p2a {

    public static void main(String[] args) throws IOException {

        //create constants for input file names
        final String P1_MAT1 = "charris_p1_mat1.txt";
        final String P1_MAT2 = "charris_p1_mat2.txt";
        final String P1_MAT3 = "charris_p1_mat3.txt";
        final String P1_MAT4 = "charris_p1_mat4.txt";
        final String P1_MAT5 = "charris_p1_mat5.txt";

        //create constants for output file names
        //combos for mat 1
        final String P2A_OUT11 = "charris_p2a_out11.txt";
        final String P2A_OUT12 = "charris_p2a_out12.txt";
        final String P2A_OUT13 = "charris_p2a_out13.txt";
        final String P2A_OUT14 = "charris_p2a_out14.txt";
        final String P2A_OUT15 = "charris_p2a_out15.txt";
        //combos for mat2
        final String P2A_OUT21 = "charris_p2a_out21.txt";
        final String P2A_OUT22 = "charris_p2a_out22.txt";
        final String P2A_OUT23 = "charris_p2a_out23.txt";
        final String P2A_OUT24 = "charris_p2a_out24.txt";
        final String P2A_OUT25 = "charris_p2a_out25.txt";
        //combos for mat3
        final String P2A_OUT31 = "charris_p2a_out31.txt";
        final String P2A_OUT32 = "charris_p2a_out32.txt";
        final String P2A_OUT33 = "charris_p2a_out33.txt";
        final String P2A_OUT34 = "charris_p2a_out34.txt";
        final String P2A_OUT35 = "charris_p2a_out35.txt";
        //combos for mat4
        final String P2A_OUT41 = "charris_p2a_out41.txt";
        final String P2A_OUT42 = "charris_p2a_out42.txt";
        final String P2A_OUT43 = "charris_p2a_out43.txt";
        final String P2A_OUT44 = "charris_p2a_out44.txt";
        final String P2A_OUT45 = "charris_p2a_out45.txt";
        //combos for mat5
        final String P2A_OUT51 = "charris_p2a_out51.txt";
        final String P2A_OUT52 = "charris_p2a_out52.txt";
        final String P2A_OUT53 = "charris_p2a_out53.txt";
        final String P2A_OUT54 = "charris_p2a_out54.txt";
        final String P2A_OUT55 = "charris_p2a_out55.txt";


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
        calcAdditon(mat1, mat1, P2A_OUT11);
        calcAdditon(mat1, mat2, P2A_OUT12);
        calcAdditon(mat1, mat3, P2A_OUT13);
        calcAdditon(mat1, mat4, P2A_OUT14);
        calcAdditon(mat1, mat5, P2A_OUT15);
        //combos for mat2
        calcAdditon(mat2, mat1, P2A_OUT21);
        calcAdditon(mat2, mat2, P2A_OUT22);
        calcAdditon(mat2, mat3, P2A_OUT23);
        calcAdditon(mat2, mat4, P2A_OUT24);
        calcAdditon(mat2, mat5, P2A_OUT25);
        //combos for mat3
        calcAdditon(mat3, mat1, P2A_OUT31);
        calcAdditon(mat3, mat2, P2A_OUT32);
        calcAdditon(mat3, mat3, P2A_OUT33);
        calcAdditon(mat3, mat4, P2A_OUT34);
        calcAdditon(mat3, mat5, P2A_OUT35);
        //combos for mat4
        calcAdditon(mat4, mat1, P2A_OUT41);
        calcAdditon(mat4, mat2, P2A_OUT42);
        calcAdditon(mat4, mat3, P2A_OUT43);
        calcAdditon(mat4, mat4, P2A_OUT44);
        calcAdditon(mat4, mat5, P2A_OUT45);
        //combos for mat5
        calcAdditon(mat5, mat1, P2A_OUT51);
        calcAdditon(mat5, mat2, P2A_OUT52);
        calcAdditon(mat5, mat3, P2A_OUT53);
        calcAdditon(mat5, mat4, P2A_OUT54);
        calcAdditon(mat5, mat5, P2A_OUT55);

    }//end main


    /*
    Methods:
     */

    //calcAddition method calculates the matrix addition and prints the output to a file
    public static void calcAdditon(double[][] matA, double[][]matB, String fileToPrint) throws IOException {

        //add matrices if possible
        if (isPossible(matA, matB)){
            double[][] mat12 = new double[matA.length][matB[0].length];
            mat12 = addMatrices(matA, matB);
            printMatrix(mat12, fileToPrint);
        }

        else {
            printError(fileToPrint);
        }

    }//end calcAddition

    //isPossible method sees if matrix addition is possible
    public static boolean isPossible(double[][] matA, double[][] matB){

        if ((matA.length == matB.length) && (matA[0].length == matB[0].length)){

            return true;

        }//end if

        else {
            return false;
        }

    }//end isPossible

    //addMatrices method takes two matrices and adds them or returns an error
    public static double[][] addMatrices(double[][] matA, double[][] matB){

        double[][] resultMat = new double[matA.length][matA[0].length];

        //iterate through the rows, then the columns adding each index
        for (int row = 0; row < matA.length; row++) {

            //iterate through columns
            for (int col = 0; col < matA[0].length; col++) {

                resultMat[row][col] = (matA[row][col] + matB[row][col]);

            }//end col for

        }//end row for

        return resultMat;

    }//end addMatrices

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

}//end charris_p2a
