import java.util.Scanner;

public class Snow {
    private static final String NAME = "Snow";
    private static final TaskList tasks = new TaskList(100);
    private static final String INDENT = "     ";

    private static void line() {
        System.out.println("    ____________________________________________________________");
    }

    private static void say(String cmd) {
        line();
        System.out.println(INDENT + cmd);
        line();
    }

    private static void handle_input() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                return;
            }
            else if (input.equals("list")) {
                line();
                for (int i = 0; i < tasks.size(); ++i) {
                    System.out.println(INDENT + (i + 1) + ". " + tasks.get(i));
                }
                line();
            }
            else if (tasks.add(new Task(input))) {
                say("added: " + input);
            }
        }
    }
    public static void main(String[] args) {
        say("Hello! I'm " + NAME + "\n" + INDENT + "What can I do for you?");
        handle_input();
        say(" Bye! Stay cool and see you again soon!");
    }
}
