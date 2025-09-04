package snow.exception;

/**
 * Exception thrown when an invalid description is provided.
 */
public class SnowInvalidDescriptionException extends SnowException {
    /**
     * Constructs a SnowInvalidDescriptionException with a default message.
     */
    public SnowInvalidDescriptionException() {

        super("The description of this command can only be a number");
    }
}
