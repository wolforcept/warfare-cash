package main;

import java.util.ArrayList;
import java.util.LinkedList;

public class Level {

	private String name, original_name;
	private LinkedList<City> cities;

	public Level(String levelData) {
		cities = new LinkedList<City>();

		print("Loading level...");
		String[] split1 = levelData.split(":");
		original_name = split1[0].toLowerCase();
		name = split1[0].replace('_', ' ');
		print(" name: " + name);

		String[] split2 = split1[1].split(" ");
		print("  cities:");
		for (int i = 1; i < split2.length; i++) {
			String[] split3 = split2[i].split("-");

			String[] split4 = split3[2].split(",");
			cities.add(new City(//
					split3[0], //
					Integer.parseInt(split4[0]), //
					Integer.parseInt(split4[1]), //
					Integer.parseInt(split3[1]) //
			));
		}
	}

	@Override
	public String toString() {
		return name;
	}

	public String getBeautifulName() {
		return name;
	}

	public String getName() {
		return original_name;
	}

	public synchronized void removeCity(int index) {
		// cities.get(index).hazerded = true;
	}

	public synchronized ArrayList<City> getCitiesSnapshot() {
		return new ArrayList<City>(cities);
	}

	public City getCity(int index) {
		return getCitiesSnapshot().get(index);
	}

	private void print(String string) {
		// System.out.println(string);
	}
}
