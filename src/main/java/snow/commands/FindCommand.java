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

    private static final String FIND = "Here are the matching tasks in your list:";

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
        resetString();
        List<Task> tasksFound = tasks.find(pattern);
        command.append(FIND);
        for (int i = 0; i < tasksFound.size(); ++i) {
            command.append("\n").append("  ").append(i + 1).append(".").append(tasksFound.get(i));
        }
        ui.printFind(tasksFound);
    }
}
