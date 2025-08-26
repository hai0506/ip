package john.tasks;

/**
 * Abstract representation of a task that the chatbot manages.
 */
public abstract class Task {
    private String name;
    private boolean done;
    /**
     * Constructor for task.
     */
    public Task(String name) {
        this.name = name;
        this.done = false;
    }
    /**
     * Constructor for task with specified mark as done.
     */
    public Task(String name, boolean done) {
        this.name = name;
        this.done = done;
    }

    /**
     * Mark a task as done.
     */
    public void mark() {
        this.done = true;
    }
    /**
     * Unmark a task as done.
     */
    public void unMark() {
        this.done = false;
    }

    /**
     * Check whether the keyword is in the task name.
     */
    public boolean nameContains(String keyword) {
        return this.name.contains(keyword);
    }

    /**
     * String representation of the task for displaying to user.
     */
    @Override
    public String toString() {
        if(this.done) {
            return "[X] " + name;
        }
        return "[] " + name;
    }
    /**
     * String representation of the task for saving in the save file.
     */
    public String writeString() {
        int doneInt = done ? 1 : 0;
        return doneInt + " | " + name;
    }
}
