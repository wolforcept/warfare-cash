package data;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import main.FileReader;
import data.enums.Cargo;

public class Data {

	public static final double WAR_CHANCE = 0.01, INITIAL_HAZARD_RISK = 0.001;
	public static final int //
			DAY_LENGTH = 50, //
			WAR_COOLDOWN = 100, //
			NUMBER_OF_PRODUCTS_AVAILABLE = 7, //
			PODUCT_BOX_MAX_HEIGHT = 40, //
			PODUCT_BOX_MIN_HEIGHT = 14, //
			INITIAL_MONEY = 1000, //
			TRUCK_CONSUMPTION = 2, //
			NUMBER_OF_CLASSES = 5, //
			GLOBAL_DAY_PRECISION = 5;
	//
	;

	public static final Dimension PANEL_DEFAULT_SIZE = new Dimension(320, 320);

	private LinkedList<Debt> debts;
	private LinkedList<Truck> trucks;
	private LinkedList<War> wars;

	private int[] resources;
	private ProductCenter productCenter;

	private String levelName;
	private int money, selectedCity, warehouseCity, day;
	private double hazardRisk, dayCounter;
	private boolean thoroughReload, reloadProductPanel;

	private Level level;
	private WifeStat[] stats;

	public Data(Level level) {

		stats = FileReader.readStats();

		this.money = Data.INITIAL_MONEY;
		this.selectedCity = warehouseCity = -1;
		this.levelName = level.getName();
		this.hazardRisk = INITIAL_HAZARD_RISK;
		this.dayCounter = 0;
		this.day = 0;
		this.level = level;
		this.productCenter = new ProductCenter();

		debts = new LinkedList<Debt>();
		trucks = new LinkedList<Truck>();
		wars = new LinkedList<War>();

		resources = new int[Cargo.values().length];
		for (int i = 0; i < resources.length; i++) {
			resources[i] = 0;
		}

		reloadProductPanel = true;
		thoroughReload = false;
	}

	public boolean isThoroughReload() {
		return thoroughReload;
	}

	public void setThoroughReload(boolean thoroughReload) {
		this.thoroughReload = thoroughReload;
	}
	
	public boolean isReloadProductPanel() {
		return reloadProductPanel;
	}
	
	public void setReloadProductPanel(boolean reloadProductPanel) {
		this.reloadProductPanel = reloadProductPanel;
	}

	/*
	 * MONEY
	 */

	public int getMoney() {
		return money;
	}

	public void addMoney(int ammount) {
		money += ammount;
	}

	/*
	 * WIFE
	 */

	public ProductCenter getProductCenter() {
		return productCenter;
	}

	/*
	 * DEBTS
	 */

	public int getDebtTotal() {
		int total = 0;
		for (Debt d : debts) {
			if (!d.isPaid())
				total += d.getValue();
		}
		return total;
	}

	public void addDebt(Debt debt) {
		debts.add(debt);
	}

	public int tryPayDebt(int id) {
		Debt debt = debts.get(id);

		if (money >= debt.getValue()) {
			money -= debt.getValue();
			return 0;
		} else {
			int left = debt.getValue() - money;
			money = 0;
			debt.setValue(left);
			return -left;
		}
	}

	public LinkedList<Debt> getDebtsSnapshot() {
		return new LinkedList<Debt>(debts);
	}

	public void updateDebts() {

		for (Iterator<Debt> it = debts.iterator(); it.hasNext();) {
			Debt d = (Debt) it.next();
			if (d.isPaid())
				it.remove();
			else
				d.increase();

		}
	}

	/*
	 * RESOURCES
	 */

	public boolean hasResource(int index, int ammount) {
		return resources[index] >= ammount;
	}

	public void sellAll(int i) {
		resources[i] = 0;
	}

	public void addResource(int index, int ammount) {
		resources[index] += ammount;
	}

	public void removeResource(int index, int ammount) {
		resources[index] -= ammount;
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
		dayCounter += (double) 1 / GLOBAL_DAY_PRECISION;
	}

	public void resetDayCounter() {
		dayCounter = 0;
	}

	public double getDayCounter() {
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
		reloadProductPanel = true;
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

	public int getTripPrice(City c1, City c2) {
		if (c1.equals(c2)) {
			return 0;
		}
		return (int) Math.floor((c1.distanceTo(c2) / Data.TRUCK_CONSUMPTION));
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
	 * WARS
	 */
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

	public void removeTruck(Truck t) {

		for (Iterator<Truck> it = trucks.iterator(); it.hasNext();) {
			Truck truck = (Truck) it.next();
			if (t.equals(truck))
				it.remove();
		}
	}

	public WifeStat[] getStats() {
		return stats;
	}

}
