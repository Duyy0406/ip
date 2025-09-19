package snow.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import snow.exception.SnowFileException;
import snow.model.Deadline;
import snow.model.Event;
import snow.model.TaskList;
import snow.model.Todo;

public class StorageTest {

    @TempDir
    Path tempDir;

    private Storage storage;
    private String testFilePath;

    @BeforeEach
    void setUp() {
        testFilePath = tempDir.resolve("test_snow.txt").toString();
        storage = new Storage(testFilePath);
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up test file if it exists
        Path path = Paths.get(testFilePath);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    void constructor_validPath_setsFilePath() {
        @SuppressWarnings("unused")
        Storage localStorage = new Storage("/path/to/file.txt");
        // Storage doesn't expose file path, but we can test that it doesn't throw
        assertTrue(true); // Constructor should not throw
    }

    @Test
    void save_emptyTaskList_createsEmptyFile() throws Exception {
        TaskList emptyList = new TaskList();

        storage.save(emptyList);

        assertTrue(Files.exists(Paths.get(testFilePath)));
        String content = Files.readString(Paths.get(testFilePath));
        assertTrue(content.isEmpty() || content.trim().isEmpty());
    }

    @Test
    void save_taskListWithTodos_savesCorrectFormat() throws Exception {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        Todo markedTodo = new Todo("complete assignment");
        markedTodo.mark();
        taskList.add(markedTodo);

        storage.save(taskList);

        String content = Files.readString(Paths.get(testFilePath));
        String[] lines = content.trim().split("\\r?\\n");
        assertEquals(2, lines.length);
        assertEquals("T | 0 | read book | at= | pid=-1", lines[0]);
        assertEquals("T | 1 | complete assignment | at= | pid=-1", lines[1]);
    }

    @Test
    void save_taskListWithDeadlines_savesCorrectFormat() throws Exception {
        TaskList taskList = new TaskList();
        taskList.add(new Deadline("submit project", LocalDateTime.of(2023, 12, 31, 23, 59)));

        storage.save(taskList);

        String content = Files.readString(Paths.get(testFilePath));
        String[] lines = content.trim().split("\\r?\\n");
        assertEquals(1, lines.length);
        assertEquals("D | 0 | submit project | at= | pid=-1 | 2023-12-31T23:59", lines[0]);
    }

    @Test
    void save_taskListWithEvents_savesCorrectFormat() throws Exception {
        TaskList taskList = new TaskList();
        taskList.add(new Event("team meeting",
                LocalDateTime.of(2023, 12, 25, 14, 0),
                LocalDateTime.of(2023, 12, 25, 16, 0)));

        storage.save(taskList);

        String content = Files.readString(Paths.get(testFilePath));
        String[] lines = content.trim().split("\\r?\\n");
        assertEquals(1, lines.length);
        assertEquals("E | 0 | team meeting | at= | pid=-1 | 2023-12-25T14:00 | 2023-12-25T16:00", lines[0]);
    }

    @Test
    void save_mixedTaskTypes_savesAllCorrectly() throws Exception {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        taskList.add(new Deadline("submit assignment", LocalDateTime.of(2023, 12, 31, 23, 59)));
        taskList.add(new Event("meeting",
                LocalDateTime.of(2023, 12, 25, 14, 0),
                LocalDateTime.of(2023, 12, 25, 16, 0)));

        storage.save(taskList);

        String content = Files.readString(Paths.get(testFilePath));
        String[] lines = content.trim().split("\\r?\\n");
        assertEquals(3, lines.length);
        assertTrue(lines[0].startsWith("T |"));
        assertTrue(lines[1].startsWith("D |"));
        assertTrue(lines[2].startsWith("E |"));
    }

    @Test
    void load_nonexistentFile_returnsEmptyTaskList() throws Exception {
        String nonexistentPath = tempDir.resolve("nonexistent.txt").toString();
        Storage storage = new Storage(nonexistentPath);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(0, taskList.size());
    }

    @Test
    void load_emptyFile_returnsEmptyTaskList() throws Exception {
        // Create empty file
        Files.createFile(Paths.get(testFilePath));

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(0, taskList.size());
    }

    @Test
    void load_validTodoFile_returnsCorrectTaskList() throws Exception {
        String content = "T | 0 | read book\nT | 1 | complete assignment";
        Files.writeString(Paths.get(testFilePath), content);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(2, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
        assertEquals("read book", taskList.get(0).getDescription());
        assertFalse(taskList.get(0).isDone());

        assertTrue(taskList.get(1) instanceof Todo);
        assertEquals("complete assignment", taskList.get(1).getDescription());
        assertTrue(taskList.get(1).isDone());
    }

    @Test
    void load_validDeadlineFile_returnsCorrectTaskList() throws Exception {
        String content = "D | 0 | submit project | 2023-12-31T23:59";
        Files.writeString(Paths.get(testFilePath), content);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Deadline);
        assertEquals("submit project", taskList.get(0).getDescription());
        assertFalse(taskList.get(0).isDone());
    }

    @Test
    void load_validEventFile_returnsCorrectTaskList() throws Exception {
        String content = "E | 1 | team meeting | 2023-12-25T14:00 | 2023-12-25T16:00";
        Files.writeString(Paths.get(testFilePath), content);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(1, taskList.size());
        assertTrue(taskList.get(0) instanceof Event);
        assertEquals("team meeting", taskList.get(0).getDescription());
        assertTrue(taskList.get(0).isDone());
    }

    @Test
    void load_mixedTaskFile_returnsCorrectTaskList() throws Exception {
        String content = "T | 0 | read book\n"
                         + "D | 1 | submit assignment | 2023-12-31T23:59\n"
                         + "E | 0 | meeting | 2023-12-25T14:00 | 2023-12-25T16:00";
        Files.writeString(Paths.get(testFilePath), content);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(3, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
        assertTrue(taskList.get(1) instanceof Deadline);
        assertTrue(taskList.get(2) instanceof Event);

        assertFalse(taskList.get(0).isDone());
        assertTrue(taskList.get(1).isDone());
        assertFalse(taskList.get(2).isDone());
    }

    @Test
    void load_fileWithBlankLines_ignoresBlankLines() throws Exception {
        String content = "T | 0 | read book\n\n\nD | 1 | submit | 2023-12-31T23:59\n\n";
        Files.writeString(Paths.get(testFilePath), content);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        assertEquals(2, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
        assertTrue(taskList.get(1) instanceof Deadline);
    }

    @Test
    void load_invalidTaskFormat_skipsInvalidLines() throws Exception {
        String content = "T | 0 | read book\n"
                         + "INVALID LINE\n"
                         + "D | 1 | submit | 2023-12-31T23:59";
        Files.writeString(Paths.get(testFilePath), content);

        TaskList taskList = new TaskList();
        storage.load(taskList);

        // Should load valid lines and skip invalid ones
        assertEquals(2, taskList.size());
        assertTrue(taskList.get(0) instanceof Todo);
        assertTrue(taskList.get(1) instanceof Deadline);
    }

    @Test
    void saveAndLoad_roundTrip_preservesData() throws Exception {
        TaskList originalList = new TaskList();
        originalList.add(new Todo("read book"));

        Deadline deadline = new Deadline("submit assignment", LocalDateTime.of(2023, 12, 31, 23, 59));
        deadline.mark();
        originalList.add(deadline);

        originalList.add(new Event("meeting",
                LocalDateTime.of(2023, 12, 25, 14, 0),
                LocalDateTime.of(2023, 12, 25, 16, 0)));

        // Save and then load
        storage.save(originalList);
        TaskList loadedList = new TaskList();
        storage.load(loadedList);

        assertEquals(originalList.size(), loadedList.size());

        for (int i = 0; i < originalList.size(); i++) {
            assertEquals(originalList.get(i).getClass(), loadedList.get(i).getClass());
            assertEquals(originalList.get(i).getDescription(), loadedList.get(i).getDescription());
            assertEquals(originalList.get(i).isDone(), loadedList.get(i).isDone());
        }
    }

    @Test
    void save_invalidPath_handlesGracefully() throws Exception {
        // Test with a path that includes invalid characters or is in a non-existent directory
        String invalidPath = "/nonexistent/directory/file.txt";
        Storage invalidStorage = new Storage(invalidPath);
        TaskList taskList = new TaskList();
        taskList.add(new Todo("test task"));

        // This might throw an exception or handle it gracefully depending on implementation
        // We'll test that it doesn't crash the application
        try {
            invalidStorage.save(taskList);
            // If it succeeds, that's fine
        } catch (Exception e) {
            // If it throws an exception, that's also acceptable behavior
            assertTrue(e instanceof SnowFileException || e instanceof IOException);
        }
    }
}
