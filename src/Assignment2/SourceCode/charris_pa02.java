package Assignment2.SourceCode;

import java.io.*;
import java.util.*;

public class charris_pa02 {

    //initialize constants
    final static char PLAYER_1 = 'x';
    final static char PLAYER_2 = 'o';
    final static String FILE_INPUT_1 = "pa2_input_1.txt";
    final static String FILE_INPUT_2 = "pa2_input_2.txt";
    final static String FILE_INPUT_3 = "pa2_input_3.txt";
    final static String FILE_OUTPUT_1 = "pa2_output_1.txt";
    final static String FILE_OUTPUT_2 = "pa2_output_2.txt";
    final static String FILE_OUTPUT_3 = "pa2_output_3.txt";
    final static String USER_DEF_INPUTS = "charris_input_1.txt";

    public static void main(String[] args) throws IOException {

        //initialize variables
        boolean continuePlaying = true;
        boolean validChoice = false;
        boolean fileInput = false;
        Scanner userInput = new Scanner(System.in);

        //introduce game
        System.out.println("CS2300 - Linear Domination Game");
        System.out.println("-------------------------------------------");

        do {

            int userChoice = 0;

            //prompt user to see if they would like to use file input or manual input
            do {

                try {
                    System.out.print("Would you like to use file(1) or keyboard(2) input for this game? ");
                    userChoice = userInput.nextInt();
                } catch (InputMismatchException e) { }

                if (userChoice == 1 || userChoice == 2){
                    validChoice = true;
                }
                else{
                    System.out.println("Invalid choice.");
                }

            }while (!validChoice);

            //call play game to simulate a game being played
            if (userChoice == 1){

                playGame(true, FILE_INPUT_1, FILE_OUTPUT_1, null);
                playGame(true, FILE_INPUT_2, FILE_OUTPUT_2, null);
                playGame(true, FILE_INPUT_3, FILE_OUTPUT_3, null);

            } else {
                //call play game to simulate a game being played
                playGame(false, null, null, USER_DEF_INPUTS);
            }

            //prompt the user to see if they would like to play another game
            do {
                validChoice = false;

                try {
                    System.out.print("Would you like to play another game? (y or n): ");
                    userChoice = userInput.next().charAt(0);
                } catch (InputMismatchException e) { }

                if (userChoice == 'y' || userChoice == 'Y'){
                    validChoice = true;
                }

                else if (userChoice == 'n' || userChoice == 'N'){
                    validChoice = true;
                    continuePlaying = false;
                }

                else{
                    System.out.println("Invalid choice.");
                }

            }while (!validChoice);

        } while(continuePlaying);

    }//end main

    //method playGame is the control method that will call the other methods to simulate a game
    public static void playGame(boolean fileInput, String inputFileName, String outputFileName, String userDefInputs) throws IOException {

        //initialize variables
        int n = 0;
        int k = 0;
        int j = 0;
        int lineCtr = 0;
        int numInvalidMoves = 0;
        int numPoints = 0;
        int numLines = 0;
        boolean gameOver = false;
        boolean validPlay = false;
        char [][] gameBoard;
        char currentPlayer = PLAYER_1;
        ArrayList<Line> allLines = new ArrayList<>();
        Line[] pastKTurnsLines;
        Line currentLine = null;


        //if fileInput, get n and k from file
        if (fileInput){

            //open file for reading
            File fileName = new File(inputFileName);
            Scanner inputFile = new Scanner(fileName);

            //read data from file
            n = inputFile.nextInt();
            k = inputFile.nextInt();

            //iterate lineCtr
            lineCtr++;

            //initialize number of lines
            numLines = (findRows(inputFileName) - 1);

        } else{

            //prompt user for n and k
            Scanner userInput = new Scanner(System.in);

            System.out.println("Please enter how large you would like the board to be in N x N cells: ");
            n = userInput.nextInt();
            System.out.println("What would you like to set K turns to: ");
            k = userInput.nextInt();

        }

        //create game board using defBoard(n) method
        gameBoard = defBoard(n);

        //allocate memory for pastKTurns array
        pastKTurnsLines = new Line[k];

        //while game over criteria not met
        while (!gameOver){

            //get input for plays by calling getPlay method
            if(fileInput) {

                //open file for reading
                File fileName = new File(inputFileName);
                Scanner inputFile = new Scanner(fileName);

                //checks to see if there is another point
                if(lineCtr <= numLines) {
                    currentLine = getPlay(true, inputFileName, lineCtr, n);
                    lineCtr++;
                }

            } else {
                currentLine = getPlay(false, inputFileName, lineCtr, n);
                lineCtr++;
                numLines++;
            }

            //test validity of play
            validPlay = isValid(pastKTurnsLines,currentLine, allLines);

            //store currentLine in all lines and in past k turns if k is not 0
            allLines.add(currentLine);
            if (k > 0) {
                pastKTurnsLines[j] = currentLine;
                j++;
            }

            //checks to see if last element in array has been reached, then overwrites the oldest element
            if (j == k){
                j = 0;
            }

            //if move was valid, flip cells
            if (validPlay){
                flipCells(gameBoard, currentPlayer, currentLine);
            }


            //if not from input file, display current score to console and inputs to file, else to file output
            if (!fileInput){

                //display to console
                displayScore(gameBoard);

                //print inputs to input file
                printInputs(allLines, n, k, numLines, userDefInputs);

            } else{

                //print game board and winner to file
                printFileResults(gameBoard, outputFileName);

            }//end if

            //determine if the end of game criteria have been satisfied
            if (!validPlay){
                numInvalidMoves++;
            } else {
                numInvalidMoves = 0;
            }

            //checks to see if 2 invalid moves were made in a row
            if (numInvalidMoves == 2){
                gameOver = true;
            }

            //checks to see if there are any free cells
            for (int i = 0; i < pastKTurnsLines.length; i++){

                if (pastKTurnsLines[i] != null){
                    numPoints += 2;
                }

            }//end for

            //compares number of points in lines to how many are possible for past k turns
            if (numPoints >= Math.pow(n, 2)){
                gameOver = true;
            }

            //change players
            if (currentPlayer == PLAYER_1){
                currentPlayer = PLAYER_2;
            } else{
                currentPlayer = PLAYER_1;
            }

        }//end gameOver While

    }//end playGame

    //method defBoard allocates the appropriate size of memory for the game board
    public static char [][] defBoard(int n){

        //create matrix of size n x n
        char[][] gameBoard = new char[n][n];

        //initialize all elements to default
        for (int row = 0; row < gameBoard.length; row++){

            for (int col = 0; col < gameBoard[0].length; col++){

                gameBoard[row][col] = '.';

            }//end col for

        }//end row for

        return gameBoard;

    }//end defBoard

    //method getPlay gets either the user's input for the plays or file input
    public static Line getPlay(boolean fileInput, String inputFileName, int lineCtr, int n) throws IOException{

        //initialize variables
        int[] point1 = new int[2];
        int[] point2 = new int[2];
        Line currentLine = null;
        boolean validChoice = false;

        //get input from file
        if (fileInput){

            //open file for reading
            File fileName = new File(inputFileName);
            Scanner inputFile = new Scanner(fileName);

            //go to next play line
            for (int i = 0; i < lineCtr; i++){
                inputFile.nextLine();
            }

            //read in points from file
            point1[0] = inputFile.nextInt();
            point1[1] = inputFile.nextInt();
            point2[0] = inputFile.nextInt();
            point2[1] = inputFile.nextInt();

        } else{

            do {

                //prompt user for points
                Scanner userInput = new Scanner(System.in);

                try {
                    System.out.print("Please enter x1 for point 1: ");
                    point1[0] = userInput.nextInt();
                } catch (InputMismatchException e) { }

                if (point1[0] > 0 && point1[0] <= n){
                    validChoice = true;
                }
                else{
                    System.out.println("Invalid input.");
                }

            }while (!validChoice);
            validChoice = false;
            do {

                //prompt user for points
                Scanner userInput = new Scanner(System.in);

                try {
                    System.out.print("Please enter x2 for point 1: ");
                    point1[1] = userInput.nextInt();
                } catch (InputMismatchException e) { }

                if (point1[1] > 0 && point1[1] <= n){
                    validChoice = true;
                }
                else{
                    System.out.println("Invalid input.");
                }

            }while (!validChoice);
            validChoice = false;
            do {

                //prompt user for points
                Scanner userInput = new Scanner(System.in);

                try {
                    System.out.print("Please enter x1 for point 2: ");
                    point2[0] = userInput.nextInt();
                } catch (InputMismatchException e) { }

                if (point2[0] > 0 && point2[0] <= n){
                    validChoice = true;
                }
                else{
                    System.out.println("Invalid input.");
                }

            }while (!validChoice);
            validChoice = false;
            do {

                //prompt user for points
                Scanner userInput = new Scanner(System.in);

                try {
                    System.out.print("Please enter x1 for point 1: ");
                    point2[1] = userInput.nextInt();
                } catch (InputMismatchException e) { }

                if (point2[1] > 0 && point2[1] <= n){
                    validChoice = true;
                }
                else{
                    System.out.println("Invalid input.");
                }

            }while (!validChoice);

        }

        //create line object
        currentLine = new Line(point1, point2);

        return currentLine;

    }//end getPlay

    //method isValid will return a boolean value whether the play entered is valid or not
    public static boolean isValid(Line[] paskKTurnsLines, Line currentLine, ArrayList<Line> allLines){

        //initialize variables
        boolean valid = true;
        int numLines = allLines.size();

        //check if current line has midpoint, start, or end point of past k turn lines
        if (paskKTurnsLines.length > 0) {
            for (int i = 0; i < paskKTurnsLines.length; i++) {

                if (paskKTurnsLines[i] != null) {

                    if (currentLine.getStartingPoint() == paskKTurnsLines[i].getStartingPoint()) {
                        valid = false;
                    } else if (currentLine.getEndingPoint() == paskKTurnsLines[i].getEndingPoint()) {
                        valid = false;
                    } else if (currentLine.getMidpoint() == paskKTurnsLines[i].getMidpoint()) {
                        valid = false;
                    }

                }//end if

            }//end for

        }//end if

        //checks to see if line is parallel to any line played in the game
        for (int i = 0; i < numLines; i++){

            if (currentLine.getSlope() == allLines.get(i).getSlope()){
                valid = false;
            }

        }//end for

        return valid;

    }//end isValid

    //method displayScore() will take in the game board and display it to the console
    public static void displayScore(char[][] board){

        //initialize variables
        int xCounter = 0;
        int oCounter = 0;

        //iterate through indices and calculate each players score and print index
        for (int row = 0; row < board.length; row++){

            for (int col = 0; col < board[0].length; col++){

                //if last column
                if (col == (board[0].length - 1)){
                    System.out.println(board[row][col]);
                } else {
                    System.out.print(board[row][col]);
                }

                if (board[row][col] == 'x'){
                    xCounter++;
                }
                else if (board[row][col] == 'o'){
                    oCounter++;
                }

            }//end col for

        }//end row for

        //see who is winning!
        if (xCounter > oCounter){
            System.out.printf("Player 1 is winning!\nNumber of X's: %d\tNumber of O's: %d\n\n",
                    xCounter, oCounter);
        }
        else if (xCounter < oCounter){
            System.out.printf("Player 2 is winning!\nNumber of X's: %d\tNumber of O's: %d\n\n",
                    xCounter, oCounter);
        }
        else if (xCounter == oCounter){
            System.out.printf("Player 1 and Player 2 are tied!\nNumber of X's: %d\tNumber of O's: %d\n\n",
                    xCounter, oCounter);
        }

    }//end displayScore

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

    //printFileResults method prints the results of the game
    public static void printFileResults(char gameBoard[][], String file) throws IOException {

        //open file for printing
        File fileName = new File(file);
        PrintWriter outputFile = new PrintWriter(fileName);

        //iterate through rows and columns and prints out data
        for (int row = 0; row < gameBoard.length; row++) {

            for (int col = 0; col < gameBoard[row].length; col++) {

                outputFile.printf("%c", gameBoard[row][col]);

                //prints a space unless it's the last column
                if (col < (gameBoard[row].length - 1)){
                    outputFile.print(" ");
                }

            }//end col for

            //goes to next line unless last row
            if (row < (gameBoard.length - 1)) {
                outputFile.println("");
            }

        }//end row for

        //close outputFile
        outputFile.close();

    }//end printMatrix method

    //printInputs method prints out the inputs that the user defined
    public static void printInputs(ArrayList<Line> allLines, int n, int k, int numLines, String file) throws IOException {

        //initialize variables
        Line currentLine = null;

        //open file for printing
        File fileName = new File(file);
        PrintWriter outputFile = new PrintWriter(fileName);

        //print out n dimensions and k turns to file's first line
        outputFile.println(n + " " + k);

        //print out each starting and end points for each line in arraylist
        for (int i = 0; i < numLines; i++){

            currentLine = allLines.get(i);
            outputFile.printf("%d %d %d %d\n",
                    currentLine.getStartingPoint()[0],
                    currentLine.getStartingPoint()[1],
                    currentLine.getEndingPoint()[0],
                    currentLine.getEndingPoint()[1]);

        }//end for

        //close outputFile
        outputFile.close();

    }//end printMatrix method

    //flipCells method flips the cells along the path of the line
    public static void flipCells(char[][] gameBoard, char player, Line line){

        //initialize variables
        int y1 = line.getStartingPoint()[1];
        int y2 = line.getEndingPoint()[1];
        int x1 = line.getStartingPoint()[0];
        int x2 = line.getEndingPoint()[0];
        int deltaY = Math.abs(y1 - y2);
        int deltaX = Math.abs(x1 - x2);
        int numPoints = 0;
        int[] point = {0, 0};

        //assign the starting cells and the ending cells to the player's letter
        gameBoard[x1 - 1][y1 - 1] = player;
        gameBoard[x2 - 1][y2 - 1] = player;

        //checks to see which cell should be flipped over
        if (deltaY > deltaX){
            numPoints =  + 1;
        } else {
            numPoints = deltaX + 1;
        }

        for (int i = 0; i < numPoints; i++){
            point[0] = x1 + (x2 - x1) * (i / numPoints);
            point[1] = y1 + (y2 - y1) * (i / numPoints);

            gameBoard[Math.round(point[0]) - 1][Math.round(point[1]) - 1] = player;
        }

    }//end flipCells

}//end charris_pa02
