package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import main.Level.City;

public class MainWindow {

	// SWING
	private JFrame frame;
	private JLabel label_cash, label_debt, label_cityname;

	private JLabel[] label_resources, label_wayfaring;
	private JSpinner[] spinners;

	private JPanel panel_means, panel_, panel_shop;
	private Map panel_map;
	private JButton[] buttons_buy;
	//
	private Data data;

	public MainWindow(Level level) {

		data = new Data(level);

		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(2, 2));
		// frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		{// PANEL MEANS
			panel_means = new JPanel();
			panel_means.setLayout(new BorderLayout());
			panel_means.setBorder(BorderFactory.createTitledBorder("Means"));

			label_cash = new JLabel();
			label_cash.setFont(new Font(null, Font.BOLD, 18));
			panel_means.add(label_cash);
			label_debt = new JLabel();
			panel_means.add(label_debt);

			JButton button_loan = new JButton("loan");
			button_loan.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String answer = JOptionPane.showInputDialog(null,
							"How much do you want to ask");
					if (isNumeric(answer)) {
						int ammount = Integer.parseInt(answer);
						double interest = 0.0005;
						if (ammount >= data.getMoneyInt()) {
							interest = 0.002;
						}
						if (0 == JOptionPane.showConfirmDialog(null,
								"Do you agree with a " + interest * 100
										+ "% interest?")) {
							data.loan(ammount, interest);
						}
					}
					reloadUI();
				}
			});
			panel_means.add(button_loan);
			{
				JPanel panel_resources = new JPanel();
				panel_resources.setBorder(BorderFactory
						.createTitledBorder("Warehouse"));
				panel_resources.setLayout(new GridLayout(1, 3));

				JPanel panel_resources_ammount = new JPanel();
				panel_resources_ammount.setLayout(new GridLayout(
						Cargo.values().length, 1));
				JPanel panel_resources_sell = new JPanel();
				panel_resources_sell.setLayout(new GridLayout(
						Cargo.values().length, 1));
				JPanel panel_resources_sell_all = new JPanel();
				panel_resources_sell_all.setLayout(new GridLayout(Cargo
						.values().length, 1));

				label_resources = new JLabel[Cargo.values().length];
				for (int i = 0; i < Cargo.values().length; i++) {
					label_resources[i] = new JLabel(data.getResourceAmmount(i)
							+ "");
					panel_resources_ammount.add(label_resources[i]);

					addSellButtons(i, panel_resources_sell,
							panel_resources_sell_all);
				}
				panel_resources.add(panel_resources_ammount);
				panel_resources.add(panel_resources_sell);
				panel_resources.add(panel_resources_sell_all);

				panel_means.add(panel_resources, BorderLayout.CENTER);
			}

			{
				JPanel panel_wayfaring = new JPanel();
				panel_wayfaring.setBorder(BorderFactory
						.createTitledBorder("Wayfaring"));
				panel_wayfaring.setLayout(new GridLayout(Cargo.values().length,
						1));

				label_wayfaring = new JLabel[Cargo.values().length];
				for (int i = 0; i < Cargo.values().length; i++) {
					label_resources[i] = new JLabel(data.getResourceAmmount(i)
							+ "",JLabel.CENTER);
					panel_wayfaring.add(label_resources[i]);

				}
				panel_means.add(panel_wayfaring, BorderLayout.SOUTH);
			}
			frame.getContentPane().add(panel_means);
		}

		{// PANEL MAP
			panel_map = new Map(this, data, data.getBackgroundImage());
			panel_map.setPreferredSize(new Dimension(320, 320));
			frame.getContentPane().add(panel_map);
		}

		{// PANEL
			panel_ = new JPanel();
			panel_.add(new JLabel("x will be here"));

			JButton button_hazard = new JButton("HAZARD!");
			button_hazard.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					data.hazardCity();
					reloadUI();
				}
			});
			panel_.add(button_hazard);

			frame.getContentPane().add(panel_);
		}

		{// PANEL SHOP
			panel_shop = new JPanel();
			panel_shop.setLayout(new BorderLayout());
			label_cityname = new JLabel("no city selected");
			label_cityname.setFont(new Font(null, Font.BOLD, 18));
			panel_shop.add(label_cityname, BorderLayout.NORTH);

			JPanel panel_resources = new JPanel();
			panel_resources.setBorder(BorderFactory
					.createTitledBorder("Warehouse"));
			panel_resources.setLayout(new GridLayout(Cargo.values().length, 1));

			buttons_buy = new JButton[Cargo.values().length];
			spinners = new JSpinner[Cargo.values().length];
			for (int i = 0; i < Cargo.values().length; i++) {

				JPanel panel_cargo_private = new JPanel();
				panel_cargo_private.setLayout(new GridLayout(1, 1));

				// TOP
				JPanel panel_cargo_private_top = new JPanel();
				panel_cargo_private_top.setLayout(new BorderLayout());
				panel_cargo_private.add(panel_cargo_private_top);

				panel_cargo_private_top.add(new JLabel(Cargo.values()[i]
						.getName(), JLabel.CENTER));

				JPanel panel_cargo_private_top_east = new JPanel();
				panel_cargo_private_top_east.setLayout(new FlowLayout());
				spinners[i] = new JSpinner(getSpinnerModel());
				spinners[i].setPreferredSize(new Dimension(60, 20));
				panel_cargo_private_top_east
						.add(spinners[i], BorderLayout.EAST);

				buttons_buy[i] = new JButton("Buy");
				buttons_buy[i].addActionListener(new BuyActionListener(i));
				panel_cargo_private_top_east.add(buttons_buy[i]);
				panel_cargo_private_top.add(panel_cargo_private_top_east,
						BorderLayout.EAST);

				// // BOTTOM
				// JButton button_zero = new JButton("0");
				// button_zero.setPreferredSize(new Dimension(45, 20));
				// button_zero.addActionListener(new ZeroActionListener(
				// spinners[i]));
				// panel_cargo_private_top_east.add(button_zero);
				// JPanel panel_cargo_private_bottom = new JPanel();
				// panel_cargo_private_bottom.setLayout(new BorderLayout());
				// panel_cargo_private.add(panel_cargo_private_bottom);
				//
				// panel_cargo_private_bottom.add(new JLabel("p",
				// JLabel.CENTER));

				//

				//
				panel_resources.add(panel_cargo_private);
			}

			panel_shop.add(panel_resources, BorderLayout.CENTER);
			JButton button_buy_all = new JButton("Buy All");
			button_buy_all.addActionListener(new BuyAllActionListener());
			panel_shop.add(button_buy_all, BorderLayout.SOUTH);

			frame.getContentPane().add(panel_shop);
		}

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		reloadUI();
		new Controller(this, data).start();
	}

	private void addSellButtons(final int index, JPanel sell, JPanel sell_all) {
		{
			JButton button_sell_resource = new JButton("Sell");
			button_sell_resource.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					data.sellAll(index);
					reloadUI();
				}
			});
			sell.add(button_sell_resource);
		}
		{
			JButton button_sell_all_resource = new JButton("Sell All");
			button_sell_all_resource.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					data.sellAll(index);
					reloadUI();
				}
			});
			sell_all.add(button_sell_all_resource);
		}
	}

	void reloadUI() {
		City selectedCity = data.getSelectedCity();
		if (data.getWarehouseCity() != null)
			panel_means.setBorder(BorderFactory.createTitledBorder("Means at "
					+ data.getWarehouseCity().name));

		{// PANEL MEANS
			label_cash.setText(data.getMoneyInt() + "$");
			if (data.getDebtInt() > 0)
				label_debt.setText(data.getDebtInt() + "$ debt");
			else
				label_debt.setText("no debts");
			for (int i = 0; i < label_resources.length; i++) {
				label_resources[i].setText("" + data.getResourceAmmount(i));
			}
		}
		{// PANEL SHOP
			panel_shop = new JPanel();
			if (selectedCity != null) {
				label_cityname.setText(selectedCity.name);
				// for (int i = 0; i < buttons_buy.length; i++) {
				// buttons_buy[i].setVisible(true);
				// }
				// } else {
				// for (int i = 0; i < buttons_buy.length; i++) {
				// buttons_buy[i].setVisible(false);
				// }
			}
		}

		frame.repaint();

	}

	private boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	private SpinnerNumberModel getSpinnerModel() {

		return new SpinnerNumberModel(new Integer(0), // value
				new Integer(0), // min
				new Integer(999999), // max
				new Integer(1) // step
		);
	}

	class BuyActionListener implements ActionListener {
		int index;

		public BuyActionListener(int i) {
			index = i;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			int[] ammounts = new int[Cargo.values().length];
			ammounts[index] = (Integer) spinners[index].getValue();

			if (ammounts[index] > 0)
				if (data.getSelectedCity() == data.getWarehouseCity()) {
					data.addResources(ammounts);
				} else {
					Truck c = new Truck(ammounts, data.getSelectedCity(),
							data.getWarehouseCity());
					data.addTruck(c);
				}
			reloadUI();
		}
	};

	class BuyAllActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {

			int[] ammounts = new int[Cargo.values().length];
			int sum = 0;
			for (int i = 0; i < ammounts.length; i++) {
				ammounts[i] = (Integer) spinners[i].getValue();
				sum += ammounts[i];
			}

			if (sum > 0)
				if (data.getSelectedCity() == data.getWarehouseCity()) {
					data.addResources(ammounts);
				} else {
					Truck c = new Truck(ammounts, data.getSelectedCity(),
							data.getWarehouseCity());
					data.addTruck(c);
				}
			reloadUI();
		}
	};

	class ZeroActionListener implements ActionListener {
		JSpinner spinner;

		public ZeroActionListener(JSpinner s) {
			spinner = s;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			spinner.setValue(0);
			reloadUI();
		}
	};

}
