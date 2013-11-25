package main;

public class StuffType {

	private String name;
	private int min, max;

	public StuffType(String input) {

		String[] splits = input.split(",");
		name = splits[0];
		min = Integer.parseInt(splits[1]);
		max = Integer.parseInt(splits[2]);

	}

	class Stuff {
		double val;

		public Stuff(int min, int max) {
			val = max + Math.random() * (max - min);
		}

		public double getVal() {
			return val;
		}
	}
}
