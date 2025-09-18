package snow.io;

import java.time.LocalDate;
import java.time.LocalDateTime;

import snow.commands.AddCommand;
import snow.commands.ByeCommand;
import snow.commands.Command;
import snow.commands.DeleteCommand;
import snow.commands.FindByDateCommand;
import snow.commands.FindCommand;
import snow.commands.ListCommand;
import snow.commands.MarkCommand;
import snow.commands.PlacesCommand;
import snow.commands.UnmarkCommand;
import snow.datetime.DateTime;
import snow.exception.SnowException;
import snow.exception.SnowInvalidCommandException;
import snow.exception.SnowInvalidDateException;
import snow.exception.SnowTaskException;
import snow.model.Deadline;
import snow.model.Event;
import snow.model.Place;
import snow.model.PlaceRegistry;
import snow.model.Task;
import snow.model.Todo;

/**
 * Provides methods to parse user input into commands and arguments.
 */
public class Parser {

    /**
     * Returns the task description and place name.
     * @param description Input string including place
     */
    public static String[] parsePlace(String description) {
        return description.split("\\s*/at\\s*", 2);
    }

    /**
     * Returns the command type from a given user input string.
     *
     * @param cmd User input string.
     * @return Command corresponding to the first word of the input.
     * @throws SnowInvalidCommandException If the input does not start with a valid command.
     */
    public static Command getCmd(String cmd) throws SnowException {
        // Sanitize input: trim and handle multiple spaces
        if (cmd == null) {
            throw new SnowInvalidCommandException("Command cannot be null");
        }

        cmd = cmd.trim();
        if (cmd.isEmpty()) {
            throw new SnowInvalidCommandException("Command cannot be empty");
        }

        // Replace multiple spaces with single space
        cmd = cmd.replaceAll("\\s+", " ");

        String[] parts = cmd.split(" ", 2);
        if (parts.length == 0) {
            throw new SnowInvalidCommandException("Command cannot be empty");
        }

        String firstWord = parts[0].toLowerCase(); // Case insensitive commands
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
        case "findbydate" -> createFindByDateCommand(description);
        case "places" -> new PlacesCommand();
        case "bye" -> new ByeCommand();
        default -> throw new SnowInvalidCommandException(firstWord);
        };
    }

    /**
     * Validates that all required parts are present and non-blank.
     *
     * @param parts the parts to validate
     * @return true if all parts are valid
     */
    private static boolean isInvalid(String... parts) {
        for (String part : parts) {
            if (part == null || part.isBlank()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a FindByDateCommand from the given date description.
     *
     * <p>Expected format: {@code findbydate <date>} where date can be in YYYY-MM-DD or D/M/YYYY format.
     *
     * @param description the date description
     * @return a FindByDateCommand for the specified date
     * @throws SnowException if the date format is invalid
     */
    private static FindByDateCommand createFindByDateCommand(String description) throws SnowException {
        if (description == null || description.isBlank()) {
            throw SnowTaskException.missingDate("findbydate");
        }

        try {
            // Use DateTime parser to support same formats as other commands
            LocalDateTime dateTime = DateTime.parse(description.trim());
            LocalDate date = dateTime.toLocalDate();
            return new FindByDateCommand(date);
        } catch (Exception e) {
            throw new SnowInvalidDateException(description);
        }
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
        if (parts.length == 0 || isInvalid(parts[0])) {
            throw SnowTaskException.emptyDescription("deadline");
        }
        if (parts.length == 1 || isInvalid(parts[1])) {
            throw SnowTaskException.missingDate("deadline");
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
     * @throws SnowDataValidationException if start date is after end date
     */
    public static Command splitEvent(String description) throws SnowException {
        if (description == null || description.trim().isEmpty()) {
            throw SnowTaskException.emptyDescription("event");
        }

        // Check for missing /to keyword
        if (!description.contains("/to")) {
            throw SnowTaskException.missingEndTime();
        }

        String[] parts = description.split("\\s*/from\\s*", 2);
        if (parts.length == 0 || isInvalid(parts[0])) {
            throw SnowTaskException.emptyDescription("event");
        }
        if (parts.length == 1 || isInvalid(parts[1])) {
            throw SnowTaskException.missingDate("event");
        }

        String[] dates = parts[1].split("\\s*/to\\s*", 2);
        if (dates.length < 2 || isInvalid(dates[0], dates[1])) {
            throw SnowTaskException.missingEndTime();
        }

        String fromDate = dates[0].trim();
        String toDate = dates[1].trim();

        // Validate that both dates can be parsed
        LocalDateTime startTime = DateTime.parse(fromDate);
        LocalDateTime endTime = DateTime.parse(toDate);

        // Validate that start is before end
        if (!startTime.isBefore(endTime)) {
            throw SnowTaskException.invalidTimeOrder();
        }

        return AddCommand.event(parts[0], startTime, endTime);
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
            String statusStr = parts[1];

            // Strict validation: only accept "0" or "1"
            if (!"0".equals(statusStr) && !"1".equals(statusStr)) {
                return null; // Invalid status value
            }

            boolean isDone = "1".equals(statusStr);
            String name = parts[2];

            Task t = null;
            if ("T".equals(type)) {
                t = new Todo(name);
            } else if ("D".equals(type)) {
                // For deadline, find the date (it should be the last part)
                String dateStr = parts[parts.length - 1];
                t = new Deadline(name, LocalDateTime.parse(dateStr));
            } else if ("E".equals(type)) {
                // For event, find the start and end dates (last two parts)
                String startStr = parts[parts.length - 2];
                String endStr = parts[parts.length - 1];
                t = new Event(name, LocalDateTime.parse(startStr), LocalDateTime.parse(endStr));
            }

            if (t != null) {
                if (isDone) {
                    t.mark();
                }

                // Handle place information if present
                for (int i = 3; i < parts.length; i++) {
                    String part = parts[i];
                    if (part.startsWith("pid=")) {
                        int placeId = Integer.parseInt(part.substring(4));
                        if (placeId != -1) { // -1 means no place
                            Place place = PlaceRegistry.findById(placeId);
                            if (place != null) {
                                t.setPlace(place);
                            }
                        }
                        break;
                    }
                }
            }
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parses a place from a storage line.
     *
     * @param line serialized place line
     * @return a {@link Place} instance, or {@code null} if not a place or parsing fails
     */
    public static Place parsePlaceFromStorage(String line) {
        try {
            String[] parts = line.split(" \\| ");

            if (parts.length >= 3 && "P".equals(parts[0])) {
                int id = Integer.parseInt(parts[1]);
                String name = parts[2];
                return new Place(id, name);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}
