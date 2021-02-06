/**
 * An exception thrown in the folder class to indicate that
 * the name entered/given already exist.
 * 
 * @author
 *      XinCheng Chi, SBU ID#：111919385,Recition R30
 * Assignment:
 *      Homework #5 for CSE 214, Summer 2020
 * Date:
 *      August 6,2020
 */
public class ExistedNameException extends Exception
{
    /**
     * Default constructor for an ExistedNameException that
     * passes a default string to the Exception class constructor.
     * Postcondition:
     *    The object is created and contains the default message.
     */
    public ExistedNameException()
    {
        super("Name already exist");
    }
    /**
     * Second constructor for the ExistedNameException that
     * passes a provided string to the Exception class constructor.
     * @param message
     *    the message that the object is to contain
     * Postcondition:
     *    The object is created and contains the provided message.
     */
    public ExistedNameException(String message)
    {
        super(message);
    }
}