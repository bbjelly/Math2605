import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Valerie on 3/26/2015.
 */
public class lu_fact {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------------------------");
        System.out.println("----------LU FACTORIZATION----------");
        System.out.println("------------------------------------");

        System.out.println("Please give the location of the matrix .dat file " +
                "(ex. b.dat)");
        String fileName = scanner.nextLine();
        // Create a File
        File myFile = new File(fileName);
        //File myFile = new File("src/b.dat");


        double[][] matrix = datToMatrix(myFile);
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] L = new double[rows][cols];

        rowReduce(rows, matrix, L);

        double[][] LU = matrixMult(L, matrix);
        double[][] LUMinusA = matrixSubtraction(LU, matrix);
        double LUerror = 0.0; //TODO

        System.out.println(" \nU");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + "   ");
            }
            System.out.println("");
        }

        System.out.println(" \nL");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(L[i][j] + "   ");
            }
            System.out.println("");
        }

        System.out.println(" \nLU");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(LU[i][j] + "   ");
            }
            System.out.println("");
        }

        System.out.println("Error ||LU−A||∞ = " + LUerror);

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

    /**
     * multiplies two SQUARE matrices together
     * @param m1 first SQUARE matrix
     * @param m2 second SQUARE matrix
     * @return result of matrix multiplication
     */
    private static double[][] matrixMult(double[][] m1, double[][] m2) {
        if (m1[0].length != m2.length) {
            throw new IllegalArgumentException("m1 must have the same number " +
                    "of columns as m2 has rows.");
        }
        double[][] mSol = new double[m1.length][m2[0].length];
        for (int c = 0; c < m1.length; c++) {
            for (int l = 0; l < m1.length; l++) {
                for (int u = 0; u < m1.length; u++) {
                    mSol[c][l] += m2[u][l] * m1[c][u];
                }
            }
        }
        return mSol;
    }

    /**
     * Subtracts square matrix m2 from square matrix m1
     * @param m1 first SQUARE matrix
     * @param m2 second SQUARE matrix
     * @return m1 - m2
     */
    private static double[][] matrixSubtraction(double[][] m1, double[][] m2) {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
            throw new IllegalArgumentException("m1 must have the same number " +
                    "of columns as m2 has rows.");
        }
        double[][] mSol = new double[m1.length][m1[0].length];
        for (int r = 0; r < m1.length; r++) {
            for (int c = 0; c < m1.length; c++) {
                mSol[r][c] = m1[r][c] - m2[r][c];
            }
        }
        return mSol;
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
