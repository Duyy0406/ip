package snow;

import java.time.LocalDateTime;

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
     * Creates an event with the specified description, start date, and end date.
     *
     * @param name Description of the event.
     * @param fromDate Start date of the event.
     * @param toDate End date of the event.
     */
    public Event(String name, String fromDate, String toDate) {
        super(name);
        this.fromDate = DateTime.parse(fromDate);
        this.toDate = DateTime.parse(toDate);
    }

    public Event(String name, LocalDateTime fromDate, LocalDateTime toDate) {
        super(name);
        this.fromDate = fromDate;
        this.toDate = toDate;
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
