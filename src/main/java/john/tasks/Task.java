package john.tasks;

public abstract class Task {
    private String name;
    private boolean done;
    public Task(String name) {
        this.name = name;
        this.done = false;
    }
    public Task(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    public void mark() {
        this.done = true;
    }
    public void unMark() {
        this.done = false;
    }

    /**
     * Check whether the keyword is in the task name.
     */
    public boolean nameContains(String keyword) {
        return this.name.contains(keyword);
    }

    @Override
    public String toString() {
        if(this.done) {
            return "[X] " + name;
        }
        return "[] " + name;
    }
    public String writeString() {
        int doneInt = done ? 1 : 0;
        return doneInt + " | " + name;
    }
}
