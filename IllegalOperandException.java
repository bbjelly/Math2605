/**
*Extends Exception class
*Thrown if the parameters do not match up like trying to add
*two matrices with different dimensions
*@author Se Yeon Kim
*
*/
public class IllegalOperandException extends Exception {
    /**
    *Creates an instance variable of IllegalOperandException
    *
    *
    */
    public IllegalOperandException() {
        super();
    }
    /**
    *Creates an instance variable of IllegalOperandException
    *with a specific message
    *@param String message to be stored for later access
    *
    */
    public IllegalOperandException(String message) {
        super(message);
    }
}
