package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;

import javax.swing.JComponent;

import data.Data;

public class Watch extends JComponent {

	private static final long serialVersionUID = 1L;

	private Data data;
	private int w, h;

	public Watch(Data data, int size) {
		this.data = data;
		w = h = size;
	}

	@Override
	public void paint(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.black);
		g.fill(new Arc2D.Double(0, 0, w, h, 0, 180, Arc2D.Double.PIE));
		g.setColor(Color.red);

		AffineTransform t = g.getTransform();

		g.setPaint(new GradientPaint(0, 0, Color.red, 20, 20, Color.blue));
		g.translate(w / 2, h / 2);
		g.rotate(Math.PI + ( Math.PI) * (double) data.getDayCounter()
				/ Data.DAY_LENGTH);
		g.fillPolygon(createPointer());
		g.setTransform(t);

	}

	private Polygon createPointer() {
		Polygon p = new Polygon();

		int s = 5, t = 2, b = 10, c = w / 2 - b;

		p.addPoint(-s, 0);
		p.addPoint(0, s);
		p.addPoint(s - t, t);

		p.addPoint(c - b + t, t);
		p.addPoint(c, s);
		p.addPoint(c + b, 0);
		p.addPoint(c, -s);
		p.addPoint(c - b + t, -t);

		p.addPoint(s - t, -t);
		p.addPoint(0, -s);

		return p;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(w, h);
	}

}
