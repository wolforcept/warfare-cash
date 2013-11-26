package data;

import data.enums.Stat;

public class Product {
	private double price;
	private String name;
	private int[] stats;

	public Product(String name, int price, int[] stats) {
		this.name = name;
		this.price = price;
		this.stats = stats;
	}

	public double getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int getStat(Stat stat) {
		return stats[stat.ordinal()];
	}
}
