package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.util.Collections;
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
		// new
		// Level("Atomic_Wastelands: Gor-10000-0,0 Far-5000-2,3 Dur-2000-0,5");
	}

	private JFrame frame;
	private JList list_levels;

	public Main() {

		Level[] levels = readLevels();
		String[] levelNames = getLevelNames(levels);

		frame = new JFrame(GAME_NAME);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel_main = new JPanel();
		panel_main.setLayout(new BorderLayout());
		frame.getContentPane().add(panel_main, BorderLayout.CENTER);
		{// MAIN PANEL
			list_levels = new JList();
			list_levels.setBorder(BorderFactory.createTitledBorder("Levels"));
			list_levels.setListData(levelNames);
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
						new MainWindow((String) list_levels.getSelectedValue());
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

	private String[] getLevelNames(Level[] levels) {
		String[] levelNames = new String[levels.length];
		for (int i = 0; i < levels.length; i++) {
			levelNames[i] = levels[i].getName();
		}
		return levelNames;
	}

	private Level[] readLevels() {
		InputStream levels_input = getClass().getResourceAsStream(
				"/resources/levels");
		Scanner scanner = new Scanner(levels_input);

		LinkedList<Level> levels = new LinkedList<Level>();
		while (scanner.hasNext()) {
			levels.add(new Level(scanner.nextLine()));
		}

		return levels.toArray(new Level[levels.size()]);
	}
}
