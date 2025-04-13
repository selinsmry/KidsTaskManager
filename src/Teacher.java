public class Teacher extends Person {
	public Teacher(String name) {
		super(name, "T");
	}

	public void approveTask(Task task, int rating) {
		task.setApproved(true);
		task.setRating(rating);
	}
}