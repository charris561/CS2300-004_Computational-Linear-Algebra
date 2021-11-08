/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 004
Due date: 11/6
Assignment: PA 4 - Part A

Description:
This program calculates if a triangle planar facet will be front facing or back facing,
calculates the light intensity reflected off the triangular planar facet, then combines
the two to simulate shading.
*/

import java.util.*;
import java.io.*;

public class charris_pa04_partA {

    public static void main (String[] args) throws IOException {

        boolean userIsDone = false;

        //complete more computations until user is done
        while (!userIsDone) {

            //get input/output file name
            String inputFileName = getInputFile();
            String outputFileName = getOutputFile(inputFileName);

            //use fileName to complete necessary computations
            partAComputations(inputFileName, outputFileName);

            //see if user is done with computations
            System.out.print("Would you like to make more computations? ");
            char userChoice = getUserChoice();

            if (userChoice == 'n'){userIsDone = true;}

        }//end userIsDone

    }//end main

    //partAComputations() function opens the passed in fileName completes the computations and outputs to a file
    public static void partAComputations(String inputFileName, String outputFileName) throws IOException {

        System.out.println("\n*****Starting Part A Computations*****\n");

        //open file for reading, if file does not open, get a new filename
        boolean fileFound = false;
        boolean fileNotFound = true;
        File fileName;
        Scanner inputFile;

        while (!fileFound) {
            try {

                fileName = new File(inputFileName);
                inputFile = new Scanner(fileName);
                fileNotFound = false;

            } catch (Exception FileNotFoundException) {

                System.out.println("File was not found, please enter a new input file name.");
                inputFileName = getInputFile();
                outputFileName = getOutputFile(inputFileName);

            }//end try catch block

            if (!fileNotFound){fileFound = true;}

        }

        fileName = new File(inputFileName);
        inputFile = new Scanner(fileName);

        //assign first three values to eye location
        double[][] eyeLocation = new double[3][1];
        for (int r = 0; r < eyeLocation.length; r++){

            for (int c = 0; c < eyeLocation[0].length; c++){

                eyeLocation[r][c] = inputFile.nextDouble();

            }//end iterating columns

        }//end iterating rows

        //assign next 3 values to light direction
        double[][] lightDirection = new double[3][1];
        for (int r = 0; r < lightDirection.length; r++){

            for (int c = 0; c < lightDirection[0].length; c++){

                lightDirection[r][c] = inputFile.nextDouble();

            }//end iterating columns

        }//end iterating rows

        //ignore last 3 on first line
        for (int i = 0; i < 3; i++){inputFile.nextDouble();}

        //find how many triangles will be in file
        int numTriangles = findRows(inputFileName) - 1;

        //create triangles for each row after first
        ArrayList<P04_Triangle> triangles = new ArrayList<>();
        for (int i = 0; i < numTriangles; i++){

            //initialize points from input file
            double[][] p = new double[3][1];
            double[][] q = new double[3][1];
            double[][] r = new double[3][1];

            //fill from input file
            for (int j = 0; j < p.length; j++){

                for (int k = 0; k < p[0].length; k++){

                    p[j][k] = inputFile.nextDouble();

                }//end iterating columns

            }//end iterating rows
            for (int j = 0; j < q.length; j++){

                for (int k = 0; k < q[0].length; k++){

                    q[j][k] = inputFile.nextDouble();

                }//end iterating columns

            }//end iterating rows
            for (int j = 0; j < r.length; j++){

                for (int k = 0; k < r[0].length; k++){

                    r[j][k] = inputFile.nextDouble();

                }//end iterating columns

            }//end iterating rows

            //create triangle
            P04_Triangle triangle = new P04_Triangle(p, q, r);

            //add to triangles arraylist
            triangles.add(triangle);

        }//end getting triangles

        //define view vectors for each triangle and set normal vectors **done in class for triangle
        for (int i = 0; i < numTriangles; i++){
            triangles.get(i).setViewVector(eyeLocation);
        }

        //output computations to file
        printOutput(outputFileName, triangles, lightDirection);

        System.out.printf("Calculations completed successfully. See %s for results.\n\n", outputFileName);


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

        outputFileName += "A.txt";

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

    //prints output to file
    public static void printOutput (String fileToPrint, ArrayList<P04_Triangle> triangles, double[][] lightDirection) throws IOException {

        int numTriangles = triangles.size();

        //open file for printing
        File fileName = new File(fileToPrint);
        PrintWriter outputFile = new PrintWriter(fileName);

        //output culling for all triangles on one line
        for (int i = 0; i < numTriangles; i++){

            if (triangles.get(i).isCulling()){ outputFile.printf("%d ", 0);}
            else { outputFile.printf("%d ", 1);}

        }//end outputting culling

        //go to new line
        outputFile.println();

        //output the lighting intensity for each triangle
        for (int i = 0; i < numTriangles; i++){

            outputFile.printf("%.2f ", triangles.get(i).calcLightIntensity(lightDirection));

        }//end outputting light intensity

        //got to new line
        outputFile.println();

        //output culling and lighting combined approach
        for (int i = 0; i < numTriangles; i++){

            outputFile.printf("%s ", triangles.get(i).shade(lightDirection));

        }//end outputting shading

        //close output file
        outputFile.close();

    }//end print output

}//end charris_pa04_partA
