public class Main {
	public static void main(String[] args) {
		Child child = new Child("Default Child");
		FileHandler fileHandler = new FileHandler();
		TaskManager taskManager = new TaskManager(fileHandler, child);
		WishManager wishManager = new WishManager(fileHandler, child);
		CommandProcessor processor = new CommandProcessor(taskManager, wishManager, fileHandler);

		// Load data
		taskManager.loadTasks();
		wishManager.loadWishes();

		// Process commands
		processor.processCommands();
		

		if (child.getPoints() < 10) {
			System.err.println("ERROR: Points not added correctly!");
		}
	}
}