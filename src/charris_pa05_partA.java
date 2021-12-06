/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 004
Due date: 12/7
Assignment: PA 5 - Part A

Description:

*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_pa05_partA {

    public static void main (String[] args) throws IOException {

        //declare constants for filenames
        final String INPUT_FILE_NAME = "input_A.txt";
        final String OUTPUT_FILE_NAME = "charris_output_A.txt";
        System.out.println("\n*****Starting Part A Computations*****\n");

        //initialize matrix with n x n dimensions
        int n = findRows(INPUT_FILE_NAME);
        double[][] mat = new double[n][n];

        //determine if input is valid and fill matrix with values from file. If it is valid, proceed
        if (isValidInput(INPUT_FILE_NAME, mat)){



        }
        else {
            printInputErr(OUTPUT_FILE_NAME);
        }

    }//main

    //returns boolean based on if data from input file is valid
    public static boolean isValidInput(String inputFileName, double[][] mat) throws IOException{

        boolean isValid = true;

        //open file for reading
        File fileInput = new File(inputFileName);
        Scanner inputFile = new Scanner(fileInput);

        //fill n x n matrix with data
        for (int r = 0; r < mat.length; r++){
            for (int c = 0; c < mat[0].length; c++){

                mat[r][c] = inputFile.nextDouble();

            }
        }

        //see if any elements are negative
        for (int r = 0; r < mat.length; r++){
            for (int c = 0; c < mat[0].length; c++){

                if (mat[r][c] < 0){isValid = false;}

            }
        }

        //see if all columns add to one
        double colSum = 0;
        for (int c = 0; c < mat[0].length; c++){
            for (int r = 0; r < mat.length; r++) {

                //multiply sum by 1000 to capture accuracy to thousands place
                colSum += mat[r][c] * 1000;

            }

            if (colSum != 1000){ isValid = false;}

            //reset sum
            colSum = 0;
        }

        return isValid;

    }//isValidInput

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

    //prints invalid input to file
    public static void printInputErr(String fileToPrint) throws IOException{

        //open file for printing
        File fileName = new File(fileToPrint);
        PrintWriter outputFile = new PrintWriter(fileName);

        outputFile.println("Invalid Input");

        //close output file
        outputFile.close();

    }//printInputErr

}//charris_pa05_partB
