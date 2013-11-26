package data;

public class ProductType {

	private String name;
	private int min, max;

	public ProductType(String input) {

		String[] splits = input.split(" ");
		name = splits[0];
		String[] priceRange = splits[1].split(",");
		min = Integer.parseInt(priceRange[0]);
		max = Integer.parseInt(priceRange[1]);
		

	}
}
