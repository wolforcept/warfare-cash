package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
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
					if (Math.hypot(x - data.getCity(i).x, y - data.getCity(i).y) < 10) {

						if (data.getWarehouseCityIndex() == -1) {

							if (0 == JOptionPane.showConfirmDialog(null,
									"Are you sure you want to build your warehouse in "
											+ data.getCity(i).name + "?",
									"Your First Warehouse",
									JOptionPane.YES_NO_OPTION))
								data.setWarehouseCity(i);

						}

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
			if (data.getWarehouseCity() != null
					&& c.name.equals(data.getWarehouseCity().name)) {
				g.setColor(new Color(0.5f, 1f, 0.5f, 0.5f));
				g.fillRect(c.x - 3, c.y - 3, 6, 6);
			} else {
				g.setColor(new Color(1f, 1f, 1f, 0.5f));
				g.fillRect(c.x - 2, c.y - 2, 4, 4);
			}
			if (showNames) {
				g.setColor(new Color(0f, 0f, 0f, 0.5f));
				g.drawString(c.name, c.x - 20, c.y + 12);
			}
		}

		g.setColor(new Color(0f, 0f, 0.4f, 0.5f));

		LinkedList<Truck> trucks = data.getTrucksSnapshot();
		for (Truck t : trucks) {
			if (t.getDistanceLeft() > 2)
				g.fillPolygon(createShape(t.getX(), t.getY(), t.getDirection()));
		}
	}

	private Polygon createShape(int x, int y, double angle) {

		int d = 7;
		Polygon p = new Polygon();

		p.addPoint(x + (int) (d * Math.cos(angle)),
				y + (int) (d * Math.sin(angle)));

		p.addPoint(x + (int) (d * Math.cos(angle + Math.PI * 0.8)), y
				+ (int) (d * Math.sin(angle + Math.PI * 0.8)));

		p.addPoint(x + (int) (d * Math.cos(angle - Math.PI * 0.8)), y
				+ (int) (d * Math.sin(angle - Math.PI * 0.8)));

		return p;

	}
}
