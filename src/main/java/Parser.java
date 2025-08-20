public class Parser {
    public static Command getCmd(String cmd) throws SnowInvalidCommandException {
        String[] parts = cmd.split(" ");
        if (parts.length == 0) {
            throw new SnowInvalidCommandException();
        }
        String firstWord = parts[0];
        switch (firstWord) {
            case "todo": return Command.TODO;
            case "deadline": return Command.DEADLINE;
            case "event": return Command.EVENT;
            case "mark": return Command.MARK;
            case "unmark": return Command.UNMARK;
            case "list": return Command.LIST;
            case "delete": return Command.DELETE;
            case "bye": return Command.BYE;
            default: throw new SnowInvalidCommandException();
        }

    }
    public static String[] splitCommand(String cmd) throws SnowEmptyTaskException {
        String[] parts = cmd.split(" ", 2);
        if (parts.length < 2) {
            throw new SnowEmptyTaskException(parts[0]);
        }
        return parts;
    }

    public static String[] splitDeadline(String deadline) throws SnowEmptyDateException, SnowEmptyTaskException {
        String[] parts = splitCommand(deadline)[1].split(" /by ", 2);
        if (parts.length < 2) {
            throw new SnowEmptyDateException("deadline");
        }
        return parts;
    }

    public static String[] splitEvent(String event) throws SnowEmptyDateException, SnowEmptyTaskException {
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
