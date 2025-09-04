package snow.model;

import java.time.LocalDate;

/**
 * Represents a todo task.
 */
public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        return false;
    }

    @Override
    public String toSaveString() {
        return "T | " + super.toSaveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
