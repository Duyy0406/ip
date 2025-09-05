package snow.datetime;

import snow.exception.SnowException;
import snow.exception.SnowInvalidDateException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides date and time for tasks.
 */
public final class DateTime {
    // output formats
    public static final DateTimeFormatter OUT_DT = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");

    // default time
    public static final LocalTime DEFAULT_TIME = LocalTime.of(23, 59);

    // input formats
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_ALT = DateTimeFormatter.ofPattern("d/M/yyyy");
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DT_ALT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private DateTime() {

    }

    /**
     * Parse a task: accept datetime or date.
     * If only a date is given, default to 23:59 (end of day).
     */
    public static LocalDateTime parse(String input) throws SnowException {
        input = input.trim();
        try {
            return LocalDateTime.parse(input, DT_FMT); // yyyy-MM-dd HHmm
        } catch (Exception ignored) {
            // ignore exception
        }
        try {
            return LocalDateTime.parse(input, DT_ALT); // d/M/yyyy HHmm
        } catch (Exception ignored) {
            // ignore exception
        }
        try {
            return LocalDate.parse(input, DATE_FMT).atTime(DEFAULT_TIME);
        } catch (Exception ignored) {
            // ignore exception
        }
        try {
            return LocalDate.parse(input, DATE_ALT).atTime(DEFAULT_TIME);
        } catch (Exception e) {
            throw new SnowInvalidDateException();
        }
    }

}
