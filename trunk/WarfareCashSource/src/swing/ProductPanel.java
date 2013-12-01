package swing;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.MainWindow;
import data.Data;

public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ProductBox[] productBoxes;

	public ProductPanel(MainWindow window, ProductBox[] productBoxes) {

		this.productBoxes = productBoxes;

		setBorder(BorderFactory.createTitledBorder("Products for Sale"));

		productBoxes[0] = new ProductBox(window, 0);
		for (int i = 1; i < Data.NUMBER_OF_PRODUCTS_AVAILABLE; i++) {
			productBoxes[i] = new ProductBox(window, i);
		}
	}

	public void reloadUI() {

		removeAll();

		BoxLayout l = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(l);
		for (int i = 0; i < productBoxes.length; i++) {
			add(productBoxes[i]);
		}

	}
}
