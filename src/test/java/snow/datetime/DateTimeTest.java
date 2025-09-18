package snow.datetime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import snow.exception.SnowInvalidDateException;

public class DateTimeTest {

    @Test
    void parse_validYearMonthDay_returnsCorrectDateTime() throws Exception {
        LocalDateTime result = DateTime.parse("2023-12-25");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(23, result.getHour()); // Default time 23:59
        assertEquals(59, result.getMinute());
    }

    @Test
    void parse_validDayMonthYear_returnsCorrectDateTime() throws Exception {
        LocalDateTime result = DateTime.parse("25/12/2023");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(23, result.getHour()); // Default time 23:59
        assertEquals(59, result.getMinute());
    }

    @Test
    void parse_validDateTimeFormat_returnsCorrectDateTime() throws Exception {
        LocalDateTime result = DateTime.parse("2023-12-25 14:30");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    void parse_validAlternateDateTimeFormat_returnsCorrectDateTime() throws Exception {
        LocalDateTime result = DateTime.parse("25/12/2023 14:30");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    void parse_nullInput_throwsSnowInvalidDateException() {
        SnowInvalidDateException exception = assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse(null);
        });
        assertTrue(exception.getMessage().contains("null"));
    }

    @Test
    void parse_emptyInput_throwsSnowInvalidDateException() {
        SnowInvalidDateException exception = assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("");
        });
        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    void parse_whitespaceOnlyInput_throwsSnowInvalidDateException() {
        SnowInvalidDateException exception = assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("   ");
        });
        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    void parse_invalidFormat_throwsSnowInvalidDateException() {
        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("invalid-date");
        });
    }

    @Test
    void parse_invalidDate_throwsSnowInvalidDateException() {
        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("2023-13-32"); // Invalid month and day
        });
    }

    @Test
    void parse_invalidPatterns_throwsSnowInvalidDateException() {
        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("//2023-12-25"); // Double slash
        });

        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("/2023-12-25"); // Starting slash
        });

        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("2023-12-25/"); // Ending slash
        });
    }

    @Test
    void parse_multipleSpaces_handledCorrectly() throws Exception {
        LocalDateTime result = DateTime.parse("2023-12-25    14:30");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
        assertEquals(14, result.getHour());
        assertEquals(30, result.getMinute());
    }

    @Test
    void parse_dateTimeValidation_rejectsFarPast() {
        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("1900-01-01"); // Too far in past
        });
    }

    @Test
    void parse_dateTimeValidation_rejectsFarFuture() {
        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parse("2150-01-01"); // Too far in future
        });
    }

    @Test
    void parseDate_validDate_returnsLocalDate() throws Exception {
        LocalDate result = DateTime.parseDate("2023-12-25");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
    }

    @Test
    void parseDate_alternateDateFormat_returnsLocalDate() throws Exception {
        LocalDate result = DateTime.parseDate("25/12/2023");
        assertEquals(2023, result.getYear());
        assertEquals(12, result.getMonthValue());
        assertEquals(25, result.getDayOfMonth());
    }

    @Test
    void parseDate_invalidInput_throwsException() {
        assertThrows(SnowInvalidDateException.class, () -> {
            DateTime.parseDate("invalid");
        });
    }
}
