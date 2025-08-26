package john;

import john.tasks.Deadline;
import john.tasks.Event;
import john.tasks.Task;
import john.tasks.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class John {
    public John(String filePath) {

    }

    private static final String FILEPATH = "data/tasks.txt";

    public static void printHLine() {
        System.out.println("---------------------------------------");
    }

    public void run() {
        ArrayList<Task> list = new ArrayList<>();

        // load file
        File dataFile = new File(FILEPATH);
        if (!dataFile.getParentFile().exists()) {
            dataFile.getParentFile().mkdirs();
        }
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Could not create data file.");
            }
        } else {
            try {
                Scanner fileReader = new Scanner(dataFile);
                while (fileReader.hasNext()) {
                    String[] data = fileReader.nextLine().split(" \\| ");
                    try {
                        switch (data[0]) {
                            case "T":
                                list.add(new ToDo(data[2], data[1].equals("1")));
                                break;
                            case "D":
                                list.add(new Deadline(data[2], LocalDate.parse(data[3]), data[1].equals("1")));
                                break;
                            case "E":
                                list.add(new Event(data[2], LocalDate.parse(data[3]),
                                        LocalDate.parse(data[4]), data[1].equals("1")));
                                break;
                        }
                    } catch(DateTimeParseException e) {
                        System.out.println("Unable to parse date from save file.");
                    }
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            }
        }

        printHLine();
        System.out.println("   Hi I'm John.");
        System.out.println("   How can I help you?");
        printHLine();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        String prompt = sc.nextLine();
        Command command = Parser.parseCommand(prompt);

        // main loop
        while (command != Command.BYE) {
            printHLine();
            try {
                switch (command) {
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
                            Deadline deadline = new Deadline(name, LocalDate.parse(time));
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
                            Event event = new Event(name, LocalDate.parse(start), LocalDate.parse(end));
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
            catch (JohnException e) { // print errors
                System.out.println(e.getMessage());
            }
            catch (IndexOutOfBoundsException e) { // when no time is provided after /by or /to
                System.out.println("   Please provide the required time details for this task.");
            }
            catch (DateTimeParseException e) {
                System.out.println("Unable to parse the date. Please use format yyyy-mm-dd");
            }
            printHLine();
            System.out.println();
            prompt = sc.nextLine();
            command = Parser.parseCommand(prompt);
        }
        // end
        printHLine();
        System.out.println("   Bye!");
        printHLine();

        // save tasks
        try {
            FileWriter fileWriter = new FileWriter(FILEPATH);
            for(Task task : list) {
                fileWriter.write(task.writeString() + "\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to write to file.");
        }
    }
    public static void main(String[] args) {
        new John("data/tasks.txt").run();
    }
}
