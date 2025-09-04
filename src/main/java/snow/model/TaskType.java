package snow.model;

/**
 * Enumerates the kinds of tasks supported by the application.
 */
public enum TaskType {
    TODO("todo"),
    DEADLINE("deadline"),
    EVENT("event");

    private final String label;

    /**
     * Creates a task type with a display label.
     *
     * @param label lowercase string used for display/serialization
     */
    TaskType(String label) {
        this.label = label;
    }

    /**
     * Returns the display label of this task type.
     *
     * @return label such as {@code "todo"}, {@code "deadline"}, or {@code "event"}
     */
    @Override
    public String toString() {
        return this.label;
    }
}
