//https://github.com/bbjelly/Math2605.git
import java.util.ArrayList;
public class QRFact {
    public static Matrix hilbertMatrix(int dim) {
        double[][] hilb = new double[dim][dim];
        for (int r = 1; r <= dim; r++) {
            for (int c = 0; c < dim; c++) {
                hilb[r-1][c] = (double) 1/(r+c);
            }
        }
        return new Matrix(hilb);
    }
    //square matrix
    //Q, R, error (ArrayList order)
    public static ArrayList<Object> qr_fact_househ(Matrix matrix)
        throws IllegalOperandException {
        ArrayList<Object> qr = new ArrayList<>();
        int length = matrix.getWidth();
        int n = 0;
        qr.add(0, houseHelper(matrix, n, qr));
        Matrix q = (Matrix) qr.remove(1);
        while (qr.size() > 1) {
            try {
                q = LinearAlgebra.matrixMatrixMultiply(q, (Matrix) qr.remove(1));
            } catch (IllegalOperandException e) {
                System.out.println("something wrong, 97");
            }
        }
        q = LinearAlgebra.scalarMultMatrix(q, -1);
        qr.add(LinearAlgebra.scalarMultMatrix((Matrix) qr.remove(0), -1));
        qr.add(0, q);
        //calculate error
        Matrix error = LinearAlgebra.matrixAdd(
                    LinearAlgebra.matrixMatrixMultiply(q, (Matrix) qr.get(1)),
                    LinearAlgebra.scalarMultMatrix(matrix, -1));
        double errorMax = error(error);
        qr.add(errorMax);
        return qr;
    }
    public static double error(Matrix error) {
        double errorMax = 0;
        double rowSum = 0;
        for (int i = 0; i < error.getHeight(); i++) {
            for (int j = 0; j < error.getWidth(); j++) {
                rowSum += Math.abs(error.get(i,j));
            }
            if (rowSum > errorMax) {
                errorMax = rowSum;
            }
            rowSum = 0;
        }
        return errorMax;
    }
    public static Vector e1Gen(int rowNum) {
        double[] e1 = new double[rowNum];
        e1[0] = 1;
        return new Vector(e1);
    }
    public static Matrix smallerMat(Matrix matrix, int n) {
        double[][] small = new double[matrix.getHeight() -n][matrix.getWidth() - n];
        for (int i = 0; i < small.length; i++) {
            for (int j = 0; j < small[0].length; j++) {
                small[i][j] = matrix.get(i+n,j+n);
            }
        }
        return new Matrix(small);
    }
    public static Matrix identity(int row) {
        double[][] identity = new double[row][row];
        for (int i = 0; i < row; i++) {
            identity[i][i] = 1;
        }
        return new Matrix(identity);
    }
    //return R
    //n = number of times recursion should run
    public static Matrix houseHelper(Matrix matrix, int n, ArrayList<Object> qr)
        throws IllegalOperandException {
        if (n >= matrix.getHeight() - 1) {
            return matrix;
        }
        Matrix copy = smallerMat(matrix, n);
        Vector x = copy.getColumn(0);
        Vector e1 = e1Gen(copy.getHeight());
        Vector xe1 = LinearAlgebra.vectorAdd(x,
                LinearAlgebra.scalarMultVector(e1, x.mag()));
        Vector uPrev = LinearAlgebra.scalarMultVector(xe1, (1 / xe1.mag()));
        double[][] uArr = new double[uPrev.getLength()][1];
        for (int i = 0; i < uArr.length; i++) {
            uArr[i][0] = uPrev.get(i);
        }
        Matrix u = new Matrix(uArr);
        Matrix h = weirdMatrixAdd(identity(matrix.getWidth()),
            LinearAlgebra.scalarMultMatrix(
            LinearAlgebra.matrixMatrixMultiply(u, LinearAlgebra.transpose(u)), -2));
        qr.add(h);
        n++;
        return houseHelper(LinearAlgebra.matrixMatrixMultiply(h, matrix), n, qr);
    }
    public static Matrix weirdMatrixAdd(Matrix m, Matrix n) {
        int diff = m.getWidth() - n.getWidth();
        double[][] add = new double[m.getWidth()][m.getWidth()];
        for (int i = 0; i < diff; i++) {
            add[i][i] = 1;
        }
        for (int i = 0; i < n.getHeight(); i++) {
            for (int j = 0; j < n.getWidth(); j++) {
                add[i+diff][j+diff] = m.get(i+diff,j+diff) + n.get(i,j);
            }
        }
        return new Matrix(add);
    }

    //solve Ax = b
    public static Matrix  solve_qr_b(Matrix a, Matrix b)
    throws IllegalOperandException {
        ArrayList qrFact = qr_fact_househ(a);
        Matrix qTb = LinearAlgebra.matrixMatrixMultiply(
                    LinearAlgebra.transpose((Matrix) qrFact.get(0)) , b);
        Matrix r = (Matrix) qrFact.get(1);
        double[][] xArr = new double[a.getWidth()][1];
        for (int i = xArr.length -1; i >= 0; i--) {
            double factor = qTb.get(i, 0);
            for (int j = i+1; j < xArr.length; j++) {
                factor = factor - (r.get(i, j) * xArr[j][0]);
            }
            double elem = 0;
            elem = factor / r.get(i,i);
            xArr[i][0] = elem;
        }
        return new Matrix(xArr);
    }

    //Givens
    public static ArrayList<Object> qr_fact_givens(Matrix matrix) throws IllegalOperandException {
        ArrayList<Object> qr = new ArrayList<>();
        double[][] arr = new double[2][1];
        arr[0][0] = matrix.get(0, 0);
        arr[1][0] = matrix.get(1, 0);
        Matrix x = new Matrix(arr);
        qr.add(0, givensHelper(matrix, qr, x, 0, 0, 1));
        Matrix q = LinearAlgebra.transpose((Matrix) qr.remove(1));
        while (qr.size() > 1) {
            q = LinearAlgebra.matrixMatrixMultiply(q,
                            LinearAlgebra.transpose((Matrix) qr.remove(1)));
        }
        qr.add(0, q);
        //calculate error
        Matrix error = LinearAlgebra.matrixAdd(
                    LinearAlgebra.matrixMatrixMultiply(q, (Matrix) qr.get(1)),
                    LinearAlgebra.scalarMultMatrix(matrix, -1));
        double errorMax = error(error);
        qr.add(errorMax);
        return qr;
    }
    //x= 2x2 matrix to change
    //int i = location of first elem of x
    //int j = location of 2nd elem of x
    public static Matrix givensHelper(
            Matrix m, ArrayList<Object> qr, Matrix x, int col, int i, int j)
            throws IllegalOperandException {
        if (col >= m.getWidth() -1) {
            return m;
        }
        Matrix g = identity(m.getWidth());
        if (x.get(1, 0) != 0) {
            double c = x.get(0,0)/Math.hypot(x.get(0,0), x.get(1,0));
            double s = -x.get(1,0)/Math.hypot(x.get(0,0), x.get(1,0));
            int diff = j - i;
            g.set(i, col, c);
            g.set(j, col, s);
            g.set(i, col + diff, -s);
            g.set(j, col + diff, c);
            qr.add(g);
        }
        m = LinearAlgebra.matrixMatrixMultiply(g, m);
        boolean done = true;
        for (int k = 1 + col; k < m.getHeight(); k++) {
            if (m.get(k, col) != 0) {
                done = false;
            }
        }
        if (done) {
            col++;
            j = col;
            i = col;
        }
        x.set(0, 0, m.get(i, col));
        if (j < m.getHeight() - 1) {
            x.set(1, 0, m.get(++j, col));
        }
        return givensHelper(m, qr, x, col, i, j);
    }
}
