package snow.exception;

public class SnowEmptyDateException extends SnowException {
    public SnowEmptyDateException(String type) {
        super("Needed a specific date for this " + type);
    }
}
