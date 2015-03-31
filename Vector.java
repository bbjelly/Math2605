/**
 * Immutable abstraction for Vector
 *
 * @author Michael Maurer
 * @version 1.1
 */
public class Vector {

    /*
    Create final instance variables
    */
    private final double[] vector;
    private final int length;

    /*
    * Initialize instance variables
    */
    public Vector(double[] vector) {
        this.vector = new double[vector.length];
        for (int i = 0; i < vector.length; i++) {
            this.vector[i] = vector[i];
        }
        length = vector.length;
    }
    public double mag() {
        double sum = 0;
        for (int i = 0; i < vector.length; i++) {
            sum += Math.pow(vector[i], 2);
        }
        return Math.sqrt(sum);
    }
    public Vector norm() {
        double[] normal = new double[vector.length];
        double mag = this.mag();
        for (int i = 0; i < vector.length; i++) {
            normal[i] = vector[i]/mag;
        }
        return new Vector(normal);
    }
    // public void set(int i, double value) {
    //     vector[i] = value;
    // }

    /**
    * Gets value located at specified index
    * @param int index 'i' in vector
    * @return double located at index 'i' in vector
    */
    public double get(int i) {
        if (i >= 0 && i < vector.length) {
            return vector[i];
        }
        throw new VectorIndexOutOfBoundsException("An index at " + i
            + " is not in the vector with length of " + vector.length);
    }

    /**
    * @return number of components in vector
    */
    public int getLength() {
        return vector.length;
    }

    /**
    * String representation of vector with components
    * separated by tabs
    * @return String representation of vector
    */
    public String toString() {
        String stringVector = "";
        for (int i = 0; i < vector.length; i++) {
            stringVector += vector[i] + "    ";
        }
        return stringVector;
    }
}
