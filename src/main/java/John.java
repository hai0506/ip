import java.util.Scanner;

public class John {
    public static void printHLine() {
        System.out.println("---------------------------------------");
    }
    public static void main(String[] args) {
        Task[] list = new Task[100];
        int listIndex = 0;
        printHLine();
        System.out.println("    Hi I'm John.");
        System.out.println("    How can I help you?");
        printHLine();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        String prompt = sc.nextLine();
        while(!prompt.toLowerCase().equals("bye")) {
            printHLine();
            if(prompt.toLowerCase().matches("^mark\\s\\d+$")) {
                int index = Integer.parseInt(prompt.split(" ")[1]);
                if(index > listIndex || index <= 0) {
                    System.out.println("Task does not exist.");
                }
                else {
                    System.out.println("    I've marked this task as done:");
                    list[index - 1].mark();
                    System.out.println("    " + list[index - 1]);
                }
            }
            else if(prompt.toLowerCase().matches("^unmark\\s\\d+$")) {
                int index = Integer.parseInt(prompt.split(" ")[1]);
                if(index > listIndex || index <= 0) {
                    System.out.println("Task does not exist.");
                }
                else {
                    System.out.println("    I've marked this task as not done yet:");
                    list[index - 1].unMark();
                    System.out.println("    " + list[index - 1]);
                }
            }
            else if(prompt.toLowerCase().equals("list")) {
                for(int i = 0; i < listIndex; i++) {
                    System.out.println("    " + (i + 1) + ". " + list[i]);
                }
            }
            else {
                System.out.println("    added: " + prompt);
                list[listIndex] = new Task(prompt);
                listIndex++;
            }
            printHLine();
            System.out.println();
            prompt = sc.nextLine();
        }
        printHLine();
        System.out.println("     Bye!");
        printHLine();
    }
}
