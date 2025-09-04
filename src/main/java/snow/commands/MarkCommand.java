package snow.commands;

import snow.exception.SnowException;
import snow.exception.SnowInvalidDescriptionException;
import snow.exception.SnowInvalidIndexException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the Mark command.
 */
public class MarkCommand extends Command {

    private final int index;

    /**
     * Constructs a MarkCommand with the given description.
     * @param description The description containing the task index to mark
     * @throws SnowException if the description is invalid
     */
    public MarkCommand(String description) throws SnowException {
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
        tasks.mark(index);
        storage.save(tasks);
        ui.printMark(tasks.get(index));
    }
}
