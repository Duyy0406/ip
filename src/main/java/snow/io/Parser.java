package snow.io;

import java.time.LocalDateTime;

import snow.commands.AddCommand;
import snow.commands.ByeCommand;
import snow.commands.Command;
import snow.commands.DeleteCommand;
import snow.commands.FindCommand;
import snow.commands.ListCommand;
import snow.commands.MarkCommand;
import snow.commands.UnmarkCommand;
import snow.datetime.DateTime;
import snow.exception.SnowEmptyDateException;
import snow.exception.SnowEmptyTaskException;
import snow.exception.SnowException;
import snow.exception.SnowInvalidCommandException;
import snow.model.Deadline;
import snow.model.Event;
import snow.model.Task;
import snow.model.Todo;

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
    public static Command getCmd(String cmd) throws SnowException {
        String[] parts = cmd.split(" ", 2);
        if (parts.length == 0) {
            throw new SnowInvalidCommandException();
        }
        String firstWord = parts[0];
        String description = (parts.length == 1) ? "" : parts[1];

        return switch (firstWord) {
        case "todo" -> AddCommand.todo(description);
        case "deadline" -> splitDeadline(description);
        case "event" -> splitEvent(description);
        case "mark" -> new MarkCommand(description);
        case "unmark" -> new UnmarkCommand(description);
        case "list" -> new ListCommand();
        case "delete" -> new DeleteCommand(description);
        case "find" -> new FindCommand(description);
        case "bye" -> new ByeCommand();
        default -> throw new SnowInvalidCommandException();
        };
    }

    /**
     * Validates that all required parts are present and non-blank.
     *
     * @param parts the parts to validate
     * @return true if all parts are valid
     */
    private static boolean validateParts(String... parts) {
        for (String part : parts) {
            if (part == null || part.isBlank()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Builds an add-deadline command from the given description.
     *
     * <p>Expected format: {@code deadline <desc> /by <datetime>}.
     *
     * @param description user input string for a deadline command
     * @return an {@link Command} that adds a deadline
     * @throws SnowEmptyDateException if the date is missing
     * @throws SnowEmptyTaskException if the description is missing
     */
    public static Command splitDeadline(String description) throws SnowException {
        String[] parts = description.split("\\s*/by\\s*", 2);
        if (parts.length == 0 || !validateParts(parts[0])) {
            throw new SnowEmptyTaskException("deadline");
        }
        if (parts.length == 1 || !validateParts(parts[1])) {
            throw new SnowEmptyDateException("deadline");
        }
        return AddCommand.deadline(parts[0], DateTime.parse(parts[1].trim()));
    }

    /**
     * Builds an add-event command from the given description.
     *
     * <p>Expected format: {@code event <desc> /from <start> /to <end>}.
     *
     * @param description user input string for an event command
     * @return an {@link Command} that adds an event
     * @throws SnowEmptyDateException if start or end times are missing
     * @throws SnowEmptyTaskException if the description is missing
     */
    public static Command splitEvent(String description) throws SnowException {
        String[] parts = description.split("\\s*/from\\s*", 2);
        if (parts.length == 0 || !validateParts(parts[0])) {
            throw new SnowEmptyTaskException("event");
        }
        if (parts.length == 1 || !validateParts(parts[1])) {
            throw new SnowEmptyDateException("event");
        }
        String[] dates = parts[1].split("\\s*/to\\s*", 2);
        if (dates.length < 2 || !validateParts(parts[0], parts[1])) {
            throw new SnowEmptyDateException("event");
        }
        String fromDate = dates[0].trim();
        String toDate = dates[1].trim();
        return AddCommand.event(parts[0], DateTime.parse(fromDate), DateTime.parse(toDate));
    }

    /**
     * Parses a saved line into a {@link Task}.
     *
     * <p>Expected saved formats (pipe-delimited):
     * <ul>
     *   <li>{@code T | 0|1 | name}</li>
     *   <li>{@code D | 0|1 | name | 2024-01-31T23:59}</li>
     *   <li>{@code E | 0|1 | name | 2024-01-31T10:00 | 2024-01-31T12:00}</li>
     * </ul>
     *
     * @param line serialized task line
     * @return a {@link Task} instance, or {@code null} if the type is unknown or parsing fails
     */
    public static Task parseLine(String line) {
        try {
            String[] parts = line.split(" \\| ");

            String type = parts[0];
            boolean isDone = "1".equals(parts[1]);
            String name = parts[2];

            Task t = null;
            if ("T".equals(type)) {
                t = new Todo(name);
            } else if ("D".equals(type)) {
                t = new Deadline(name, LocalDateTime.parse(parts[3]));
            } else if ("E".equals(type)) {
                t = new Event(name, LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
            }

            if (t != null && isDone) {
                t.mark();
            }
            return t;
        } catch (Exception ex) {
            return null;
        }
    }
}
