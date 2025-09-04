package snow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import snow.model.TaskList;
import snow.model.Todo;


public class TaskListTest {

    @Test
    void add_increasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());

        list.add(new Todo("read book"));
        assertEquals(1, list.size());
    }

    @Test
    void markAndUnmarkChangeState() {
        TaskList list = new TaskList();
        Todo t = new Todo("read book");
        list.add(t);

        list.mark(0);
        assertTrue(list.get(0).isDone());

        list.unmark(0);
        assertFalse(list.get(0).isDone());
    }
}
