package main;

import java.sql.Date;
import java.util.LinkedList;

public class Data {

	private double money;
	private LinkedList<Debt> debts;

	public Data(int initialMoney) {
		money = initialMoney;

	}

	public double getMoney() {
		return money;
	}

	public int getMoneyInt() {
		return (int) Math.round(money);
	}

	class Debt {

		Date expirationDate;
		double ammount;

		public Debt(Date expirationDate, double ammount) {
			this.expirationDate = expirationDate;
			this.ammount = ammount;
		}
	}

	public void addMoney(int ammount) {
		money += ammount;
	}
}
