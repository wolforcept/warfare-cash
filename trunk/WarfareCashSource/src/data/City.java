package data;

import data.enums.Cargo;

public class City {

	private String name;
	private int x, y, a_nr_with_no_purpose_yet;
	private double[] currentPrices;
	private Product[] products;

	public City(String name, int x, int y, int nr) {
		this.x = x;
		this.y = y;
		this.a_nr_with_no_purpose_yet = nr;
		// this.hazarded = false;
		this.name = name;
		// System.out.println("   " + name + " " + x + "," + y + " nr:" + nr);

		currentPrices = new double[Cargo.values().length];
		for (int i = 0; i < currentPrices.length; i++) {
			currentPrices[i] = Cargo.values()[i].getMin() + Math.random()
					* (Cargo.values()[i].getMax() - Cargo.values()[i].getMin());
		}

		products = new Product[Data.NUMBER_OF_PRODUCTS_AVALIABLE];
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

	public Product[] getProducts() {
		return products;
	}

	public void setProduct(int index, Product p) {
		this.products[index] = p;
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

	public void updateProducts() {
		for (int i = 0; i < products.length; i++) {
			products[i] = new Product("Amostra " + i, 1000, new int[] { 5, 3,
					2, 7 });
		}
	}

}
