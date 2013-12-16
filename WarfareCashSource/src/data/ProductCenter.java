package data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import main.FileReader;

public class ProductCenter {

	private static int id = 1;

	private String[][] possibleProducts;

	public ProductCenter() {

		String[] products = FileReader.readProducts();

		possibleProducts = new String[Data.NUMBER_OF_CLASSES][];

		for (int i = 0; i < Data.NUMBER_OF_CLASSES; i++) {

			LinkedList<String> list = new LinkedList<String>();

			for (int j = 0; j < products.length; j++) {
				if (Integer.parseInt("" + products[j].charAt(0)) == i) {
					list.add(products[j]);
					System.out.println(products[j]);
				}
			}
			possibleProducts[i] = list.toArray(new String[list.size()]);
		}
	}

	public Product createProduct(int classe, int numberOfStats) {

		Random random = new Random();

		String[] possibleProductsOfClass = possibleProducts[classe - 1];

		int randomProductIndex = random.nextInt(possibleProductsOfClass.length);

		String[] splits = possibleProductsOfClass[randomProductIndex]
				.split(" ");

		String name = splits[1];
		int price = Integer.parseInt(splits[2]);
		int priceMultiplier = Integer.parseInt(splits[3]);

		int totalStatPoints = 10 / classe;
		int[] statValues = new int[numberOfStats];
		Arrays.fill(statValues, 0);

		while (totalStatPoints > 0) {

			statValues[random.nextInt(statValues.length)] += 1;
			totalStatPoints--;
		}

		return new Product(name, price
				* (int) (Math.random() * priceMultiplier), statValues, id++);
	}
	// public boolean stillHasProducts() {
	// for (int i = 0; i < products.length; i++) {
	// if (products[i] != null)
	// return true;
	// }
	// return false;
	// }

	// public Product[] getProducts() {
	// System.out.println("Ps" + products.length);
	// return products;
	// }

	// private Product getProduct(int classe, int numberOfStats) {
	//
	// Random random = new Random();
	// int[] statValues = new int[numberOfStats];
	// for (int i = 0; i < statValues.length; i++) {
	// statValues[i] = (random.nextInt(Product.MAX_STAT + 1)) / classe;
	// }
	// return new Product(//
	// productType.getName(), //
	// random.nextInt(productType.getPrice())
	// * productType.getPriceMultiplier(), //
	// statValues, //
	// idMaker++);
	// }
}
