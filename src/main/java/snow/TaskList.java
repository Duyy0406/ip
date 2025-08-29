package snow;

import snow.exception.SnowInvalidIndexException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TaskList {
    private final List<Task> items;
    private int size;

    public TaskList() {
        this.items = new ArrayList<Task>();
        this.size = 0;
    }

    public void add(Task task) {
        items.add(task);
        size++;
    }

    public void mark(int index) {
        items.get(index).mark();
    }

    public void unmark(int index) {
        items.get(index).unmark();
    }

    public int size() {
        return this.size;
    }

    public Task get(int i) {
        return items.get(i);
    }

    public Task remove(int i) throws SnowInvalidIndexException {
        if (i < 0 || i >= size) {
            throw new SnowInvalidIndexException();
        }
        size--;
        return items.remove(i);
    }

    public List<Task> find(String keyword) {
        Pattern p = Pattern.compile(Pattern.quote(keyword), Pattern.CASE_INSENSITIVE);
        return items.stream()
                .filter(t -> p.matcher(t.getDescription()).find())
                .collect(Collectors.toList());
    }
}
