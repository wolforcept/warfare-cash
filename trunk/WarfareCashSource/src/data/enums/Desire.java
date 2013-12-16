package data.enums;

public enum Desire {
	TRIP("", "I really feel like leaving the city for a while.");

	private String text;

	private Desire(String type, String text) {

		this.text = text;
	}

	public String getText() {
		return text;
	}
}
