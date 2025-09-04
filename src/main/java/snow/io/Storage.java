package snow.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
    public void save(TaskList taskList) {
        try {
            File f = new File(filePath);

            // ensure folder exists
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    System.out.println("Warning: failed to create directory " + parent);
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                for (int i = 0; i < taskList.size(); i++) {
                    bw.write(taskList.get(i).toSaveString());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to save tasks: " + e.getMessage());
        }
    }

    /** Loads tasks from the file into the given {@code taskList}. */
    public void load(TaskList taskList) {
        try {
            File f = new File(filePath);

            // ensure folder exists
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                if (!parent.mkdirs()) {
                    System.out.println("Warning: failed to create directory " + parent);
                }
            }

            if (!f.exists()) {
                // create empty file so future saves don't fail
                if (!f.createNewFile()) {
                    System.out.println("Warning: failed to create file " + f);
                }
                return; // nothing to load yet
            }

            try (Scanner sc = new Scanner(f)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task task = Parser.parseLine(line); // assumes Parser in same package
                    if (task != null) {
                        taskList.add(task);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to load tasks: " + e.getMessage());
        }
    }
}
