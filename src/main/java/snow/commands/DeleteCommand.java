package snow.commands;

import snow.exception.SnowException;
import snow.exception.SnowInvalidDescriptionException;
import snow.exception.SnowInvalidIndexException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.Task;
import snow.model.TaskList;

/**
 * Represents the Delete command with multiple types of tasks.
 */
public class DeleteCommand extends Command {

    private final int index;

    /**
     * Constructs a DeleteCommand with the given description.
     * @param description The description containing the task index to delete
     * @throws SnowException if the description is invalid
     */
    public DeleteCommand(String description) throws SnowException {
        try {
            index = Integer.parseInt(description) - 1;
        } catch (NumberFormatException e) {
            throw new SnowInvalidDescriptionException();
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        if (index >= tasks.size() || index < 0) {
            throw new SnowInvalidIndexException();
        }
        Task removed = tasks.remove(index);
        storage.save(tasks);
        ui.printDelete(removed, tasks.size());
    }
}
