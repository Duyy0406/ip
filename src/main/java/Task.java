public class Task {
    private final String name;

    Task(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
