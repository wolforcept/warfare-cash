package data;

public class Product {
	public static final int MAX_STAT = 7;
	private int price;
	private String name;
	private int[] statsValues;
	private long id;

	public Product(String name, int price, int[] statValues, long id) {
		this.name = name.replace("_", " ");
		this.price = price;
		this.statsValues = statValues;
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int[] getStatValues() {
		return statsValues;
	}

	public int getStatValue(int index) {
		return statsValues[index];
	}

	public long getId() {
		return id;
	}
}
