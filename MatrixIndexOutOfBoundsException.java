/**
*Extends IndexOutOfBoundsException class
*Thrown if the parameters are not within the given matrix
*@author Se Yeon Kim
*
*/
public class MatrixIndexOutOfBoundsException
    extends IndexOutOfBoundsException {
    /**
    *Creates an instance variable of MatrixIndexOutOfBoundsException
    *
    *
    */
    public MatrixIndexOutOfBoundsException() {
        super();
    }
    /**
    *Creates an instance variable of MatrixIndexOutOfBoundsException
    *with a specific message
    *@param String message to be stored for later access
    *
    */
    public MatrixIndexOutOfBoundsException(String message) {
        super(message);
    }
}
