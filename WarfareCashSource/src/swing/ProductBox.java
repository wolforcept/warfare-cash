package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

import main.MainWindow;

import swing.MyButton.MyAction;
import data.Product;
import data.enums.Stat;

public class ProductBox extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int BORDER = 5;

	private MyButton product_name, product_price;

	private Product product;
	private int width, height;

	private boolean closed;
	private JPanel panel_bottom, panel_top;
	// private JPanel contents;

	private MainWindow window;

	public ProductBox(MainWindow window, boolean closed) {
		this.window = window;
		this.closed = closed;

		panel_top = new JPanel();
		panel_top.setLayout(new GridLayout(1, 2));

		product_name = new MyButton("unavaliable", 20, 20);
		product_name.addAction(new Action_openClose());
		product_name.setFont(new Font(null, Font.BOLD, 14));

		product_price = new MyButton("-", 20, 20);
		product_price.addAction(new Action_BuyProduct());
		product_price.setFont(new Font(null, Font.BOLD, 14));

		panel_bottom = new JPanel();
		panel_bottom.setLayout(new GridLayout(1, Stat.values().length));

		panel_top.add(product_name);
		panel_top.add(product_price);

		setLayout(new BorderLayout());
		add(panel_top, BorderLayout.NORTH);

		for (int i = 0; i < Stat.values().length; i++) {

			Stat s = Stat.values()[i];

			StatBox statBox = new StatBox(s);
			panel_bottom.add(statBox);

		}
		add(panel_bottom, BorderLayout.CENTER);
		
		setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER,
				BORDER));


	}

	public void setProduct(Product p) {
		product = p;
		updateLayout();
	}

	public void updateLayout() {

		if (product != null) {
			product_name.setText(product.getName());
			product_name.setForeground(Color.black);

			product_price.setText("buy for $ " + product.getPrice());
			product_name.setForeground(Color.black);
		} else {
			product_name.setText("unavaliable");
			product_name.setForeground(Color.gray);

			product_price.setText("-");
			product_name.setForeground(Color.gray);
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawProductBoxShape(g, 3, Color.gray);

	}

	private void drawProductBoxShape(Graphics g, int size, Color col) {
		g.setColor(col);
		for (int i = 0; i < size; i++) {
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 1 - (float) i / size));
			g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i
					- 1, 10, 10);
		}
	}

	@Override
	@Transient
	public Dimension getMaximumSize() {
		return new Dimension(width, height);
	}

	@Override
	@Transient
	public Dimension getMinimumSize() {
		return new Dimension(width, height);
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	private class Action_BuyProduct implements MyAction {

		@Override
		public void perform() {
			if (product != null) {
				System.out.println(product.getName());
			}
		}
	}

	private class Action_openClose implements MyAction {
		@Override
		public void perform() {
			closed = !closed;
			updateLayout();
			window.reloadUI();
		}
	}

	private class StatBox extends JComponent {

		private static final long serialVersionUID = 1L;
		private Stat stat;

		public StatBox(Stat stat) {
			this.stat = stat;
		}

		@Override
		public void paint(Graphics g) {

			if (product != null) {
				g.setColor(new Color(100, 100, 100, 200));
				g.fillRect(4, 7, 73, 15);
				g.setColor(new Color(50, 50, 50, 200));
				g.fillRect(2, 5, 73, 15);
				g.setColor(stat.getColor());
				g.drawString(stat.name().toLowerCase(), 4 + stat.getFoward(),
						17);
				g.setColor(Color.black);
				g.drawLine(5, 23, product.getStat(stat) * 10, 23);
				for (int i = 0; i < product.getStat(stat); i++) {

					int x = 7 + 10 * i;
					int y = 21;

					g.setColor(Color.black);
					g.fillRect(x - 2, y - 2, 8, 8);

					g.setColor(stat.getColor());
					g.fillRect(x - 1, y - 1, 6, 6);

				}
			}
		}
	}
}
