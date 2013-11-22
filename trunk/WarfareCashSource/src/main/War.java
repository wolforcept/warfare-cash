package main;

import java.util.Random;

public class War {

	private City c1, c2;

	private int state, add;
	private Random random;

	public War(City c1, City c2) {
		random = new Random();
		this.c1 = c1;
		this.c2 = c2;
		state = 5 - random.nextInt(11);
		add = 0;
	}

	public boolean stepAndTryEnd() {
		int sign = -random.nextInt(2);
		add++;
		state += sign * add;
		if (state > 99) {
			return true;
		}
		return false;
	}

	public City getC1() {
		return c1;
	}

	public City getC2() {
		return c2;
	}
}
