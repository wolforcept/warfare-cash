package data;

public class ProductType {

	private String name;
	private int min, max;

	public ProductType(String input) {

		String[] splits = input.split(",");
		name = splits[0];
		min = Integer.parseInt(splits[1]);
		max = Integer.parseInt(splits[2]);

	}
}
