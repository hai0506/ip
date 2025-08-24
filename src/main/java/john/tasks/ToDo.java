package john.tasks;

public class ToDo extends Task {
    public ToDo(String name) {
        super(name);
    }
    public ToDo(String name, boolean done) {
        super(name, done);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
    @Override
    public String writeString() {
        return "T | " + super.writeString();
    }
}
