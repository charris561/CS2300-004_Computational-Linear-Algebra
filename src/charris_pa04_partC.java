/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 004
Due date: 11/6
Assignment: PA 4 - Part C

Description:

*/

import java.util.*;
import java.io.*;

public class charris_pa04_partC {

    public static void main (String[] args) throws IOException {

        boolean userIsDone = false;

        //complete more computations until user is done
        while (!userIsDone) {

            //get input/output file name
            String inputFileName = getInputFile();
            String outputFileName = getOutputFile(inputFileName);

            //use fileName to complete necessary computations
            partCComputations(inputFileName, outputFileName);

            //see if user is done with computations
            System.out.print("Would you like to make more computations? ");
            char userChoice = getUserChoice();

            if (userChoice == 'n'){userIsDone = true;}

        }//end userIsDone

    }//end main

    //partAComputations() function opens the passed in fileName completes the computations and outputs to a file
    public static void partCComputations(String inputFileName, String outputFileName) throws IOException{

        System.out.println("\n*****Starting Part C Computations*****\n");

        //read point q on plane, vector n normal to plane, and point x from file
        File fileInput = new File(inputFileName);
        Scanner inputFile = new Scanner(fileInput);

        double[][] pointOnPlane_Q = new double[3][1];
        double[][] normalVector_N = new double[3][1];
        double[][] point_X = new double[3][1];

        

    }//end partAComputations

    //getInputFile() gets the input file name from user
    public static String getInputFile(){

        String fileName = "";

        //prompt user for fileName
        System.out.print("Please enter the input file name: ");
        Scanner userInput = new Scanner(System.in);
        fileName = userInput.next();

        //see if file can be opened successfully
        boolean fileFound = false;
        boolean fileNotFound = true;
        File inputFile;
        Scanner inputFileScanner;

        while (!fileFound) {
            try {

                inputFile = new File(fileName);
                inputFileScanner = new Scanner(inputFile);
                fileNotFound = false;

            } catch (Exception FileNotFoundException) {

                System.out.print("File was not found, please enter a new input file name: ");
                fileName = userInput.next();

            }//end try catch block

            if (!fileNotFound){fileFound = true;}

        }

        return fileName;

    }//end getInputFile()

    //getOutputFile() gets an output file name based on the input file name passed in
    public static String getOutputFile(String inputFileName){

        String outputFileName = "charris_output_";
        char inputNum = '0'; //stores input number based on input file
        boolean noNum = false; //flag to see if there were any numbers in input file name
        boolean numFound = false; //flag to see if num found

        //iterate through characters in inputFileName and see if a number was inputted
        for (int i = 0; (i < inputFileName.length()) && !numFound; i++){

            //if number
            if (Character.isDigit(inputFileName.charAt(i))){
                inputNum = inputFileName.charAt(i);
                numFound = true;
                noNum = false;
            }
            else {
                noNum = true;
            }

        }

        if (!noNum){
            outputFileName += inputNum;
        }

        outputFileName += "C.txt";

        return outputFileName;

    }//end getOutputFile

    //get user choice returns a y or n for the user choice
    public static char getUserChoice(){

        char userChoice = '\0';

        //get choice from user
        do{
            //prompt user for y or n
            System.out.print("Please enter (y)es or (n)o: ");
            Scanner userInput = new Scanner(System.in);

            //get user input and make lower case
            userChoice = Character.toLowerCase(userInput.next().charAt(0));

        } while (userChoice != 'y' && userChoice != 'n');

        return userChoice;

    }//end getUserChoice()

}//end charris_pa04_partC
