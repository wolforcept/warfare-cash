package data.enums;

public enum Cargo {
	FIRE_WEAPONS("Fire Weapons", 5, 50, 200), //
	WAR_MACHINES("War Machines", 60, 600, 100), //
	CHEMICAL_WEAPONS("Chemical Weapons", 400, 4000, 50), //
	// BIOWEAPONS("Bio Agents", 800, 8000), //
	NUCLEAR_WARHEADS("Nuclear Warheads", 1000, 10000, 8); //

	private String name;
	private int minPrice, maxPrice, maxQnt;

	private Cargo(String name, int minP, int maxP, int maxQnt) {
		this.name = name;
		this.minPrice = minP;
		this.maxPrice = maxP;
		this.maxQnt = maxQnt;
	}

	public String getName() {
		return name;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public double getMaxQnt() {
		return maxQnt;
	}

}
