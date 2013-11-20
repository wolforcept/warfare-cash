package main;

import java.util.LinkedList;

import main.Truck;

public class Controller extends Thread {

	private Data data;
	private MainWindow window;

	public Controller(MainWindow mainWindow, Data data) {
		this.data = data;
		this.window = mainWindow;
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
				if(Math.random() < data.getHazardRisk()){
					data.hazardCity();
				}

				if (data.releaseTrucks()) {
					window.reloadUI();
				}
				sleep(1000);

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
