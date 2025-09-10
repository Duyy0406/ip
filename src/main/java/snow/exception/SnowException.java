package snow.exception;

/**
 * Base exception class for Snow application.
 */
public class SnowException extends Exception {
    /**
     * Constructs a SnowException with the specified message.
     * @param msg The exception message
     */
    public SnowException(String msg) {
        super(msg);
    }
}
