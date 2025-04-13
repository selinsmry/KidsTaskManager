public abstract class Person {
	private String name;
	private String type; // "P" for parent, "T" for teacher

	public Person(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
}