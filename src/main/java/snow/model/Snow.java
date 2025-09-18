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

    /** Central list of tasks managed by the application. */
    private static final TaskList TASKS = new TaskList();

    /** The file path for the STORAGE */
    private static final String FILE_PATH = "data/snow.txt";

    /** The Ui for printing */
    private static final Ui UI = new Ui();

    /** The Storage for saving data */
    private static final Storage STORAGE = new Storage(FILE_PATH);

    private String commandType;
    private boolean shouldExit = false;

    public Snow() {
        STORAGE.load(TASKS);
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.getCmd(input);
            assert c != null : "Parser must return a valid command";
            c.execute(TASKS, UI, STORAGE);
            commandType = c.getClass().getSimpleName();
            shouldExit = c.isExit();
            return c.getString();
        } catch (SnowException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String getCommandType() {
        return commandType;
    }

    public boolean shouldExit() {
        return shouldExit;
    }

    /**
     * Runs the Snow application.
     * Initializes the user interface, greets the user, and processes commands
     * until the user exits.
     *
     * @param args argument
     */
    public static void main(String[] args) {
        UI.printGreeting();

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
