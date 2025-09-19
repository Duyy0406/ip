package snow.exception;

/**
 * Exception thrown for task-related errors (empty description, missing dates, etc.).
 */
public class SnowTaskException extends SnowException {

    /**
     * Private constructor - use static factory methods instead.
     */
    private SnowTaskException(String message) {
        super(message);
    }

    /**
     * Creates exception for empty task description.
     */
    public static SnowTaskException emptyDescription(String taskType) {
        return new SnowTaskException("The description of a " + taskType + " cannot be empty.");
    }

    /**
     * Creates exception for missing date.
     */
    public static SnowTaskException missingDate(String taskType) {
        return new SnowTaskException("Please specify a date for the " + taskType + ".");
    }

    /**
     * Creates exception for missing end time in events.
     */
    public static SnowTaskException missingEndTime() {
        return new SnowTaskException("Event must have both start time (/from) and end time (/to).");
    }

    /**
     * Creates exception for invalid time order in events.
     */
    public static SnowTaskException invalidTimeOrder() {
        return new SnowTaskException("Event start time must be before end time.");
    }

    /**
     * Creates exception for invalid task index.
     */
    public static SnowTaskException invalidIndex(int index, int maxSize) {
        return new SnowTaskException("Task number " + index + " does not exist. You have " + maxSize + " tasks.");
    }

    /**
     * Creates exception for missing task number in commands like mark, unmark, delete.
     */
    public static SnowTaskException missingTaskNumber(String commandName) {
        return new SnowTaskException("Please specify which task to " + commandName
                + ". Usage: " + commandName + " <task number>");
    }
}
