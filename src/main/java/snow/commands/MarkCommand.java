package snow.commands;

import snow.exception.SnowException;
import snow.exception.SnowTaskException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the Mark command.
 */
public class MarkCommand extends Command {

    private static final String MARK = "Nice! I've marked this task as done:";

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
            throw SnowTaskException.invalidIndex(Integer.parseInt(description.trim().isEmpty() ? "0" : description), 0);
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        resetString();
        if (index >= tasks.size() || index < 0) {
            throw SnowTaskException.invalidIndex(index + 1, tasks.size());
        }
        tasks.mark(index);
        storage.save(tasks);
        command.append(MARK).append('\n').append(tasks.get(index));
        ui.printMark(tasks.get(index));
    }
}
