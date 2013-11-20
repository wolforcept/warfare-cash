package main;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import main.Level.City;

public class Data {

	private double money;
	private LinkedList<Debt> debts;
	private int selectedCity, warehouseCity;
	private LinkedList<Truck> trucks;
	private int[] resources;
	private String levelName;
	private double hazardRisk;
	private Level level;

	public Data(Level level) {
		money = 100;
		selectedCity = warehouseCity = -1;
		levelName = level.getName();
		hazardRisk = 0.001;
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

	public void addMoney(int ammount) {
		money += ammount;
	}

	public City getCity(int i) {
		return level.getCity(i);
	}

	public int getDebtInt() {
		int debt = 0;
		for (Debt d : debts) {
			debt += d.ammount;
		}
		return debt;
	}

	public void loan(int ammount, double interest) {
		money += ammount;
		debts.add(new Debt(null, ammount, interest));
		increaseHazard();
	}

	public void setSelectedCity(int i) {
		selectedCity = i;
		if (warehouseCity == -1) {
			warehouseCity = i;
			JOptionPane.showMessageDialog(null,
					"you chose to build your warehouse in "
							+ level.getCity(i).name);
		}
	}

	public City getSelectedCity() {
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
		increaseHazard();
	}

	public void increaseDebts() {
		for (Debt debt : debts) {
			debt.increase();
		}
	}

	public City getWarehouseCity() {
		if (warehouseCity < 0)
			return null;
		return getCity(warehouseCity);
	}

	public boolean releaseTrucks() {
		boolean any = false;
		for (Iterator<Truck> it = trucks.iterator(); it.hasNext();) {
			Truck t = (Truck) it.next();
			if (t.isArrived()) {
				dumpTruck(t);
				it.remove();
				any = true;
			}
		}
		return any;
	}

	private void dumpTruck(Truck t) {
		addResources(t.getAmmounts());
	}

	public void addResources(int[] ammounts) {
		for (int i = 0; i < ammounts.length; i++) {
			resources[i] += ammounts[i];
		}
	}

	public int getResourceAmmount(int i) {
		return resources[i];
	}

	/*
	 * 
	 */

	class Debt {

		// private Date expirationDate;
		private double ammount;
		private double interest;

		public Debt(Date expirationDate, double ammount, double interest) {
			// this.expirationDate = expirationDate;
			this.ammount = ammount;
			this.interest = interest;
		}

		public void increase() {
			ammount *= 1 + interest;
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

	private void increaseHazard() {
		hazardRisk *= 1.01;
	}

	public void hazardCity() {
		// level.removeCity();
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

}
