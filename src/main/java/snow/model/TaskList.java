package snow.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import snow.exception.SnowInvalidIndexException;

/**
 * A mutable list of {@link Task} objects with simple query helpers.
 */
public class TaskList {
    private final List<Task> items;
    private int size;

    /** Creates an empty {@code TaskList}. */
    public TaskList() {
        this.items = new ArrayList<Task>();
        this.size = 0;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        items.add(task);
        size++;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index zero-based index of the task
     */
    public void mark(int index) {
        items.get(index).mark();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index zero-based index of the task
     */
    public void unmark(int index) {
        items.get(index).unmark();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the list
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the task at the given index.
     *
     * @param i zero-based index
     * @return the task at the index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public Task get(int i) {
        return items.get(i);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param i zero-based index
     * @return the removed task
     * @throws SnowInvalidIndexException if the index is out of range
     */
    public Task remove(int i) throws SnowInvalidIndexException {
        if (i < 0 || i >= size) {
            throw new SnowInvalidIndexException();
        }
        size--;
        return items.remove(i);
    }

    /**
     * Finds tasks whose description contains the given keyword (case-insensitive).
     *
     * @param keyword the search keyword
     * @return matching tasks (possibly empty)
     */
    public List<Task> find(String keyword) {
        Pattern p = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);
        return items.stream()
                .filter(t -> p.matcher(t.getDescription()).find())
                .collect(Collectors.toList());
    }

    /**
     * Finds tasks that occur on the specified date.
     *
     * @param date the date to match
     * @return tasks that fall on {@code date}
     */
    public List<Task> findTaskWithDate(LocalDate date) {
        return items.stream().filter(t -> t.isOnDate(date)).collect(Collectors.toList());
    }
}
