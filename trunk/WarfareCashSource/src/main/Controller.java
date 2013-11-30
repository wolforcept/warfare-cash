package main;

import java.util.LinkedList;

import data.Data;
import data.Event;
import data.Truck;

public class Controller extends Thread {

	private DataController dataController;
	private MainWindow window;
	private int war_cooldown, war_max_cooldown;

	public Controller(MainWindow mainWindow, DataController dataController) {
		this.dataController = dataController;
		this.window = mainWindow;
		war_cooldown = war_max_cooldown = Data.WAR_COOLDOWN;

		new Thread() {
			public void run() {
				setName("Painter Thread");
				try {
					while (true) {
						window.repaintUI();
						sleep(50);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			};
		}.start();
	}

	@Override
	public void run() {
		try {
			setName("Controller Thread");

			dataController.nextDay();
			while (true) {

				LinkedList<Truck> trucks = dataController.getData()
						.getTrucksSnapshot();
				if (trucks.size() > 0) {
					for (Truck truck : trucks) {
						truck.approach();
					}
				}

				if (Math.random() < dataController.getData().getHazardRisk()) {
					dataController.hazard(Event.getRandom());
				}

				dataController.releaseTrucks();

				if (war_cooldown > 0) {
					war_cooldown--;
				} else if (Math.random() < Data.WAR_CHANCE) {
					dataController.startWar();
					war_cooldown = war_max_cooldown;
				}
				dataController.stepWars();

				dataController.step();

				window.reloadUI();
				sleep(1000);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
