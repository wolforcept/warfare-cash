package data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

public class PanelWife extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * mood ranges from 0 to 8. Zero means Death
	 */
	private int mood, w, h;

	int temp = 0;

	private int radius, border, separation, blockHeight;
	private double blockTrim;

	public PanelWife(Data data, int radius, int border, int separation,
			int blockHeight, double blockTrim) {
		mood = 4;

		this.radius = radius;
		this.border = border;
		this.separation = separation;
		this.blockHeight = blockHeight;
		this.blockTrim = blockTrim;

		this.w = 4 * 2 * radius + 3 * border + 2 * border;
		this.h = 2 * radius + 3 * border + 2 * blockHeight;
	}

	public int getMood() {
		return mood;
	}

	public void reload() {
		temp = temp >= 1000 ? 100 : temp + 100;
	}

	@Override
	public void paint(Graphics g) {

		g.setColor(Color.GRAY);
		g.drawRect(0, 0, w, h);

		for (int i = 0; i < 4; i++) {

			int x = border + i * (2 * radius + separation);
			int y = border;

			g.setColor(Color.GRAY);
			g.fillRect(x, y, 2 * radius, 2 * radius);

			Polygon p = createPolygonSquare(radius, temp);
			p.translate(x, y);
			g.setColor(Color.DARK_GRAY);
			g.fillPolygon(p);

			g.setColor(Color.BLACK);
			g.drawPolygon(p);
			g.drawRect(x, y, 2 * radius, 2 * radius);

		}

		int nrOfBlocks = 8;
		int blockWidth = w / nrOfBlocks;
		int trim = (int) (blockTrim * blockWidth);
		for (int i = 0; i < nrOfBlocks; i++) {

			int x = border + i * (blockWidth - trim/2);
			int y = 2 * border + 2 * radius + blockHeight / 2;

//			g.drawLine(x, 40, x, 100);
			
			Polygon p = createPolygonRect(blockWidth, blockHeight, blockTrim);
			p.translate(x, y);
			g.setColor(Color.GRAY);
			g.fillPolygon(p);
			g.setColor(Color.BLACK);
			g.drawPolygon(p);
		}
		g.setColor(Color.BLACK);
		g.fillRect(0 + border, 2 * border + 2 * radius, trim, blockHeight * 2);
		g.fillRect(w - border, 2 * border + 2 * radius, trim, blockHeight * 2);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(w, h);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(w, h);
	}

	private Polygon createPolygonRect(int blockWidth, int blockheight,
			double trim) {

		int blockTrim = (int) (blockWidth * trim);
		Polygon p = new Polygon();

		p.addPoint(blockTrim, 0);
		p.addPoint(blockWidth, 0);
		p.addPoint(blockWidth - blockTrim, blockheight);
		p.addPoint(0, blockheight);

		return p;

	}

	private Polygon createPolygonSquare(int r, int A) {

		Polygon p = new Polygon();

		p.addPoint(r, r);
		p.addPoint(r, 0);
		if (A > 875) {
			p.addPoint(r * (A - 875) / 125, 0);
			return p;
		}
		p.addPoint(0, 0);

		if (A > 625) {
			p.addPoint(0, 2 * r * (875 - A) / 250);
			return p;
		}
		p.addPoint(0, 2 * r);

		if (A > 375) {
			p.addPoint(2 * r * (625 - A) / 250, 2 * r);
			return p;
		}
		p.addPoint(2 * r, 2 * r);

		if (A > 125) {
			p.addPoint(2 * r, 2 * r * (A - 125) / 250);
			return p;
		}
		p.addPoint(2 * r, 0);
		p.addPoint(2 * r * (125 + A) / 250, 0);

		return p;

	}
}
