package main;

import java.awt.BorderLayout;
import java.awt.Color;
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

import swing.MyButton;
import swing.MyButton.MyAction;
import data.Data;
import data.Debt;

public class Dialog_debts extends JDialog {

	private static final long serialVersionUID = 1L;

	private final Dialog_debts myself;

	private Thread updater;
	private LinkedList<Debt> debts;

	private Data data;

	private JPanel panel_debts;

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

		JLabel label_title = new JLabel("Debts", JLabel.CENTER);
		label_title.setForeground(Color.DARK_GRAY);
		label_title.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(label_title, BorderLayout.NORTH);

		panel_debts = new JPanel();

		MyButton button_pay_all = new MyButton("Pay All", 80, 20);
		button_pay_all.addAction(new Action_pay_all());

		MyButton button_close = new MyButton("Close", 80, 20);
		button_close.addAction(new MyAction() {
			@Override
			public void perform() {
				myself.setVisible(false);
				myself.dispose();
				updater.interrupt();
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
		updater = new Thread() {
			public void run() {
				try {
					while (!isInterrupted()) {
						myself.repaint();

						sleep(1000 / 60);

					}
				} catch (InterruptedException e) {
					System.err.println("dialog interrupted");
				}
			};
		};
		updater.start();

		debts = data.getDebtsSnapshot();

		panel_debts.removeAll();

		if (debts != null) {

			panel_debts.setLayout(new GridLayout(//
					// // debts.size() == 0 ? 3 : //
					Math.max(1, debts.size()), 1));

			add(panel_debts, BorderLayout.CENTER);

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
					MyButton button_pay = new MyButton("Pay", 40, 20);
					button_pay.addAction(new Action_pay());
					panel.add(button_pay, BorderLayout.EAST);

					panel_debts.add(panel);
				}
			}
		}

	}

	private class Action_pay implements MyAction {
		@Override
		public void perform() {
			// TODO data.tryPayDebts();
		}
	}

	private class Action_pay_all implements MyAction {
		@Override
		public void perform() {
			if (debts != null && debts.size() > 0) {
				// TODO data.tryPayDebt();
			}
		}
	}
}
