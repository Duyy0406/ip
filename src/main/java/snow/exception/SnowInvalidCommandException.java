package snow.exception;

/**
 * Exception thrown when an invalid command is provided.
 */
public class SnowInvalidCommandException extends SnowException {
    /**
     * Constructs a SnowInvalidCommandException with a default message.
     */
    public SnowInvalidCommandException() {
        super("Oops! I don't know what you mean :((  ... Can you try another message?");
    }

    /**
     * Constructs a SnowInvalidCommandException with a custom message.
     * @param command The invalid command that was attempted
     */
    public SnowInvalidCommandException(String command) {
        super("Unknown command: '" + command + "'. Try 'list', 'todo', 'deadline', 'event', "
                + "'mark', 'unmark', 'delete', 'find', 'findbydate', 'places', or 'bye'.");
    }
}
