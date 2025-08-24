package john.tasks;

public class Deadline extends Task {
    private String deadline;
    public Deadline(String name, String deadline) {
        super(name);
        this.deadline = deadline;
    }
    public Deadline(String name, String deadline, boolean done) {
        super(name, done);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + deadline + ")";
    }
    @Override
    public String writeString() {
        return "D | " + super.writeString() + " | " + this.deadline;
    }
}
