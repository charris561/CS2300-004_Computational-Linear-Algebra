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

            if (userChoice == 1){

                //call play game to simulate a game being played
                playGame(true, FILE_INPUT_1);
                playGame(true, FILE_INPUT_2);
                playGame(true, FILE_INPUT_3);

            }
            else{
                //call play game to simulate a game being played
                playGame(false, "");
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
    public static void playGame(boolean fileInput, String inputFileName) throws IOException {

        //initialize variables
        int n = 0;
        int k = 0;
        int j = 0;
        int lineCtr = 0;
        int numInvalidMoves = 0;
        int numPoints = 0;
        boolean validChoice = true;
        boolean gameOver = false;
        boolean validPlay = false;
        char [][] gameBoard;
        char currentPlayer = PLAYER_1;
        ArrayList<Line> allLines = new ArrayList<>();
        Line[] pastKTurnsLines;

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

        } else{

            //prompt user for n and k
            Scanner userInput = new Scanner(System.in);

            System.out.println("Please enter how large you would like the board to be in N x N cells: ");
            n = userInput.nextInt();

        }

        //create game board using defBoard(n) method
        gameBoard = defBoard(n);

        //allocate memory for pastKTurns array
        pastKTurnsLines = new Line[k];

        //while game over criteria not met
        while (!gameOver){

            //get input for plays by calling getPlay method
            Line currentLine = getPlay(fileInput, inputFileName, lineCtr);
            lineCtr++;

            //test validity of play
            validPlay = isValid(pastKTurnsLines,currentLine, allLines);

            //store currentLine in all lines and in past k turns
            allLines.add(currentLine);
            pastKTurnsLines[j] = currentLine;
            j++;

            //checks to see if last element in array has been reached, then overwrites the oldest element
            if (j == k){
                j = 0;
            }

            //if move was valid, flip cells

            //if not from input file, display current score to console
            if (!fileInput){
                displayScore(gameBoard);
            }

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
    public static Line getPlay(boolean fileInput, String inputFileName, int lineCtr) throws IOException{

        //initialize variables
        int[] point1 = new int[2];
        int[] point2 = new int[2];
        Line currentLine = null;

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

            //create a line object
            currentLine = new Line(point1, point2);

        } else{

            //prompt user for points
            Scanner userInput = new Scanner(System.in);
            System.out.println("Please enter x1 for point 1: ");
            point1[0] = userInput.nextInt();
            System.out.println("Please enter x2 for point 1: ");
            point1[1] = userInput.nextInt();
            System.out.println("Please enter x1 for point 2: ");
            point2[0] = userInput.nextInt();
            System.out.println("Please enter x2 for point 2: ");
            point2[1] = userInput.nextInt();

            //create a line object
            currentLine = new Line(point1, point2);

        }

        return currentLine;

    }//end getPlay

    //method isValid will return a boolean value whether the play entered is valid or not
    public static boolean isValid(Line[] paskKTurnsLines, Line currentLine, ArrayList<Line> allLines){

        //initialize variables
        boolean valid = true;
        int numLines = allLines.size();

        //check if current line has midpoint, start, or end point of past k turn lines
        for (int i = 0; i < paskKTurnsLines.length; i++){

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

}//end charris_pa02
