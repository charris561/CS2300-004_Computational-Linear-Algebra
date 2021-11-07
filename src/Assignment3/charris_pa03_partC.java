package Assignment3;
/*
Caleb Harris
CS2300 - Computational Linear Algebra
Section: 001
Due date: 10/25
Assignment: PA 3 - Part C

Description:
This program will calculate the areas and distances given three points in 2D or 3D
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class charris_pa03_partC {

    public static void main(String[] args) throws IOException {

        //declare constants for input/output file names
        final String INPUT_FILE1 = "test_input_1.txt";
        final String INPUT_FILE2 = "test_input_2.txt";
        final String INPUT_FILE3 = "test_input_3.txt";
        final String INPUT_FILE4 = "test_input_4.txt";
        final String OUTPUT_FILE1 = "charris_partC_test_output_1.txt";
        final String OUTPUT_FILE2 = "charris_partC_test_output_2.txt";
        final String OUTPUT_FILE3 = "charris_partC_test_output_3.txt";
        final String OUTPUT_FILE4 = "charris_partC_test_output_4.txt";
        final String INPUT_FILE1_3D = "3D_test_input_1.txt";
        final String INPUT_FILE2_3D = "3D_test_input_2.txt";
        final String OUTPUT_FILE1_3D = "charris_partC_3D_test_output_1.txt";
        final String OUTPUT_FILE2_3D = "charris_partC_3D_test_output_2.txt";

        //make calculations and output to output file
        //case: 2D calculations
        partCCalculations(INPUT_FILE1, OUTPUT_FILE1);
        partCCalculations(INPUT_FILE2, OUTPUT_FILE2);
        partCCalculations(INPUT_FILE3, OUTPUT_FILE3);
        partCCalculations(INPUT_FILE4, OUTPUT_FILE4);

        //case: 3D calculations
        partCCalculations(INPUT_FILE1_3D, OUTPUT_FILE1_3D);
        partCCalculations(INPUT_FILE2_3D, OUTPUT_FILE2_3D);

    }//end main

    //partCCalculations calculates area and distances given three points in 2D or 3D
    public static void partCCalculations(String inputFile, String outputFile) throws IOException {

        //initialize necessary variables
        boolean is2D = false; //stores if file represents 2D
        boolean is3D = false; //stores if file represents 3D
        String output = ""; //stores output

        //check to see if 3D or 2D
        if (findRows(inputFile) == 2) {
            is2D = true;
        } else if (findRows(inputFile) == 3) {
            is3D = true;
        }

        //if 2D, proceed accordingly
        if (is2D) {

            //initialize variables for 3 points
            double[][] points = new double[2][3];

            //get points from file
            readFile(inputFile, points);

            //area of triangle formed by vectors equals half the determinant
            //find the two vectors a1 and a2
            double[][] a = new double[2][2];
            findVectors(a, points);

            //calculate area
            double area = Math.abs(0.5 * calcDet(a));

            //construct a line using first two points
            double[] point1 = {points[0][0], points[1][0]};
            double[] point2 = {points[0][1], points[1][1]};
            Line line = new Line(point1, point2);

            //find distance to point3
            double[] point3 = {points[0][2], points[1][2]};
            double dist = findDist(line, point3);

            output = String.format("%.4f\n%.4f\n", area, dist);

        }//end is2D

        else if (is3D) {

            //initialize variables for 3 points
            double[][] points = new double[3][3];

            //get points from file
            readFile(inputFile, points);
            double[] point1 = { points[0][0], points[1][0], points[2][0] };
            double[] point2 = { points[0][1], points[1][1], points[2][1] };
            double[] point3 = { points[0][2], points[1][2], points[2][2] };

            //find vectors a1 and a2
            double[][] a = new double[3][2];
            findVectors(a, points);

            //calculate cross product of a1 and a2
            double[] vector_a1 = { a[0][0], a[1][0], a[2][0] };
            double[] vector_a2 = { a[0][1], a[1][1], a[2][1] };
            double[][] crossProd = calcCrossProd(vector_a1, vector_a2);

            //find area
            double area = calcArea(crossProd);

            //construct plane
            double[] m = {(point2[0] + point1[0] / 2), (point2[1] + point1[1] / 2), (point2[2] + point1[2] / 2)};

            double[] diff_p2p1 = {(point2[0] - point1[0]), (point2[1] - point1[1]), (point2[2] - point1[2])};
            double lengthOfDiffp2p1 = calcLength(diff_p2p1);
            double[] n = new double[3];
            n[0] = diff_p2p1[0] / lengthOfDiffp2p1;
            n[1] = diff_p2p1[1] / lengthOfDiffp2p1;
            n[2] = diff_p2p1[2] / lengthOfDiffp2p1;

            //distance = n * p3 - n * m = 0

            //calculate distance
            double dist = (n[0] * point3[0]) + (n[1] * point3[1]) + (n[2] * point3[1]) - ((n[0] * m[0]) + (n[1] * m[1]) + (n[2] * m[2]));

            output = String.format("%.4f\n%.4f\n", area, dist);

        }//end is3D

        //print to output file
        fileOut(outputFile, output);

    }//end partCCalculations

    //calcArea calculates the area of cross product x
    public static double calcArea(double[][] x){

        double area_part1 = Math.abs(0.5 * x[0][0]);
        double area_part2 = Math.abs(0.5 * x[1][0]);
        double area_part3 = Math.abs(0.5 * x[2][0]);

        return area_part1 + area_part2 + area_part3;

    }//end calcArea

    //calcCrossProd calculates the cross product of vector v and w
    public static double[][] calcCrossProd(double[] v, double[] w){

        double[][] crossProd = new double[3][1];

            crossProd[0][0] = (v[1] * w[2]) - (w[1] * v[2]);
            crossProd[1][0] = (v[2] * w[0]) - (w[2] * v[0]);
            crossProd[2][0] = (v[0] * w[1]) - (w[0] * v[1]);

        return crossProd;

    }//end calc cross prod

    //findDist finds the distance of a line to a point
    public static double findDist(Line line, double[] point) {

        double dist = 0; //stores the distance from the line

        //calculate vector w
        double[] w = new double[2];
        w[0] = point[0] - line.getPoint1()[0];
        w[1] = point[1] - line.getPoint1()[1];

        //calculate cos(theta)
        double cos_theta = dotProd(line.getVector_v(), w) / (calcLength(line.getVector_v()) * calcLength(w));

        //calculate distance
        dist = calcLength(w) * Math.sqrt(1 - Math.pow(cos_theta, 2));

        return dist;

    }//end findDist

    //dotProd method computes the dot product of the passed in vectors
    public static double dotProd(double[] vectorA, double[] vectorB) {

        //multiplies each index of vectors by respective indices then adds them to compute dot product
        double result = 0;//stores the result of dot product operation

        for (int i = 0; i < vectorA.length; i++) {

            result += (vectorA[i] * vectorB[i]);

        }//end for

        return result;

    }//end dotProd

    //calcLength calculates the length of a passed in vector
    public static double calcLength(double[] v) {

        return Math.sqrt((Math.pow(v[0], 2)) + (Math.pow(v[1], 2)));

    }//end calcLength

    //findVectors method finds the vectors needed given 3 points
    public static void findVectors(double[][] a, double[][] points) {

        if (points.length == 2) {
            //a1 = p2 - p1
            a[0][0] = points[0][1] - points[0][0];
            a[1][0] = points[1][1] - points[1][0];

            //a2 = p3 - p1
            a[0][1] = points[0][2] - points[0][0];
            a[1][1] = points[1][2] - points[1][0];
        }
        else {
            //a1 = p2 - p1
            a[0][0] = points[0][1] - points[0][0];
            a[1][0] = points[1][1] - points[1][0];
            a[2][0] = points[2][1] - points[2][0];

            //a2 = p3 - p1
            a[0][1] = points[0][2] - points[0][0];
            a[1][1] = points[1][2] - points[1][0];
            a[2][1] = points[2][2] - points[2][0];
        }

    }//end findVectors

    //calcDet calculates the determinant of the passed in 2x2 matrix
    public static double calcDet(double[][] A) {

        //initialize necessary variables
        double det = 0;

        //calculate determinant -> eq: (a1,1*a2,2) - (a1,2*a2,1) = det of A
        det = (A[0][0] * A[1][1]) - (A[0][1] * A[1][0]);

        return det;

    }//end calcDet

    //finds the amount of rows in the file passed in
    public static int findRows(String fileToRead) throws IOException {

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //initialize rows
        int rows = 0;

        //count the rows
        while (inputFile.hasNextLine() && (inputFile.hasNextInt() || inputFile.hasNextDouble())) {

            //iterate rows
            rows++;

            //goes to next line
            inputFile.nextLine();

        }//end while

        inputFile.close();

        return rows;

    }//end findRows

    //readFile method reads the points from file
    public static void readFile(String fileToRead, double[][] p) throws IOException {

        //open file for reading
        File fileName = new File(fileToRead);
        Scanner inputFile = new Scanner(fileName);

        //assign points from file to passed in array
        while (inputFile.hasNextDouble()) {

            for (int r = 0; r < p.length; r++) {

                for (int c = 0; c < p[0].length; c++) {
                    p[r][c] = inputFile.nextDouble();
                }

            }

        }//while there is another number

        //close file
        inputFile.close();

    }//end readFile

    //fileOut method writes the outputs of the calculations to the corresponding output file
    public static void fileOut(String fileToOutput, String output) throws IOException {

        //open file for printing
        File fileName = new File(fileToOutput);
        PrintWriter outputFile = new PrintWriter(fileName);

        outputFile.print(output);

        //close outputFile
        outputFile.close();

    }//end fileOut

}//end charris_pa03_partC
