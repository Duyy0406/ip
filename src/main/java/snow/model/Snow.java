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

    /** The file path for the STORAGE */
    private static final String FILE_PATH = "data/snow.txt";

    /** The Ui for printing */
    private static final Ui UI = new Ui();

    /** The Storage for saving data */
    private static final Storage STORAGE = new Storage(FILE_PATH);

    private String commandType;

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.getCmd(input);
            c.execute(TASKS, UI, STORAGE);
            commandType = c.getClass().getSimpleName();
            return c.getString();
        } catch (SnowException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Runs the Snow application.
     *
     * Initializes the user interface, greets the user, and processes commands
     * until the user exits.
     *
     * @param args argument
     */
    public static void main(String[] args) {
        UI.printLine();
        UI.print("Hello! I'm " + NAME);
        UI.print("What can I do for you?");
        UI.printLine();

        STORAGE.load(TASKS);

        while (true) {
            String input = UI.getInput();
            if (input == null) {
                UI.printBye();
                break;
            }
            UI.printLine();
            try {
                Command cmd = Parser.getCmd(input);
                cmd.execute(TASKS, UI, STORAGE);
                if (cmd.isExit()) {
                    break;
                }
            } catch (SnowException e) {
                UI.print(e.getMessage());
            }
            UI.printLine();
        }

    }
}
