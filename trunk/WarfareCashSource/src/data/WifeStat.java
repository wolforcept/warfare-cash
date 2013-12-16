package data;

import java.awt.Color;

public class WifeStat {

	private static final double RATE_DECREASE_PERCENT = 0.8;
	private int foward;
	private double currentValue, rate;
	private Color color;
	private String name;

	public WifeStat(String data) {

		String[] splits = data.split(":");

		this.name = splits[0];
		foward = Integer.parseInt(splits[1]);

		String[] splitsColor = splits[2].split(",");

		int red = Integer.parseInt(splitsColor[0]);
		int green = Integer.parseInt(splitsColor[1]);
		int blue = Integer.parseInt(splitsColor[2]);
		color = new Color(red, green, blue);

		currentValue = (int) (Math.random() * 1000);
		rate = 100;
	}

	public Color getColor() {
		return color;
	}

	public int getFoward() {
		return foward;
	}

	public double getCurrentValue() {
		return currentValue;
	}

	public void increaseRate(int value) {
		rate = 100 * value / Product.MAX_STAT;
	}

	public void decrease() {
		if (rate > 0)
			rate *= RATE_DECREASE_PERCENT;
		else if (rate > -10)
			rate--;

		currentValue += rate / Data.GLOBAL_DAY_PRECISION;

		if (currentValue < 0)
			currentValue = 0;

		if (currentValue > 1000)
			currentValue = 1000;
	}

	public String getName() {
		return name;
	}
}
