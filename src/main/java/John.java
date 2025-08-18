import java.util.Locale;
import java.util.Scanner;
public class John {
    public static void printHLine() {
        System.out.println("---------------------------------------");
    }
    public static void main(String[] args) {
        String[] list = new String[100];
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
            if(prompt.toLowerCase().equals("list")) {
                for(int i = 0; i < listIndex; i++) {
                    System.out.println("    " + (i + 1) + ". " + list[i]);
                }
            }
            else {
                System.out.println("    added: " + prompt);
                list[listIndex] = prompt;
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
