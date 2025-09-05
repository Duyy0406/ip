package snow.commands;

import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.TaskList;

/**
 * Represents the Bye command.
 */
public class ByeCommand extends Command {

    private static final String BYE = "Bye! Stay cool and see u again soon!";

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        resetString();
        command.append(BYE);
        ui.printBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
