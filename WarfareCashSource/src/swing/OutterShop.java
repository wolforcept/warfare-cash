package swing;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class OutterShop extends JDialog {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new OutterShop().setVisible(true);
	}

	public OutterShop() {
		setPreferredSize(new Dimension(100, 100));
	}

}
