package main;

import java.util.HashMap;

import main.StuffType.Stuff;

public class Wife {

	/**
	 * mood ranges from 0 to 8. Zero means Death
	 */
	private int mood;

	private HashMap<Stuff, Double> avaliableStuff;
	private StuffType[] stuffTypes;

	public Wife(StuffType[] stuffs) {
		mood = 4;

		this.stuffTypes = stuffs;

		avaliableStuff = new HashMap<Stuff, Double>();
		//avaliableStuff.put(s, s.getVal());

	}

	public Stuff makeStuff(int stuffClass, int day) {

		double c = Math.floor(stuffClass * stuffTypes.length)+(int)(Math.random()*2);
		// stuffTypes
		// Stuff s = new Stuff();
		return s;
	}

	public int getMood() {
		return mood;
	}

}
