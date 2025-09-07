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
                int index = Integer.parseInt(prompt.split(" ")[1]);
                if (index > list.size() || index <= 0) {
                    throw new JohnException("Task does not exist.");
                } else {
                    list.get(index).mark();
                    res = this.ui.markTask(list.get(index));
                }
                break;
            }
            case UNMARK: {
                int index = Integer.parseInt(prompt.split(" ")[1]);
                if (index > list.size() || index <= 0) {
                    throw new JohnException("Task does not exist.");
                } else {
                    list.get(index).unMark();
                    res = this.ui.unMarkTask(list.get(index));
                }
                break;
            }
            case LIST: {
                res = this.ui.listTasks(this.list);
                break;
            }
            case TODO: {
                String name = prompt.substring(4).strip();
                if (name.equals("")) {
                    throw new JohnException("The description cannot be empty.");
                } else {
                    ToDo todo = new ToDo(name);
                    list.addTask(todo);
                    res = this.ui.addTask(todo, list);
                }
                break;
            }
            case DEADLINE: {
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
                        res = this.ui.addTask(deadline, list);
                    }
                }
                break;
            }
            case EVENT: {
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
                        res = this.ui.addTask(event, list);
                    }
                }
                break;
            }
            case DELETE: {
                int index = Integer.parseInt(prompt.split(" ")[1]);
                if (index > list.size() || index <= 0) {
                    throw new JohnException("Task does not exist.");
                } else {
                    res = this.ui.deleteTask(list.deleteTask(index), list);
                }
                break;
            }
            case FIND: {
                String keyword = prompt.substring(4).strip();
                if (keyword.equals("")) {
                    throw new JohnException("Please provide the search term.");
                }
                res = this.ui.findTasks(this.list.search(keyword), this.list);
                break;
            }
            case BYE:
                res = this.ui.endProgram();
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

        // save
        try {
            this.storage.save(this.list);
        } catch (JohnException e) {
            res = this.ui.displayJohnException(e);
        }

        assert res != "" : "Bot response should not be empty.";
        return res;
    }
}
