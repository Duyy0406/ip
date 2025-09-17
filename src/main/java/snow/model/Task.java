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

    private Place place = Place.NONE;

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

    /**
     * Returns the string representation of this task for saving to storage.
     * @return A formatted string containing task data for persistence.
     */
    public String toSaveString() {
        String base = (this.done ? "1" : "0") + " | " + this.name;
        if (place != null) {
            base += " | at=" + place.getName() + " | pid=" + place.getId();
        }
        return base;
    }

    public boolean isDone() {
        return this.done;
    }

    /**
     * Checks if this task has a place attached to it.
     */
    public boolean hasPlace() {
        return place != Place.NONE;
    }

    /**
     * Sets this task's place to a place.
     * @param place Place that this task is attached to
     */
    public void setPlace(Place place) {
        this.place = place;
    }


    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + name
                + ((place != Place.NONE) ? " at " + place : "");
    }
}
