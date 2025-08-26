package john;

import john.tasks.Task;

public class Ui {
    private static void printHLine() {
        System.out.println("---------------------------------------");
    }
    public void startUp() {
        printHLine();
        System.out.println("   Hi I'm John.");
        System.out.println("   How can I help you?");
        printHLine();
        System.out.println();
    }
    public void endProgram() {
        printHLine();
        System.out.println("   Bye!");
        printHLine();
    }
    public void markTask(Task task) {
        printHLine();
        System.out.println("   I've marked this task as done:");
        System.out.println("   " + task);
        printHLine();
        System.out.println();
    }
    public void unMarkTask(Task task) {
        printHLine();
        System.out.println("   I've marked this task as not done yet:");
        System.out.println("   " + task);
        printHLine();
        System.out.println();
    }
    public void listTasks(TaskList list) {
        printHLine();
        if (list.size() == 0) {
            System.out.println("You currently have no tasks. Try adding a task.");
        }
        for (int i = 1; i <= list.size(); i++) {
            System.out.println("   " + i + ". " + list.get(i));
        }
        printHLine();
        System.out.println();
    }
    public void addTask(Task task, TaskList list) {
        printHLine();
        System.out.println("   I've added:");
        System.out.println("   " + task);
        System.out.println("   You now have " + list.size() + " tasks.");
        printHLine();
        System.out.println();
    }
    public void deleteTask(Task task, TaskList list) {
        printHLine();
        System.out.println("   I've removed this task:");
        System.out.println("   " + task);
        System.out.println("   You now have " + list.size() + " tasks.");
        printHLine();
        System.out.println();
    }
    public void displayJohnException(JohnException e) {
        printHLine();
        System.out.println("   " + e.getMessage());
        printHLine();
        System.out.println();
    }
}
