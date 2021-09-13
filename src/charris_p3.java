/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 9/13
Assignment: PA 1 - Part 2a

Description:
This program will read in a user defined input file, then a class
input file, then output the specified calculation from the following
options:
AD - addition
SU - subtraction
SC - scaling
DO - dot product
PR - orthogonal projection
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_p3 {

    public static void main(String[] args) throws IOException {

        //generate the user defined input file
        //prompt user for type of calculation
        System.out.printf("Please enter the calculation type from the following list:\n" +
                "(AD, SU, SC, DO, PR): ");

        //create a scanner object
        Scanner userInput = new Scanner(System.in);

        //get user choice
        String calcType = userInput.next().toUpperCase();

        //check if correct value was entered
        checkCalcType(calcType);

        //initialize the input file to print to
        //open file for printing
        File fileName = new File("charris_p3_input.txt");
        PrintWriter outputFile = new PrintWriter(fileName);

        boolean moreCalculations = true; // used to get more rows of input if the user would like

        while(moreCalculations) {
            //show user their selection and proceed accordingly
            switch (calcType) {

                case "AD":
                    System.out.println("You selected addition.");
                    printInput("AD", getVector(), getVector(), outputFile);
                    break;
                case "SU":
                    System.out.println("You selected subtraction.");
                    printInput("SU", getVector(), getVector(), outputFile);
                    break;
                case "SC":
                    System.out.println("You selected scaling.");
                    printInput("SC", getVector(), getVector(), outputFile);
                    break;
                case "DO":
                    System.out.println("You selected dot product.");
                    printInput("DO", getVector(), getVector(), outputFile);
                    break;
                case "PR":
                    System.out.println("You selected orthogonal projection");
                    printInput("PR", getVector(), getVector(), outputFile);
                    break;

            }//end switch

            //prompt user to see if they would like to make another calculation
            System.out.print("Would you like to make another calculation? (y/n): ");
            char userChoice = userInput.next().charAt(0);

            //validate user input
            while ((userChoice != 'y') && (userChoice != 'n') && (userChoice != 'Y') && (userChoice != 'N')){

                System.out.print("Please enter y or n");
                userChoice = userInput.next().charAt(0);

            }//end while

            //if yes, add a row to input file
            if (userChoice == 'y'){
                outputFile.println("");

                //get new calcType
                System.out.printf("Please enter the calculation type from the following list:\n" +
                        "(AD, SU, SC, DO, PR): ");
                calcType = userInput.next().toUpperCase();

                //check if correct value was entered
                checkCalcType(calcType);
            }//end if

            //if no, set moreCalculations to false to escape while loop
            else if (userChoice == 'n'){
                moreCalculations = false;
            }

        }//end while

        //close file
        outputFile.close();

        //read in the user defined input file, parse for calc type, then proceed accordingly

    }//end main

    //printInput method takes in the user choices from the test program and prints them to usable input file
    public static void printInput(String calcType, double[] vectorA, double[] vectorB, PrintWriter outputFile) throws IOException{

        //prints out the calc type and all vector elements
        outputFile.printf("%s %.1f %.1f %.1f %.1f",
                calcType, vectorA[0], vectorA[1], vectorB[0], vectorB[1]);

    }//end printInput

    //checkCalcType checks to see if the calcType is one of the possible computations
    public static void checkCalcType(String calcType){

        //create scanner object
        Scanner userInput = new Scanner(System.in);

        boolean correctInput = false; // used to see if correct value was entered
        while (!correctInput){

            //checks for correct entry
            switch (calcType){
                case "AD":
                case "SU":
                case "SC":
                case "DO":
                case "PR":
                    correctInput = true;
                    break;
            }//end switch

            //prompt user for correct entry
            if (!correctInput) {
                System.out.printf("Please enter a valid calculation type from the following list:\n" +
                        "(AD, SU, SC, DO, PR):\n");
                calcType = userInput.next().toUpperCase();
            }//end if

        }//end while

    }//end checkCalcType

    //gets vector inputs for calculations
    public static double[] getVector(){

        //create scanner object
        Scanner userInput = new Scanner(System.in);

        //prompt user for vector elements
//***note: see if you can validate user input after program completed
        System.out.print("Please enter first element of vector: ");
        double[] vector = new double[2];
        vector[0] = userInput.nextDouble();
        System.out.print("Please enter second element of vector: ");
        vector[1] = userInput.nextDouble();

        System.out.printf("Vector = [%.1f][%.1f]\n", vector[0], vector[1]);

        return vector;

    }//end getVector

}//end charris_p3
