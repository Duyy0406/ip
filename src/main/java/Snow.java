public class Snow {
    private static final String NAME = "Snow";
    private static final TaskList tasks = new TaskList(100);
    private static Ui ui;


    public static void main(String[] args) {
        ui = new Ui();
        ui.printLine();
        ui.print("Hello! I'm " + NAME);
        ui.print("What can I do for you?");
        ui.printLine();
        while (true) {
            String input = ui.getInput();
            ui.printLine();
            if (input.equals("bye")) {
                ui.print("Bye! Stay cool and see you again soon!");
                ui.printLine();
                return;
            }
            else if (input.equals("list")) {
                ui.printList(tasks);
            }
            else if (input.startsWith("mark")) {
                int index = Integer.parseInt(Parser.splitCommand(input)[1]) - 1;
                tasks.mark(index);
                ui.printMark(tasks.get(index));
            }
            else if (input.startsWith("unmark")) {
                int index = Integer.parseInt(Parser.splitCommand(input)[1]) - 1;
                tasks.unmark(index);
                ui.printUnmark(tasks.get(index));
            }
            else if (tasks.add(new Task(input))) {
                ui.print("added: " + input);
            }
            ui.printLine();
        }
    }
}
