import java.time.LocalDate;
import java.time.LocalTime;

public class Task {
	private String id;
	private Person assignedBy;
	private String title;
	private String description;
	private LocalDate deadlineDate;
	private LocalTime deadlineTime;
	private LocalDate startDate;
	private LocalTime startTime;
	private LocalDate endDate;
	private LocalTime endTime;
	private int points;
	private boolean completed;
	private boolean approved;
	private int rating;

	// Constructor for TASK1 (without activity time)
	public Task(String id, Person assignedBy, String title, String description, LocalDate deadlineDate,
			LocalTime deadlineTime, int points) {
		this.id = id;
		this.assignedBy = assignedBy;
		this.title = title;
		this.description = description;
		this.deadlineDate = deadlineDate;
		this.deadlineTime = deadlineTime;
		this.points = points;
		this.completed = false;
		this.approved = false;
		this.rating = 0;
	}

	// Constructor for TASK2 (with activity time)
	public Task(String id, Person assignedBy, String title, String description, LocalDate startDate,
			LocalTime startTime, LocalDate endDate, LocalTime endTime, int points) {
		this.id = id;
		this.assignedBy = assignedBy;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.points = points;
		this.completed = false;
		this.approved = false;
		this.rating = 0;
	}

	// Getters and setters
	public String getId() {
		return id;
	}

	public Person getAssignedBy() {
		return assignedBy;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDeadlineDate() {
		return deadlineDate;
	}

	public LocalTime getDeadlineTime() {
		return deadlineTime;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public int getPoints() {
		return points;
	}

	public boolean isCompleted() {
		return completed;
	}

	public boolean isApproved() {
		return approved;
	}

	public int getRating() {
		return rating;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public boolean hasActivityTime() {
		return startDate != null && startTime != null && endDate != null && endTime != null;
	}
}