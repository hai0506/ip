package john.tasks;

/**
 * A simple task with no extra specifications.
 */
public class ToDo extends Task {
    /**
     * Constructor for todo.
     */
    public ToDo(String name) {
        super(name);
    }
    /**
     * Constructor for todo with specified mark as done.
     */
    public ToDo(String name, boolean done) {
        super(name, done);
    }

    /**
     * String representation of the todo for displaying to user.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
    /**
     * String representation of the todo for saving in the save file.
     */
    @Override
    public String writeString() {
        return "T | " + super.writeString();
    }
}
