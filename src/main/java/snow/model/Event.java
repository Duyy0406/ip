package snow.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import snow.datetime.DateTime;

/**
 * Represents an event task with a start and end date.
 * An Event has a description, a from-date, and a to-date.
 */
public class Event extends Task {

    /** Start date of the event. */
    private final LocalDateTime fromDate;

    /** End date of the event. */
    private final LocalDateTime toDate;

    /**
     * Creates an event with the specified description and datetimes.
     *
     * @param name     description of the event
     * @param fromDate start datetime of the event
     * @param toDate   end datetime of the event
     */
    public Event(String name, LocalDateTime fromDate, LocalDateTime toDate) {
        super(name);

        assert fromDate != null : "Event start date cannot be null";
        assert toDate != null : "Event end date cannot be null";
        assert !fromDate.isAfter(toDate) : "Event start date must not be after end date";

        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public boolean isOnDate(LocalDate date) {
        LocalDate fromDate = this.fromDate.toLocalDate();
        LocalDate toDate = this.toDate.toLocalDate();
        return !(date.isBefore(fromDate)) && !(date.isAfter(toDate));
    }

    @Override
    public String toSaveString() {
        return "E | " + super.toSaveString() + " | " + this.fromDate + " | " + this.toDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate.format(DateTime.OUT_DT)
                + " to: " + toDate.format(DateTime.OUT_DT) + ")";
    }
}
