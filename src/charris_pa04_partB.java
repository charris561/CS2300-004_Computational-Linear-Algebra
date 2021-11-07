/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 004
Due date: 11/6
Assignment: PA 4 - Part B

Description:

*/

import java.util.*;
import java.io.*;

public class charris_pa04_partB {

    public static void main (String[] args) throws IOException {

        boolean userIsDone = false;

        //complete more computations until user is done
        while (!userIsDone) {

            //get input/output file name
            String inputFileName = getInputFile();
            String outputFileName = getOutputFile(inputFileName);

            //use fileName to complete necessary computations
            partBComputations(inputFileName, outputFileName);

            //see if user is done with computations
            System.out.print("Would you like to make more computations? ");
            char userChoice = getUserChoice();

            if (userChoice == 'n'){userIsDone = true;}

        }//end userIsDone

    }//end main

    //partAComputations() function opens the passed in fileName completes the computations and outputs to a file
    public static void partBComputations(String inputFileName, String outputFileName){

        System.out.println("Will complete computations here...");

    }//end partAComputations

    //getInputFile() gets the input file name from user
    public static String getInputFile(){

        String fileName = "";

        //prompt user for fileName
        System.out.print("Please enter the input file name: ");
        Scanner userInput = new Scanner(System.in);
        fileName = userInput.next();

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

        outputFileName += "A";

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

}//end charris_pa04_partB
