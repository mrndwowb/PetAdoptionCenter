package exception;

//exception for application processing error
public class ApplicationProcessingException extends Exception {
    public ApplicationProcessingException(String message) {
        super(message);
    }
}