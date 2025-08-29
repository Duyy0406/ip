package snow;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import snow.exception.SnowEmptyDateException;
import snow.exception.SnowEmptyTaskException;
import snow.exception.SnowInvalidCommandException;

class ParserTest {

    @Test
    void getCmd_core_ok() {
        assertEquals(Command.BYE,      assertDoesNotThrow(() -> Parser.getCmd("bye")));
        assertEquals(Command.LIST,     assertDoesNotThrow(() -> Parser.getCmd("list")));
        assertEquals(Command.TODO,     assertDoesNotThrow(() -> Parser.getCmd("todo read book")));
        assertEquals(Command.DEADLINE, assertDoesNotThrow(() -> Parser.getCmd("deadline return book /by 2025-10-01")));
        assertEquals(Command.EVENT,    assertDoesNotThrow(() -> Parser.getCmd("event meet Bob /from 2025-10-01 10:00 /to 2025-10-01 11:00")));
        assertEquals(Command.MARK,     assertDoesNotThrow(() -> Parser.getCmd("mark 2")));
        assertEquals(Command.UNMARK,   assertDoesNotThrow(() -> Parser.getCmd("unmark 3")));
        assertEquals(Command.DELETE,   assertDoesNotThrow(() -> Parser.getCmd("delete 1")));
    }

    @Test
    void getCmd_unknown_throws() {
        assertThrows(SnowInvalidCommandException.class, () -> Parser.getCmd("wut is this"));
    }

    @Test
    void splitCommand_ok() {
        assertArrayEquals(new String[] {"todo", "buy milk"},
                assertDoesNotThrow(() -> Parser.splitCommand("todo buy milk")));
        assertArrayEquals(new String[] {"mark", "2"},
                assertDoesNotThrow(() -> Parser.splitCommand("mark 2")));
        assertArrayEquals(new String[] {"unmark", "5"},
                assertDoesNotThrow(() -> Parser.splitCommand("unmark 5")));
        assertArrayEquals(new String[] {"delete", "10"},
                assertDoesNotThrow(() -> Parser.splitCommand("delete 10")));
    }

    @Test
    void splitDeadline_ok() {
        assertArrayEquals(new String[] {"submit report", "2025-10-01 18:00"},
                assertDoesNotThrow(() -> Parser.splitDeadline("deadline submit report /by 2025-10-01 18:00")));
    }

    @Test
    void splitEvent_ok() {
        assertArrayEquals(new String[] {"team sync", "2025-10-02 10:00", "2025-10-02 11:00"},
                assertDoesNotThrow(() -> Parser.splitEvent("event team sync /from 2025-10-02 10:00 /to 2025-10-02 11:00")));
    }

    // ---- error cases ----

    @Test
    void splitCommand_missing_pieces_throw() {
        assertThrows(SnowEmptyTaskException.class, () -> Parser.splitCommand("todo"));
        assertThrows(SnowEmptyTaskException.class, () -> Parser.splitCommand("delete"));
    }

    @Test
    void splitDeadline_missingBy_throws() {
        assertThrows(SnowEmptyDateException.class, () -> Parser.splitDeadline("deadline submit report"));
    }

    @Test
    void splitEvent_missingPieces_throws() {
        assertThrows(SnowEmptyDateException.class, () -> Parser.splitEvent("event team sync /from 2025-10-02 10:00"));
        assertThrows(SnowEmptyDateException.class, () -> Parser.splitEvent("event team sync /to 2025-10-02 11:00"));
    }
}
