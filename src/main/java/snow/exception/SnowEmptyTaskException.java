package snow.exception;

public class SnowEmptyTaskException extends SnowException {
    public SnowEmptyTaskException(String type) {
        super("Oops! The description of " + type + " cannot be empty!");
    }
}
