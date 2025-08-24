package john.tasks;

public class Event extends Task {
    private String start;
    private String end;
    public Event(String name, String start, String end) {
        super(name);
        this.start = start;
        this.end = end;
    }
    public Event(String name, String start, String end, boolean done) {
        super(name, done);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + start + ", to: " + end + ")";
    }
    @Override
    public String writeString() {
        return "E | " + super.writeString() + " | " + this.start + " | " + this.end;
    }
}
