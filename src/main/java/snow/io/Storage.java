package snow.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import snow.exception.SnowFileException;
import snow.model.Place;
import snow.model.PlaceRegistry;
import snow.model.Task;
import snow.model.TaskList;

/**
 * Handles persistence of tasks to/from a file path.
 * Saves a {@link TaskList} in a simple line-based format and loads it back.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage that reads/writes at the given file path.
     *
     * @param filePath path to the save file
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /** Saves all tasks from {@code taskList} into the file. */
    public void save(TaskList taskList) throws SnowFileException {
        try {
            File f = new File(filePath);

            // Validate file path
            if (filePath == null || filePath.trim().isEmpty()) {
                throw SnowFileException.accessDenied("null or empty path");
            }

            // ensure folder exists
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw SnowFileException.directoryCreationFailed(parent.getAbsolutePath());
                }
            }

            // Check if parent directory is writable
            if (parent != null && !parent.canWrite()) {
                throw SnowFileException.accessDenied(parent.getAbsolutePath());
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                // Save places first
                for (Place place : PlaceRegistry.getPlaces()) {
                    bw.write(place.toSaveString());
                    bw.newLine();
                }

                // Then save tasks
                for (int i = 0; i < taskList.size(); i++) {
                    bw.write(taskList.get(i).toSaveString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw SnowFileException.accessDenied(filePath + " - " + e.getMessage());
        }
    }

    /** Loads tasks from the file into the given {@code taskList}. */
    public void load(TaskList taskList) throws SnowFileException {
        try {
            File f = new File(filePath);

            // Validate file path
            if (filePath == null || filePath.trim().isEmpty()) {
                throw SnowFileException.accessDenied("null or empty path");
            }

            // ensure folder exists
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    throw SnowFileException.directoryCreationFailed(parent.getAbsolutePath());
                }
            }

            if (!f.exists()) {
                // create empty file so future saves don't fail
                if (!f.createNewFile()) {
                    throw SnowFileException.accessDenied(f.getAbsolutePath());
                }
                return; // nothing to load yet
            }

            // Check if file is readable
            if (!f.canRead()) {
                throw SnowFileException.accessDenied(f.getAbsolutePath());
            }

            // Clear existing places before loading
            PlaceRegistry.clearPlaces();

            try (Scanner sc = new Scanner(f)) {
                int lineNumber = 0;
                while (sc.hasNextLine()) {
                    lineNumber++;
                    String line = sc.nextLine().trim();

                    // Skip empty lines
                    if (line.isEmpty()) {
                        continue;
                    }

                    // Try to parse as place first
                    Place place = Parser.parsePlaceFromStorage(line);
                    if (place != null) {
                        PlaceRegistry.addPlace(place);
                        continue;
                    }

                    // Otherwise parse as task
                    Task task = Parser.parseLine(line);
                    if (task != null) {
                        taskList.add(task);
                    } else {
                        System.out.println("Warning: Failed to parse line " + lineNumber + ": " + line);
                    }
                }
            }
        } catch (IOException e) {
            throw SnowFileException.accessDenied(filePath + " - " + e.getMessage());
        }
    }
}
