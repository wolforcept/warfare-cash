package swing;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.MainWindow;
import data.Data;

public class ProductPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainWindow window;
	private ProductBox[] productBoxes;

	public ProductPanel(MainWindow window, ProductBox[] productBoxes) {

		this.window = window;
		this.productBoxes = productBoxes;

		setBorder(BorderFactory.createTitledBorder("Products for Sale"));

		productBoxes[0] = new ProductBox(this, 0);
		for (int i = 1; i < Data.NUMBER_OF_PRODUCTS_AVALIABLE; i++) {
			productBoxes[i] = new ProductBox(this, i);
		}
		reloadUI();
	}

	public void reloadUI() {
		 setLayout(new GridLayout(5, 1));
//		setLayout(new GridBagLayout());
		removeAll();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		for (int i = 0; i < productBoxes.length; i++) {
			c.gridheight = ProductBox.getOpenedIndex() == i ? 2 : 1;
			add(productBoxes[i], c);
//			add(productBoxes[i]);
		}
		window.reloadUI();
	}
}
