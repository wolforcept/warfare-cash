package data;

import java.util.Iterator;
import java.util.LinkedList;

import data.enums.Cargo;

public class City {

	private String name;
	private int x, y, a_nr_with_no_purpose_yet;
	private int[] resourcePrices;
	private int[] resourceQuantities;
	private LinkedList<Product> products;

	public City(String name, int x, int y, int nr) {
		this.x = x;
		this.y = y;
		this.a_nr_with_no_purpose_yet = nr;
		// this.hazarded = false;
		products = new LinkedList<Product>();
		this.name = name;

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

	public LinkedList<Product> getProducts() {
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

	public void updateProducts(ProductType[] productTypes) {
		products.clear();
		for (int i = 0; i < productTypes.length; i++) {
			for (int j = 0; j < Data.NUMBER_OF_PRODUCTS_AVAILABLE_PER_CATEGORY; j++) {
				Product p = productTypes[i].createProduct();
				products.add(p);
				// System.out.println("added " + p.getName() + " for " +
				// p.getPrice());
			}
		}
	}

	public void removeProduct(Product p) {
		for (Iterator<Product> it = products.iterator(); it.hasNext();it.next()) {
			if (it.equals(p))
				it.remove();
		}
	}

	public double distanceTo(City remoteCity) {
		return Math.hypot(x - remoteCity.getX(), y - remoteCity.getY());
	}

	public void addResource(int index, int ammount) {
		resourceQuantities[index] += ammount;
	}

}
