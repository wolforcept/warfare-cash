package data;

import java.util.Random;

public class ProductType {

	private String type;
	private int min, max;
	private String[] names;

	public ProductType(String input) {

		String[] splits = input.split(" ");
		type = splits[0];
		String[] priceRange = splits[1].split(",");
		min = Integer.parseInt(priceRange[0]);
		max = Integer.parseInt(priceRange[1]);
		names = splits[2].split(",");

	}

	public String getType() {
		return type;
	}

	public Product createProduct() {

		Random r = new Random();
		new Product(names[r.nextInt(names.length)], r.nextInt(max - min),
				getRandomStats());
		return null;
	}

	private int[] getRandomStats() {
		return new int[] { 3, 3, 3, 3 };
	}
}
