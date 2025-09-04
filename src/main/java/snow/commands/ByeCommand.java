package snow.commands;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the Bye command.
 */
public class ByeCommand extends Command {


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        ui.printBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
