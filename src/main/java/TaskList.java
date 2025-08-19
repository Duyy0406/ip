public class TaskList {
    private final Task[] items;
    private int capacity;
    private int size;

    TaskList(int capacity) {
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

    public int size() {
        return this.size;
    }

    public Task get(int i) {
        return items[i];
    }
}
