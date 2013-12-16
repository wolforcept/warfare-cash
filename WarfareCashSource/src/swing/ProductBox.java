package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.Transient;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.MainWindow;
import swing.MyButton.MyAction;
import data.Data;
import data.Product;
import data.WifeStat;

public class ProductBox extends JComponent {
	private static final long serialVersionUID = 1L;
	private final int BORDER = 5;

	private MyButton product_price;
	private ProductNameLabel product_name;
	private Product product;
	private int width, maxHeight, minHeight;

	private static int openedIndex;
	private int actualIndex;

	private JPanel panel_bottom, panel_top;

	private MainWindow window;
	private WifeStat[] stats;
	private Data data;

	public ProductBox(MainWindow window, Data data, WifeStat[] stats, int id,
			Product product) {
		this.window = window;
		this.stats = stats;
		this.actualIndex = id;
		this.product = product;
		this.data = data;

		setFont(new Font(null, Font.BOLD, 14));

		width = 0;
		minHeight = Data.PODUCT_BOX_MIN_HEIGHT;
		maxHeight = Data.PODUCT_BOX_MAX_HEIGHT;

		setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER,
				BORDER));

		panel_top = new JPanel();
		panel_top.setLayout(new GridLayout(1, 2));

		product_name = new ProductNameLabel("unavaliable", 20, 20,
				new Action_openClose());

		product_price = new MyButton("-", 20, 20);
		product_price.addClickAction(new Action_BuyProduct());
		product_price.addMouseEnterAction(new Action_openClose());
		product_price.setFont(getFont());

		panel_bottom = new JPanel();
		panel_bottom.setLayout(new GridLayout(1, stats.length));

		panel_top.add(product_name);
		panel_top.add(product_price);

		for (int i = 0; i < stats.length; i++) {
			panel_bottom.add(new StatBox(i));
		}
	}

	public static int getOpenedIndex() {
		return openedIndex;
	}

	public void updateLayout() {

		if (product != null) {
			product_name.setText((actualIndex + 1) + "  -  "
					+ product.getName());
			product_name
					.setForeground(openedIndex == actualIndex ? Color.darkGray
							: Color.gray);

			product_price.setText("buy for $ " + product.getPrice());
			product_price.setForeground(Color.darkGray);
		} else {
			product_name.setText("unavaliable");
			product_name.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			product_name.setForeground(Color.gray);

			product_price.setText("-");
			product_price.setForeground(Color.gray);
		}
		removeAll();
		if (openedIndex == actualIndex) {
			setLayout(new BorderLayout());
			add(panel_top, BorderLayout.NORTH);
			add(panel_bottom, BorderLayout.CENTER);
		} else {
			setLayout(new BorderLayout());
			add(panel_top, BorderLayout.CENTER);
		}
	}

	@Override
	public void paint(Graphics g) {
		// g.setColor(Color.WHITE);
		// g.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
		drawProductBoxShape(g, BORDER, openedIndex == actualIndex);

	}

	private void drawProductBoxShape(Graphics g, int size, boolean col) {
		for (int i = 0; i < size; i++) {
			float f = 0.5f;
			g.setColor(new Color(f, f, f, 1 - (float) i / size));
			g.drawRoundRect(i, i, getWidth() - 2 * i - 1, getHeight() - 2 * i
					- 1, 10, 10);
		}
	}

	@Override
	@Transient
	public Dimension getMaximumSize() {
		return new Dimension(10000, 10000);
	}

	@Override
	@Transient
	public Dimension getMinimumSize() {
		return new Dimension(0, 0);
	}

	@Override
	@Transient
	public Dimension getPreferredSize() {
		return new Dimension(width, openedIndex == actualIndex ? maxHeight
				: minHeight);
	}

	private class Action_openClose implements MyAction {
		@Override
		public void perform() {
			openedIndex = actualIndex;
			data.setReloadProductPanel(true);
			updateLayout();
			window.reloadUI_thorough();
		}
	}

	private class Action_BuyProduct implements MyAction {

		@Override
		public void perform() {
			if (product != null) {

				if (data.getMoney() < product.getPrice()) {
					JOptionPane
							.showMessageDialog(null, "You can't afford that");
				} else {
					data.addMoney(-product.getPrice());
					for (int i = 0; i < stats.length; i++) {
						stats[i].increaseRate(product.getStatValues()[i]);
					}
					data.getWarehouseCity().removeProduct(product.getId());

					data.setReloadProductPanel(true);
					window.reloadUI_thorough();
				}
			}
		}
	}

	private class StatBox extends JComponent {

		private static final long serialVersionUID = 1L;
		private int statIndex;

		public StatBox(int statIndex) {
			this.statIndex = statIndex;
		}

		@Override
		public void paint(Graphics g) {

			if (product != null) {

				WifeStat stat = stats[statIndex];
				g.setColor(new Color(100, 100, 100, 200));
				g.fillRect(4, 7, 73, 15);
				g.setColor(new Color(50, 50, 50, 200));
				g.fillRect(2, 5, 73, 15);
				g.setColor(stat.getColor());
				g.drawString(stat.getName().toLowerCase(),
						4 + stat.getFoward(), 17);
				if (product.getStatValue(statIndex) > 0) {
					g.setColor(Color.black);
					g.drawLine(5, 23, product.getStatValue(statIndex) * 10, 23);
					for (int i = 0; i < product.getStatValue(statIndex); i++) {

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

	private class ProductNameLabel extends JLabel implements MouseListener {

		private static final long serialVersionUID = 1L;

		private MyAction action;
		private int w, h;

		public ProductNameLabel(String text, int width, int height,
				MyAction action) {
			super(text);
			this.action = action;
			this.w = width;
			this.h = height;
			this.addMouseListener(this);
			setForeground(new Color(0.2f, 1f, 0.2f, 1f));
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(w, h);
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			action.perform();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

	}

}
