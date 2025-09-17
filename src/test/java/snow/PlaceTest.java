package snow;

import snow.io.Storage;
import snow.model.Place;
import snow.model.PlaceRegistry;
import snow.model.TaskList;
import snow.model.Todo;

/**
 * Simple test to verify place persistence.
 */
public class PlaceTest {
    public static void main(String[] args) {
        // Create a place
        Place library = PlaceRegistry.getPlace("library");
        System.out.println("Created place: " + library.getName() + " (ID: " + library.getId() + ")");

        // Create a task with the place
        Todo task = new Todo("read book");
        task.setPlace(library);

        // Create TaskList and add task
        TaskList tasks = new TaskList();
        tasks.add(task);

        // Save to file
        Storage storage = new Storage("data/test-snow.txt");
        storage.save(tasks);
        System.out.println("Saved tasks and places to file");

        // Clear and reload
        PlaceRegistry.clearPlaces();
        TaskList newTasks = new TaskList();
        storage.load(newTasks);

        System.out.println("Loaded " + PlaceRegistry.getPlaces().size() + " places:");
        for (Place p : PlaceRegistry.getPlaces()) {
            System.out.println("  " + p.getName() + " (ID: " + p.getId() + ")");
        }

        System.out.println("Loaded " + newTasks.size() + " tasks:");
        for (int i = 0; i < newTasks.size(); i++) {
            System.out.println("  " + newTasks.get(i));
        }
    }
}
