package john;

import john.tasks.Task;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> list;
    public TaskList() {
        list = new ArrayList<>();
    }

    public void addTask(Task task) {
        list.add(task);
    }
    public Task deleteTask(int taskIndex) {
        Task task = list.get(taskIndex - 1);
        list.remove(taskIndex - 1);
        return task;
    }
    public Task get(int index) {
        return this.list.get(index - 1);
    }
    public int size() {
        return this.list.size();
    }
}
