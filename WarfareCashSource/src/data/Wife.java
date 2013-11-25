package data;


public class Wife {

	/**
	 * mood ranges from 0 to 8. Zero means Death
	 */
	private int mood;

	private ProductType[] productTypes;

	public Wife(ProductType[] products) {
		mood = 4;

		this.productTypes = products;

	}

	public int getMood() {
		return mood;
	}

}
