package main;

import java.util.LinkedList;

public class Controller extends Thread {

	private Data data;
	private MainWindow window;
	private int war_cooldown, war_max_cooldown;

	public Controller(MainWindow mainWindow, Data data) {
		this.data = data;
		this.window = mainWindow;
		war_cooldown = war_max_cooldown = Data.WAR_COOLDOWN;

		new Thread() {
			public void run() {
				try {
					while (true) {
						window.reloadUI();
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
			while (true) {

				LinkedList<Truck> trucks = data.getTrucksSnapshot();
				if (trucks.size() > 0) {
					for (Truck truck : trucks) {
						truck.approach();
					}
				}

				if (Math.random() < data.getHazardRisk()) {
					data.hazard(Data.Hazard.getRandom());
				}

				data.releaseTrucks();

				if (war_cooldown > 0) {
					war_cooldown--;
				} else if (Math.random() < Data.WAR_CHANCE) {
					data.startWar();
					war_cooldown = war_max_cooldown;
				}
				data.stepWars();

				data.step();

				window.reloadUI();
				sleep(1000);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
