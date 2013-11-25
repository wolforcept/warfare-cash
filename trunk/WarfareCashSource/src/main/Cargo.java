package main;

public enum Cargo {
	FIRE_WEAPONS("Fire Weapons", 5, 50), //
	WAR_MACHINES("War Machines", 60, 600), //
	CHEMICAL_WEAPONS("Chemical Weapons", 400, 4000), //
	BIOWEAPONS("Bio Agents", 800, 8000), //
	NUCLEAR_WARHEADS("Nuclear Warheads", 1000, 10000); //

	private String name;
	private int min, max;

	private Cargo(String name, int min, int max) {
		this.name = name;
		this.min = min;
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

}
