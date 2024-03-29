package swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import swing.MyButton.MyAction;

import data.Data;
import data.Debt;

public class Dialog_debts extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final int DEBT_PANEL_WIDTH = 100, DEBT_PANEL_HEIGHT = 20,
			DEBT_PANEL_BORDER = 2;

	private final Dialog_debts myself;

	private Thread updater;
	private JPanel panel_debts;
	private Data data;

	public Dialog_debts(JFrame frame, Data data) {
		super(frame, true);
		myself = this;
		this.data = data;
		setUndecorated(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		((JComponent) getContentPane()).setBorder(BorderFactory
				.createCompoundBorder(//
						BorderFactory.createRaisedSoftBevelBorder(),//
						BorderFactory.createLoweredSoftBevelBorder()//
				));

		JLabel label_title = new JLabel("Today's Debts", JLabel.CENTER);
		label_title.setForeground(Color.DARK_GRAY);
		label_title.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(label_title, BorderLayout.NORTH);

		panel_debts = new JPanel();

		add(panel_debts, BorderLayout.CENTER);

		MyButton button_pay_all = new MyButton("Pay All", 80, 20);
		button_pay_all.addClickAction(new Action_pay_all());

		MyButton button_close = new MyButton("Close", 80, 20);
		button_close.addClickAction(new MyAction() {
			@Override
			public void perform() {
				close();
			}
		});

		JPanel panel_bottom = new JPanel();
		panel_bottom.setLayout(new FlowLayout());
		panel_bottom.add(button_pay_all);
		panel_bottom.add(button_close);
		add(panel_bottom, BorderLayout.SOUTH);

		pack();
	}

	public void reloadDebts() {

		System.out.println("reloaded Debts");

		if (updater != null)
			updater.interrupt();

		updater = new Thread() {
			public void run() {
				try {
					while (!isInterrupted()) {
						myself.repaint();

						sleep(1000 / 60);

					}
				} catch (InterruptedException e) {
				}
			};
		};
		updater.start();

		panel_debts.removeAll();
		LinkedList<Debt> debts = data.getDebtsSnapshot();
		int height = debts.size() == 20 ? 1 : debts.size()
				* (DEBT_PANEL_HEIGHT + DEBT_PANEL_BORDER);
		panel_debts.setPreferredSize(new Dimension(DEBT_PANEL_WIDTH, height));
		pack();

		if (debts != null) {
			panel_debts.setLayout(new GridLayout(Math.max(1, debts.size()), 1));

			if (debts.size() == 0) {
				JLabel label_noDebts = new JLabel("No debts.\nGood for you!",
						JLabel.CENTER);
				label_noDebts.setForeground(Color.gray);
				panel_debts.add(label_noDebts);
			} else {
				for (Debt debt : debts) {
					JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());

					panel.add(new JLabel("Debt " + debt.getValue()),
							BorderLayout.CENTER);
					if (debt.isPaid()) {
						JLabel label_paid = new JLabel("Paid");
						panel.add(label_paid, BorderLayout.EAST);
					} else {
						MyButton button_pay = new MyButton("Pay", 40, 20);
						button_pay.addClickAction(new Action_pay(debt));
						panel.add(button_pay, BorderLayout.EAST);
					}
					panel_debts.add(panel);
				}
			}
		}
		revalidate();
		repaint();
	}

	private void close() {
		myself.setVisible(false);
		myself.dispose();
		updater.interrupt();
	}

	private class Action_pay implements MyAction {

		private Debt debt;

		public Action_pay(Debt debt) {
			this.debt = debt;
		}

		@Override
		public void perform() {
			payDebt(debt);
			reloadDebts();
		}
	}

	private class Action_pay_all implements MyAction {
		@Override
		public void perform() {
			LinkedList<Debt> debts = data.getDebtsSnapshot();
			if (debts != null && debts.size() > 0) {
				for (Debt debt : debts) {
					if (debt.getValue() <= data.getMoney()) {
						data.addMoney(-debt.getValue());
						debt.paid();
					}
				}
			}
			close();
		}
	}

	private void payDebt(Debt debt) {
		if (debt.getValue() <= data.getMoney()) {
			data.addMoney(-debt.getValue());
			debt.paid();
		}
	}
}
