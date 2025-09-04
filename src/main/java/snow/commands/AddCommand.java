package snow.commands;

import java.time.LocalDateTime;

import snow.exception.SnowEmptyTaskException;
import snow.exception.SnowException;
import snow.io.Storage;
import snow.io.Ui;
import snow.model.Deadline;
import snow.model.Event;
import snow.model.Task;
import snow.model.TaskList;
import snow.model.TaskType;
import snow.model.Todo;

/**
 * Represents the add command with multiple types of tasks.
 */
public class AddCommand extends Command {

    private final TaskType type;
    private final String description;
    private final LocalDateTime by;
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs an AddCommand with the specified parameters.
     * @param type The type of task
     * @param description The description of the task
     * @param by The deadline datetime (for deadline tasks)
     * @param from The start datetime (for event tasks)
     * @param to The end datetime (for event tasks)
     */
    private AddCommand(TaskType type, String description,
                       LocalDateTime by, LocalDateTime from, LocalDateTime to) {
        this.type = type;
        this.description = description;
        this.by = by;
        this.from = from;
        this.to = to;
    }

    /**
     * Creates a todo task.
     * @param description The description of the todo task
     * @return An AddCommand for the todo task
     */
    public static AddCommand todo(String description) {
        return new AddCommand(TaskType.TODO, description, null, null, null);
    }

    /**
     * Creates a deadline task.
     * @param description The description of the deadline task
     * @param by The deadline datetime
     * @return An AddCommand for the deadline task
     */
    public static AddCommand deadline(String description, LocalDateTime by) {
        return new AddCommand(TaskType.DEADLINE, description, by, null, null);
    }

    /**
     * Creates an event task.
     * @param description The description of the event task
     * @param from The start datetime
     * @param to The end datetime
     * @return An AddCommand for the event task
     */
    public static AddCommand event(String description, LocalDateTime from, LocalDateTime to) {
        return new AddCommand(TaskType.EVENT, description, null, from, to);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws SnowException {
        if (description == null || description.isBlank()) {
            throw new SnowEmptyTaskException(this.type.toString());
        }

        Task task = switch (type) {
        case TODO -> new Todo(this.description);
        case DEADLINE -> new Deadline(this.description, this.by);
        case EVENT -> new Event(this.description, this.from, this.to);
        };
        tasks.add(task);
        storage.save(tasks);
        ui.printAdd(task, tasks.size());
    }
}
