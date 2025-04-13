import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
	private FileHandler fileHandler;
	private List<Task> tasks;
	private Child child;

	public TaskManager(FileHandler fileHandler, Child child) {
		this.fileHandler = fileHandler;
		this.tasks = new ArrayList<>();
		this.child = child; // Use the shared child instance
	}

	public void loadTasks() {
		List<String> taskLines = fileHandler.readTasks();
		for (String line : taskLines) {
			// Parse and create tasks from file
			// Implementation depends on your file format
		}
	}

	public void addTask1(String params) {
		// Parse parameters
		String[] parts = params.split("\"");
		String[] firstPart = parts[0].trim().split(" ");
		String assignedByType = firstPart[0];
		String taskId = firstPart[1];

		String title = parts[1].trim();
		String description = parts[3].trim();

		String[] lastPart = parts[4].trim().split(" ");
		String dateStr = lastPart[0];
		String timeStr = lastPart[1];
		int points = Integer.parseInt(lastPart[2]);

		// Parse date and time
		LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));

		// Create person who assigned the task
		Person assignedBy = assignedByType.equals("T") ? new Teacher("Default Teacher") : new Parent("Default Parent");

		// Create and add task
		Task task = new Task(taskId, assignedBy, title, description, date, time, points);
		tasks.add(task);
		child.addTask(task);

		// Save to file
		saveTasks();
		System.out.println("Task " + taskId + " is succesfully added.");

	}

	public void addTask2(String params) {
		try {
			// Sample input: ADD_TASK2 T 102 "School Picnic" "Göksu Park" 2025-03-05 10:00
			// 2025-03-05 12:00 15
			String[] parts = params.split("\"");

			// First part contains: [ADD_TASK2, T/F, taskID]
			String[] firstPart = parts[0].trim().split(" ");
			String assignerType = firstPart[0]; // T or F
			String taskId = firstPart[1];

			// Extract task details
			String title = parts[1].trim();
			String description = parts[3].trim();

			// Last part contains: [startDate, startTime, endDate, endTime, points]
			String[] lastPart = parts[4].trim().split(" ");
			String startDateStr = lastPart[0];
			String startTimeStr = lastPart[1];
			String endDateStr = lastPart[2];
			String endTimeStr = lastPart[3];
			int points = Integer.parseInt(lastPart[4]);

			// Create assigner (Teacher or Parent)
			Person assigner = assignerType.equals("T") ? new Teacher("Default Teacher") : new Parent("Default Parent");

			// Parse date and times
			LocalDate startDate = LocalDate.parse(startDateStr);
			LocalTime startTime = LocalTime.parse(startTimeStr);
			LocalDate endDate = LocalDate.parse(endDateStr);
			LocalTime endTime = LocalTime.parse(endTimeStr);

			// Create and add task
			Task task = new Task(taskId, assigner, title, description, startDate, startTime, endDate, endTime, points);
			tasks.add(task);
			child.addTask(task);

			// Save to file
			saveTasks();

			System.out.println("Task " + taskId + " is succesfully added.");
		} catch (Exception e) {
			System.err.println("Error adding TASK2: " + e.getMessage());
			System.err.println("Command format must be:");
			System.err.println(
					"ADD_TASK2 [T/F] [taskID] \"[title]\" \"[description]\" [startDate] [startTime] [endDate] [endTime] [points]");
			System.err.println(
					"Example: ADD_TASK2 T 102 \"School Picnic\" \"Göksu Park\" 2025-03-05 10:00 2025-03-05 12:00 15");
		}
	}

	public void listAllTasks(String filter) {
		LocalDate today = LocalDate.now();
		List<Task> filteredTasks = new ArrayList<>();

		for (Task task : tasks) {
			LocalDate taskDate = task.hasActivityTime() ? task.getStartDate() : task.getDeadlineDate();

			if (filter.isEmpty()) {
				// No filter - show all
				filteredTasks.add(task);
			} else if (filter.equals("D")) {
				// Daily filter
				if (taskDate.equals(today)) {
					filteredTasks.add(task);
				}
			} else if (filter.equals("W")) {
				// Weekly filter (next 7 days)
				if (!taskDate.isBefore(today) && !taskDate.isAfter(today.plusDays(6))) {
					filteredTasks.add(task);
				}
			}
		}

		// Display results
		if (filteredTasks.isEmpty()) {
			System.out.println(
					"No tasks found" + (filter.equals("D") ? " for today." : filter.equals("W") ? " this week." : "."));
			return;
		}

		System.out
				.println("Tasks (" + (filter.equals("D") ? "Today" : filter.equals("W") ? "This Week" : "All") + "):");

		for (Task task : filteredTasks) {
			System.out.print(task.getId() + ": " + task.getTitle() + " - ");
			if (task.hasActivityTime()) {
				System.out.println(task.getStartDate() + " " + task.getStartTime() + " to " + task.getEndTime());
			} else {
				System.out.println("Due: " + task.getDeadlineDate() + " " + task.getDeadlineTime());
			}
		}
	}

	public void markTaskDone(String taskId) {
		for (Task task : tasks) {
			if (task.getId().equals(taskId)) {
				task.setCompleted(true);
				saveTasks();
				System.out.println("Task " + taskId + " marked as completed.");
				return;
			}
		}
		System.out.println("Task " + taskId + " not found.");
	}

	public void approveTask(String params) {
		String[] parts = params.split(" ");
		if (parts.length < 2) {
			System.err.println("Invalid TASK_CHECKED command format");
			return;
		}

		String taskId = parts[0];
		int rating = Integer.parseInt(parts[1]);

		for (Task task : tasks) {
			if (task.getId().equals(taskId)) {
				if (!task.isCompleted()) {
					System.err.println("Task " + taskId + " not completed yet");
					return;
				}
				if (task.isApproved()) {
					System.err.println("Task " + taskId + " already checked");
					return;
				}

				task.setApproved(true);
				task.setRating(rating);

				// Update child's points and rating
				child.addPoints(task.getPoints());
				child.addRating(rating);

				saveTasks();
				System.out.println("Task " + taskId + " checked. Added " + task.getPoints() + " points and " + rating
						+ " rating.");
				return;
			}
		}
		System.err.println("Task " + taskId + " not found");
	}

	private void saveTasks() {
		List<String> taskLines = new ArrayList<>();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		for (Task task : tasks) {
			StringBuilder line = new StringBuilder();

			// Basic task info
			line.append(task.getAssignedBy().getType()).append(","); // T or F
			line.append(task.getId()).append(",");
			line.append("\"").append(task.getTitle()).append("\",");
			line.append("\"").append(task.getDescription()).append("\",");

			// Task type specific fields
			if (task.hasActivityTime()) {
				// TASK2 format with activity time
				line.append(task.getStartDate().format(dateFormatter)).append(",");
				line.append(task.getStartTime().format(timeFormatter)).append(",");
				line.append(task.getEndDate().format(dateFormatter)).append(",");
				line.append(task.getEndTime().format(timeFormatter)).append(",");
				line.append(task.getPoints()).append(",");
			} else {
				// TASK1 format with just deadline
				line.append(task.getDeadlineDate().format(dateFormatter)).append(",");
				line.append(task.getDeadlineTime().format(timeFormatter)).append(",,,"); // Empty activity time fields
				line.append(task.getPoints()).append(",");
			}

			// Completion status
			line.append(task.isCompleted()).append(",");
			line.append(task.isApproved()).append(",");
			line.append(task.getRating());

			taskLines.add(line.toString());
		}

		fileHandler.writeTasks(taskLines);
	}
}