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

		products = new Product[Data.NUMBER_OF_PRODUCTS_AVAILABLE];
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

	public void updateProducts(ProductType[] productTypes) {
		for (int i = 0; i < products.length; i++) {
			int type = (int)Math.floor((double) productTypes.length / (double) (products.length - i));
			System.out.println("type: " + type);
			// products[i] = productTypes[(int) Math.round(i
			// / (productTypes.length - 1))].createProduct();
		}
	}

	public double distanceTo(City remoteCity) {
		return Math.hypot(x - remoteCity.getX(), y - remoteCity.getY());
	}

}
