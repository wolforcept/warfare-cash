package main;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainWindow {

	// SWING
	private JFrame frame;
	private JLabel label_cash;

	private JPanel panel_means, panel_map, panel_, panel_shop;

	//
	private Data data;

	public MainWindow(String levelName) {

		frame = new JFrame(levelName);
		frame.getContentPane().setLayout(new GridLayout(2, 2));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		{// PANEL MEANS
			panel_means = new JPanel();

			label_cash = new JLabel();
			panel_means.add(label_cash);

			frame.getContentPane().add(panel_means);
		}

		{// PANEL MAP
			panel_map = new JPanel();
			panel_map.add(new JLabel("map will be here"));

			frame.getContentPane().add(panel_map);
		}

		{// PANEL
			panel_ = new JPanel();
			panel_.add(new JLabel("x will be here"));

			frame.getContentPane().add(panel_);
		}

		{// PANEL MAP
			panel_shop = new JPanel();
			panel_shop.add(new JLabel("shop will be here"));

			frame.getContentPane().add(panel_shop);
		}

		data = new Data(100);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		reloadUI();
	}

	private void reloadUI() {
		label_cash.setText(data.getMoneyInt() + "$");
		frame.repaint();
	}

	private void paintMap() {

		panel_map.repaint();
	}

}
