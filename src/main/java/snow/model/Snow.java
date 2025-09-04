package snow.model;

import snow.commands.Command;
import snow.exception.SnowException;
import snow.io.Parser;
import snow.io.Storage;
import snow.io.Ui;

/**
 * Represents the main entry point of the Snow application.
 * Handles user interaction, parses commands, and delegates task operations.
 */
public class Snow {

    /** Display name of the assistant. */
    private static final String NAME = "snow";

    /** Central list of tasks managed by the application. */
    private static final TaskList TASKS = new TaskList();

    /** The file path for the storage */
    private static final String FILE_PATH = "data/snow.txt";

    /**
     * Runs the Snow application.
     *
     * Initializes the user interface, greets the user, and processes commands
     * until the user exits.
     *
     * @param args argument
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.printLine();
        ui.print("Hello! I'm " + NAME);
        ui.print("What can I do for you?");
        ui.printLine();

        Storage storage = new Storage(FILE_PATH);
        storage.load(TASKS);

        while (true) {
            String input = ui.getInput();
            if (input == null) {
                ui.printBye();
                break;
            }
            ui.printLine();
            try {
                Command cmd = Parser.getCmd(input);
                cmd.execute(TASKS, ui, storage);
                if (cmd.isExit()) {
                    break;
                }
            } catch (SnowException e) {
                ui.print(e.getMessage());
            }
            ui.printLine();
        }

    }
}
