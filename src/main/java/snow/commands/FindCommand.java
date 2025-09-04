package snow.commands;

import java.util.List;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.Task;
import snow.model.TaskList;

/**
 * Represents the Find command.
 */
public class FindCommand extends Command {

    private final String pattern;

    /**
     * Constructs a FindCommand with the given pattern.
     * @param pattern The pattern to search for in task descriptions
     */
    public FindCommand(String pattern) {
        this.pattern = pattern;
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        List<Task> tasksFound = tasks.find(pattern);
        ui.printFind(tasksFound);
    }
}
