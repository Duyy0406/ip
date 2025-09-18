package snow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import snow.exception.SnowTaskException;

public class TaskListTest {

    private TaskList list;
    private Todo todo;
    private Deadline deadline;
    private Event event;

    @BeforeEach
    void setUp() {
        list = new TaskList();
        todo = new Todo("read book");
        deadline = new Deadline("submit assignment", LocalDateTime.of(2023, 12, 31, 23, 59));
        event = new Event("team meeting",
                LocalDateTime.of(2023, 12, 25, 14, 0),
                LocalDateTime.of(2023, 12, 25, 16, 0));
    }

    @Test
    void add_increasesSize() {
        assertEquals(0, list.size());
        list.add(todo);
        assertEquals(1, list.size());
        list.add(deadline);
        assertEquals(2, list.size());
    }

    @Test
    void markAndUnmarkChangeState() {
        list.add(todo);
        list.mark(0);
        assertTrue(list.get(0).isDone());

        list.unmark(0);
        assertFalse(list.get(0).isDone());
    }

    @Test
    void get_validIndex_returnsCorrectTask() {
        list.add(todo);
        list.add(deadline);

        assertEquals(todo, list.get(0));
        assertEquals(deadline, list.get(1));
    }

    @Test
    void remove_validIndex_removesAndReturnsTask() throws SnowTaskException {
        list.add(todo);
        list.add(deadline);

        Task removed = list.remove(0);
        assertEquals(todo, removed);
        assertEquals(1, list.size()); // Now size should be correct
        assertEquals(deadline, list.get(0));
    }

    @Test
    void remove_invalidIndex_throwsException() {
        list.add(todo);

        // Test negative index
        assertThrows(SnowTaskException.class, () -> list.remove(-1));

        // Test index too large
        assertThrows(SnowTaskException.class, () -> list.remove(1));
        assertThrows(SnowTaskException.class, () -> list.remove(10));
    }

    @Test
    void mark_invalidIndex_handledGracefully() {
        list.add(todo);

        // These should not throw exceptions and should be handled gracefully
        list.mark(-1);
        list.mark(1);

        // The valid task should remain unchanged
        assertFalse(list.get(0).isDone());
    }

    @Test
    void find_existingTask_returnsMatches() {
        list.add(todo);
        list.add(deadline);
        list.add(event);

        List<Task> results = list.find("assignment");
        assertEquals(1, results.size());
        assertEquals(deadline, results.get(0));

        results = list.find("meeting");
        assertEquals(1, results.size());
        assertEquals(event, results.get(0));
    }

    @Test
    void find_noMatch_returnsEmptyList() {
        list.add(todo);
        list.add(deadline);

        List<Task> results = list.find("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    void find_caseInsensitive_returnsMatches() {
        list.add(new Todo("Read Book"));

        List<Task> results = list.find("read");
        assertEquals(1, results.size());

        results = list.find("BOOK");
        assertEquals(1, results.size());

        results = list.find("ReAd BoOk");
        assertEquals(1, results.size());
    }

    @Test
    void findTaskWithDate_existingDate_returnsMatches() {
        list.add(todo);
        list.add(deadline);
        list.add(event);

        LocalDate deadlineDate = LocalDate.of(2023, 12, 31);
        List<Task> results = list.findTaskWithDate(deadlineDate);
        assertEquals(1, results.size());
        assertEquals(deadline, results.get(0));

        LocalDate eventDate = LocalDate.of(2023, 12, 25);
        results = list.findTaskWithDate(eventDate);
        assertEquals(1, results.size());
        assertEquals(event, results.get(0));
    }

    @Test
    void findTaskWithDate_noMatch_returnsEmptyList() {
        list.add(todo);
        list.add(deadline);

        LocalDate randomDate = LocalDate.of(2023, 1, 1);
        List<Task> results = list.findTaskWithDate(randomDate);
        assertTrue(results.isEmpty());
    }

    @Test
    void findTaskWithDate_todoTask_notIncluded() {
        list.add(todo);

        // Todo tasks don't have dates, so they shouldn't match any date
        LocalDate anyDate = LocalDate.of(2023, 12, 25);
        List<Task> results = list.findTaskWithDate(anyDate);
        assertTrue(results.isEmpty());
    }

    @Test
    void emptyList_operations() {
        assertEquals(0, list.size());

        // These operations on empty list should be safe
        assertThrows(SnowTaskException.class, () -> list.remove(0));

        List<Task> results = list.find("anything");
        assertTrue(results.isEmpty());

        LocalDate anyDate = LocalDate.of(2023, 12, 25);
        results = list.findTaskWithDate(anyDate);
        assertTrue(results.isEmpty());
    }

    @Test
    void mixedTaskTypes_allOperations() {
        list.add(todo);
        list.add(deadline);
        list.add(event);

        assertEquals(3, list.size());

        // Mark different types
        list.mark(0);
        list.mark(2);

        assertTrue(list.get(0).isDone());
        assertFalse(list.get(1).isDone());
        assertTrue(list.get(2).isDone());

        // Test search across different types
        List<Task> allTasks = list.find("e"); // Should match multiple
        assertTrue(allTasks.size() >= 2); // "read", "meeting", etc.
    }
}
