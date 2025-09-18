package snow.exception;

/**
 * Exception thrown when a file operation fails.
 */
public class SnowFileException extends SnowException {

    /**
     * Private constructor - use static factory methods instead.
     */
    private SnowFileException(String message) {
        super(message);
    }

    /**
     * Creates exception for file access issues.
     */
    public static SnowFileException accessDenied(String filePath) {
        return new SnowFileException("Cannot access file: " + filePath + ". Check file permissions.");
    }

    /**
     * Creates exception for directory creation failures.
     */
    public static SnowFileException directoryCreationFailed(String dirPath) {
        return new SnowFileException("Failed to create directory: " + dirPath);
    }

    /**
     * Creates exception for file corruption or unexpected format.
     */
    public static SnowFileException corruptedFile(String filePath) {
        return new SnowFileException("File appears to be corrupted or in unexpected format: " + filePath);
    }
}
