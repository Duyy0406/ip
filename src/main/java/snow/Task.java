package snow;

/**
 * Represents a task with a description and completion status.
 * A Task can be marked as done or undone.
 */
public class Task {

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
        this.name = name;
        this.done = false;
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

    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + name;
    }
}
