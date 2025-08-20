public class Parser {
    public static String[] splitCommand(String cmd) {
        return cmd.split(" ", 2);
    }

    public static String[] splitDeadline(String deadline) {
        return splitCommand(deadline)[1].split(" /by ", 2);
    }

    public static String[] splitEvent(String event) {
        String[] parts = splitCommand(event)[1].split(" /from ", 2);
        String[] dates = parts[1].split(" /to ", 2);
        return new String[] {parts[0], dates[0], dates[1]};
    }
}
