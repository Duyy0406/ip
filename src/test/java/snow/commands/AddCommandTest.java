package snow.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import snow.io.Storage;
import snow.io.Ui;
import snow.model.Deadline;
import snow.model.Event;
import snow.model.TaskList;
import snow.model.Todo;

public class AddCommandTest {

    private TaskList taskList;
    private Ui ui;
    private Storage storage;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        ui = new Ui(); // Assuming Ui has a default constructor
        storage = new Storage("test_path.txt"); // Mock storage
    }

    @Test
    void todo_createsValidTodoCommand() throws Exception {
        AddCommand command = AddCommand.todo("read book");

        command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
        assertEquals("read book", taskList.get(0).getDescription());
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    void deadline_createsValidDeadlineCommand() throws Exception {
        LocalDateTime dueDate = LocalDateTime.of(2023, 12, 31, 23, 59);
        AddCommand command = AddCommand.deadline("submit assignment", dueDate);

        command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Deadline);
        assertEquals("submit assignment", taskList.get(0).getDescription());
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    void event_createsValidEventCommand() throws Exception {
        LocalDateTime start = LocalDateTime.of(2023, 12, 25, 14, 0);
        LocalDateTime end = LocalDateTime.of(2023, 12, 25, 16, 0);
        AddCommand command = AddCommand.event("team meeting", start, end);

        command.execute(taskList, ui, storage);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Event);
        assertEquals("team meeting", taskList.get(0).getDescription());
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    void execute_multipleCommands_increasesTaskCount() throws Exception {
        AddCommand todo = AddCommand.todo("read book");
        AddCommand deadline = AddCommand.deadline("submit", LocalDateTime.of(2023, 12, 31, 23, 59));

        todo.execute(taskList, ui, storage);
        deadline.execute(taskList, ui, storage);

        assertEquals(2, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
        assertTrue(taskList.get(1) instanceof Deadline);
    }

    @Test
    void isExit_returnsFalse() {
        AddCommand command = AddCommand.todo("test task");
        assertFalse(command.isExit());
    }

    @Test
    void getString_returnsCommandRepresentation() {
        AddCommand command = AddCommand.todo("test task");
        // Execute the command first to initialize the command string
        try {
            command.execute(taskList, ui, storage);
        } catch (Exception e) {
            // Ignore execution errors for this test
        }
        String commandString = command.getString();
        assertTrue(commandString != null && !commandString.isEmpty());
    }
}
