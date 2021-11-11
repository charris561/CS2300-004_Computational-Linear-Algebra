/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 004
Due date: 11/6
Assignment: PA 4 - Part C

Description:

*/

import java.lang.management.PlatformLoggingMXBean;
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
            String outputFileName2 = getOutputFile2(inputFileName);

            //use fileName to complete necessary computations
            partCComputations(inputFileName, outputFileName, outputFileName2);

            //see if user is done with computations
            System.out.print("Would you like to make more computations? ");
            char userChoice = getUserChoice();

            if (userChoice == 'n'){userIsDone = true;}

        }//end userIsDone

    }//end main

    //partAComputations() function opens the passed in fileName completes the computations and outputs to a file
    public static void partCComputations(String inputFileName, String outputFileName, String outputFileName2) throws IOException{

        System.out.println("\n*****Starting Part C Computations*****\n");

        /*
        Part 1
         */
        //create planes for each row of input
        ArrayList<P04_Plane> planes = fscanPlanes(inputFileName);

        //find the distance of each point x to plane
        ArrayList<Double> distances = findDistances(planes);

        //print distances to first output file
        printDists(distances, outputFileName);

        System.out.println("Part C - Sub part 1 calculations completed successfully. Please see this file for results: " + outputFileName);

        /*
        Part 2
         */
        //create line from first line in file
        P04_Line line = fgetLine(inputFileName);




    }//end partAComputations

    //gets the line from the first line in input
    public static P04_Line fgetLine(String inputFileName) throws IOException{

        //open file for reading
        File fileInput = new File(inputFileName);
        Scanner inputFile = new Scanner(fileInput);

        //get 2 points from first 6 numbers in input
        double[][] point1 = new double[3][1];
        double[][] point2 = new double[3][1];

        for (int r = 0; r < point1.length; r++){
            for (int c = 0; c < point1[0].length; c++){
                point1[r][c] = inputFile.nextDouble();
            }
        }
        for (int r = 0; r < point2.length; r++){
            for (int c = 0; c < point2[0].length; c++){
                point2[r][c] = inputFile.nextDouble();
            }
        }

        return new P04_Line(point1, point2);

    }//end fgetLine

    //prints distances to first output file
    public static void printDists(ArrayList<Double> distances, String fileToPrint) throws IOException{

        //open file for printing
        File fileName = new File(fileToPrint);
        PrintWriter outputFile = new PrintWriter(fileName);

        //iterate through arraylist printing distances
        int numPlanes = distances.size();
        for (int i = 0; i < numPlanes; i++){
            outputFile.printf("Distance %d = %.2f\n", i + 1, distances.get(i));
        }

        //close output file
        outputFile.close();

    }//end printDists

    //finds the distances of point x to plane
    public static ArrayList<Double> findDistances(ArrayList<P04_Plane> planes){

        ArrayList<Double> distances = new ArrayList<>();

        //iterate through planes and find distances to lines
        int numPlanes = planes.size();
        for (int i = 0; i < numPlanes; i++){
            distances.add(planes.get(i).calcDistXFromPlane());
        }

        return distances;

    }//end findDistances

    //reads point q, vector n, and point x from file
    public static ArrayList<P04_Plane> fscanPlanes(String inputFileName)throws IOException{

        //initialize necessary values
        double[][] q = new double[3][1]; //stores point on plane q
        double[][] n = new double[3][1]; //stores vector normal to plane n
        double[][] x = new double[3][1]; //stores point on plane x
        ArrayList<P04_Plane> planes = new ArrayList<>();

        //open file for reading
        File fileInput = new File(inputFileName);
        Scanner inputFile = new Scanner(fileInput);

        //create planes for each row in input
        int numPlanes = charris_pa04_partA.findRows(inputFileName);
        for (int i = 0; i < numPlanes; i++) {

            //fill information from file into arrays
            for (int r = 0; r < q.length; r++) {
                for (int c = 0; c < q[0].length; c++) {
                    q[r][c] = inputFile.nextDouble();
                }
            }
            for (int r = 0; r < n.length; r++) {
                for (int c = 0; c < n[0].length; c++) {
                    n[r][c] = inputFile.nextDouble();
                }
            }
            for (int r = 0; r < x.length; r++) {
                for (int c = 0; c < x[0].length; c++) {
                    x[r][c] = inputFile.nextDouble();
                }
            }

            P04_Plane plane = new P04_Plane(q, n, x);

            //store plane in array list
            planes.add(plane);

            //reset q, n, and x
            q = new double[3][1];
            n = new double[3][1];
            x = new double[3][1];

        }


        //close input file
        inputFile.close();

        return planes;

    }//end fscanPlanes

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

        outputFileName += "C1.txt";

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

        outputFileName += "C2.txt";

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
