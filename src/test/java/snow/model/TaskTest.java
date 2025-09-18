package snow.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaskTest {

    private Todo todo;
    private Deadline deadline;
    private Event event;
    private Place place;

    @BeforeEach
    void setUp() {
        todo = new Todo("Read book");
        deadline = new Deadline("Submit assignment", LocalDateTime.of(2023, 12, 31, 23, 59));
        event = new Event("Meeting",
                LocalDateTime.of(2023, 12, 25, 14, 0),
                LocalDateTime.of(2023, 12, 25, 16, 0));
        place = new Place(1, "Library");
    }

    @Test
    void todo_creation_hasCorrectProperties() {
        assertEquals("Read book", todo.getDescription());
        assertFalse(todo.isDone());
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    void todo_mark_changesStatus() {
        todo.mark();
        assertTrue(todo.isDone());
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void todo_unmark_changesStatus() {
        todo.mark();
        todo.unmark();
        assertFalse(todo.isDone());
        assertEquals("[T][ ] Read book", todo.toString());
    }

    @Test
    void todo_withPlace_displaysCorrectly() {
        todo.setPlace(place);
        assertEquals("[T][ ] Read book at Library", todo.toString());
        assertTrue(todo.hasPlace());
    }

    @Test
    void todo_toSaveString_correctFormat() {
        assertEquals("T | 0 | Read book | at= | pid=-1", todo.toSaveString());

        todo.mark();
        assertEquals("T | 1 | Read book | at= | pid=-1", todo.toSaveString());

        todo.setPlace(place);
        assertEquals("T | 1 | Read book | at=Library | pid=1", todo.toSaveString());
    }

    @Test
    void deadline_creation_hasCorrectProperties() {
        assertEquals("Submit assignment", deadline.getDescription());
        assertFalse(deadline.isDone());
        assertTrue(deadline.toString().contains("[D][ ] Submit assignment"));
        assertTrue(deadline.toString().contains("Dec 31 2023"));
    }

    @Test
    void deadline_toSaveString_correctFormat() {
        String saveString = deadline.toSaveString();
        assertTrue(saveString.startsWith("D | 0 | Submit assignment"));
        assertTrue(saveString.contains("2023-12-31T23:59"));
    }

    @Test
    void event_creation_hasCorrectProperties() {
        assertEquals("Meeting", event.getDescription());
        assertFalse(event.isDone());
        assertTrue(event.toString().contains("[E][ ] Meeting"));
        assertTrue(event.toString().contains("Dec 25 2023"));
    }

    @Test
    void event_toSaveString_correctFormat() {
        String saveString = event.toSaveString();
        assertTrue(saveString.startsWith("E | 0 | Meeting"));
        assertTrue(saveString.contains("2023-12-25T14:00"));
        assertTrue(saveString.contains("2023-12-25T16:00"));
    }

    @Test
    void deadline_isOnDate_correctlyIdentifies() {
        assertTrue(deadline.isOnDate(LocalDateTime.of(2023, 12, 31, 10, 0).toLocalDate()));
        assertFalse(deadline.isOnDate(LocalDateTime.of(2023, 12, 30, 10, 0).toLocalDate()));
    }

    @Test
    void event_isOnDate_correctlyIdentifies() {
        assertTrue(event.isOnDate(LocalDateTime.of(2023, 12, 25, 10, 0).toLocalDate()));
        assertFalse(event.isOnDate(LocalDateTime.of(2023, 12, 24, 10, 0).toLocalDate()));
        assertFalse(event.isOnDate(LocalDateTime.of(2023, 12, 26, 10, 0).toLocalDate()));
    }

    @Test
    void place_creation_hasCorrectProperties() {
        assertEquals("Library", place.getName());
        assertEquals(1, place.getId());
    }

    @Test
    void place_toSaveString_correctFormat() {
        assertEquals("P | 1 | Library", place.toSaveString());
    }

    @Test
    void place_none_isSpecialInstance() {
        assertEquals(Place.NONE, Place.NONE);
        assertEquals(-1, Place.NONE.getId());
        assertEquals("", Place.NONE.getName());
    }
}
