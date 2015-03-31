/**
 * Immutable abstraction of Matrix.
 *
 * @author Michael Maurer
 * @version 1.1
 */
public class Matrix {
    /*
    Create final instance variables
    */
    private final double[][] matrix;
    private final int height;
    private final int width;

    /*
    * Initialize instance variables
    */
    public Matrix(double[][] matrix) {
        this.matrix = new double[matrix.length][matrix[0].length];
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                this.matrix[row][col] = matrix[row][col];
            }
        }
        height = matrix.length;
        width = matrix[0].length;
    }

    //i = th column
    public Vector getColumn(int i) {
        double[] vector = new double[matrix.length];
        for (int j = 0; j < matrix.length; j++) {
            vector[j] = matrix[j][i];
        }
        return new Vector(vector);
    }
    public double[][] array() {
        return matrix;
    }

    /**
    * Gets value located at specified row and column
    * @param int row 'i'
    * @param int row 'j'
    * @return double located at row i and column j in matrix
    */
    public double get(int i, int j) {
        if (i >= 0 && i < matrix.length && j >= 0 && j < matrix[0].length) {
            return matrix[i][j];
        }
        throw new MatrixIndexOutOfBoundsException("The indexes at " + i
            + " and " + j + " is not in the " + matrix.length + " by "
            + matrix[0].length + " matrix.");
    }

    public void set(int i, int j, double k) {
        matrix[i][j] = k;
    }
    /**
    * @return number of rows in matrix
    */
    public int getHeight() {
        return matrix.length;
    }

    /**
    * @return number of columns in matrix
    */
    public int getWidth() {
        return matrix[0].length;
    }

    /**
    * Gets String representation of matrix.
    * Columns separated by tabs, rows by new lines.
    * @return String representation of matrix.
    */
    public String toString() {
        String stringMatrix = "";
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                stringMatrix += matrix[row][col] + "    ";
            }
            stringMatrix += "\n";
        }
        return stringMatrix;
    }
}
