package main;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Scanner;

import data.Level;
import data.WifeStat;

public class FileReader {

	public static Level[] readLevels() {
		InputStream levels_input = new Object().getClass().getResourceAsStream(
				"/resources/levels");
		Scanner scanner = new Scanner(levels_input);

		LinkedList<Level> levels = new LinkedList<Level>();
		while (scanner.hasNext()) {
			levels.add(new Level(scanner.nextLine()));
		}

		scanner.close();

		return levels.toArray(new Level[levels.size()]);
	}

	public static String[] readProducts() {
		InputStream products_input = new Object().getClass()
				.getResourceAsStream("/resources/producttypes");
		Scanner scanner = new Scanner(products_input);

		LinkedList<String> strings = new LinkedList<String>();
		while (scanner.hasNext()) {
			strings.add(scanner.nextLine());
		}
		scanner.close();

		return strings.toArray(new String[strings.size()]);
	}

	public static WifeStat[] readStats() {
		InputStream products_input = new Object().getClass()
				.getResourceAsStream("/resources/stats");
		Scanner scanner = new Scanner(products_input);

		LinkedList<WifeStat> stats = new LinkedList<WifeStat>();
		while (scanner.hasNext()) {
			stats.add(new WifeStat(scanner.nextLine()));
		}

		scanner.close();

		return stats.toArray(new WifeStat[stats.size()]);
	}
}
