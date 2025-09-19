package snow.exception;

/**
 * Exception thrown when a date is required but not provided.
 */
public class SnowEmptyDateException extends SnowException {
    /**
     * Constructs a SnowEmptyDateException with a message specifying the task type.
     * @param type The type of task that requires a date
     */
    public SnowEmptyDateException(String type) {

        super("Needed a specific date for this " + type);
    }
}
