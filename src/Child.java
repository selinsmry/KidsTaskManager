import java.util.ArrayList;
import java.util.List;

public class Child {
	private String name;
	private int level = 1;
	private List<Task> tasks;
	private List<Wish> wishes;
	private int points = 0;
	private double averageRating = 0.0;
	private int totalRatings = 0;
	private int ratingSum = 0;

	public Child(String name) {
		this.name = name;
		this.points = 0;
		this.averageRating = 0;
		this.level = 1;
		this.tasks = new ArrayList<>();
		this.wishes = new ArrayList<>();
		this.totalRatings = 0;
		this.ratingSum = 0;
	}

	public void addPoints(int points) {
		this.points += points;
		updateLevel();
//        System.out.println("DEBUG: Added " + points + " points. Total now: " + this.points);
	}

	public void addRating(int rating) {
		this.ratingSum += rating;
		this.totalRatings++;
		this.averageRating = (double) ratingSum / totalRatings;
		updateLevel();
//        System.out.println("DEBUG: New rating added. Average now: " + averageRating);
	}

	private void updateLevel() {
		int newLevel;
		if (points >= 80)
			newLevel = 4;
		else if (points >= 60)
			newLevel = 3;
		else if (points >= 40)
			newLevel = 2;
		else
			newLevel = 1;

		if (newLevel != level) {
			level = newLevel;
			System.out.println("DEBUG: Level updated to " + level + " based on " + points + " points");
		}
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public int getPoints() {
		return points;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public int getLevel() {
		return level;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public List<Wish> getWishes() {
		return wishes;
	}

	public void addTask(Task task) {
		tasks.add(task);
	}

	public void addWish(Wish wish) {
		wishes.add(wish);
	}
}