import java.time.LocalDate;
import java.time.LocalTime;

public class Wish {
	private String id;
	private String title;
	private String description;
	private LocalDate startDate; // For WISH2
	private LocalTime startTime; // For WISH2
	private LocalDate endDate; // For WISH2
	private LocalTime endTime; // For WISH2
	private String status; // "PENDING", "APPROVED", "REJECTED"
	private int requiredLevel;

	// Constructor for WISH1 (product)
	public Wish(String id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = "PENDING";
		this.requiredLevel = 1;
	}

	// Constructor for WISH2 (activity)
	public Wish(String id, String title, String description, LocalDate startDate, LocalTime startTime,
			LocalDate endDate, LocalTime endTime) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.status = "PENDING";
		this.requiredLevel = 1;
	}

	// Getters and setters
	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
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

	public String getStatus() {
		return status;
	}

	public int getRequiredLevel() {
		return requiredLevel;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setRequiredLevel(int requiredLevel) {
		this.requiredLevel = requiredLevel;
	}

	public boolean isActivity() {
		return startDate != null && startTime != null && endDate != null && endTime != null;
	}

	// Helper method to display time range
	public String getActivityTime() {
		if (!isActivity())
			return "N/A";
		return startDate + " " + startTime + " to " + endDate + " " + endTime;
	}
}