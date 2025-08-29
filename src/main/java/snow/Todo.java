package snow;

public class Todo extends Task {

    public Todo(String name) {
        super(name);
    }

    @Override
    public String toSaveString() {
        return "T | " + super.toSaveString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
