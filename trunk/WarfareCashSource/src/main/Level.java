package main;

import java.util.LinkedList;

public class Level {

	private String name;
	private LinkedList<City> cities;

	public Level(String data) {
		cities = new LinkedList<Level.City>();

		System.out.println("Loading level...");
		String[] split1 = data.split(":");

		name = split1[0];
		System.out.println("   name: " + name);

		String[] split2 = split1[1].split(" ");
		System.out.println("    cities:");
		for (int i = 1; i < split2.length; i++) {
			String[] split3 = split2[i].split("-");

			String[] split4 = split3[2].split(",");
			cities.add(new City(//
					split3[0],//
					Integer.parseInt(split4[0]), //
					Integer.parseInt(split4[1]), //
					Integer.parseInt(split3[1]) //
			));
		}
	}

	class City {
		int x, y, nr;
		String name;

		public City(String name, int x, int y, int nr) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.nr = nr;
			System.out
					.println("     " + name + " " + x + "," + y + " nr:" + nr);
		}

	}

	public String getName() {
		return name;
	}
}
