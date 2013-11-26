package data;

import java.util.Random;

public enum Hazard {
	WIFE_DIVORCE;

	public static Hazard getRandom() {
		Random random = new Random();
		return values()[random.nextInt(values().length)];
	}
}
