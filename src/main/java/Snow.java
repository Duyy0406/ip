import java.util.Scanner;

public class Snow {
    private static final String NAME = "Snow";

    private static void line() {
        System.out.println("    ____________________________________________________________");
    }

    private static void say(String cmd) {
        line();
        System.out.println("    " + cmd);
        line();
    }

    private static void handle_input() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equals("bye")) {
                return;
            }
            else {
                say(input);
            }
        }
    }
    public static void main(String[] args) {
        say(" Hello! I'm " + NAME + "\n     What can I do for you?");
        handle_input();
        say(" Bye! Stay cool and see you again soon!");
    }
}
