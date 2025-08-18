public abstract class Task {
    private String name;
    private boolean done;
    public Task(String name) {
        this.name = name;
        this.done = false;
    }
    public void mark() {
        this.done = true;
    }
    public void unMark() {
        this.done = false;
    }

    @Override
    public String toString() {
        if(this.done) return "[X] " + name;
        return "[] " + name;
    }
}
