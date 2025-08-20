public class Snow {
    private static final String NAME = "Snow";
    private static final TaskList tasks = new TaskList(100);


    public static void main(String[] args) {
        Ui ui = new Ui();
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
            else if (input.startsWith("todo")) {
                ToDo task = new ToDo(Parser.splitCommand(input)[1]);
                if (tasks.add(task)) {
                    ui.printAdd(task, tasks.size());
                }
                else {
                    ui.printFull();
                }
            }
            else if (input.startsWith("deadline")) {
                String[] parts = Parser.splitDeadline(input);
                Deadline task = new Deadline(parts[0], parts[1]);
                if (tasks.add(task)) {
                    ui.printAdd(task, tasks.size());
                }
                else {
                    ui.printFull();
                }
            }
            else if (input.startsWith("event")) {
                String[] parts = Parser.splitEvent(input);
                Event task = new Event(parts[0], parts[1], parts[2]);
                if (tasks.add(task)) {
                    ui.printAdd(task, tasks.size());
                }
                else {
                    ui.printFull();
                }
            }
            else {
                ui.printInvalid();
            }
            ui.printLine();
        }
    }
}
