package main;

public enum Cargo {
	FIRE_WEAPONS("Fire Weapons", 5, 50), //
	WAR_MACHINES("War Machines", 80, 800), //
	CHEMICAL_WEAPONS("Chemical Weapons", 400, 4000), //
	NUCLEAR_WARHEADS("Nuclear Warheads", 6000, 60000); //

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
