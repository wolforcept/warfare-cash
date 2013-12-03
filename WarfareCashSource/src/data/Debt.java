package data;

public class Debt {

	// private Date expirationDate;
	private static int incrementableId = 0;
	private double interest;
	private int id, debtValue, initialValue;

	public Debt(int ammount, double interest) {
		// this.expirationDate = expirationDate;
		this.debtValue = this.initialValue = ammount;
		this.interest = interest / 100;
		System.out.println("Created debt of " + ammount + " increasing "
				+ interest);
		this.id = incrementableId++;
	}

	public void increase() {
		debtValue += initialValue * interest;
	}

	public int getValue() {
		return debtValue;
	}

	public int getId() {
		return id;
	}

	public void setValue(int ammount) {
		debtValue = ammount;
	}

}
