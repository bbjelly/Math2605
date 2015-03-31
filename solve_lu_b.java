import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Valerie on 3/26/2015.
 */
public class solve_lu_b {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------------------------");
        System.out.println("----------LU FACTORIZATION----------");
        System.out.println("--------------Ax = b----------------");

        System.out.println("Please give the location of the matrix .dat file " +
                "(ex. b.dat)");
        String fileName = scanner.nextLine();
        // Create a File
        File myFile = new File(fileName);
        //File myFile = new File("src/b.dat");


        double[][] augMatrix = datToMatrix(myFile);
        int rows = augMatrix.length;
        int cols = augMatrix[0].length - 1;
        double[] b = new double[rows];
        double[][] matrix = new double[rows][cols];
        //separate matrix and b
        for (int r = 0; r < rows; r++) {
            b[r] = augMatrix[r][augMatrix[r].length - 1];
            for (int c = 0; c < cols; c++) {
                matrix[r][c] = augMatrix[r][c];
            }
        }

        double[][] L = new double[rows][cols];
        rowReduce(rows, matrix, L);

        //TODO: find an easy way to import L and U
        double[] y = Axb(L, b);
        double[] x = Axb(matrix, y);

        System.out.println(" \nU");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println("");
        }

        System.out.println(" \ny");
        for (int i = 0; i < rows; i++) {
            System.out.println(y[i]);
        }

        System.out.println(" \nx");
        for (int i = 0; i < rows; i++) {
            System.out.println(x[i]);
        }

        System.out.println(" \nb");
        for (int i = 0; i < rows; i++) {
            System.out.println(b[i]);
        }
    }


    /**
     * turns a .dat file to an augmented matrix [A|b] where A is n × n and v is
     * n × 1
     * @param myFile .dat file to be turned into a matrix. Delimiter is a
     *               space between each column and a new line for each row.
     * @return double[][] matrix with same data as the .dat file
     * @throws java.io.IOException
     */
    private static double[][] datToMatrix(File myFile) throws
            IOException {
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
       //int cols = rowStrings.size();
        int cols = rowStrings.get(0).split(" ").length;
        double[][] matrix = new double[rows][cols];
        double[][] L = new double[rows][cols];

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

    /**
     * calculates and returns the 'x' in Ax = b. Assumes does not need any
     * factorization or decomposition.
     * @param A matrix
     * @param b vector
     * @return x solution vector
     */
    private static double[] Axb(double[][] A, double[] b) {
        //TODO
        double[] x = new double[b.length];
        double[] variables = new double[b.length];
        for (int i = 0; i< variables.length; i++) {
            double sub = 0;
            for (int j = i; j >= 0; j--) {
                sub -= A[i][j] * x[j];
            }
            x[i] = (b[i] + sub)/A[i][i];
            //b[i] - A[i][0] * variables[i-1]

//            variables[i] = A[i][i];
//            variables[i] += variables[i - 1]; //TODO: see oneNote
//            variables[i] = b[i] / variables[i];
        }
//        for (int row = 0; row < A.length; row++) {
//            int sum = 0;
//            for (int col = 0; col < A[row].length; col++) {
//
//                sum += A[row][col];
//            }
//            x[row] = b[row]/sum;
//        }
        return x;
    }

    /**
     * separates matrices into U (matrix) and L (L)
     * @param size portion of matrix to row reduce or this iteration (ex. 2
     *             on a 3x3 matrix would analyze the overlap of rows 2-3 and
     *             columns 2-3.
     * @param matrix matrix to be reduced to LU decomposition
     * @param L L matrix for L part of decomposition to be stored in.
     */
    private static void rowReduce(int size, double[][] matrix, double[][] L) {
        int corner = matrix.length - size;
        for (int r = corner + 1; r < matrix.length; r++) {
            double mult = - matrix[r][corner] / matrix[corner][corner];
            for (int c = corner; c < matrix[r].length; c++) {
                matrix[r][c] = mult * matrix[corner][c] + matrix[r][c];
                L[c][c] = 1.0;
            }
            L[r][corner] = - mult; //TODO: double check this is negative

        }
        size--;
        if (size > 1) {
            rowReduce(size, matrix, L);
        }
    }
}
