package data.enums;

import java.awt.Color;

public enum Stat {
	AFFECTION(11, new Color(255, 192, 203)), //
	WELLBEING(9, new Color(80, 200, 80)), //
	AMUSEMENT(1, new Color(110, 150, 225)), //
	STATUS(18, new Color(255, 204, 51));

	Color color;
	int foward;

	private Stat(int f, Color c) {
		color = c;
		foward = f;
	}

	public Color getColor() {
		return color;
	}
	
	public int getFoward() {
		return foward;
	}
}
