import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WishManager {
	private FileHandler fileHandler;
	private List<Wish> wishes;
	private Child child;

	public WishManager(FileHandler fileHandler, Child child) {
		this.fileHandler = fileHandler;
		this.wishes = new ArrayList<>();
		this.child = child; // Use the shared child instance
	}

	public void loadWishes() {
		List<String> wishLines = fileHandler.readWishes();
		for (String line : wishLines) {
			// Parse and create wishes from file
			// Implementation depends on your file format
		}
	}

	public void addWish1(String params) {
		// Parse parameters for product wish
		String[] parts = params.split("\"");
		if (parts.length < 4) {
			System.err.println("Invalid wish format: " + params);
			return;
		}

		String[] firstPart = parts[0].trim().split(" ");
		String wishId = firstPart[0];

		String title = parts[1].trim();
		String description = parts[3].trim();

		// Create and add wish
		Wish wish = new Wish(wishId, title, description);
		wishes.add(wish);
		child.addWish(wish);
		System.out.println("Wish " + wishId + " is succesfully added.");

		// Save to file
		saveWishes();
	}

	public void addWish2(String fullCommand) {
		try {
			// First remove "ADD_WISH2" if present (some commands might include it)
			String params = fullCommand.replaceFirst("^ADD_WISH2\\s+", "");

			// Split the input string while preserving quoted strings
			List<String> parts = new ArrayList<>();
			Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(params);

			while (m.find()) {
				if (m.group(1) != null) {
					parts.add(m.group(1)); // Add quoted string without quotes
				} else {
					parts.add(m.group(2)); // Add non-quoted strings
				}
			}

			// We expect exactly 6 parameters (W103, title, description, date, start, end)
			if (parts.size() != 7) {
				System.err.println("Invalid format. Expected 7 parameters, got " + parts.size());
				System.err.println("Received: " + params);
				System.err.println("Parts detected: " + parts);
				System.err.println("Please use exactly:");
				System.err.println("W103 \"Title\" \"Description\" yyyy-MM-dd HH:mm HH:mm");
				return;
			}

			// Extract components
			String wishId = parts.get(0);
			String title = parts.get(1);
			String description = parts.get(2);
			String startdateStr = parts.get(3);
			String startTimeStr = parts.get(4);
			String enddateStr = parts.get(5);
			String endTimeStr = parts.get(6);

			// Parse date and times
			LocalDate startDate = LocalDate.parse(startdateStr);
			LocalTime startTime = LocalTime.parse(startTimeStr);
			LocalDate endDate = LocalDate.parse(enddateStr);
			LocalTime endTime = LocalTime.parse(endTimeStr);

			Wish wish = new Wish(wishId, title, description, startDate, startTime, endDate, endTime);
			wishes.add(wish);
			child.addWish(wish);
			saveWishes();

			System.out.println("Wish " + wishId + " is succesfully added.");
		} catch (DateTimeParseException e) {
			System.err.println("Error parsing date/time: " + e.getMessage());
			System.err.println("Dates must be in yyyy-MM-dd format, times in HH:mm format");
		} catch (Exception e) {
			System.err.println("Error processing wish: " + e.getMessage());
			System.err.println("Command format must be exactly:");
			System.err.println("W103 \"Title\" \"Description\" yyyy-MM-dd HH:mm HH:mm");
			System.err.println("Example: W103 \"Go to the Cinema\" \"Price:100TL\" 2025-03-07 14:00 16:00");
		}
	}

	public void listAllWishes() {
		for (Wish wish : wishes) {
			System.out.println(wish.getId() + ": " + wish.getTitle());
			if (wish.isActivity()) {
				System.out.println("  Activity: " + wish.getStartDate() + " " + wish.getStartTime() + " to "
						+ wish.getEndDate() + " " + wish.getEndTime());
			}
			System.out.println("  Status: " + wish.getStatus());
			if (wish.getStatus().equals("APPROVED")) {
				System.out.println("  Required Level: " + wish.getRequiredLevel());
			}
		}
	}

	public void addBudgetCoin(String amountStr) {
		int amount = Integer.parseInt(amountStr);
		child.addPoints(amount);
		System.out.println("Added " + amount + " points to child's budget.");
	}

	public void checkWish(String params) {
		try {
			String[] parts = params.split(" ");
			String wishId = parts[0];
			String status = parts[1].toUpperCase();
			int requiredLevel = status.equals("APPROVED") ? Integer.parseInt(parts[2]) : 1;

			for (Wish wish : wishes) {
				if (wish.getId().equals(wishId)) {
					wish.setStatus(status);
					wish.setRequiredLevel(requiredLevel);

					// Remove from list if rejected
					if (status.equals("REJECTED")) {
						wishes.remove(wish);
						child.getWishes().remove(wish);
					}

					saveWishes();
					System.out.println("Wish " + wishId + " " + status.toLowerCase()
							+ (status.equals("APPROVED") ? " (Level: " + requiredLevel + ")" : ""));
					return;
				}
			}
			System.err.println("Wish " + wishId + " not found");
		} catch (Exception e) {
			System.err.println("Error processing WISH_CHECKED: " + e.getMessage());
			System.err.println("Format: WISH_CHECKED [wishID] [APPROVED/REJECTED] [requiredLevel-if-approved]");
		}
	}

	public void printBudget() {
		System.out.println("Current budget: " + child.getPoints() + " points.");
	}

	public void printStatus() {
		System.out.println("Current level: " + child.getLevel());
		System.out.println("Average rating: " + child.getAverageRating());
	}

	private void saveWishes() {
		List<String> wishLines = new ArrayList<>();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		for (Wish wish : wishes) { // Only active wishes are in this list
			StringBuilder line = new StringBuilder();
			line.append(wish.getId()).append(",");
			line.append("\"").append(wish.getTitle()).append("\",");
			line.append("\"").append(wish.getDescription()).append("\",");

			if (wish.isActivity()) {
				line.append(wish.getStartDate().format(dateFormatter)).append(",");
				line.append(wish.getStartTime().format(timeFormatter)).append(",");
				line.append(wish.getEndDate().format(dateFormatter)).append(",");
				line.append(wish.getEndTime().format(timeFormatter)).append(",");
			} else {
				line.append(",,,,");
			}

			line.append(wish.getStatus()).append(",");
			if (wish.getStatus() == "ACCEPTED")
				line.append(wish.getRequiredLevel());

			wishLines.add(line.toString());
		}
		fileHandler.writeWishes(wishLines);
	}
}