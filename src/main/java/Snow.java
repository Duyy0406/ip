public class Snow {
    private static final String NAME = "Snow";
    private static final TaskList tasks = new TaskList();


    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.printLine();
        ui.print("Hello! I'm " + NAME);
        ui.print("What can I do for you?");
        ui.printLine();
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
                        ui.printList(tasks);
                        break;
                    case MARK: {
                        int index = Integer.parseInt(Parser.splitCommand(input)[1]) - 1;
                        tasks.mark(index);
                        ui.printMark(tasks.get(index));
                        break;
                    }
                    case UNMARK: {
                        int index = Integer.parseInt(Parser.splitCommand(input)[1]) - 1;
                        tasks.unmark(index);
                        ui.printUnmark(tasks.get(index));
                    }
                    case TODO: {
                        ToDo todo = new ToDo(Parser.splitCommand(input)[1]);
                        tasks.add(todo);
                        ui.printAdd(todo, tasks.size());
                        break;
                    }
                    case DEADLINE: {
                        String[] parts = Parser.splitDeadline(input);
                        Deadline deadline = new Deadline(parts[0], parts[1]);
                        tasks.add(deadline);
                        ui.printAdd(deadline, tasks.size());
                        break;
                    }
                    case EVENT: {
                        String[] parts = Parser.splitEvent(input);
                        Event event = new Event(parts[0], parts[1], parts[2]);
                        tasks.add(event);
                        ui.printAdd(event, tasks.size());
                        break;
                    }
                    case DELETE: {
                        String[] parts = Parser.splitCommand(input);
                        int index = Integer.parseInt(parts[1]) - 1;
                        Task removed = tasks.remove(index);
                        ui.printDelete(removed, tasks.size());
                        break;
                    }
                }
            }
            catch (SnowException e) {
                ui.print(e.getMessage());
            }
            ui.printLine();
        }
    }
}
