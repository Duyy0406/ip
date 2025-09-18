package snow.exception;

/**
 * Exception thrown when a date format is invalid.
 */
public class SnowInvalidDateException extends SnowException {
    /**
     * Constructs a SnowInvalidDateException with a default message.
     */
    public SnowInvalidDateException() {
        super("Wrong date format. Please use: yyyy-MM-dd [HH:mm] or d/M/yyyy [HH:mm]");
    }

    /**
     * Constructs a SnowInvalidDateException with details about the invalid input.
     * @param invalidInput The input that couldn't be parsed
     */
    public SnowInvalidDateException(String invalidInput) {
        super("Invalid date: '" + invalidInput + "'. Please use: yyyy-MM-dd [HH:mm] or d/M/yyyy [HH:mm]");
    }
}
