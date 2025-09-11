package snow.commands;

import java.time.LocalDate;
import java.util.List;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.Task;
import snow.model.TaskList;

/**
 * Represents the FindByDate command.
 */
public class FindByDateCommand extends Command {

    private static final String FIND_DATE = "Here are the tasks on the specified date:";

    private final LocalDate date;

    /**
     * Constructs a FindByDateCommand with the given date.
     * @param date The date to search for in tasks
     */
    public FindByDateCommand(LocalDate date) {
        this.date = date;
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        resetString();
        List<Task> tasksFound = tasks.findTaskWithDate(date);
        command.append(FIND_DATE);
        for (int i = 0; i < tasksFound.size(); ++i) {
            command.append("\n").append("  ").append(i + 1).append(".").append(tasksFound.get(i));
        }
        ui.printFind(tasksFound);
    }
}
