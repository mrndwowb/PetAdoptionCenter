package exception;

//exception for invalid user input errors
public class InvalidInputException extends Exception  {
    public InvalidInputException(String message) {
        super(message);
    }
}
