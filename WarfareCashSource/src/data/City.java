package data;

import java.util.LinkedList;

public class City {

	private String name;
	private int x, y, nr;
	private double[] currentPrices;
	private LinkedList<Product> products;

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

		products = new LinkedList<Product>();
	}

	/*
	 * GETTERS
	 */

	public double getPrice(int index) {
		return currentPrices[index];
	}

	public double[] getCurrentPrices() {
		return currentPrices;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	/*
	 * ACTIONS
	 */

	public void updatePrices() {
		for (int i = 0; i < currentPrices.length; i++) {
			currentPrices[i] = Cargo.values()[i].getMin() + Math.random()
					* (Cargo.values()[i].getMax() - Cargo.values()[i].getMin());
		}
	}
}
