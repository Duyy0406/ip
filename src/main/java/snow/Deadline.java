package snow;

import java.time.LocalDateTime;

/**
 * Represents a task with a deadline.
 * A Deadline has a description and a due date.
 */
public class Deadline extends Task {

    /** Due date of the deadline task. */
    private final LocalDateTime date;

    /**
     * Creates a deadline task with the specified description and due date.
     *
     * @param name Description of the deadline task.
     * @param date Due date of the deadline task.
     */
    public Deadline(String name, String date) {
        super(name);
        this.date = DateTime.parse(date);
    }

    public Deadline(String name, LocalDateTime date) {
        super(name);
        this.date = date;
    }

    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + this.date;
    }

    /**
     * {@inheritDoc}
     * Returns a string representation of the deadline, including its type,
     * description, and due date.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date.format(DateTime.OUT_DT) + ")";
    }
}
