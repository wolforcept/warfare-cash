package main;

public class City {

	String name;
	int x, y, nr;
	private double[] currentValues;

	// boolean hazerded;

	public City(String name, int x, int y, int nr) {
		this.x = x;
		this.y = y;
		this.nr = nr;
		// this.hazarded = false;
		this.name = name;
		// System.out.println("   " + name + " " + x + "," + y + " nr:" + nr);

		currentValues = new double[Cargo.values().length];
		for (int i = 0; i < currentValues.length; i++) {
			currentValues[i] = Cargo.values()[i].getMin() + Math.random()
					* (Cargo.values()[i].getMax() - Cargo.values()[i].getMin());
		}
	}

	public void updateValues() {
		for (int i = 0; i < currentValues.length; i++) {
			currentValues[i] = Cargo.values()[i].getMin() + Math.random()
					* (Cargo.values()[i].getMax() - Cargo.values()[i].getMin());
		}
	}

	public double valueOf(int index) {
		return currentValues[index];
	}
}
