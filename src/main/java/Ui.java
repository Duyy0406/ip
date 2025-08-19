import java.util.Scanner;

public class Ui {
    private static final String LIST = "Here are the tasks in your list:";
    private static final String MARK = "Nice! I've marked this task as done:";
    private static final String UNMARK = "OK, I've marked this task as not done yet:";
    private static final String INDENT = "     ";
    private static final String LINE = "    ____________________________________________________________";

    private final Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public void print(String command) {
        System.out.println(INDENT + command);
    }

    public void printLine() {
        System.out.println(LINE);
    }

    public String getInput() {
        return sc.nextLine();
    }

    public void printList(TaskList tasks) {
        print(LIST);
        for (int i = 0; i < tasks.size(); ++i) {
            print("  " + (i + 1) + "." + tasks.get(i));
        }
    }

    public void printMark(Task task) {
        print(MARK);
        print("  " + task);
    }

    public void printUnmark(Task task) {
        print(UNMARK);
        print("  " + task);
    }
}
