import java.util.Locale;
import java.util.Scanner;
public class John {
    public static void printHLine() {
        System.out.println("---------------------------------------");
    }
    public static void main(String[] args) {
        printHLine();
        System.out.println("    Hi I'm John.");
        System.out.println("    How can I help you?");
        printHLine();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        String prompt = sc.nextLine();
        while(!prompt.toLowerCase().equals("bye")) {
            printHLine();
            System.out.println("    " + prompt);
            printHLine();
            System.out.println();
            prompt = sc.nextLine();
        }
        printHLine();
        System.out.println("     Bye!");
        printHLine();
    }
}
