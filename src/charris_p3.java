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

        //initialize constants for file names
        final String userDefInputName = "charris_p3_input.txt";//user defined input file
        final String P3_OUT = "charris_p3_output.txt";//output file for user defined input file
        final String CLASS_INPUT = "class_p3_input.txt";//class input file
        final String CLASS_OUTPUT = "class_p3_output.txt";//class output file

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
        File fileName = new File(userDefInputName);
        PrintWriter outputFile = new PrintWriter(fileName);

        boolean moreCalculations = true; // used to get more rows of input if the user would like

        while (moreCalculations) {
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
            while ((userChoice != 'y') && (userChoice != 'n') && (userChoice != 'Y') && (userChoice != 'N')) {

                System.out.print("Please enter y or n");
                userChoice = userInput.next().charAt(0);

            }//end while

            //if yes, add a row to input file
            if (userChoice == 'y') {
                outputFile.println("");

                //get new calcType
                System.out.printf("Please enter the calculation type from the following list:\n" +
                        "(AD, SU, SC, DO, PR): ");
                calcType = userInput.next().toUpperCase();

                //check if correct value was entered
                checkCalcType(calcType);
            }//end if

            //if no, set moreCalculations to false to escape while loop
            else if (userChoice == 'n') {
                moreCalculations = false;
            }

        }//end while

        //close file
        outputFile.close();

        //read in the user defined input file, parse for calc type, then proceed accordingly
        //open file for reading
        Scanner inputFile = new Scanner(fileName);

        //open output file for writing
        File userDefOut = new File(P3_OUT);
        PrintWriter outputFile2 = new PrintWriter(userDefOut);

        int calcCount = findRows(userDefInputName);//read how many rows there are in file
        double[] vectorA = new double[2];//stores first vector value from file
        double[] vectorB = new double[2];//stores second vector value from file
        double[] result = new double[2];//stores resultant vector

        //iterate through each row of file
        for (int i = 0; i < calcCount; i++) {

            //store necessary values from file
            calcType = inputFile.next();
            vectorA[0] = inputFile.nextDouble();
            vectorA[1] = inputFile.nextDouble();
            vectorB[0] = inputFile.nextDouble();
            vectorB[1] = inputFile.nextDouble();

            //switch calcType to handle each computation type appropriately
            switch (calcType) {

                case "AD":
                    //compute then print results to output file
                    result = add(vectorA, vectorB);
                    printOutput(result, outputFile2);
                    break;

                case "SU":
                    //compute then print results to output file
                    result = subtract(vectorA, vectorB);
                    printOutput(result, outputFile2);
                    break;

                case "SC":
                    //compute then print results to output file
                    result = scale(vectorA, vectorB);
                    printOutput(result, outputFile2);
                    break;

                case "DO":
                    //compute then print results to output file
                    outputFile2.print(dotProd(vectorA, vectorB));
                    break;

                case "PR":

                    //compute then print results to output file
                    result = orthProj(vectorA, vectorB);
                    printOutput(result, outputFile2);
                    break;


            }//end switch

            //goes to next line unless last calculation
            if (i < (calcCount - 1)) {
                outputFile2.println("");
            }

        }//end for loop

        //close files
        inputFile.close();
        outputFile2.close();


    }//end main

    //printInput method takes in the user choices from the test program and prints them to usable input file
    public static void printInput(String calcType, double[] vectorA, double[] vectorB, PrintWriter outputFile) throws IOException {

        //prints out the calc type and all vector elements
        outputFile.printf("%s %.1f %.1f %.1f %.1f",
                calcType, vectorA[0], vectorA[1], vectorB[0], vectorB[1]);

    }//end printInput

    //printOutput method takes in the calcType, result vector, and the file printwriter object then prints result to filename
    public static void printOutput(double[] result, PrintWriter outputFile) throws IOException {

        //prints out the calculation type then the resultant vector
        outputFile.printf("%.1f %.1f", result[0], result[1]);

    }//end printOutput

    //checkCalcType checks to see if the calcType is one of the possible computations
    public static void checkCalcType(String calcType) {

        //create scanner object
        Scanner userInput = new Scanner(System.in);

        boolean correctInput = false; // used to see if correct value was entered
        while (!correctInput) {

            //checks for correct entry
            switch (calcType) {
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
    public static double[] getVector() {

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

    //finds the amount of rows in the file passed in
    public static int findRows(String fileToRead) throws IOException {

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //initialize rows
        int rows = 0;

        //count the rows
        while (inputFile.hasNextLine()) {

            //iterate rows
            rows++;

            //goes to next line
            inputFile.nextLine();

        }//end while

        inputFile.close();

        return rows;

    }//end findRows

    /*
    Vector Operations Methods
     */

    //add method adds the passed in vectors
    public static double[] add(double[] vectorA, double[] vectorB) {

        //initialize resultant vector
        double[] result = new double[2];

        //add x-components then assign to result
        result[0] = vectorA[0] + vectorB[0];

        //add y-components then assign to result
        result[1] = vectorA[1] + vectorB[1];

        return result;

    }//end add

    //subtract method subtracts the passed in vectors
    public static double[] subtract(double[] vectorA, double[] vectorB) {

        //initialize resultant vector
        double[] result = new double[2];

        //add x-components then assign to result
        result[0] = vectorA[0] - vectorB[0];

        //add y-components then assign to result
        result[1] = vectorA[1] - vectorB[1];

        return result;

    }//end subtract

    //scale method scales the second vector by the magnitude of the first vector
    public static double[] scale(double[] vectorA, double[] vectorB) {

        //initialize the result vector
        double[] result = new double[2];

        //find the magnitude of the first array
        double magnitudeA = Math.sqrt((Math.pow(vectorA[0], 2)) + (Math.pow(vectorA[1], 2)));

        //scale vectorB by magnitude of vectorA
        result[0] = vectorB[0] * magnitudeA;
        result[1] = vectorB[1] * magnitudeA;

        return result;

    }//end scale

    //dotProd method computes the dot product of the passed in vectors
    public static double dotProd(double[] vectorA, double[] vectorB) {

        //multiplies each index of vectors by respective indices then adds them to compute dot product
        double result = 0;//stores the result of dot product operation

        for (int i = 0; i < vectorA.length; i++) {

            result += (vectorA[i] * vectorB[i]);

        }//end for

        return result;

    }//end dotProd

    //orthProj method computes the orthogonal projection of vectorA onto vectorB
    public static double[] orthProj(double[] v, double[] w) {

        //initialize resultant vector
        double[] u = new double[2];

        //find the length of v
        double lengthV = Math.sqrt((Math.pow(v[0], 2)) + (Math.pow(v[1], 2)));

        //compute orthogonal projection
        //equation: u = ((v * w) / (length of v)^2) * v
        u[0] = ((dotProd(v, w) / Math.pow(lengthV, 2)) * v[0]);
        u[1] = ((dotProd(v, w) / Math.pow(lengthV, 2)) * v[1]);

        return u;

    }//end orthProj

}//end charris_p3
