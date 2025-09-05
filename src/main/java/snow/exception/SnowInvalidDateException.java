package snow.exception;

/**
 * Exception thrown when a date is required but not provided.
 */
public class SnowInvalidDateException extends SnowException {
    /**
     * Constructs a SnowEmptyDateException with a message specifying the task type.
     */
    public SnowInvalidDateException() {

        super("Wrong date format. Please try again.");
    }
}
