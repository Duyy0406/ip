package snow.commands;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the set of valid commands that can be issued by the user.
 */
public abstract class Command {

    protected StringBuilder command;
    /**
     * Executes the commands
     * @param tasks List of tasks
     * @param ui The user interface for inputs and outputs
     * @param storage The storage to save existing data
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException;

    public boolean isExit() {
        return false;
    }

    /**
     * Gets the String of the command.
     * @return The String represents the command
     */
    public String getString() {
        return command.toString();
    }

    /**
     * Resets command string.
     */
    public void resetString() {
        command = new StringBuilder();
    }
}
