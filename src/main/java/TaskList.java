public class TaskList {
    private final Task[] items;
    private final int capacity;
    private int size;

    public TaskList(int capacity) {
        this.items = new Task[capacity];
        this.capacity = capacity;
    }

    public boolean add(Task task) {
        if (size >= capacity) {
            return false;
        }
        items[size++] = task;
        return true;
    }

    public void mark(int index) {
        items[index].mark();
    }

    public void unmark(int index) {
        items[index].unmark();
    }

    public int size() {
        return this.size;
    }

    public Task get(int i) {
        return items[i];
    }
}
