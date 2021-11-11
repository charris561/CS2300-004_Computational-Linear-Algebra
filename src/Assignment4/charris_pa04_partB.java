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
            String outputFileName1 = getOutputFile(inputFileName);
            String outputFileName2 = getOutputFile2(inputFileName);

            //use fileName to complete necessary computations
            partBComputations(inputFileName, outputFileName1, outputFileName2);

            //see if user is done with computations
            System.out.print("Would you like to make more computations? ");
            char userChoice = getUserChoice();

            if (userChoice == 'n'){userIsDone = true;}

        }//end userIsDone

    }//end main

    //partAComputations() function opens the passed in fileName completes the computations and outputs to a file
    public static void partBComputations(String inputFileName, String outputFileName, String outputFileName2) throws IOException {

        System.out.println("\n*****Starting Part B Computations*****\n");

        //open file for reading
        File fileInput = new File(inputFileName);
        Scanner inputFile = new Scanner(fileInput);

        //get point q on plane, vector n - normal to plane, and vector v - projection direction
        double [][] pointOnPlane_Q = new double[3][1];
        double [][] normalVector_N = new double[3][1];
        double [][] projectionDirection_V = new double[3][1];

        for (int r = 0; r < pointOnPlane_Q.length; r++){
            for (int c = 0; c < pointOnPlane_Q[0].length; c++){
                pointOnPlane_Q[r][c] = inputFile.nextDouble();
            }
        }
        for (int r = 0; r < normalVector_N.length; r++){
            for (int c = 0; c < normalVector_N[0].length; c++){
                normalVector_N[r][c] = inputFile.nextDouble();
            }
        }
        for (int r = 0; r < projectionDirection_V.length; r++){
            for (int c = 0; c < projectionDirection_V[0].length; c++){
                projectionDirection_V[r][c] = inputFile.nextDouble();
            }
        }

        //define the points x as all other 3 digit combinations
        ArrayList<double[][]> points = new ArrayList<>();
        while (inputFile.hasNextDouble()){
            double[][] points_X = new double[3][1];

            for (int r = 0; r < points_X.length; r++){
                for (int c = 0; c < points_X[0].length; c++){
                    points_X[r][c] = inputFile.nextDouble();
                }
            }

            points.add(points_X);
        }//end getting points

        //parallel projection
        ArrayList<double[][]> parallelProjections = new ArrayList<>();
        int numPoints = points.size();
        for (int i = 0; i < numPoints; i++){
            double[][] parallelProjection = computeParallelProjection(pointOnPlane_Q, normalVector_N, projectionDirection_V, points.get(i));
            parallelProjections.add(parallelProjection);
        }

        //perspective projection
        ArrayList<double[][]> perspectiveProjections = new ArrayList<>();
        for (int i = 0; i < numPoints; i++){
            double[][] perspectiveProjection = computePerspectiveProjection(pointOnPlane_Q, normalVector_N, points.get(i));
            perspectiveProjections.add(perspectiveProjection);
        }

        //print to output file
        printOutput(outputFileName, parallelProjections);
        printOutput(outputFileName2, perspectiveProjections);

        System.out.printf("Calculations completed successfully. See %s and %s for results.\n\n", outputFileName, outputFileName2);

    }//end partAComputations

    //computes the parallel projection points with passed in points and q, n, and v
    public static double[][] computeParallelProjection(double[][] pointOnPlane_Q, double[][] normalVector_N, double[][] projectionDirection_V, double[][] point_x){

        double[][] x_prime = new double[3][1];

        //eq. = x + [([q - x] * n) / v * n] v

        double[][] qSubX = subPointsOrVectors(pointOnPlane_Q, point_x);

        for (int r = 0; r < x_prime.length; r++){
            for (int c = 0; c < x_prime[0].length; c++){
                x_prime[r][c] = point_x[r][c] + (calcDotProd(qSubX, normalVector_N) / calcDotProd(projectionDirection_V, normalVector_N)) * projectionDirection_V[r][c];
            }
        }

        return x_prime;

    }//end computeParallelProjection

    //computes the perspective projection points with passed in q, n, and point x
    public static double[][] computePerspectiveProjection(double[][] pointOnPlane_Q, double[][] normalVector_N, double[][] point_X){

        double[][] projection = new double[3][1];

        //eq. = [q * n / x * n] x

        for (int r = 0; r < projection.length; r++){
            for (int c = 0; c < projection[0].length; c++){
                projection[r][c] = (calcDotProd(pointOnPlane_Q, normalVector_N) / calcDotProd(point_X, normalVector_N)) * point_X[r][c];
            }
        }

        return projection;

    }//end computePerspectiveProjection

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

        outputFileName += "B1.txt";

        return outputFileName;

    }//end getOutputFile

    //getOutputFile2() gets an output file name based on the input file name passed in
    public static String getOutputFile2(String inputFileName){

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

        outputFileName += "B2.txt";

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

    //calculate dot product of v and w
    public static double calcDotProd(double[][] v, double[][] w){

        double dotProd = 0;

        for (int r = 0; r < v.length; r++){

            for (int c = 0; c < v[0].length; c++){

                dotProd += v[r][c] * w[r][c];

            }//end iterating columns

        }//end iterating rows

        return dotProd;

    }//end calcCrossProd

    //subtracts to vectors/points
    public static double[][] subPointsOrVectors(double[][] point1, double[][] point2){

        double[][] result = new double[3][1];

        for (int r = 0; r < result.length; r++){

            for (int c = 0; c < result[0].length; c++){

                result[r][c] = point1[r][c] - point2[r][c];

            }//end iterating columns

        }//end iterating rows

        return result;

    }//end subPointsOrVectors

    //prints output to file
    public static void printOutput (String fileToPrint, ArrayList<double[][]> projection) throws IOException {

        int numPoints = projection.size();

        //open file for printing
        File fileName = new File(fileToPrint);
        PrintWriter outputFile = new PrintWriter(fileName);

        //print projections to file
        for (int i = 0; i < numPoints; i++){

            //iterate through point
            double[][] point = projection.get(i);
            for (int r = 0; r < point.length; r++){
                for (int c = 0; c < point[0].length; c++){
                    outputFile.printf("%.2f ", point[r][c]);
                }
            }

            //go to next line for each point
            outputFile.println();

        }//end iterating through arraylist

        //close output file
        outputFile.close();

    }//end print output

}//end charris_pa04_partB
