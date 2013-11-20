package main;

public enum Cargo {
	FIRE_WEAPONS("Fire Weapons", 5, 500), //
	WAR_MACHINES("War Machines", 80, 8000), //
	CHEMICAL_WEAPONS("Chemical Weapons", 400, 40000), //
	NUCLEAR_WARHEADS("Nuclear Warheads", 6000, 600000), //
	PRISIONERS_OF_WAR("Prisioners of War", 20000, 2000000);

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
