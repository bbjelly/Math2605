import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.util.Scanner;

/**
 * Driver for Linear Algebra.
 *
 * @author Valerie Avva
 * @version 1.1
 */
public class LinearAlgebraDriver {

    /**
     * Runs program asking user for input and running linear algebra methods.
     */
    public static void main(String[] args) throws IOException {
        LinearAlgebraScanner input = new LinearAlgebraScanner();
        Scanner console = new Scanner(System.in);
        boolean done = false;
        System.out.println("Hello!");
        System.out.println("Please enter the name of .dat file (ex: a.dat)!");
        // System.out.println("Enter empty line to terminate!");
        String fileName = console.nextLine();
        File myFile = new File(fileName);
        double[][] arr = datToMatrix(myFile);
        while (!done) {
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.println("0. Householder");
            System.out.println("1. Givens");
            System.out.println("2. Solve Ax = b");
            System.out.println("3. Type another matrix");
            System.out.println("4. Exit\n");
            String line = input.nextLine();
            int userInput = Integer.parseInt(line);
            System.out.println();
            if (userInput == 0) {
                try {
                    Matrix m1 = new Matrix(arr);
                    System.out.println();
                    ArrayList<Object> qrHouse = QRFact.qr_fact_househ(m1);
                    System.out.println("Q: " + qrHouse.get(0));
                    System.out.println("R: " + qrHouse.get(1));
                    System.out.println("error: " + qrHouse.get(2));
                } catch  (IllegalOperandException e) {
                    System.out.println("Sorry, something went wrong.");
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Sorry, something went wrong.");
                    System.out.println(e.getMessage());
                }
            } else if (userInput == 1) {
                try {
                    Matrix m1 = new Matrix(arr);
                    System.out.println();
                    System.out.println();
                    ArrayList<Object> qrfact = QRFact.qr_fact_givens(m1);
                    System.out.println("Q: " + qrfact.get(0));
                    System.out.println("R: " + qrfact.get(1));
                    System.out.println("error: " + qrfact.get(2));
                } catch (IllegalOperandException e) {
                    System.out.println("Sorry, something went wrong.");
                    System.out.println(e.getMessage());
                } catch  (InputMismatchException e) {
                    System.out.println("Sorry, something went wrong.");
                    System.out.println(e.getMessage());
                }
            } else if (userInput == 2) {
                System.out.println("Ax = b");
                try {
                    Matrix m1 = new Matrix(arr);
                    System.out.println();
                    System.out.println("Please enter a vector (b)!");
                    System.out.println("Separate vector components by "
                        + "separating the lines (press enter).");
                    System.out.println("Enter empty line to terminate!");
                    Matrix v1 = input.readMatrix();
                    System.out.println();
                    System.out.println();
                    System.out.println(QRFact.solve_qr_b(m1, v1));
                } catch (IllegalOperandException e) {
                    System.out.println("Sorry, something went wrong.");
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.println("Sorry, something went wrong.");
                    System.out.println(e.getMessage());
                }
            } else if (userInput == 3) {
                System.out.println("Please enter the name of .dat file (ex: a.dat)!");
                fileName = console.nextLine();
                myFile = new File(fileName);
                arr = datToMatrix(myFile);

            } else if (userInput == 4) {
                done = true;
            }
        }
    }
    /**
    * turns a .dat file to a 2-dimensional array (aka matrix)
    * @param myFile .dat file to be turned into a matrix. Delimiter is a
    *               space between each column and a new line for each row.
    * @return double[][] matrix with same data as the .dat file
    * @throws IOException
    */
    private static double[][] datToMatrix(File myFile) throws IOException {
        FileReader inStream = new FileReader(myFile);
        BufferedReader in = new BufferedReader(inStream);

        ArrayList<String> rowStrings = new ArrayList<String>();
        String temp = "";
        while (temp != null) {
            temp = in.readLine();
            if (temp != null) {
                rowStrings.add(temp);
                //System.out.println(temp);
            }
        }
        int rows = rowStrings.size();
        int cols = rowStrings.get(0).split(" ").length;
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rowStrings.size(); i++) {
            String[] colStrings = rowStrings.get(i).split(" ");
            for (int j = 0; j < colStrings.length; j++) {
                matrix[i][j] = Double.valueOf(colStrings[j]);
                //System.out.print(Double.valueOf(colStrings[j]) + " ");
            }
            //System.out.println(" ");
        }
        return matrix;
    }
}
