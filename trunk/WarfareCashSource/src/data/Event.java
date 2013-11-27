package data;

import java.util.Random;

public enum Event {
	WIFE_DIVORCE;

	public static Event getRandom() {
		Random random = new Random();
		return values()[random.nextInt(values().length)];
	}
}
