package data;

import data.enums.Cargo;

public class City {

	private String name;
	private int x, y, a_nr_with_no_purpose_yet;
	private int[] resourcePrices;
	private int[] resourceQuantities;
	private Product[] products;
	private int idMaker;

	public City(String name, int x, int y, int nr) {
		this.x = x;
		this.y = y;
		this.a_nr_with_no_purpose_yet = nr;
		// this.hazarded = false;
		this.products = new Product[Data.NUMBER_OF_PRODUCTS_AVAILABLE];
		this.name = name;
		this.idMaker = 1;

		resourcePrices = new int[Cargo.values().length];
		resourceQuantities = new int[Cargo.values().length];
		updateResources();

	}

	/*
	 * GETTERS
	 */

	public int getResourcePrice(int index) {
		return resourcePrices[index];
	}

	public int getResourceQuantity(int index) {
		return resourceQuantities[index];
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

	/*
	 * ACTIONS
	 */

	public void updateResources() {
		for (int i = 0; i < resourcePrices.length; i++) {
			resourcePrices[i] = (int) (Cargo.values()[i].getMinPrice() + Math
					.random()
					* (Cargo.values()[i].getMaxPrice() - Cargo.values()[i]
							.getMinPrice()));

			resourceQuantities[i] = (int) (Math.random() * Cargo.values()[i]
					.getMaxQnt());
		}
	}

	public void updateProducts(ProductCenter center, int numberOfStats) {
		for (int i = 0; i < products.length; i++) {
			products[i] = center.createProduct(Data.NUMBER_OF_CLASSES,
					numberOfStats);
		}
	}

	public void removeProduct(long id) {
		for (int i = 0; i < products.length; i++) {
			if (products[i] != null && products[i].getId() == id)
				products[i] = null;
		}
	}

	public double distanceTo(City remoteCity) {
		return Math.hypot(x - remoteCity.getX(), y - remoteCity.getY());
	}

	public void addResourceQuantity(int index, int ammount) {
		resourceQuantities[index] += ammount;
	}

}
