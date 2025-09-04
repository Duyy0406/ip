package snow.commands;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the List command with multiple types of tasks.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        ui.printList(tasks);
    }
}
