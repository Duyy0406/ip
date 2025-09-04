package snow.exception;

/**
 * Exception thrown when an invalid index is provided.
 */
public class SnowInvalidIndexException extends SnowException {
    /**
     * Constructs a SnowInvalidIndexException with a default message.
     */
    public SnowInvalidIndexException() {

        super("Invalid index. Please try again.");
    }
}
