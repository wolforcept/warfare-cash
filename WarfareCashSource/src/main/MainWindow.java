package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import swing.DayCyclePanel;
import swing.Map;
import swing.MyButton;
import swing.MyButton.MyAction;
import swing.ProductPanel;
import data.Data;
import data.Level;
import data.ProductType;
import data.Truck;
import data.enums.Cargo;

public class MainWindow {

	private JFrame frame;
	private JLabel label_cash, label_news, label_debt, label_cityname,
			label_citycost, label_day_number;

	private JLabel[] label_warehouse_quantities, label_city_prices,
			label_city_quantities, label_wayfaring;
	private JSpinner[] spinners;

	private JPanel panel_means, panel_wife, panel_shop;
	private Map panel_map;
	private ProductPanel panel_products;
	private Data data;
	private DataController dataController;
	private Dialog_debts dialog_debts;

	public MainWindow(Level level, ProductType[] productTypes) {

		data = new Data(level, productTypes);
		dataController = new DataController(data);

		frame = new JFrame();
		frame.getContentPane().setLayout(new GridLayout(2, 2));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addPanelMeans();
		{// PANEL MAP
			panel_map = new Map(this, data, data.getBackgroundImage());
			panel_map.setPreferredSize(new Dimension(320, 320));
			frame.getContentPane().add(panel_map);
		}

		{// PANEL WIFE
			panel_wife = new JPanel();
			panel_wife.setLayout(new BorderLayout());

			JLabel label_wife = new JLabel("Wife");
			panel_wife.add(label_wife, BorderLayout.NORTH);

			panel_products = new ProductPanel();

			panel_wife.add(panel_products, BorderLayout.CENTER);

			frame.getContentPane().add(panel_wife);
		}
		addPanelShop();

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		reloadUI();

		/*
		 * NOT SWING
		 */
		dialog_debts = new Dialog_debts(frame, data);

		new Controller(this, dataController).start();
	}

	private void addPanelMeans() {

		panel_means = new JPanel();
		panel_means.setLayout(new BorderLayout());
		panel_means.setBorder(BorderFactory.createTitledBorder("Means"));
		panel_means.setPreferredSize(Data.PANEL_DEFAULT_SIZE);

		label_cash = new JLabel();
		label_cash.setFont(new Font(null, Font.BOLD, 18));
		JPanel panel_means_money = new JPanel();
		panel_means_money.add(label_cash);
		label_debt = new JLabel();
		panel_means_money.add(label_debt);

		MyButton button_loan = new MyButton("Loan", 45, 20);
		button_loan.addAction(new MyAction() {
			@Override
			public void perform() {
				String answer = JOptionPane.showInputDialog(null,
						"How much do you want to ask");
				if (isNumeric(answer)) {
					int ammount = Integer.parseInt(answer);

					double of = 0.5 * ammount / data.getMoney();
					double interest = Math.min(0.5, of);

					double temp = interest * 100;
					interest = temp < 0.01 ? 0 : temp;

					DecimalFormat df = new DecimalFormat("#.###");
					String show = df.format(interest);
					System.out.println(interest + " interest, show: " + show);

					String string = "Do you agree with a " + show
							+ "% interest?";
					if (interest > 0) {
						if (0 == JOptionPane.showConfirmDialog(null, string)) {
							dataController.loan(ammount, interest);
						}
					} else
						dataController.loan(ammount, interest);

				}
				reloadUI();
			}
		});
		panel_means_money.add(button_loan);

		MyButton button_pay = new MyButton("Pay", 45, 20);
		button_pay.addAction(new Action_pay());
		panel_means_money.add(button_pay);

		panel_means.add(panel_means_money, BorderLayout.NORTH);

		{
			JPanel panel_means_main = new JPanel();
			panel_means_main.setLayout(new BorderLayout());

			JPanel panel_resources = new JPanel();
			panel_resources.setBorder(BorderFactory
					.createTitledBorder("Warehouse"));
			panel_resources.setLayout(new GridLayout(Cargo.values().length, 1));

			label_warehouse_quantities = new JLabel[Cargo.values().length];
			for (int i = 0; i < Cargo.values().length; i++) {
				JPanel panel_private_resource = new JPanel();
				panel_private_resource.setLayout(new BorderLayout());
				JLabel label_resource_name = new JLabel(
						Cargo.values()[i].getName() + ":  ", JLabel.RIGHT);

				label_warehouse_quantities[i] = new JLabel();
				label_warehouse_quantities[i].setPreferredSize(new Dimension(
						80, 20));
				panel_private_resource.add(label_resource_name,
						BorderLayout.CENTER);
				panel_private_resource.add(label_warehouse_quantities[i],
						BorderLayout.EAST);
				panel_resources.add(panel_private_resource);
			}

			panel_means_main.add(panel_resources, BorderLayout.CENTER);

			JPanel panel_day_settings = new JPanel();
			panel_day_settings.setLayout(new BorderLayout());

			label_day_number = new JLabel("day 0");
			panel_day_settings
					.add(new DayCyclePanel(data), BorderLayout.CENTER);
			panel_day_settings.add(label_day_number, BorderLayout.NORTH);

			panel_means_main.add(panel_day_settings, BorderLayout.EAST);

			panel_means.add(panel_means_main);
		}

		{
			JPanel panel_wayfaring = new JPanel();
			panel_wayfaring.setBorder(BorderFactory
					.createTitledBorder("Wayfaring"));
			panel_wayfaring.setLayout(new GridLayout(Cargo.values().length, 1));

			label_wayfaring = new JLabel[Cargo.values().length];
			for (int i = 0; i < Cargo.values().length; i++) {
				label_wayfaring[i] = new JLabel(
						data.getResourceAmmount(i) + "", JLabel.CENTER);
				panel_wayfaring.add(label_wayfaring[i]);

			}
			panel_means.add(panel_wayfaring, BorderLayout.SOUTH);
		}
		frame.getContentPane().add(panel_means);

	}

	private void addPanelShop() {

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

		label_city_prices = new JLabel[Cargo.values().length];
		label_city_quantities = new JLabel[Cargo.values().length];
		spinners = new JSpinner[Cargo.values().length];
		for (int i = 0; i < Cargo.values().length; i++) {

			JPanel panel_cargo_private = new JPanel();
			panel_cargo_private.setLayout(new GridLayout(2, 1));

			// TOP
			JPanel panel_cargo_private_top = new JPanel();
			panel_cargo_private_top.setLayout(new BorderLayout());

			label_city_quantities[i] = new JLabel("", JLabel.RIGHT);
			label_city_quantities[i].setPreferredSize(new Dimension(45, 20));
			panel_cargo_private_top.add(label_city_quantities[i],
					BorderLayout.WEST);

			JLabel label_cargo_name = new JLabel(Cargo.values()[i].getName(),
					JLabel.CENTER);
			label_cargo_name.setPreferredSize(new Dimension(100, 20));
			panel_cargo_private_top.add(label_cargo_name, BorderLayout.CENTER);

			label_city_prices[i] = new JLabel();
			label_city_prices[i].setPreferredSize(new Dimension(45, 20));
			panel_cargo_private_top
					.add(label_city_prices[i], BorderLayout.EAST);

			panel_cargo_private.add(panel_cargo_private_top);

			// BOTTOM
			JPanel panel_cargo_private_bottom = new JPanel();
			panel_cargo_private.add(panel_cargo_private_bottom);

			panel_cargo_private_bottom.setLayout(new FlowLayout());
			{// BUTTONS
				MyButton button_zero = new MyButton("0", 19, 20);
				button_zero.setPreferredSize(new Dimension(45, 20));
				button_zero.addAction(new Action_setzero(i));
				panel_cargo_private_bottom.add(button_zero);

				spinners[i] = new JSpinner(getSpinnerModel());
				spinners[i].setPreferredSize(new Dimension(60, 20));
				panel_cargo_private_bottom.add(spinners[i], BorderLayout.EAST);

				MyButton button_max_buy = new MyButton("Max", 40, 20);
				button_max_buy.setPreferredSize(new Dimension(65, 20));
				button_max_buy.addAction(new Action_setMaxPrice(i));

				MyButton buttons_buy = new MyButton("Buy", 38, 20);
				buttons_buy.setPreferredSize(new Dimension(65, 20));
				buttons_buy.addAction(new Action_buy(i));

				MyButton button_max_sell = new MyButton("Max", 40, 20);
				button_max_sell.setPreferredSize(new Dimension(65, 20));
				button_max_sell.addAction(new Action_setMaxMerch(i));

				MyButton button_sell = new MyButton("Sell", 38, 20);
				button_sell.setPreferredSize(new Dimension(65, 20));
				button_sell.addAction(new Action_sell(i));

				panel_cargo_private_bottom.add(button_max_buy);
				panel_cargo_private_bottom.add(buttons_buy);
				panel_cargo_private_bottom.add(button_max_sell);
				panel_cargo_private_bottom.add(button_sell);
			}

			panel_resources.add(panel_cargo_private);
		}

		panel_shop.add(panel_resources, BorderLayout.CENTER);

		label_news = new JLabel("Your evil plan was set in motion");
		panel_shop.add(label_news, BorderLayout.SOUTH);

		frame.getContentPane().add(panel_shop);
	}

	/**
	 * RELOAD THE UI, UPDATE ALL THE DESIRED LABELS
	 */
	public void repaintUI() {

		frame.repaint();
	}

	public void reloadUI(boolean b) {
		data.isUpdatePanelProducts(b);
		reloadUI();
	}

	public void reloadUI() {

		if (data.getWarehouseCity() != null)
			panel_means.setBorder(BorderFactory.createTitledBorder("Means at "
					+ data.getWarehouseCity().getName()));

		{// PANEL MEANS
			label_cash.setText("$ " + String.format("%,d", data.getMoney()));
			label_day_number.setText("day " + data.getDay());
			int debtTotal = data.getDebtTotal();
			if (debtTotal > 0)
				label_debt.setText(debtTotal + "$ debt");
			else
				label_debt.setText("no debts");

			for (int i = 0; i < label_warehouse_quantities.length; i++) {
				label_warehouse_quantities[i].setText(data
						.getResourceAmmount(i) + "");
			}

			for (int i = 0; i < label_wayfaring.length; i++) {
				int sums = 0;
				for (Truck t : data.getTrucksSnapshot()) {
					if (!t.isSeller() && t.getCargoIndex() == i)
						sums += t.getCargoAmmount();
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

		{// PANEL WIFE
			if (data.getWarehouseCity() != null && data.isUpdatePanelProducts()) {
				panel_products.setProducts(this, data.getWarehouseCity()
						.getProducts());
				data.isUpdatePanelProducts(false);
			}
		}

		{// PANEL SHOP
			panel_shop = new JPanel();

			if (data.getSelCity() != null) {
				label_cityname.setText(data.getSelCity().getName());
				label_citycost.setText(" trip cost: $ "
						+ (int) data.getTripPrice(data.getSelCity(),
								data.getWarehouseCity()));

				for (int i = 0; i < label_warehouse_quantities.length; i++) {
					label_city_quantities[i].setText(data.getSelCity()
							.getResourceQuantity(i) + "  ");
					int nr = (int) Math.round(data.getSelCity()
							.getResourcePrice(i));
					label_city_prices[i].setText("$ "
							+ String.format("%,d", nr));
				}
			}
		}

		panel_products.reloadUI();
		frame.revalidate();
	}

	private boolean isNumeric(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(str.charAt(i))) {
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

	class Action_buy implements MyAction {
		int index;

		public Action_buy(int i) {
			index = i;
		}

		@Override
		public void perform() {

			int ammount = (Integer) spinners[index].getValue();

			if (ammount > 0 && data.getSelCity() != null) {

				ammount = Math.min(ammount, data.getSelCity()
						.getResourceQuantity(index));

				int merch_price = ammount
						* data.getSelCity().getResourcePrice(index);
				int trip_price = data.getTripPrice(data.getSelCity(),
						data.getWarehouseCity());

				int total_price = merch_price + trip_price;

				if (data.getMoney() > total_price) {

					if (data.getSelCity() == data.getWarehouseCity()) {
						data.addResource(index, ammount);
						data.addMoney(-total_price);
					} else {

						Truck c = new Truck(index, ammount, data.getSelCity(),
								data.getWarehouseCity(), false, trip_price);
						data.addTruck(c);
						data.addMoney(-total_price);
					}

				} else {
					JOptionPane.showMessageDialog(frame,
							"You can't afford that. \nTotal Cost: "
									+ total_price + ".\nYou are missing $ "
									+ (total_price - data.getMoney()) + ".");
				}
				reloadUI();
			}
		}
	};

	private class Action_sell implements MyAction {
		int index;

		public Action_sell(int i) {
			index = i;
		}

		@Override
		public void perform() {
			int ammount = (Integer) spinners[index].getValue();

			if (ammount > 0 && data.getSelCity() != null) {

				int package_price = ammount
						* data.getSelCity().getResourcePrice(index);

				int trip_price = data.getTripPrice(data.getSelCity(),
						data.getWarehouseCity());

				if (data.hasResource(index, ammount)) {
					data.removeResource(index, ammount);
					if (data.getSelCity() == data.getWarehouseCity()) {
						data.addMoney(package_price);
					} else {
						data.addMoney(-trip_price);
						Truck c = new Truck(index, ammount,
								data.getWarehouseCity(), data.getSelCity(),
								true, trip_price);
						data.addTruck(c);
					}
				} else {
					JOptionPane.showMessageDialog(frame,
							"You dont own that much.");
				}
			}
			reloadUI();
		}

	};

	private class Action_setzero implements MyAction {
		int index;

		public Action_setzero(int i) {
			index = i;
		}

		@Override
		public void perform() {
			spinners[index].setValue(0);
			reloadUI();
		}
	};

	private class Action_setMaxMerch implements MyAction {
		int index;

		public Action_setMaxMerch(int i) {
			index = i;
		}

		@Override
		public void perform() {
			spinners[index].setValue(data.getResourceAmmount(index));
			reloadUI();
		}
	}

	private class Action_setMaxPrice implements MyAction {
		int index;

		public Action_setMaxPrice(int i) {
			index = i;
		}

		@Override
		public void perform() {

			if (data.getSelCity() != null) {
				double cost = data.getSelCity().getResourcePrice(index);
				double money = data.getMoney()
						- data.getTripPrice(data.getWarehouseCity(),
								data.getSelCity());

				int avaliableQnt = data.getSelCity().getResourceQuantity(index);
				int desiredQnt = (int) (money / cost);

				int maxQnt = Math.min(desiredQnt, avaliableQnt);

				spinners[index].setValue(Math.max(0, maxQnt));
				reloadUI();
			}
		}
	}

	private class Action_pay implements MyAction {
		@Override
		public void perform() {
			dialog_debts.reloadDebts();
			dialog_debts.setVisible(true);
		}
	}

}
