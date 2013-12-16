package swing;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainWindow;
import data.Data;
import data.Product;
import data.WifeStat;

public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ProductBox[] productBoxes;
	private WifeStat[] stats;

	public ProductPanel(WifeStat[] stats) {
		this.stats = stats;
		productBoxes = new ProductBox[Data.NUMBER_OF_PRODUCTS_AVAILABLE];
		setBorder(BorderFactory.createTitledBorder("Products for Sale"));
	}

	public void reloadUI(Data data) {
		if (data.isReloadProductPanel()) {
			removeAll();
			if (data.getWarehouseCity() != null) {
				BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
				setLayout(l);
				for (ProductBox p : productBoxes) {
					if (p != null) {
						add(p);
						p.updateLayout();
					}
				}
			} else {
				setLayout(new GridLayout(1, 1));
				add(new JLabel("No Products Avaliable", JLabel.CENTER));
			}

			data.setReloadProductPanel(false);
		}
	}

	public void setProducts(MainWindow window, Data data, Product[] products) {

		int id = 0;
		for (int j = 0; j < Data.NUMBER_OF_PRODUCTS_AVAILABLE; j++) {
			productBoxes[j] = new ProductBox(window, data, stats, id++,
					products[j]);
		}
	}
}
