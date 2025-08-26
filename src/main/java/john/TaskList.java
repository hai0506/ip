package john;

import john.tasks.Task;

import java.util.ArrayList;

/**
 * List of tasks managed by the chatbot.
 */
public class TaskList {
    private ArrayList<Task> list;
    /**
     * Constructor for TaskList.
     */
    public TaskList() {
        list = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     */
    public void addTask(Task task) {
        list.add(task);
    }
    /**
     * Removes a task from the list.
     */
    public Task deleteTask(int taskIndex) {
        Task task = list.get(taskIndex - 1);
        list.remove(taskIndex - 1);
        return task;
    }
    /**
     * Retrieves a task from the list.
     */
    public Task get(int index) {
        return this.list.get(index - 1);
    }
    /**
     * Gives the current number of tasks in the list.
     */
    public int size() {
        return this.list.size();
    }
}
