package main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JPanel;

import main.Level.City;

public class Map extends JPanel {

	private static final long serialVersionUID = 1L;

	private Data data;
	private MainWindow window;

	private boolean showNames;

	private Image back;

	public Map(MainWindow mainWindow, Data argdata, Image back) {
		this.data = argdata;
		this.window = mainWindow;
		showNames = false;
		this.back = back;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				for (int i = 0; i < data.getNumberOfCities(); i++) {
					if (Math.hypot(x - data.getCity(i).x, y - data.getCity(i).y) < 4) {
						data.setSelectedCity(i);
						window.reloadUI();
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				showNames = false;
				window.reloadUI();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				showNames = true;
				window.reloadUI();
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		if (back != null)
			g.drawImage(back, 0, 0, this);
		updateMap(g);
	}

	public void updateMap(Graphics g) {
		ArrayList<City> cities = data.getCitiesSnapshot();
		for (City c : cities) {
			g.drawOval(c.x - 4, c.y - 4, 8, 8);
			if (showNames)
				g.drawString(c.name, c.x - 20, c.y + 16);

		}

		LinkedList<Truck> trucks = data.getTrucksSnapshot();
		for (Truck t : trucks) {
			g.fillOval(t.getX() - 2, t.getY() - 2, 4, 4);
		}
	}
}
