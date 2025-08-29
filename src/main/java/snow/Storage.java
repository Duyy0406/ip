package snow;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /** Save all tasks from taskList into the file. */
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

    /** Load tasks from the file into the given TaskList. */
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
                f.createNewFile();
                return; // nothing to load yet
            }

            try (Scanner sc = new Scanner(f)) {
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    Task task = Parser.parseLine(line);
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
