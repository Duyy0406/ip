package snow.commands;

import snow.exception.SnowException;
import snow.exception.SnowTaskException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.Task;
import snow.model.TaskList;

/**
 * Represents the Delete command with multiple types of tasks.
 */
public class DeleteCommand extends Command {

    private static final String DELETE = "Noted. I've removed this task:";

    private final int index;

    /**
     * Constructs a DeleteCommand with the given description.
     * @param description The description containing the task index to delete
     * @throws SnowException if the description is invalid
     */
    public DeleteCommand(String description) throws SnowException {
        if (description == null || description.trim().isEmpty()) {
            throw SnowTaskException.missingTaskNumber("delete");
        }

        try {
            index = Integer.parseInt(description.trim()) - 1;
        } catch (NumberFormatException e) {
            throw SnowTaskException.missingTaskNumber("delete");
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        resetString();
        if (index >= tasks.size() || index < 0) {
            throw SnowTaskException.invalidIndex(index + 1, tasks.size());
        }
        Task removed = tasks.remove(index);
        storage.save(tasks);
        command.append(DELETE).append('\n').append("  ").append(removed).append('\n')
                .append("Now you have ").append(tasks.size()).append(" tasks in your list");
        ui.printDelete(removed, tasks.size());
    }
}
