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
import java.util.Scanner;

public class charris_p2a {

    public static void main(String[] args) throws IOException {

        //allocate memory for matrices
        double[][] matA = mallocMat("charris_p1_mat1.txt");
        double[][] matB = mallocMat("charris_p1_mat2.txt");
        double[][] matC = mallocMat("charris_p1_mat3.txt");
        double[][] matD = mallocMat("charris_p1_mat4.txt");
        double[][] matF = mallocMat("charris_p1_mat5.txt");

        System.out.println(isPossible(matA, matB));

    }//end main


    /*
    Methods:
     */

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
//    public static double[][] addMatrices(double[][] matA, double[][] matB){
//
//    }//end addMatrices

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

}//end charris_p2a
