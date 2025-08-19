public class Parser {
    public static String[] splitCommand(String cmd) {
        return cmd.split(" ", 2);
    }

    public static String parse(String fullCommand) {
        String[] parts = splitCommand(fullCommand);
        return parts[0];
    }
}
