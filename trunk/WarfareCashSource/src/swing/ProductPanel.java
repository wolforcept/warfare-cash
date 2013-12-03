package swing;

import java.awt.GridLayout;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainWindow;
import data.Data;
import data.Product;

public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private LinkedList<ProductBox> productBoxes;

	public ProductPanel() {

		productBoxes = new LinkedList<ProductBox>();

		setBorder(BorderFactory.createTitledBorder("Products for Sale"));
	}

	public void reloadUI() {
		removeAll();
		if (productBoxes.size() > 0) {
			BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
			setLayout(l);
			for (ProductBox p : productBoxes) {
				add(p);
				p.updateLayout();
			}
		} else {
			setLayout(new GridLayout(1, 1));
			add(new JLabel("No Products Avaliable", JLabel.CENTER));
		}

	}

	public void setProducts(MainWindow window, LinkedList<Product> products) {
		productBoxes.clear();

		Collections.sort(products, new Comparator<Product>() {

			@Override
			public int compare(Product o1, Product o2) {
				return o1.getPrice() <= o2.getPrice() ? -1 : 1;
			}
		});

		int i = 0;
		for (Product p : products) {
			productBoxes.add(new ProductBox(window, i++, p));
		}
	}
}
