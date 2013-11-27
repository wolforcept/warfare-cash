package swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.MainWindow;

import data.City;
import data.Data;
import data.Truck;

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
					if (Math.hypot(x - data.getCity(i).getX(), y
							- data.getCity(i).getY()) < 10) {

						if (data.getWarehouseCityIndex() == -1) {

							if (0 == JOptionPane.showConfirmDialog(null,
									"Are you sure you want to build your warehouse in "
											+ data.getCity(i).getName() + "?",
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
			g.setFont(new Font(null, Font.PLAIN, 11));

			if (data.getSelCity() != null
					&& c.getName().equals(data.getSelCity().getName())) {
				g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.5f));
				g.drawRect(c.getX() - 4, c.getY() - 4, 7, 7);
			}
			if (data.getWarehouseCity() != null
					&& c.getName().equals(data.getWarehouseCity().getName())) {
				g.setFont(new Font(null, Font.BOLD, 12));
				g.setColor(new Color(0.5f, 1f, 0.5f, 0.5f));
				g.fillRect(c.getX() - 3, c.getY() - 3, 6, 6);
			} else {

				g.setColor(new Color(1f, 1f, 1f, 0.5f));
				g.fillRect(c.getX() - 2, c.getY() - 2, 4, 4);
			}
			if (showNames) {
				g.setColor(new Color(0f, 0f, 0f, 0.5f));
				g.drawString(c.getName(), c.getX()
						- (int) (3.5 * c.getName().length()), c.getY() + 14);
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
