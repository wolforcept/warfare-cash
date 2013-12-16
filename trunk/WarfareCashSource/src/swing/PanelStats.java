package swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import data.Data;
import data.WifeStat;

public class PanelStats extends JPanel {

	private static Color //
			color_border = new Color(0.3f, 0.3f, 0.3f), //
			color_contour = new Color(0.1f, 0.1f, 0.1f);

	private static final long serialVersionUID = 1L;

	private static final int WIDTH_OF_BLOCK = 16;

	private int w, h, radius, border, blockHeight;
	private Point mouse;
	private double blockTrim;

	private WifeStat[] stats;

	public PanelStats(Data data, int radius, int border, int blockHeight,
			double blockTrim) {

		this.stats = data.getStats();
		this.radius = radius;
		this.border = border;
		this.blockHeight = blockHeight;
		this.blockTrim = blockTrim;

		this.w = stats.length * 2 * radius + (stats.length - 1) * border + 2
				* border;
		this.h = 2 * radius + 3 * border + 2 * blockHeight;

		this.mouse = new Point(0, 0);

		setFont(new Font(null, Font.BOLD, 9));

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				mouse.x = e.getX();
				mouse.y = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				mouse.x = e.getX();
				mouse.y = e.getY();
			}
		});
	}

	@Override
	public void paint(Graphics g1) {

		Graphics2D g = (Graphics2D) g1;

		g.setColor(color_border);
		for (int i = 0; i < border; i++) {
			g.setColor(g.getColor().brighter());
			g.drawRect(i, i, w - 2 * i, h - 2 * i - 1);
		}
		g.drawRect(0, 0, w, h - 1);

		int a = 0;
		for (WifeStat stat : stats) {

			int x = border + a * (2 * radius + border);
			int y = border;
			a++;

			g.setColor(stat.getColor().darker().darker());
			g.fillRect(x, y, 2 * radius, 2 * radius);

			Polygon p = createPolygonSquare(radius,
					(int) (1000 - stat.getCurrentValue()));
			p.translate(x, y);
			g.setColor(stat.getColor());
			g.fillPolygon(p);

			g.setColor(color_contour);
			g.drawPolygon(p);
			g.drawRect(x, y, 2 * radius, 2 * radius);

		}

		if (mouse.x > border //
				&& mouse.y > border //
				&& mouse.x < w - border //
				&& mouse.y < h - border * 2 - blockHeight * 2) {

			int topx = border;
			int topy = border * 2 + radius * 2;

			int i = 0;
			boolean exit = false;
			do {
				if (mouse.x < 2 * radius * (i + 1) + border * (i + 1)) {

					if (i < stats.length) {

						g.setColor(setAlpha(stats[i].getColor().darker(), 128));
						g.fillRect(topx - 1, topy - 1, w - border * 2,
								blockHeight * 2);
						g.fillRect(topx + 1, topy + 1, w - border * 2,
								blockHeight * 2);

						int font_size = (int) (getFont().getSize());
						String statName = stats[i].getName();

						g.setColor(stats[i].getColor().brighter());
						g.drawString(statName, topx
								+ (int) ((w - border * 2 - statName.length()
										* font_size) / 2), topy + font_size);
					}
					exit = true;
				} else
					i++;
			} while (!exit);

		} else {

			/*
			 * DRAW BLOCKS
			 */

			int numberOfBlocks = w / WIDTH_OF_BLOCK;
			int trim = (int) (blockTrim * WIDTH_OF_BLOCK);
			int topx = border;
			int topy = border * 2 + radius * 2 + blockHeight / 2;

			{// BLOCKS BACKGROUND
				g.setColor(Color.BLACK);
				Rectangle rect = new Rectangle(w - border * 2, blockHeight + 3);
				rect.translate(topx, topy - 1);
				GradientPaint paint = new GradientPaint(//
						new Point(topx, topy), //
						new Color(0.5f, 0f, 0f).darker(), //
						new Point(w - border, topy), //
						new Color(0f, 0.5f, 0f).darker()//
				);
				g.setPaint(paint);
				g.fill(rect);

			}

			{// BLOCKS
				int sum = 0;
				for (WifeStat s : stats) {
					sum += s.getCurrentValue();
				}
				double av = (double) sum / stats.length;
				int numberOfBlocksToDraw = (int) Math.round(numberOfBlocks * av
						/ 1000);

				for (int i = 0; i < numberOfBlocksToDraw; i++) {

					int x = 2 + border + i * (WIDTH_OF_BLOCK - trim / 2);
					int y = 2 * border + 2 * radius + blockHeight / 2;

					float green = (float) (0.5 * (float) i / numberOfBlocksToDraw);
					float red = (float) (0.5 * (1 - (float) i
							/ numberOfBlocksToDraw));

					Color color = new Color(red, green, 0f);

					Polygon p = createPolygonRect(WIDTH_OF_BLOCK, blockHeight,
							blockTrim);
					p.translate(x, y);
					g.setColor(color);
					g.fillPolygon(p);
					g.setColor(new Color(red + 0.5f, green + 0.5f, 0f));

					// g.setColor(color.darker());
					g.drawPolygon(p);
				}
			}
			{// BLOCKS FOREGROUND
				g.setColor(Color.BLACK);
				g.drawRect(topx, topy - 2, w - 2 * border, blockHeight * 2 - 1);
				// g.setColor(color_contour);
				// g.fillRect(0 + border, 2 * border + 2 * radius, trim,
				// blockHeight * 2);
				// g.fillRect(w - border - trim, 2 * border + 2 * radius, trim,
				// blockHeight * 2);
			}
		}
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
		p.addPoint(blockWidth - 1, 0);
		p.addPoint(blockWidth - blockTrim - 1, blockheight);
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

	public static Color setAlpha(Color color, int alpha) {
		int red = color.getRed();
		int green = color.getGreen();
		int blue = color.getBlue();
		return new Color(red, green, blue, alpha);
	}
}
