package snow.commands;

import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the List command with multiple types of tasks.
 */
public class ListCommand extends Command {

    private static final String LIST = "Here are the tasks in your list:";


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        resetString();
        command.append(LIST);
        if (tasks.size() == 0) {
            command.append("\n").append("No tasks in your list yet.");
        } else {
            for (int i = 0; i < tasks.size(); ++i) {
                command.append("\n").append(i + 1).append(".").append(tasks.get(i));
            }
        }
        ui.printList(tasks);
    }


}
