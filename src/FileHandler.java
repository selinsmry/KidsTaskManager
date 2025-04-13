import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
	private static final String TASKS_FILE = "Tasks.txt";
	private static final String WISHES_FILE = "Wishes.txt";
	private static final String COMMANDS_FILE = "Commands.txt";

	public List<String> readCommands() {
		try {
			return Files.readAllLines(Paths.get(COMMANDS_FILE));
		} catch (IOException e) {
			System.err.println("Error reading commands file: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public List<String> readTasks() {
		try {
			return Files.readAllLines(Paths.get(TASKS_FILE));
		} catch (IOException e) {
			System.err.println("Error reading tasks file: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public List<String> readWishes() {
		try {
			return Files.readAllLines(Paths.get(WISHES_FILE));
		} catch (IOException e) {
			System.err.println("Error reading wishes file: " + e.getMessage());
			return new ArrayList<>();
		}
	}

	public void writeTasks(List<String> tasks) {
		try (PrintWriter out = new PrintWriter(TASKS_FILE)) {
			tasks.forEach(out::println);
		} catch (FileNotFoundException e) {
			System.err.println("Error writing tasks file: " + e.getMessage());
		}
	}

	public void writeWishes(List<String> wishes) {
		try (PrintWriter out = new PrintWriter(WISHES_FILE)) {
			wishes.forEach(out::println);
		} catch (FileNotFoundException e) {
			System.err.println("Error writing wishes file: " + e.getMessage());
		}
	}
}