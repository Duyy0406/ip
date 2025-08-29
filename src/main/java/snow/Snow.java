package snow;

import snow.exception.SnowException;

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
            ui.printLine();
            try {
                Command cmd = Parser.getCmd(input);
                switch (cmd) {
                    case BYE:
                        ui.print("Bye! Stay cool and see you again soon!");
                        ui.printLine();
                        return;

                    case LIST:
                        ui.printList(TASKS);
                        break;

                    case MARK: {
                        String[] parts = Parser.splitCommand(input);
                        int index = Integer.parseInt(parts[1]) - 1;
                        TASKS.mark(index);
                        storage.save(TASKS);
                        ui.printMark(TASKS.get(index));
                        break;
                    }

                    case UNMARK: {
                        String[] parts = Parser.splitCommand(input);
                        int index = Integer.parseInt(parts[1]) - 1;
                        TASKS.unmark(index);
                        storage.save(TASKS);
                        ui.printUnmark(TASKS.get(index));
                        break; // prevent fall-through
                    }

                    case TODO: {
                        String[] parts = Parser.splitCommand(input);
                        Todo todo = new Todo(parts[1]);
                        TASKS.add(todo);
                        storage.save(TASKS);
                        ui.printAdd(todo, TASKS.size());
                        break;
                    }

                    case DEADLINE: {
                        String[] parts = Parser.splitDeadline(input);
                        Deadline deadline = new Deadline(parts[0], parts[1]);
                        TASKS.add(deadline);
                        storage.save(TASKS);
                        ui.printAdd(deadline, TASKS.size());
                        break;
                    }

                    case EVENT: {
                        String[] parts = Parser.splitEvent(input);
                        Event event = new Event(parts[0], parts[1], parts[2]);
                        TASKS.add(event);
                        storage.save(TASKS);
                        ui.printAdd(event, TASKS.size());
                        break;
                    }

                    case DELETE: {
                        String[] parts = Parser.splitCommand(input);
                        int index = Integer.parseInt(parts[1]) - 1;
                        Task removed = TASKS.remove(index);
                        storage.save(TASKS);
                        ui.printDelete(removed, TASKS.size());
                        break;
                    }

                    case UNKNOWN:
                        ui.print("Unknown command.");
                        break;
                }
            } catch (SnowException e) {
                ui.print(e.getMessage());
            }
            ui.printLine();
        }

    }
}
