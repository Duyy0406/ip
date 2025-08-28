package snow;

import snow.exception.SnowEmptyDateException;
import snow.exception.SnowEmptyTaskException;
import snow.exception.SnowInvalidCommandException;


/**
 * Provides methods to parse user input into commands and arguments.
 */
public class Parser {

    /**
     * Returns the command type from a given user input string.
     *
     * @param cmd User input string.
     * @return Command corresponding to the first word of the input.
     * @throws SnowInvalidCommandException If the input does not start with a valid command.
     */
    public static Command getCmd(String cmd) throws SnowInvalidCommandException {
        String[] parts = cmd.split(" ");
        if (parts.length == 0) {
            throw new SnowInvalidCommandException();
        }
        String firstWord = parts[0];
        switch (firstWord) {
            case "todo":
                return Command.TODO;
            case "deadline":
                return Command.DEADLINE;
            case "event":
                return Command.EVENT;
            case "mark":
                return Command.MARK;
            case "unmark":
                return Command.UNMARK;
            case "list":
                return Command.LIST;
            case "delete":
                return Command.DELETE;
            case "bye":
                return Command.BYE;
            default:
                throw new SnowInvalidCommandException();
        }
    }

    /**
     * Returns the command word and the remainder of the input.
     *
     * @param cmd User input string.
     * @return Array containing command word at index 0 and arguments at index 1.
     * @throws SnowEmptyTaskException If no arguments are provided.
     */
    public static String[] splitCommand(String cmd) throws SnowEmptyTaskException {
        String[] parts = cmd.split(" ", 2);
        if (parts.length < 2) {
            throw new SnowEmptyTaskException(parts[0]);
        }
        return parts;
    }

    /**
     * Returns the description and due date from a deadline command.
     *
     * @param deadline User input string for a deadline command.
     * @return Array containing description at index 0 and due date at index 1.
     * @throws SnowEmptyDateException If the date is missing.
     * @throws SnowEmptyTaskException If the description is missing.
     */
    public static String[] splitDeadline(String deadline)
            throws SnowEmptyDateException, SnowEmptyTaskException {
        String[] parts = splitCommand(deadline)[1].split(" /by ", 2);
        if (parts.length < 2) {
            throw new SnowEmptyDateException("deadline");
        }
        return parts;
    }

    /**
     * Returns the description, start, and end times from an event command.
     *
     * @param event User input string for an event command.
     * @return Array containing description at index 0, start time at index 1, and end time at index 2.
     * @throws SnowEmptyDateException If start or end times are missing.
     * @throws SnowEmptyTaskException If the description is missing.
     */
    public static String[] splitEvent(String event)
            throws SnowEmptyDateException, SnowEmptyTaskException {
        String[] parts = splitCommand(event)[1].split(" /from ", 2);
        if (parts.length < 2) {
            throw new SnowEmptyDateException("event");
        }
        String[] dates = parts[1].split(" /to ", 2);
        if (dates.length < 2) {
            throw new SnowEmptyDateException("event");
        }
        return new String[] {parts[0], dates[0], dates[1]};
    }
}
