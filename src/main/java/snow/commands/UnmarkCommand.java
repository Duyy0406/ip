package snow.commands;

import snow.exception.SnowException;
import snow.exception.SnowInvalidDescriptionException;
import snow.exception.SnowInvalidIndexException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the Unmark command.
 */
public class UnmarkCommand extends Command {

    private static final String UNMARK = "OK, I've marked this task as not done yet:";
    private final int index;

    /**
     * Constructs an UnmarkCommand with the given description.
     * @param description The description containing the task index to unmark
     * @throws SnowException if the description is invalid
     */
    public UnmarkCommand(String description) throws SnowException {
        try {
            index = Integer.parseInt(description) - 1;
        } catch (NumberFormatException e) {
            throw new SnowInvalidDescriptionException();
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        resetString();
        if (index >= tasks.size() || index < 0) {
            throw new SnowInvalidIndexException();
        }
        tasks.unmark(index);
        storage.save(tasks);
        command.append(UNMARK).append('\n').append(tasks.get(index));
        ui.printUnmark(tasks.get(index));
    }
}
