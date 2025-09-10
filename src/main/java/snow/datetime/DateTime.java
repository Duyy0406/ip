package snow.datetime;

import snow.exception.SnowException;
import snow.exception.SnowInvalidDateException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Provides date and time parsing utilities for tasks.
 * Supports multiple input formats and provides consistent output formatting.
 */
public final class DateTime {
    /** Output format for displaying dates and times. */
    public static final DateTimeFormatter OUT_DT = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a");

    /** Default time (23:59) applied when only date is provided. */
    public static final LocalTime DEFAULT_TIME = LocalTime.of(23, 59);

    /** Primary date format: yyyy-MM-dd. */
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /** Alternative date format: d/M/yyyy. */
    private static final DateTimeFormatter DATE_ALT = DateTimeFormatter.ofPattern("d/M/yyyy");
    
    /** Primary datetime format: yyyy-MM-dd HHmm. */
    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    
    /** Alternative datetime format: d/M/yyyy HHmm. */
    private static final DateTimeFormatter DT_ALT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    private DateTime() {
        // Utility class - prevent instantiation
    }

    /**
     * Parses a date/time string into LocalDateTime.
     * Accepts datetime or date-only input. If only date is provided, 
     * defaults to 23:59 (end of day).
     *
     * @param input the date/time string to parse
     * @return parsed LocalDateTime
     * @throws SnowException if input is invalid or cannot be parsed
     */
    public static LocalDateTime parse(String input) throws SnowException {
        validateInput(input);
        String trimmedInput = input.trim();
        
        return tryParseDateTime(trimmedInput)
                .or(() -> tryParseDate(trimmedInput))
                .orElseThrow(SnowInvalidDateException::new);
    }
    
    /**
     * Validates the input string for parsing requirements.
     *
     * @param input the input to validate
     * @throws SnowException if input is null or empty
     */
    private static void validateInput(String input) throws SnowException {
        if (input == null || input.trim().isEmpty()) {
            throw new SnowInvalidDateException();
        }
    }
    
    /**
     * Attempts to parse input as LocalDateTime using available datetime formats.
     *
     * @param input the trimmed input string
     * @return Optional containing parsed LocalDateTime, or empty if parsing fails
     */
    private static Optional<LocalDateTime> tryParseDateTime(String input) {
        return tryParseWithFormat(() -> LocalDateTime.parse(input, DT_FMT))
                .or(() -> tryParseWithFormat(() -> LocalDateTime.parse(input, DT_ALT)));
    }
    
    /**
     * Attempts to parse input as LocalDate and converts to LocalDateTime with default time.
     *
     * @param input the trimmed input string
     * @return Optional containing parsed LocalDateTime, or empty if parsing fails
     */
    private static Optional<LocalDateTime> tryParseDate(String input) {
        return tryParseWithFormat(() -> LocalDate.parse(input, DATE_FMT).atTime(DEFAULT_TIME))
                .or(() -> tryParseWithFormat(() -> LocalDate.parse(input, DATE_ALT).atTime(DEFAULT_TIME)));
    }
    
    /**
     * Safely attempts parsing with the given parser function.
     * Catches only DateTimeParseException to avoid masking other errors.
     *
     * @param parser the parsing function to execute
     * @return Optional containing result if successful, empty if parsing fails
     */
    private static Optional<LocalDateTime> tryParseWithFormat(ParseFunction parser) {
        try {
            return Optional.of(parser.parse());
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
    
    /**
     * Functional interface for parsing operations.
     */
    @FunctionalInterface
    private interface ParseFunction {
        /**
         * Performs the parsing operation.
         *
         * @return parsed LocalDateTime
         * @throws DateTimeParseException if parsing fails
         */
        LocalDateTime parse() throws DateTimeParseException;
    }

}
