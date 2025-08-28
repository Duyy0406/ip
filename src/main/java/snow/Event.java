package snow;

/**
 * Represents an event task with a start and end date.
 * An Event has a description, a from-date, and a to-date.
 */
public class Event extends Task {

    /** Start date of the event. */
    private String fromDate;

    /** End date of the event. */
    private String toDate;

    /**
     * Creates an event with the specified description, start date, and end date.
     *
     * @param name Description of the event.
     * @param fromDate Start date of the event.
     * @param toDate End date of the event.
     */
    public Event(String name, String fromDate, String toDate) {
        super(name);
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + fromDate + " to: " + toDate + ")";
    }
}
