package john;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import john.tasks.Deadline;
import john.tasks.Event;
import john.tasks.ToDo;


/**
 * The John chatbot task manager.
 */
public class John {
    private Storage storage;
    private TaskList list;
    private Ui ui;

    /**
     * Constructor for John.
     */
    public John(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            list = storage.load();
        } catch (JohnException e) {
            this.ui.displayJohnException(e);
            list = new TaskList();
        }
    }

    /**
     * Returns John's greeting on startup.
     */
    public String greet() {
        return this.ui.greet();
    }

    /**
     * Returns John's response to a user input.
     */
    public String getResponse(String prompt) {
        Command command = Parser.parseCommand(prompt);
        String res = "";
        try {
            switch (command) {
            case MARK: {
                res = mark(prompt);
                break;
            }
            case UNMARK: {
                res = unMark(prompt);
                break;
            }
            case LIST: {
                res = list();
                break;
            }
            case TODO: {
                res = createToDo(prompt);
                break;
            }
            case DEADLINE: {
                res = createDeadline(prompt);
                break;
            }
            case EVENT: {
                res = createEvent(prompt);
                break;
            }
            case DELETE: {
                res = delete(prompt);
                break;
            }
            case FIND: {
                res = find(prompt);
                break;
            }
            case BYE:
                res = bye();
                break;
            default:
                throw new JohnException("Wrong command. Please try again.");
            }
        } catch (JohnException e) { // print errors
            res = this.ui.displayJohnException(e);
        } catch (IndexOutOfBoundsException e) { // when no time is provided after /by or /to
            res = this.ui.displayJohnException(
                    new JohnException("Please provide the required details for this task."));
        } catch (DateTimeParseException e) {
            res = this.ui.displayJohnException(
                    new JohnException("Unable to parse the date. Please use format yyyy-mm-dd"));
        }

        try {
            saveData();
        } catch (JohnException e) {
            res = this.ui.displayJohnException(e);
        }

        assert res != "" : "Bot response should not be empty.";
        return res;
    }

    private String mark(String prompt) throws JohnException {
        int index = Integer.parseInt(prompt.split(" ")[1]);
        if (index > list.size() || index <= 0) {
            throw new JohnException("Task does not exist.");
        } else {
            list.get(index).mark();
            return this.ui.markTask(list.get(index));
        }
    }
    private String unMark(String prompt) throws JohnException {
        int index = Integer.parseInt(prompt.split(" ")[1]);
        if (index > list.size() || index <= 0) {
            throw new JohnException("Task does not exist.");
        } else {
            list.get(index).unMark();
            return this.ui.unMarkTask(list.get(index));
        }
    }
    private String list() throws JohnException {
        return this.ui.listTasks(this.list);
    }
    private String createToDo(String prompt) throws JohnException {
        String name = prompt.substring(4).strip();
        if (name.equals("")) {
            throw new JohnException("The description cannot be empty.");
        } else {
            ToDo todo = new ToDo(name);
            list.addTask(todo);
            return this.ui.addTask(todo, list);
        }
    }
    private String createDeadline(String prompt) throws JohnException {
        if (!prompt.contains("/by")) {
            throw new JohnException("Please provide the deadline.");
        } else {
            String[] split = prompt.substring(8).split("/by");
            String name = split[0].strip();
            String time = split[1].strip();

            if (name.equals("")) {
                throw new JohnException("The description cannot be empty.");
            } else if (time.equals("")) {
                throw new JohnException("The deadline cannot be empty.");
            } else {
                Deadline deadline = new Deadline(name, LocalDate.parse(time));
                list.addTask(deadline);
                return this.ui.addTask(deadline, list);
            }
        }
    }
    private String createEvent(String prompt) throws JohnException {
        if (!prompt.contains("/from") || !prompt.contains("/to")) {
            throw new JohnException("Please provide start and end dates.");
        } else {
            String[] split = prompt.substring(6).split("/from");
            String name = split[0].strip();
            String[] time = split[1].split("/to");
            String start = time[0].strip();
            String end = time[1].strip();

            if (name.equals("")) {
                throw new JohnException("The description cannot be empty.");
            } else if (start.equals("") || end.equals("")) {
                throw new JohnException("The start and end dates cannot be empty.");
            } else {
                Event event = new Event(name, LocalDate.parse(start), LocalDate.parse(end));
                list.addTask(event);
                return this.ui.addTask(event, list);
            }
        }
    }
    private String delete(String prompt) throws JohnException {
        int index = Integer.parseInt(prompt.split(" ")[1]);
        if (index > list.size() || index <= 0) {
            throw new JohnException("Task does not exist.");
        } else {
            return this.ui.deleteTask(list.deleteTask(index), list);
        }
    }
    private String find(String prompt) throws JohnException {
        String keyword = prompt.substring(4).strip();
        if (keyword.equals("")) {
            throw new JohnException("Please provide the search term.");
        }
        return this.ui.findTasks(this.list.search(keyword), this.list);
    }
    private String bye() throws JohnException {
        return this.ui.endProgram();
    }
    private void saveData() throws JohnException {
        this.storage.save(this.list);
    }
}
