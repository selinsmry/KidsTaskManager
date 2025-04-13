import java.util.List;

public class CommandProcessor {
	private TaskManager taskManager;
	private WishManager wishManager;
	private FileHandler fileHandler;

	public CommandProcessor(TaskManager taskManager, WishManager wishManager, FileHandler fileHandler) {
		this.taskManager = taskManager;
		this.wishManager = wishManager;
		this.fileHandler = fileHandler;
	}

	public void processCommands() {
		List<String> commands = fileHandler.readCommands();

		for (String command : commands) {
			String[] parts = command.split(" ", 2);
			String cmd = parts[0];
			String params = parts.length > 1 ? parts[1] : "";

			switch (cmd) {
			case "ADD_TASK1":
				taskManager.addTask1(params);
				break;
			case "ADD_TASK2":
				taskManager.addTask2(params);
				break;
			case "LIST_ALL_TASKS":
				taskManager.listAllTasks(params);
				break;
			case "LIST_ALL_WISHES":
				wishManager.listAllWishes();
				break;
			case "TASK_DONE":
				taskManager.markTaskDone(params);
				break;
			case "TASK_CHECKED":
				taskManager.approveTask(params);
				break;
			case "ADD_WISH1":
				wishManager.addWish1(params);
				break;
			case "ADD_WISH2":
				wishManager.addWish2(params);
				break;
			case "ADD_BUDGET_COIN":
				wishManager.addBudgetCoin(params);
				break;
			case "WISH_CHECKED":
				wishManager.checkWish(params);
				break;
			case "PRINT_BUDGET":
				wishManager.printBudget();
				break;
			case "PRINT_STATUS":
				wishManager.printStatus();
				break;
			default:
				System.out.println("Unknown command: " + cmd);
			}
		}
	}
}