public class Parent extends Person {
	public Parent(String name) {
		super(name, "P");
	}

	public void approveTask(Task task, int rating) {
		task.setApproved(true);
		task.setRating(rating);
	}

	public void approveWish(Wish wish, int requiredLevel) {
		wish.setStatus("APPROVED");
		wish.setRequiredLevel(requiredLevel);
	}

	public void rejectWish(Wish wish) {
		wish.setStatus("REJECTED");
	}
}