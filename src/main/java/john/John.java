package john;

import john.tasks.Deadline;
import john.tasks.Event;
import john.tasks.ToDo;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class John {
    private Storage storage;
    private TaskList list;
    private Ui ui;

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

    public void run() {
        this.ui.startUp();
        Scanner sc = new Scanner(System.in);
        String prompt = sc.nextLine();
        Command command = Parser.parseCommand(prompt);

        // main loop
        while (command != Command.BYE) {
            try {
                switch (command) {
                case MARK: {
                    int index = Integer.parseInt(prompt.split(" ")[1]);
                    if (index > list.size() || index <= 0) {
                        throw new JohnException("Task does not exist.");
                    } else {
                        list.get(index).mark();
                        this.ui.markTask(list.get(index));
                    }
                    break;
                }
                case UNMARK: {
                    int index = Integer.parseInt(prompt.split(" ")[1]);
                    if (index > list.size() || index <= 0) {
                        throw new JohnException("Task does not exist.");
                    } else {
                        list.get(index).unMark();
                        this.ui.unMarkTask(list.get(index));
                    }
                    break;
                }
                case LIST: {
                    this.ui.listTasks(this.list);
                    break;
                }
                case TODO: {
                    String name = prompt.substring(4).strip();
                    if (name.equals("")) {
                        throw new JohnException("The description cannot be empty.");
                    } else {
                        ToDo todo = new ToDo(name);
                        list.addTask(todo);
                        this.ui.addTask(todo, list);
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
                            this.ui.addTask(deadline, list);
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
                            this.ui.addTask(event, list);
                        }
                    }
                    break;
                }
                case DELETE: {
                    int index = Integer.parseInt(prompt.split(" ")[1]);
                    if (index > list.size() || index <= 0) {
                        throw new JohnException("Task does not exist.");
                    } else {
                        this.ui.deleteTask(list.deleteTask(index), list);
                    }
                    break;
                }
                case WRONG:
                    throw new JohnException("Wrong command. Please try again.");
                }
            } catch (JohnException e) { // print errors
                this.ui.displayJohnException(e);
            } catch (IndexOutOfBoundsException e) { // when no time is provided after /by or /to
                this.ui.displayJohnException(new JohnException("Please provide the required time details for this task."));
            } catch (DateTimeParseException e) {
                this.ui.displayJohnException(new JohnException("Unable to parse the date. Please use format yyyy-mm-dd"));
            }
            prompt = sc.nextLine();
            command = Parser.parseCommand(prompt);
        }
        this.ui.endProgram();

        // save
        try {
            this.storage.save(this.list);
        } catch (JohnException e) {
            this.ui.displayJohnException(e);
        }
    }

    public static void main(String[] args) {
        new John("data/tasks.txt").run();
    }
}
