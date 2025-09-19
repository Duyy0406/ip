package snow.gui;

/**
 * Enumeration of command types for styling dialog boxes.
 */
public enum CommandType {
    ADD_COMMAND("add-label"),
    MARK_COMMAND("marked-label"),
    UNMARK_COMMAND("unmark-label"),
    DELETE_COMMAND("delete-label"),
    LIST_COMMAND("list-label"),
    FIND_COMMAND("find-label"),
    FIND_BY_DATE_COMMAND("findbydate-label"),
    PLACES_COMMAND("places-label"),
    BYE_COMMAND("bye-label"),
    GREETING_COMMAND("greeting-label"),
    UNKNOWN_COMMAND(""); // No styling for unknown commands

    private final String styleClass;

    CommandType(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    /**
     * Gets the CommandType from a command class name.
     * @param commandClassName The simple class name of the command
     * @return The corresponding CommandType, or UNKNOWN_COMMAND if not found
     */
    public static CommandType fromClassName(String commandClassName) {
        switch (commandClassName) {
        case "AddCommand":
            return ADD_COMMAND;
        case "MarkCommand":
            return MARK_COMMAND;
        case "UnmarkCommand":
            return UNMARK_COMMAND;
        case "DeleteCommand":
            return DELETE_COMMAND;
        case "ListCommand":
            return LIST_COMMAND;
        case "FindCommand":
            return FIND_COMMAND;
        case "FindByDateCommand":
            return FIND_BY_DATE_COMMAND;
        case "PlacesCommand":
            return PLACES_COMMAND;
        case "ByeCommand":
            return BYE_COMMAND;
        case "GreetingCommand":
            return GREETING_COMMAND;
        default:
            return UNKNOWN_COMMAND;
        }
    }
}
