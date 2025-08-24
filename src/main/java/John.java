import java.util.ArrayList;
import java.util.Scanner;

public class John {
    public enum Command {
        MARK, UNMARK, LIST, TODO, DEADLINE, EVENT, DELETE, BYE, WRONG;

        public static Command getCommand(String prompt) {
            String lower = prompt.toLowerCase();
            if (lower.matches("^mark\\s\\d+$")) {
                return MARK;
            }
            if (lower.matches("^unmark\\s\\d+$")) {
                return UNMARK;
            }
            if (lower.equals("list")) {
                return LIST;
            }
            if (lower.startsWith("todo")) {
                return TODO;
            }
            if (lower.startsWith("deadline")) {
                return DEADLINE;
            }
            if (lower.startsWith("event")) {
                return EVENT;
            }
            if (lower.matches("^delete\\s\\d+$")) {
                return DELETE;
            }
            if (lower.equals("bye")) {
                return BYE;
            }
            return WRONG;
        }
    }

    public static void printHLine() {
        System.out.println("---------------------------------------");
    }

    public static void main(String[] args) {
        ArrayList<Task> list = new ArrayList<>();
        printHLine();
        System.out.println("   Hi I'm John.");
        System.out.println("   How can I help you?");
        printHLine();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        String prompt = sc.nextLine();
        Command command = Command.getCommand(prompt);

        // main loop
        while(command != Command.BYE) {
            printHLine();
            try {
                switch(command) {
                case MARK: {
                    int index = Integer.parseInt(prompt.split(" ")[1]);
                    if (index > list.size() || index <= 0) {
                        throw new JohnException("   Task does not exist.");
                    } else {
                        System.out.println("   I've marked this task as done:");
                        list.get(index - 1).mark();
                        System.out.println("   " + list.get(index - 1));
                    }
                    break;
                }
                case UNMARK: {
                    int index = Integer.parseInt(prompt.split(" ")[1]);
                    if (index > list.size() || index <= 0) {
                        throw new JohnException("   Task does not exist.");
                    } else {
                        System.out.println("   I've marked this task as not done yet:");
                        list.get(index - 1).unMark();
                        System.out.println("   " + list.get(index - 1));
                    }
                    break;
                }
                case LIST: {
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println("   " + (i + 1) + ". " + list.get(i));
                    }
                    break;
                }
                case TODO: {
                    String name = prompt.substring(4).strip();
                    if (name.equals("")) {
                        throw new JohnException("   The description cannot be empty.");
                    } else {
                        ToDo todo = new ToDo(name);
                        list.add(todo);
                        System.out.println("   I've added:");
                        System.out.println("   " + todo);
                        System.out.println("   You now have " + list.size() + " tasks.");
                    }
                    break;
                }
                case DEADLINE: {
                    if (!prompt.contains("/by")) {
                        throw new JohnException("   Please provide the deadline.");
                    } else {
                        String[] split = prompt.substring(8).split("/by");
                        String name = split[0].strip();
                        String time = split[1].strip();

                        if (name.equals("")) {
                            throw new JohnException("   The description cannot be empty.");
                        } else if (time.equals("")) {
                            throw new JohnException("   The deadline cannot be empty.");
                        } else {
                            Deadline deadline = new Deadline(name, time);
                            list.add(deadline);
                            System.out.println("   I've added:");
                            System.out.println("   " + deadline);
                            System.out.println("   You now have " + list.size() + " tasks.");
                        }
                    }
                    break;
                }
                case EVENT: {
                    if (!prompt.contains("/from") || !prompt.contains("/to")) {
                        throw new JohnException("   Please provide start and end dates.");
                    } else {
                        String[] split = prompt.substring(6).split("/from");
                        String name = split[0].strip();
                        String[] time = split[1].split("/to");
                        String start = time[0].strip();
                        String end = time[1].strip();

                        if (name.equals("")) {
                            throw new JohnException("   The description cannot be empty.");
                        } else if (start.equals("") || end.equals("")) {
                            throw new JohnException("   The start and end dates cannot be empty.");
                        } else {
                            Event event = new Event(name, start, end);
                            list.add(event);
                            System.out.println("   I've added:");
                            System.out.println("   " + event);
                            System.out.println("   You now have " + list.size() + " tasks.");
                        }
                    }
                    break;
                }
                case DELETE: {
                    int index = Integer.parseInt(prompt.split(" ")[1]);
                    if (index > list.size() || index <= 0) {
                        throw new JohnException("   Task does not exist.");
                    } else {
                        System.out.println("   I've removed this task:");
                        System.out.println("   " + list.get(index - 1));
                        list.remove(index - 1);
                        System.out.println("   You now have " + list.size() + " tasks.");
                    }
                    break;
                }
                case WRONG:
                    throw new JohnException("   Wrong command. Please try again.");
                }
            }
            catch(JohnException e) { // print errors
                System.out.println(e.getMessage());
            }
            catch(IndexOutOfBoundsException e) { // when no time is provided after /by or /to
                System.out.println("   Please provide the required time details for this task.");
            }
            printHLine();
            System.out.println();
            prompt = sc.nextLine();
            command = Command.getCommand(prompt);
        }
        // end
        printHLine();
        System.out.println("   Bye!");
        printHLine();
    }
}
