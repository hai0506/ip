package john.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDate start;
    private LocalDate end;
    public Event(String name, LocalDate start, LocalDate end) {
        super(name);
        this.start = start;
        this.end = end;
    }
    public Event(String name, LocalDate start, LocalDate end, boolean done) {
        super(name, done);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " +
                this.start.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ", to: " +
                this.end.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ")";
    }
    @Override
    public String writeString() {
        return "E | " + super.writeString() + " | " +
                this.start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + " | " +
                this.end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
