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
    public void deleteTask(int taskIndex) {
        list.remove(taskIndex - 1);
    }
    public Task get(int index) {
        return this.list.get(index - 1);
    }
    public int size() {
        return this.list.size();
    }
}
