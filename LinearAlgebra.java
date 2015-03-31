/**
*This class provides some matrix and vector related operations
*,which include matrix vector multiplication, addition of two matrices,
*dot product of two vectors, and addition of two vectors
*All methods are static methods
*@author Se Yeon Kim
*
*/
public class LinearAlgebra {
    // public static ArrayList<Double> eigenvalue(Matrix m) {

    // }
    public static Matrix transpose (Matrix m) {
        double[][] t = new double[m.getWidth()][m.getHeight()];
        for (int i = 0; i < m.getWidth(); i++) {
            for (int j = 0; j < m.getHeight(); j++) {
                t[i][j] = m.get(j,i);
            }
        }
        return new Matrix(t);
    }
    public static Vector scalarMultVector (Vector m, double s) {
        double[] mult = new double[m.getLength()];
        for (int i = 0; i < m.getLength(); i++) {
            mult[i] = m.get(i) * s;
        }
        return new Vector(mult);
    }
    public static Matrix scalarMultMatrix (Matrix m, double s) {
        double[][] mult = new double[m.getHeight()][m.getWidth()];
        for (int i = 0; i < m.getHeight(); i++) {
            for (int j = 0; j < m.getWidth(); j++) {
                mult[i][j] = m.get(i,j) * s;
                if (mult[i][j] < Math.pow(10, -6) && mult[i][j] > 0 ||
                (mult[i][j] < 0 && mult[i][j] > -Math.pow(10,-6))) {
                    mult[i][j] = 0;
                }
            }
        }
        return new Matrix(mult);
    }
    public static Matrix matrixMatrixMultiply(Matrix m, Matrix n)
    throws IllegalOperandException {
        if (m.getWidth() != n.getHeight()) {
            throw new IllegalOperandException("number of columns of first matrix"
            + "doesn't match the number of rows of second matrix");
        }
        double[][] mult = new double[m.getHeight()][n.getWidth()];
        for (int i = 0; i < n.getWidth(); i++) {
            Vector nCol = n.getColumn(i);
            Vector vector = matrixVectorMultiply(m, nCol);
            for (int j = 0; j < vector.getLength(); j++) {
                mult[j][i] = vector.get(j);
            }
        }
        return new Matrix(mult);
    }
    // public static void main(String[] args) {
    //     double[][] mArr = new double[2][1];
    //     for (int i=0;i<mArr.length;i++) {
    //         for (int j = 0; j < mArr[0].length; j++) {
    //             mArr[i][j] = i + (j/10.0);
    //         }
    //     }
    //     Matrix m = new Matrix(mArr);
    //     double[][] nArr = new double[1][2];
    //     nArr[0][0] = 2;
    //     nArr[0][1] = 1;
    //     // for (int i = 0; i < nArr.length; i++) {
    //     //     nArr[i][i] = 1;
    //     // }
    //     Matrix n = new Matrix(nArr);
    //     System.out.println(matrixMatrixMultiply(m, n));
    // }
    /**
    *Gives a final value(vector) when a matrix and a vector were multiplied
    *If width of matrix doesn't match length of vector,
    *throws IllegalOperandException
    *@param Matrix m, which will be right-multiplied to v
    *@param Vector v, which will be left-multiplied to m
    *@return Vector, which is attained through multiplying m and v
    */
    public static Vector matrixVectorMultiply(Matrix m, Vector v)
        throws IllegalOperandException {
        if (m.getWidth() == v.getLength()) {
            double[] toBeVector = new double[m.getHeight()];
            double sumEntry = 0;
            for (int row = 0; row < m.getHeight(); row++) {
                for (int col = 0; col < m.getWidth(); col++) {
                    sumEntry += m.get(row, col) * v.get(col);
                }
                toBeVector[row] = sumEntry;
                if (toBeVector[row] < Math.pow(10, -6) && toBeVector[row] > 0 ||
                (toBeVector[row] < 0 && toBeVector[row] > -Math.pow(10,-6))) {
                    toBeVector[row] = 0;
                }
                sumEntry = 0;
            }
            return new Vector(toBeVector);
        }
        throw new IllegalOperandException("Cannot multiply a matrix of width "
                                        + m.getWidth()
                                        + " with a vector of length "
                                        + v.getLength());
    }
    /**
    *Adds two matrix that have same dimensions. If dimensions are not the same
    *throws IllegalOperandException
    *@param Matrix m1, which will be added to m2
    *@param Matrix m2, which will be added to m1
    *@return Matrix, which is an answer to the addition of m1 and m2
    */
    public static Matrix matrixAdd(Matrix m1, Matrix m2)
        throws IllegalOperandException {
        if (m1.getWidth() == m2.getWidth()
            && m1.getHeight() == m2.getHeight()) {
            double[][] toBeMatrix = new double[m1.getHeight()][m1.getWidth()];
            for (int row = 0; row < m1.getHeight(); row++) {
                for (int col = 0; col < m1.getWidth(); col++) {
                    toBeMatrix[row][col] = m1.get(row, col) + m2.get(row, col);
                }
            }
            return new Matrix(toBeMatrix);
        }
        throw new IllegalOperandException("Cannot add a " + m1.getHeight()
                                        + " by " + m1.getWidth()
                                        + " matrix to a " + m2.getHeight()
                                        + " by " + m2.getWidth() + " matrix.");
    }
    /**
    *Gets the dot product of two vectors
    *If given vectors have different lengths, throws IllegalOperandException
    *@param Vector v1
    *@param Vector v2
    *@return double, dot product of v1 and v2
    */
    public static double dotProduct(Vector v1, Vector v2)
        throws IllegalOperandException {
        if (v1.getLength() == v2.getLength()) {
            double dotProduct = 0;
            for (int i = 0; i < v1.getLength(); i++) {
                dotProduct += v1.get(i) * v2.get(i);
            }
            return dotProduct;
        }
        throw new IllegalOperandException("Cannot do dot product of a vector"
                                        + " with length" + v1.getLength()
                                        + "to another vector with length"
                                        + v2.getLength());
    }
    /**
    *Gives a vector, which was the result of adding two vectors
    *If two vectors have different dimensions, throws IllegalOperandException
    *@param Vector v1, which will be added to v2
    *@param Vector v2, which will be added to v1
    *@return Vector, which results from adding v1 and v2
    */
    public static Vector vectorAdd(Vector v1, Vector v2)
        throws IllegalOperandException {
        if (v1.getLength() == v2.getLength()) {
            double[] toBeVector = new double[v1.getLength()];
            for (int i = 0; i < v1.getLength(); i++) {
                toBeVector[i] = v1.get(i) + v2.get(i);
            }
            return new Vector(toBeVector);
        }
        throw new IllegalOperandException("Cannot add a vector of length"
                                          + v1.getLength()
                                          + "to a vector of length"
                                          + v2.getLength());
    }
}
