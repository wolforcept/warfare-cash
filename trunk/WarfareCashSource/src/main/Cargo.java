package main;

public enum Cargo {
	FIRE_WEAPONS("Fire Weapons", "crates", 5, 500), //
	WAR_MACHINES("War Machines", "units", 80, 8000), //
	CHEMICAL_WEAPONS("Chemical Weapons", "grams", 400, 40000), //
	NUCLEAR_WARHEADS("Nuclear Warheads", "people", 6000, 600000), //
	PRISIONERS_OF_WAR("Prisioners of War", "people", 20000, 2000000);

	private String name, units;
	private int min, max;

	private Cargo(String name, String units, int min, int max) {
		this.name = name;
		this.units = units;
		this.min = min;
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public String getUnits() {
		return units;
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

}
