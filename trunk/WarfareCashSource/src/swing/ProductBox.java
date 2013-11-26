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
import javax.swing.JLabel;
import javax.swing.JPanel;

import swing.MyButton.MyAction;
import data.Product;
import data.enums.Stat;

public class ProductBox extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int BORDER = 5;

	private JLabel product_name;
	private MyButton product_price;

	private Product product;
	private int width, height;

	private boolean closed;

	public ProductBox(boolean closed) {
		updateLayout();
		this.closed = closed;
		setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER,
				BORDER));
	}

	public void setProduct(Product p, int width, int height) {
		product = p;
		this.width = width;
		this.height = height;
	}

	public void updateLayout() {

		if (!closed)
			setLayout(new BorderLayout());

		JPanel panel_top = new JPanel();
		panel_top.setLayout(new GridLayout(1, 2));
		add(panel_top, BorderLayout.NORTH);

		product_name = new JLabel("nothing avaliable");
		product_name.setFont(new Font(null, Font.BOLD, 14));
		product_name.setForeground(Color.gray);
		panel_top.add(product_name);

		product_price = new MyButton("nothing avaliable", 20, 20);
		product_price.addAction(new Action_BuyProduct());
		product_price.setFont(new Font(null, Font.BOLD, 14));
		product_price.setForeground(Color.gray);
		panel_top.add(product_price);

		if (!closed) {

			JPanel panel_bottom = new JPanel();
			panel_bottom.setLayout(new GridLayout(1, Stat.values().length));

			for (int i = 0; i < Stat.values().length; i++) {

				Stat s = Stat.values()[i];

				StatBox statBox = new StatBox(s);
				panel_bottom.add(statBox);

			}
			add(panel_bottom, BorderLayout.CENTER);
		}

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		drawProductBoxShape(g, 5, Color.gray);
		if (product != null) {

			product_name.setForeground(Color.black);
			product_name.setText(product.getName());

			product_price.setText("buy for $ " + product.getPrice());
		}
	}

	private void drawProductBoxShape(Graphics g, int size, Color col) {
		g.setColor(col);
		for (int i = 0; i < size; i++) {
			// g.setColor(ProductBox.moreTransparent(g.getColor()));
			g.setColor(new Color(0.5f, 0.5f, 0.5f, 1 - (float) i / size));
			g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i
					- 1, 10, 10);

		}
	}

	public static Color moreTransparent(Color c) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(),
				(int) (c.getTransparency() * 0.8));
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
