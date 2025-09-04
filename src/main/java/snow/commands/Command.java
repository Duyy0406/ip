package snow.commands;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the set of valid commands that can be issued by the user.
 */
public abstract class Command {
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
}
