/**
*Extends IndexOutOfBoundsException class
*Thrown if the parameter are not within the given vector
*@author Se Yeon Kim
*
*/
public class VectorIndexOutOfBoundsException
    extends IndexOutOfBoundsException {
    /**
    *Creates an instance variable of VectorIndexOutOfBoundsException
    *
    *
    */
    public VectorIndexOutOfBoundsException() {
        super();
    }
    /**
    *Creates an instance variable of VectorIndexOutOfBoundsException
    *with a specific message
    *@param String message to be stored for later access
    *
    */
    public VectorIndexOutOfBoundsException(String message) {
        super(message);
    }
}
