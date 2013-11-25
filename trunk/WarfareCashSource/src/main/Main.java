package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main {

	private static String GAME_NAME = "Warfare Cash";
	private static String[] BUTTONS = //
	{ "Start", "Create Room", "Join Room", "Exit" };
	private static String BOTTOM_STRING = //
	"Copyright 2013 by Wolforce, all rights reserved ";

	// GAME_NAME, the All Out War Logo and the All Out War Game Code belong to
	// Wolforce, all rights reserved.";

	public static void main(String[] args) {
		new Main();
	}

	private JFrame frame;
	private JList<Level> list_levels;

	public Main() {

		Level[] levels = readLevels();

		frame = new JFrame(GAME_NAME);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout());
		frame.getContentPane().add(panel_main, BorderLayout.CENTER);
		{// MAIN PANEL
			list_levels = new JList<Level>();
			list_levels.setBorder(BorderFactory.createTitledBorder("Levels"));
			list_levels.setListData(levels);
			list_levels.setSelectedIndex(0);
			panel_main.add(list_levels, BorderLayout.WEST);

			JPanel panel_buttons = new JPanel();
			GridLayout layout_buttons = new GridLayout(4, 1);
			layout_buttons.setVgap(10);
			panel_buttons.setLayout(layout_buttons);
			panel_main.add(panel_buttons, BorderLayout.CENTER);

			{ // BUTTONS
				JButton button_start = new JButton(BUTTONS[0]);
				button_start.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new MainWindow((Level) list_levels.getSelectedValue(),
								readStuff());
						frame.setVisible(false);
						frame.dispose();
						System.gc();
					}
				});
				panel_buttons.add(button_start);

				JButton button_join = new JButton(BUTTONS[1]);
				button_join.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "Upcoming feature");
					}
				});
				panel_buttons.add(button_join);

				JButton button_host = new JButton(BUTTONS[2]);
				button_host.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "Upcoming feature");
					}
				});
				panel_buttons.add(button_host);

				JButton button_exit = new JButton(BUTTONS[3]);
				button_exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.exit(0);
					}
				});
				panel_buttons.add(button_exit);
			}
			JPanel panel_bottom = new JPanel();
			panel_bottom.add(new JLabel(BOTTOM_STRING));
			panel_main.add(panel_bottom, BorderLayout.SOUTH);
		}
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private Level[] readLevels() {
		InputStream levels_input = getClass().getResourceAsStream(
				"/resources/levels");
		Scanner scanner = new Scanner(levels_input);

		LinkedList<Level> levels = new LinkedList<Level>();
		while (scanner.hasNext()) {
			levels.add(new Level(scanner.nextLine()));
		}

		scanner.close();

		return levels.toArray(new Level[levels.size()]);
	}

	private StuffType[] readStuff() {
		InputStream stuff_input = getClass().getResourceAsStream(
				"/resources/stufftypes");
		Scanner scanner = new Scanner(stuff_input);

		LinkedList<StuffType> stuffTypes = new LinkedList<StuffType>();
		while (scanner.hasNext()) {
			stuffTypes.add(new StuffType(scanner.nextLine()));
		}

		scanner.close();

		return stuffTypes.toArray(new StuffType[stuffTypes.size()]);
	}
}
