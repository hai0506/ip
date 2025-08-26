package john;

import john.tasks.Task;
import java.util.ArrayList;

/**
 * Class for handling text displays for the chatbot.
 */
public class Ui {
    /**
     * Prints a horizontal line.
     */
    private static void printHLine() {
        System.out.println("---------------------------------------");
    }
    /**
     * Greets the user on program start.
     */
    public void startUp() {
        printHLine();
        System.out.println("   Hi I'm John.");
        System.out.println("   How can I help you?");
        printHLine();
        System.out.println();
    }
    /**
     * Say goodbye to the user on program end.
     */
    public void endProgram() {
        printHLine();
        System.out.println("   Bye!");
        printHLine();
    }
    /**
     * Displays the output of marking a task as done.
     */
    public void markTask(Task task) {
        printHLine();
        System.out.println("   I've marked this task as done:");
        System.out.println("   " + task);
        printHLine();
        System.out.println();
    }
    /**
     * Displays the output of unmarking a task as done.
     */
    public void unMarkTask(Task task) {
        printHLine();
        System.out.println("   I've marked this task as not done yet:");
        System.out.println("   " + task);
        printHLine();
        System.out.println();
    }
    /**
     * List the current tasks in the list.
     */
    public void listTasks(TaskList list) {
        printHLine();
        if (list.size() == 0) {
            System.out.println("   You currently have no tasks. Try adding a task.");
        }
        for (int i = 1; i <= list.size(); i++) {
            System.out.println("   " + i + ". " + list.get(i));
        }
        printHLine();
        System.out.println();
    }
    /**
     * Displays the output of adding a task to the list.
     */
    public void addTask(Task task, TaskList list) {
        printHLine();
        System.out.println("   I've added:");
        System.out.println("   " + task);
        System.out.println("   You now have " + list.size() + " tasks.");
        printHLine();
        System.out.println();
    }
    /**
     * Displays the output of removing a task from the list.
     */
    public void deleteTask(Task task, TaskList list) {
        printHLine();
        System.out.println("   I've removed this task:");
        System.out.println("   " + task);
        System.out.println("   You now have " + list.size() + " tasks.");
        printHLine();
        System.out.println();
    }
    /**
     * Display the list of tasks that matches the search.
     */
    public void findTasks(ArrayList<Integer> indices, TaskList list) {
        printHLine();
        if (list.size() == 0) {
            System.out.println("   You currently have no tasks. Try adding a task.");
        }
        else if (indices.size() == 0) {
            System.out.println("   There are no tasks matching your search.");
        }
        else {
            System.out.println("   Here are the matching tasks in your list:");
            for (int i : indices) {
                System.out.println("   " + i + ". " + list.get(i));
            }
        }
        printHLine();
        System.out.println();
    }
    /**
     * Displays error messages.
     */
    public void displayJohnException(JohnException e) {
        printHLine();
        System.out.println("   " + e.getMessage());
        printHLine();
        System.out.println();
    }
}
