package john;

public class Parser {
    public static Command parseCommand(String prompt) {
        String lower = prompt.toLowerCase();
        if (lower.matches("^mark\\s\\d+$")) {
            return Command.MARK;
        }
        if (lower.matches("^unmark\\s\\d+$")) {
            return Command.UNMARK;
        }
        if (lower.equals("list")) {
            return Command.LIST;
        }
        if (lower.startsWith("todo")) {
            return Command.TODO;
        }
        if (lower.startsWith("deadline")) {
            return Command.DEADLINE;
        }
        if (lower.startsWith("event")) {
            return Command.EVENT;
        }
        if (lower.matches("^delete\\s\\d+$")) {
            return Command.DELETE;
        }
        if (lower.equals("bye")) {
            return Command.BYE;
        }
        return Command.WRONG;
    }
}
