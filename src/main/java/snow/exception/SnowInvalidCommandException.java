package snow.exception;

public class SnowInvalidCommandException extends SnowException {
    public SnowInvalidCommandException() {
        super("Oops! I don't know what you mean :((  ... Can you try another message?");
    }
}
