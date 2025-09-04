package snow.exception;

/**
 * Exception thrown when a task description is empty.
 */
public class SnowEmptyTaskException extends SnowException {
    /**
     * Constructs a SnowEmptyTaskException with a message specifying the task type.
     * @param type The type of task that has an empty description
     */
    public SnowEmptyTaskException(String type) {

        super("Oops! The description of " + type + " cannot be empty!");
    }
}
