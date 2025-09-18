package snow.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SnowExceptionTest {

    @Test
    void snowTaskException_emptyDescription_createsCorrectMessage() {
        SnowTaskException exception = SnowTaskException.emptyDescription("todo");
        assertEquals("The description of a todo cannot be empty.", exception.getMessage());
    }

    @Test
    void snowTaskException_missingDate_createsCorrectMessage() {
        SnowTaskException exception = SnowTaskException.missingDate("deadline");
        assertEquals("Please specify a date for the deadline.", exception.getMessage());
    }

    @Test
    void snowTaskException_missingEndTime_createsCorrectMessage() {
        SnowTaskException exception = SnowTaskException.missingEndTime();
        assertEquals("Event must have both start time (/from) and end time (/to).", exception.getMessage());
    }

    @Test
    void snowTaskException_invalidTimeOrder_createsCorrectMessage() {
        SnowTaskException exception = SnowTaskException.invalidTimeOrder();
        assertEquals("Event start time must be before end time.", exception.getMessage());
    }

    @Test
    void snowTaskException_invalidIndex_createsCorrectMessage() {
        SnowTaskException exception = SnowTaskException.invalidIndex(5, 3);
        assertEquals("Task number 5 does not exist. You have 3 tasks.", exception.getMessage());
    }

    @Test
    void snowFileException_accessDenied_createsCorrectMessage() {
        SnowFileException exception = SnowFileException.accessDenied("/path/to/file.txt");
        assertTrue(exception.getMessage().contains("/path/to/file.txt"));
        assertTrue(exception.getMessage().contains("Cannot access file"));
    }

    @Test
    void snowFileException_directoryCreationFailed_createsCorrectMessage() {
        SnowFileException exception = SnowFileException.directoryCreationFailed("/path/to/dir");
        assertTrue(exception.getMessage().contains("/path/to/dir"));
        assertTrue(exception.getMessage().contains("Failed to create directory"));
    }

    @Test
    void snowFileException_corruptedFile_createsCorrectMessage() {
        SnowFileException exception = SnowFileException.corruptedFile("/corrupted.txt");
        assertTrue(exception.getMessage().contains("/corrupted.txt"));
        assertTrue(exception.getMessage().contains("corrupted"));
    }

    @Test
    void snowInvalidCommandException_defaultMessage() {
        SnowInvalidCommandException exception = new SnowInvalidCommandException();
        assertTrue(exception.getMessage().contains("I don't know what you mean"));
    }

    @Test
    void snowInvalidCommandException_customCommand() {
        SnowInvalidCommandException exception = new SnowInvalidCommandException("invalid");
        assertTrue(exception.getMessage().contains("Unknown command: 'invalid'"));
        assertTrue(exception.getMessage().contains("list"));
        assertTrue(exception.getMessage().contains("todo"));
    }

    @Test
    void snowInvalidDateException_defaultMessage() {
        SnowInvalidDateException exception = new SnowInvalidDateException();
        assertTrue(exception.getMessage().contains("Wrong date format"));
    }

    @Test
    void snowInvalidDateException_customInput() {
        SnowInvalidDateException exception = new SnowInvalidDateException("32/13/2023");
        assertTrue(exception.getMessage().contains("Invalid date: '32/13/2023'"));
    }
}
