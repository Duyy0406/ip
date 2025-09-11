package snow.model;

import java.time.LocalDate;

/**
 * Represents a task with a description and completion status.
 * A Task can be marked as done or undone.
 */
public abstract class Task {

    /** Description of the task. */
    private final String name;

    /** Completion status of the task. */
    private boolean done;

    /**
     * Creates a task with the specified description.
     * The task is initially not marked as done.
     *
     * @param name Description of the task.
     */
    public Task(String name) {
        assert name != null && !name.trim().isEmpty() : "Task name cannot be empty or blank";

        this.name = name;
        this.done = false;

    }

    /**
     * Gets the description of the task.
     */
    public String getDescription() {
        return this.name;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.done = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.done = false;
    }

    /**
     * Checks if this task is on a date
     * @param date Date that needs to be checked
     */
    public abstract boolean isOnDate(LocalDate date);

    public String toSaveString() {
        return (this.done ? "1" : "0") + " | " + this.name;
    }

    public boolean isDone() {
        return this.done;
    }

    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + name;
    }
}
