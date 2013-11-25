package main;

import java.awt.BorderLayout;
import java.awt.Color;
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

public class MainWindow {

	// SWING
	private JFrame frame;
	private JLabel label_cash, label_debt, label_cityname, label_citycost;

	private JLabel[] label_resource_quantities, label_resource_prices,
			label_wayfaring;
	private JSpinner[] spinners;

	private JPanel panel_means, panel_, panel_shop;
	private Map panel_map;

	private Data data;

	public MainWindow(Level level) {

		data = new Data(level);

		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(2, 2));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		{// PANEL MEANS
			panel_means = new JPanel();
			panel_means.setLayout(new BorderLayout());
			panel_means.setBorder(BorderFactory.createTitledBorder("Means"));
			panel_means.setPreferredSize(new Dimension(320, 320));

			label_cash = new JLabel();
			label_cash.setFont(new Font(null, Font.BOLD, 18));
			JPanel panel_means_money = new JPanel();
			panel_means_money.add(label_cash);
			label_debt = new JLabel();
			panel_means_money.add(label_debt);

			JButton button_loan = new JButton("Loan");
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
			panel_means_money.add(button_loan);

			panel_means.add(panel_means_money, BorderLayout.NORTH);

			{
				JPanel panel_resources = new JPanel();
				panel_resources.setBorder(BorderFactory
						.createTitledBorder("Warehouse"));
				panel_resources.setLayout(new GridLayout(Cargo.values().length,
						1));

				label_resource_quantities = new JLabel[Cargo.values().length];
				for (int i = 0; i < Cargo.values().length; i++) {
					JPanel panel_private_resource = new JPanel();
					panel_private_resource.setLayout(new GridLayout(1, 2));
					JLabel label_resource_name = new JLabel(
							Cargo.values()[i].getName() + ":  ", JLabel.RIGHT);

					label_resource_quantities[i] = new JLabel();
					panel_resources.add(label_resource_name);
					panel_resources.add(label_resource_quantities[i]);
				}

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
					label_wayfaring[i] = new JLabel(data.getResourceAmmount(i)
							+ "", JLabel.CENTER);
					panel_wayfaring.add(label_wayfaring[i]);

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

			JPanel panel_labels_top = new JPanel();
			panel_labels_top.setLayout(new GridLayout(1, 2));

			label_cityname = new JLabel("no city selected", JLabel.CENTER);
			label_cityname.setFont(new Font(null, Font.BOLD, 18));

			label_citycost = new JLabel();
			label_citycost.setFont(new Font(null, Font.PLAIN, 12));

			panel_labels_top.add(label_cityname);
			panel_labels_top.add(label_citycost);

			panel_shop.add(panel_labels_top, BorderLayout.NORTH);

			JPanel panel_resources = new JPanel();
			panel_resources.setBorder(BorderFactory.createTitledBorder("Shop"));
			panel_resources.setLayout(new GridLayout(Cargo.values().length, 1));

			label_resource_prices = new JLabel[Cargo.values().length];
			spinners = new JSpinner[Cargo.values().length];
			for (int i = 0; i < Cargo.values().length; i++) {

				JPanel panel_cargo_private = new JPanel();
				panel_cargo_private.setLayout(new GridLayout(2, 1));

				// TOP
				JPanel panel_cargo_private_b = new JPanel();
				JLabel label_cargo_name = new JLabel(
						Cargo.values()[i].getName(), JLabel.CENTER);
//				label_cargo_name.setPreferredSize(new Dimension(20, 20));
				panel_cargo_private_b
						.add(label_cargo_name, BorderLayout.CENTER);

				label_resource_prices[i] = new JLabel();
				panel_cargo_private_b.add(label_resource_prices[i]);

				panel_cargo_private.add(panel_cargo_private_b);

				// BOTTOM
				JPanel panel_cargo_private_top = new JPanel();
				panel_cargo_private_top.setLayout(new BorderLayout());
				panel_cargo_private.add(panel_cargo_private_top);

				JPanel panel_cargo_private_top_east = new JPanel();
				panel_cargo_private_top_east.setLayout(new FlowLayout());
				spinners[i] = new JSpinner(getSpinnerModel());
				spinners[i].setPreferredSize(new Dimension(60, 20));
				panel_cargo_private_top_east
						.add(spinners[i], BorderLayout.EAST);

				JButton button_zero = new JButton("0");
				button_zero.setPreferredSize(new Dimension(45, 20));
				button_zero.addActionListener(new ZeroActionListener(i));
				panel_cargo_private_top_east.add(button_zero);

				JButton buttons_buy = new JButton("Buy");
				buttons_buy.setPreferredSize(new Dimension(65, 20));
				buttons_buy.addActionListener(new BuyActionListener(i));
				panel_cargo_private_top_east.add(buttons_buy);

				JButton button_sell = new JButton("Sell");
				button_sell.setPreferredSize(new Dimension(65, 20));
				button_sell.addActionListener(new SellActionListener(i));
				panel_cargo_private_top_east.add(button_sell);

				panel_cargo_private_top.add(panel_cargo_private_top_east,
						BorderLayout.EAST);

				panel_resources.add(panel_cargo_private);
			}

			panel_shop.add(panel_resources, BorderLayout.CENTER);

			JPanel panel_buttons_all = new JPanel();
			panel_buttons_all.setLayout(new GridLayout(1, 2));
			JButton button_buy_all = new JButton("Buy All");
			JButton button_sell_all = new JButton("Sell All");
			button_buy_all.addActionListener(new BuyActionListener());
			button_sell_all.addActionListener(new SellActionListener());
			panel_buttons_all.add(button_sell_all);
			panel_buttons_all.add(button_buy_all);

			panel_shop.add(panel_buttons_all, BorderLayout.SOUTH);

			frame.getContentPane().add(panel_shop);
		}

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		reloadUI();
		new Controller(this, data).start();
	}

	/**
	 * RELOAD THE UI, UPDATE ALL THE DESIRED LABELS
	 */
	void reloadUI() {
		City selectedCity = data.getSelCity();
		if (data.getWarehouseCity() != null)
			panel_means.setBorder(BorderFactory.createTitledBorder("Means at "
					+ data.getWarehouseCity().name));

		{// PANEL MEANS
			label_cash.setText("$ " + String.format("%,d", data.getMoneyInt()));
			if (data.getDebtInt() > 0)
				label_debt.setText(data.getDebtInt() + "$ debt");
			else
				label_debt.setText("no debts");

			for (int i = 0; i < label_resource_quantities.length; i++) {
				label_resource_quantities[i].setText(data.getResourceAmmount(i)
						+ "");
			}

			for (int i = 0; i < label_wayfaring.length; i++) {
				int sums = 0;
				for (Truck t : data.getTrucksSnapshot()) {
					if (!t.isSeller())
						sums += t.getAmmounts()[i];
				}
				if (sums > 0) {
					label_wayfaring[i].setForeground(Color.black);
					label_wayfaring[i].setText(sums + " "
							+ Cargo.values()[i].getName() + " incoming");
				} else {
					label_wayfaring[i].setForeground(Color.gray);
					label_wayfaring[i].setText("no "
							+ Cargo.values()[i].getName() + " incoming");
				}
			}
		}

		{// PANEL SHOP
			panel_shop = new JPanel();

			if (data.getSelCity() != null) {
				label_cityname.setText(selectedCity.name);
				label_citycost.setText(" trip cost: $ "
						+ (int) data.getTripPrice(data.getSelCity(),
								data.getWarehouseCity()));

				for (int i = 0; i < label_resource_quantities.length; i++) {

					int nr = (int) Math.round(data.getSelCity().priceOf(i));
					label_resource_prices[i].setText("$ "
							+ String.format("%,d", nr));
				}
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

		public BuyActionListener() {
			index = -1;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {

			int[] ammounts = new int[Cargo.values().length];
			int sum;

			if (index < 0) {
				sum = getSpinnerValues(ammounts);
			} else {
				zeroFill(ammounts);
				sum = ammounts[index] = (Integer) spinners[index].getValue();
			}

			if (data.getSelCity() != null)
				if (sum > 0) {
					int totalPrice = calculatePrice(ammounts, data.getSelCity()
							.getCurrentPrices());
					if (data.getMoney() > totalPrice) {

						if (data.getSelCity() == data.getWarehouseCity()) {
							data.addResources(ammounts);
							data.addMoney(-totalPrice);
						} else {
							double tripPrice = data.getTripPrice(
									data.getSelCity(), data.getWarehouseCity());
							System.out.println("trip price from "
									+ data.getWarehouseCity().name + " to "
									+ data.getSelCity().name + " is "
									+ tripPrice);
							if (data.getMoney() > (totalPrice + tripPrice)) {
								Truck c = new Truck(ammounts,
										data.getSelCity(),
										data.getWarehouseCity());
								data.addTruck(c);
								data.addMoney(-totalPrice - tripPrice);
							}
						}

					}
					reloadUI();
				}
		}
	};

	private class SellActionListener implements ActionListener {
		int index;

		public SellActionListener(int i) {
			index = i;
		}

		public SellActionListener() {
			index = -1;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int sum = 0;
			int[] ammounts = new int[Cargo.values().length];
			if (index < 0) {
				sum = getSpinnerValues(ammounts);
			} else {
				zeroFill(ammounts);
				sum = ammounts[index] = (Integer) spinners[index].getValue();
			}
			if (sum > 0 && data.getSelCity() != null) {
				int totalPrice = calculatePrice(ammounts, data.getSelCity()
						.getCurrentPrices());
				if (data.hasResources(ammounts)) {
					data.addMoney(totalPrice);
					data.removeResources(ammounts);
					if (data.getSelCity() == data.getWarehouseCity()) {
					} else {
						Truck c = new Truck(ammounts, data.getWarehouseCity(),
								data.getSelCity(), true);
						data.addTruck(c);
					}
				}
			}
			reloadUI();
		}

	};

	private class ZeroActionListener implements ActionListener {
		int index;

		public ZeroActionListener(int i) {
			index = i;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			spinners[index].setValue(0);
			reloadUI();
		}
	};

	private int calculatePrice(int[] ammounts, double[] prices) {
		int sum = 0;
		if (ammounts.length != prices.length) {
			throw new IllegalArgumentException(
					"the arrays dont have the same length");
		}
		for (int i = 0; i < prices.length; i++) {
			sum += ammounts[i] * prices[i];
		}
		return sum;
	}

	/**
	 * Puts spinner values in the param 'ammounts' and returns its sum
	 */
	private int getSpinnerValues(int[] ammounts) {
		int sum = 0;
		for (int i = 0; i < ammounts.length; i++) {
			ammounts[i] = (Integer) spinners[i].getValue();
			sum += ammounts[i];
		}
		return sum;
	}

	private void zeroFill(int[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = 0;
		}
	}

}
