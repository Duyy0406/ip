package snow.datetime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import snow.exception.SnowException;
import snow.exception.SnowInvalidDateException;

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
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DT_ALT = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm");

    // List of formats
    private static final List<DateTimeFormatter> DATETIME_FORMATS = List.of(DT_FMT, DT_ALT);
    private static final List<DateTimeFormatter> DATE_FORMATS = List.of(DATE_FMT, DATE_ALT);

    private DateTime() {

    }

    /**
     * Parse a task: accept datetime or date.
     * If only a date is given, default to 23:59 (end of day).
     */
    public static LocalDateTime parse(String input) throws SnowException {
        assert input != null : "Date cannot be empty";
        input = input.trim();
        LocalDateTime dt = tryFormats(input, DATETIME_FORMATS, true);
        if (dt != null) {
            return dt;
        }
        LocalDateTime d = tryFormats(input, DATE_FORMATS, false);
        if (d != null) {
            return d;
        }
        throw new SnowInvalidDateException();
    }
    /**
     * Attempts to parse input as LocalDateTime using available formats.
     */
    private static LocalDateTime tryParseDateTime(String input, DateTimeFormatter format) {
        try {
            return LocalDateTime.parse(input, format);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Attempts to parse input as LocalDate and convert to LocalDateTime.
     */
    private static LocalDateTime tryParseDate(String input, DateTimeFormatter format) {
        try {
            return LocalDate.parse(input, format).atTime(DEFAULT_TIME);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Attempts to parse input with list of formats
     */
    private static LocalDateTime tryFormats(String input,
                                            List<DateTimeFormatter> formats,
                                            boolean isDateTime) {
        for (DateTimeFormatter f : formats) {
            LocalDateTime date = isDateTime
                    ? tryParseDateTime(input, f)
                    : tryParseDate(input, f);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

}
