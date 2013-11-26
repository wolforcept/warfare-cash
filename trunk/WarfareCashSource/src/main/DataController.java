package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import data.City;
import data.Data;
import data.Hazard;
import data.Debt;
import data.Truck;
import data.War;

public class DataController {

	private Data data;

	public DataController(Data data) {

		this.data = data;
	}

	public Data getData() {
		return data;
	}

	/*
	 * STEPS
	 */
	public boolean step() {

		data.incrementDayCounter();

		if (data.getDayCounter() >= Data.DAY_LENGTH) {
			nextDay();
			data.resetDayCounter();
			return true;
		}
		return false;
	}

	public void nextDay() {
		data.addDay();
		ArrayList<City> cities = data.getCitiesSnapshot();
		for (City city : cities) {
			city.updatePrices();
			city.updateProducts();
		}
		for (Debt d : data.getDebtsSnapshot()) {
			d.increase();
		}
	}

	public void releaseTrucks() {
		LinkedList<Truck> trucks = data.getTrucksSnapshot();
		for (Iterator<Truck> it = trucks.iterator(); it.hasNext();) {
			Truck t = (Truck) it.next();
			if (t.isArrived()) {
				dumpTruck(t);
				it.remove();
			}
		}
	}

	public void stepWars() {
		for (War war : data.getWarsSnapshot()) {
			if (war.stepAndTryEnd()) {

			}
		}
	}

	/*
	 * CITIES
	 */

	public void hazard(Hazard h) {
		switch (h) {
		case WIFE_DIVORCE:

			break;
		}
	}

	public void startWar() {
		City c1 = data.getRandomCity();
		City c2;
		do {
			c2 = data.getRandomCity();
		} while (c2.getName().equals(c1.getName()));
		data.addWar(new War(data.getRandomCity(), c2));
		System.out.println("a has raged between " + c1.getName() + " and "
				+ c2.getName());
	}

	/*
	 * MONEY
	 */
	public void loan(int ammount, double interest) {
		data.addMoney(ammount);
		data.addDebt(new Debt(ammount, interest));
	}

	/*
	 * TRUCKS
	 */
	private void dumpTruck(Truck t) {
		if (!t.isSeller()) {
			data.addResources(t.getAmmounts());
		}
	}

}
