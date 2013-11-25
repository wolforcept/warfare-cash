package main;

import java.util.LinkedList;

import main.Truck;

public class Controller extends Thread {

	private Data data;
	private MainWindow window;
	private int war_cooldown, war_max_cooldown;

	public Controller(MainWindow mainWindow, Data data) {
		this.data = data;
		this.window = mainWindow;
		war_cooldown = war_max_cooldown = 100;
	}

	@Override
	public void run() {
		try {
			while (true) {

				LinkedList<Truck> trucks = data.getTrucksSnapshot();
				if (trucks.size() > 0) {
					for (Truck truck : trucks) {
						truck.approach();
					}
					window.reloadUI();
				}

				data.increaseDebts();
				if (Math.random() < data.getHazardRisk()) {
					data.hazardCity();
				}

				if (data.releaseTrucks()) {
					window.reloadUI();
				}

				if (war_cooldown > 0) {
					war_cooldown--;
				} else if (Math.random() < Data.WAR_CHANCE) {
					data.startWar();
					war_cooldown = war_max_cooldown;
				}
				data.stepWars();

				sleep(1000);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
