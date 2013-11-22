package main;

public class City {

	String name;
	int x, y, nr;
	private double[] currentPrices;

	// boolean hazerded;

	public City(String name, int x, int y, int nr) {
		this.x = x;
		this.y = y;
		this.nr = nr;
		// this.hazarded = false;
		this.name = name;
		// System.out.println("   " + name + " " + x + "," + y + " nr:" + nr);

		currentPrices = new double[Cargo.values().length];
		for (int i = 0; i < currentPrices.length; i++) {
			currentPrices[i] = Cargo.values()[i].getMin() + Math.random()
					* (Cargo.values()[i].getMax() - Cargo.values()[i].getMin());
		}
	}

	public void updatePrices() {
		for (int i = 0; i < currentPrices.length; i++) {
			currentPrices[i] = Cargo.values()[i].getMin() + Math.random()
					* (Cargo.values()[i].getMax() - Cargo.values()[i].getMin());
		}
	}

	public double priceOf(int index) {
		return currentPrices[index];
	}

	public double[] getCurrentPrices() {
		return currentPrices;
	}
}
