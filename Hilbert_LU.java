import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Valerie on 3/30/2015.
 */

//error, plot, google doc for E and F
public class Hilbert_LU {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("------------------------------------");
        System.out.println("------Hilbert LU FACTORIZATION------");
        System.out.println("------------------------------------");

        System.out.println("Please give the dimensions of the Hilbert Matrix " +
                "you would like to compute.");
        int dimension = scanner.nextInt();

        int rows = dimension;
        int cols = dimension;
        double[][] matrix = hilbertMatrix(dimension);
        double[] b = new double[dimension];

        //create b vector
//        for (int r = 1; r <= rows; r++) { //TODO: check that this should
//        // start at 1
//            b[r - 1] = Math.pow(0.1, r/3.0);
//        }
        for (int r = 0; r < rows; r++) {
            b[r] = Math.pow(0.1, rows/3.0);
        }

        //double[] hxb = Axb(matrix, b);
        //double hxbError = axbError(matrix, hxb, b);
//        System.out.println(" \nmatrix");
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < cols; j++) {
//                System.out.print(matrix[i][j] + "   ");
//            }
//            System.out.println("");
//        }
        double[][] L = new double[rows][cols];
        rowReduce(rows, matrix, L);

        double[] y = Lyb(L, b);
        double[] x = Uxy(matrix, y);

        System.out.println(" \nL");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(L[i][j] + "   ");
            }
            System.out.println("");
        }

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

        System.out.println(" \nx of LUx = b:");
        for (int i = 0; i < rows; i++) {
            System.out.println("\t" + x[i]);
        }

        System.out.println(" \nb");
        for (int i = 0; i < rows; i++) {
            System.out.println(b[i]);
        }
//

//        System.out.println(" \nx of Hx = b:");
//        for (int i = 0; i < rows; i++) {
//            System.out.println("\t" + hxb[i]);
//        }
//        System.out.println("Error  ||Hxsol−b|| = " + hxbError);
    }


    private static double[][] hilbertMatrix(int dim) {
        double[][] hilb = new double[dim][dim];
        for (int r = 1; r <= dim; r++) {
            for (int c = 0; c < dim; c++) {
                hilb[r-1][c] = (double) 1/(r+c);
                //System.out.print("1/" + (r + c) + "   ");
            }
            //System.out.println("   ");
        }
        return hilb;
    }

    /**
     * calculates and returns the 'y' in Ly = b. Assumes does not need any
     * factorization or decomposition. Assumes A is a lower triangular matrixjk
     * @param L Lower Triangular matrix
     * @param b vector
     * @return y solution vector
     */
    private static double[] Lyb(double[][] L, double[] b) {
        double[] y = new double[b.length];
        for (int i = 0; i< y.length; i++) {
            double sub = 0;
            for (int j = i; j >= 0; j--) {
                sub -= L[i][j] * y[j];
            }
            y[i] = (b[i] + sub)/L[i][i];
            //b[i] - A[i][0] * variables[i-1]

//            variables[i] = A[i][i];
//            variables[i] += variables[i - 1];
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
        return y;
    }

    /**
     * calculates and returns the 'x' in Ux = y. Assumes does not need any
     * factorization or decomposition. Assumes A is a upper triangular matrix
     * @param U Upper triangular matrix
     * @param y vector
     * @return x solution vector
     */
    private static double[] Uxy(double[][] U, double[] y) {
        double[] x = new double[y.length];
        for (int i = x.length - 1; i >= 0; i--) {
            double sub = 0;
            for (int j = i; j < x.length; j++) {
                sub -= U[i][j] * x[j];
            }
            x[i] = (y[i] + sub)/U[i][i];
        }
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
                if (Math.abs(matrix[r][c]) < 0.0000000001) {
                    matrix[r][c] = 0.0;
                }
                L[c][c] = 1.0;
            }
            L[r][corner] = - mult;

        }
        size--;
        if (size > 1) {
            rowReduce(size, matrix, L);
        }
    }

    private static double axbError(double[][] a, double[] x, double[] b) {
        double[] ax = multMatrixVector(a, x);
        double sum = 0.0;
        for (int i = 0; i < b.length; i++) {
            sum += Math.pow(ax[i] - b[i], 2.0);
            System.out.println(sum);
        }
        return Math.pow(sum, 0.5);
    }

    public static double[] multMatrixVector(double[][] aMatrix, double[]
            aVector) {
        double[] multMat = new double[aMatrix.length];
        for (int m = 0; m < aMatrix.length; m++) {
            double val = 0;
            for (int n = 0; n < aMatrix[0].length; n++) {
                val = val + aMatrix[m][n]*aVector[n];
            }
            multMat[m] = val;
            val = 0;
        }
        return multMat;
    }
}
