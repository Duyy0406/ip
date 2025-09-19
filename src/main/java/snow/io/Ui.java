package snow.io;

import java.util.List;
import java.util.Scanner;

import snow.model.Place;
import snow.model.Task;
import snow.model.TaskList;

/**
 * Console user interface for printing messages and reading input.
 * <p>
 * Provides helpers for consistent formatting of list, add, delete,
 * mark/unmark, find and farewell messages.
 */
public class Ui {
    private static final String NAME = "Snow";
    private static final String LIST = "Here are the tasks in your list:";
    private static final String MARK = "Nice! I've marked this task as done:";
    private static final String UNMARK = "OK, I've marked this task as not done yet:";
    private static final String INDENT = "     ";
    private static final String LINE = "    ____________________________________________________________";
    private static final String ADD = "Got it. I've added this task:";
    private static final String DELETE = "Noted. I've removed this task:";
    private static final String FIND = "Here are the matching tasks in your list:";
    private static final String BYE = "Bye! Hope to see you again soon!";
    private static final String GREETING = "Hello! I'm " + NAME;
    private static final String ASK = "What can I do for you?";

    private final Scanner sc;

    /** Constructs a {@code Ui} that reads from standard input. */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Prints a single line with indentation.
     *
     * @param message the message to print
     */
    public void print(String message) {
        System.out.println(INDENT + message);
    }

    /** Prints a horizontal divider line. */
    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Reads the next line of user input if available.
     *
     * @return the input line, or {@code null} if no line is available
     */
    public String getInput() {
        return sc.hasNextLine() ? sc.nextLine() : null;
    }

    /**
     * Prints the greeting.
     */
    public void printGreeting() {
        printLine();
        print(GREETING);
        print(ASK);
        printLine();
    }

    /**
     * Prints the tasks in the provided list with indices.
     *
     * @param tasks the task list to display
     */
    public void printList(TaskList tasks) {
        print(LIST);
        if (tasks.size() == 0) {
            print("No tasks in your list yet.");
        } else {
            for (int i = 0; i < tasks.size(); ++i) {
                print("  " + (i + 1) + "." + tasks.get(i));
            }
        }
    }

    /**
     * Prints the "mark" acknowledgement with the task.
     *
     * @param task the task that was marked done
     */
    public void printMark(Task task) {
        print(MARK);
        print("  " + task);
    }

    /**
     * Prints the "unmark" acknowledgement with the task.
     *
     * @param task the task that was unmarked
     */
    public void printUnmark(Task task) {
        print(UNMARK);
        print("  " + task);
    }

    /**
     * Prints the "added" acknowledgement and current list size.
     *
     * @param task the task that was added
     * @param size the new size of the task list
     */
    public void printAdd(Task task, int size) {
        print(ADD);
        print("  " + task);
        print("Now you have " + size + " tasks in the list.");
    }

    /**
     * Prints the "deleted" acknowledgement and current list size.
     *
     * @param task the task that was deleted
     * @param size the new size of the task list
     */
    public void printDelete(Task task, int size) {
        print(DELETE);
        print("  " + task);
        print("Now you have " + size + " tasks in your list.");
    }

    /**
     * Prints the results of a find operation with indices.
     *
     * @param tasksFound the tasks that matched the query
     */
    public void printFind(List<Task> tasksFound) {
        print(FIND);
        if (tasksFound.size() == 0) {
            print("No matching tasks found.");
        } else {
            for (int i = 0; i < tasksFound.size(); ++i) {
                print((i + 1) + "." + tasksFound.get(i));
            }
        }
    }

    /** Prints the exit message. */
    public void printBye() {
        print(BYE);
    }

    /**
     * Prints all saved places.
     *
     * @param places the list of places to display
     */
    public void printPlaces(List<Place> places) {
        print("Here are all the saved places:");
        if (places.isEmpty()) {
            print("No places have been saved yet.");
        } else {
            for (int i = 0; i < places.size(); i++) {
                print((i + 1) + ". " + places.get(i).getName());
            }
        }
    }
}
