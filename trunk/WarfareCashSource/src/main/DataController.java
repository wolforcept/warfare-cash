package main;

import java.util.ArrayList;
import java.util.LinkedList;

import data.City;
import data.Data;
import data.Debt;
import data.Event;
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
			city.updateResources();
			city.updateProducts(data.getProductTypes());
		}
		for (Debt d : data.getDebtsSnapshot()) {
			d.increase();
		}
		data.isUpdatePanelProducts(true);
	}

	public void releaseTrucks() {
		LinkedList<Truck> trucks = data.getTrucksSnapshot();
		for (Truck t : trucks) {
			if (t.isArrived()) {
				dumpTruck(t);
				data.removeTruck(t);
			} else if (t.isDead()) {
				data.removeTruck(t);
			}
		}
	}

	public void stepWars() {
		for (War war : data.getWarsSnapshot()) {
			if (war.stepAndTryEnd()) {
				// TODO WARS STEP
			}
		}
	}

	/*
	 * CITIES
	 */

	public void hazard(Event h) {
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
		System.out.println("a war has raged between " + c1.getName() + " and "
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
			data.addResource(t.getCargoIndex(), t.getCargoAmmount());
		} else {
			data.addMoney(t.getDestination()
					.getResourcePrice(t.getCargoIndex()) * t.getCargoAmmount());
			t.getDestination().addResource(t.getCargoIndex(),
					t.getCargoAmmount());
		}
	}

}
