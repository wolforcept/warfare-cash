package main;

public enum Cargo {
	FIRE_WEAPONS("Fire Weapons"), //
	WAR_MACHINES("War Machines"), //
	CHEMICAL_WEAPONS("Chemical Weapons"), //
	NUCLEAR_WARHEADS("Nuclear Warheads"), //
	PRISIONERS_OF_WAR("Prisioners of War");

	private String name;

	private Cargo(String name) {
		this.name = name;
	}
}
