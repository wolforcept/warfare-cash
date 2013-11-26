package data;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import data.enums.Cargo;

public class Data {

	public static final double WAR_CHANCE = 0.01, INITIAL_HAZARD_RISK = 0.001;
	public static final int DAY_LENGTH = 100, WAR_COOLDOWN = 100,
			NUMBER_OF_PRODUCTS_AVALIABLE = 5;

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
	private Wife wife;

	public Data(Level level, ProductType[] stuffs) {
		money = 999999;
		selectedCity = warehouseCity = -1;
		levelName = level.getName();
		hazardRisk = INITIAL_HAZARD_RISK;
		dayCounter = 0;
		day = 0;

		wars = new LinkedList<War>();

		multiplier = 0.5;

		wife = new Wife(stuffs);

		resources = new int[Cargo.values().length];
		for (int i = 0; i < resources.length; i++) {
			resources[i] = 0;
		}

		this.level = level;
		debts = new LinkedList<Debt>();
		trucks = new LinkedList<Truck>();

	}

	/*
	 * MONEY
	 */
	public int getLoanTotal() {
		int total = 0;
		for (Debt d : debts) {
			total += d.getValue();
		}
		return total;
	}

	public void addDebt(Debt debt) {
		debts.add(debt);
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

	public int getDebtInt() {
		int debt = 0;
		for (Debt d : debts) {
			debt += d.getValue();
		}
		return debt;
	}

	/*
	 * RESOURCES
	 */
	public boolean hasResources(int[] ammounts) {
		boolean hasResources = true;
		for (int i = 0; i < ammounts.length; i++) {
			if (getResourceAmmount(i) < ammounts[i])
				hasResources = false;
		}
		return hasResources;
	}

	public void sellAll(int i) {
		resources[i] = 0;
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

	public int getResourceAmmount(int i) {
		return resources[i];
	}

	/*
	 * DAY
	 */
	public void addDay() {
		day++;
	}

	public int getDay() {
		return day;
	}

	public void incrementDayCounter() {
		dayCounter++;
	}

	public void resetDayCounter() {
		dayCounter = 0;
	}

	public int getDayCounter() {
		return dayCounter;
	}

	/*
	 * CITIES
	 */

	public City getCity(int i) {
		return level.getCity(i);
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

	public City getWarehouseCity() {
		if (warehouseCity < 0)
			return null;
		return getCity(warehouseCity);
	}

	public ArrayList<City> getCitiesSnapshot() {
		return level.getCitiesSnapshot();
	}

	public int getNumberOfCities() {
		return level.getCitiesSnapshot().size();
	}

	public void addWar(War war) {
		wars.add(war);
	}

	public City getRandomCity() {
		ArrayList<City> list = level.getCitiesSnapshot();
		Collections.shuffle(list);
		return list.get(0);
	}

	public double getTripPrice(City c1, City c2) {
		return multiplier
				* Math.hypot(c1.getX() - c2.getX(), c1.getY() - c2.getY());
	}

	public int getWarehouseCityIndex() {
		return warehouseCity;
	}

	/*
	 * TRUCKS
	 */
	public LinkedList<Truck> getTrucksSnapshot() {
		return new LinkedList<Truck>(trucks);
	}

	public void addTruck(Truck truck) {
		trucks.add(truck);
	}

	/*
	 * WIFE
	 */
	public Wife getWife() {
		return wife;
	}

	public LinkedList<Debt> getDebtsSnapshot() {
		return new LinkedList<Debt>(debts);
	}

	public LinkedList<War> getWarsSnapshot() {
		return new LinkedList<War>(wars);
	}

	/*
	 * HAZARDS
	 */
	public double getHazardRisk() {
		return hazardRisk;
	}

	/*
	 * OTHER
	 */

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

}
