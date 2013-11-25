package main;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Data {

	public static final double WAR_CHANCE = 0.01, INITIAL_HAZARD_RISK = 0.001;
	public static final int DAY_LENGTH = 100, WAR_COOLDOWN = 100;

	private double money;
	private LinkedList<Debt> debts;
	private int selectedCity, warehouseCity, dayCounter, day;
	private LinkedList<Truck> trucks;
	private int[] resources;

	private LinkedList<War> wars;

	private String levelName;
	private double hazardRisk;
	private Level level;

	private double multiplier;

	public Data(Level level) {
		money = 999999;
		selectedCity = warehouseCity = -1;
		levelName = level.getName();
		hazardRisk = INITIAL_HAZARD_RISK;
		dayCounter = 0;
		day = 0;

		wars = new LinkedList<War>();

		multiplier = 0.5;

		resources = new int[Cargo.values().length];
		for (int i = 0; i < resources.length; i++) {
			resources[i] = 0;
		}

		this.level = level;
		debts = new LinkedList<Debt>();
		trucks = new LinkedList<Truck>();

	}

	public double getMoney() {
		return money;
	}

	public int getMoneyInt() {
		return (int) Math.round(money);
	}

	public void addMoney(double totalPrice) {
		money += totalPrice;
	}

	public City getCity(int i) {
		return level.getCity(i);
	}

	public int getDebtInt() {
		int debt = 0;
		for (Debt d : debts) {
			debt += d.debtValue;
		}
		return debt;
	}

	public void loan(int ammount, double interest) {
		addMoney(ammount);
		debts.add(new Debt(null, ammount, interest));
	}

	public void setWarehouseCity(int warehouseCity) {
		this.warehouseCity = warehouseCity;
	}

	public void setSelectedCity(int i) {
		selectedCity = i;
	}

	public City getSelCity() {
		if (selectedCity >= 0
				&& selectedCity < level.getCitiesSnapshot().size())
			return getCity(selectedCity);
		return null;
	}

	public LinkedList<Truck> getTrucksSnapshot() {
		return new LinkedList<Truck>(trucks);
	}

	public void addTruck(Truck truck) {
		trucks.add(truck);
	}

	public City getWarehouseCity() {
		if (warehouseCity < 0)
			return null;
		return getCity(warehouseCity);
	}

	public void releaseTrucks() {
		for (Iterator<Truck> it = trucks.iterator(); it.hasNext();) {
			Truck t = (Truck) it.next();
			if (t.isArrived()) {
				dumpTruck(t);
				it.remove();
			}
		}
	}

	public int getDayCounter() {
		return dayCounter;
	}

	private void dumpTruck(Truck t) {
		if (!t.isSeller()) {
			addResources(t.getAmmounts());
		}
	}

	public void addResources(int[] ammounts) {
		for (int i = 0; i < ammounts.length; i++) {
			resources[i] += ammounts[i];
		}
	}

	public void removeResources(int[] ammounts) {
		for (int i = 0; i < ammounts.length; i++) {
			resources[i] -= ammounts[i];
		}
	}

	public boolean hasResources(int[] ammounts) {
		boolean hasResources = true;
		for (int i = 0; i < ammounts.length; i++) {
			if (resources[i] < ammounts[i])
				hasResources = false;
		}
		return hasResources;
	}

	public int getResourceAmmount(int i) {
		return resources[i];
	}

	/*
	 * 
	 */

	class Debt {

		// private Date expirationDate;
		private double debtValue, interest, initialValue;

		public Debt(Date expirationDate, double ammount, double interest) {
			// this.expirationDate = expirationDate;
			this.debtValue = this.initialValue = ammount;
			this.interest = interest / 100;
			System.out.println("Created debt of " + ammount + " increasing "
					+ interest);
		}

		public void increase() {
			debtValue += initialValue * interest;
		}
	}

	public Image getBackgroundImage() {
		try {
			URL resource = this.getClass().getResource(
					"/resources/" + levelName + ".png");
			if (resource != null)
				return ImageIO.read(resource);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	public double getHazardRisk() {
		return hazardRisk;
	}

	public void sellAll(int i) {
		resources[i] = 0;
	}

	public ArrayList<City> getCitiesSnapshot() {
		return level.getCitiesSnapshot();
	}

	public int getNumberOfCities() {
		return level.getCitiesSnapshot().size();
	}

	public int getWarehouseCityIndex() {
		return warehouseCity;
	}

	public double getTripPrice(City c1, City c2) {
		return multiplier * Math.hypot(c1.x - c2.x, c1.y - c2.y);
	}

	public void startWar() {
		City c1 = getRandomCity();
		City c2;
		do {
			c2 = getRandomCity();
		} while (c2.name.equals(c1.name));
		wars.add(new War(getRandomCity(), c2));
		System.out
				.println("a has raged between " + c1.name + " and " + c2.name);
	}

	private City getRandomCity() {
		ArrayList<City> list = level.getCitiesSnapshot();
		Collections.shuffle(list);
		return list.get(0);
	}

	public void stepWars() {
		for (War war : wars) {
			if (war.stepAndTryEnd()) {

			}
		}
	}

	public int getLoanTotal() {
		int total = 0;
		for (Debt d : debts) {
			total += d.debtValue;
		}
		return total;
	}

	public boolean step() {

		dayCounter++;

		if (dayCounter >= DAY_LENGTH) {
			nextDay();
			dayCounter = 0;
			return true;
		}
		return false;
	}

	private void nextDay() {
		day++;
		ArrayList<City> cities = getCitiesSnapshot();
		for (City city : cities) {
			city.updatePrices();
		}
		for (Debt d : debts) {
			d.increase();
		}
	}

	public int getDay() {
		return day;
	}

	public void hazard(Hazard h) {
		switch (h) {
		case WIFE_DIVORCE:

			break;

		}
	}

	enum Hazard {
		WIFE_DIVORCE;

		public static Hazard getRandom() {
			Random random = new Random();
			return values()[random.nextInt(values().length)];
		}
	}
}
