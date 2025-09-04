package snow.model;


import java.time.LocalDate;
import java.time.LocalDateTime;

import snow.datetime.DateTime;

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
     * @param name Description of the deadline task
     * @param date Due date of the deadline task (parsed from String)
     */
    public Deadline(String name, String date) {
        super(name);
        this.date = DateTime.parse(date);
    }

    /**
     * Creates a deadline with the specified description and datetime.
     * @param name Description of the deadline task
     * @param date Due date of the deadline task
     */
    public Deadline(String name, LocalDateTime date) {
        super(name);
        this.date = date;
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        return !date.isAfter(this.date.toLocalDate());
    }

    @Override
    public String toSaveString() {
        return "D | " + super.toSaveString() + " | " + this.date;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + date.format(DateTime.OUT_DT) + ")";
    }
}
