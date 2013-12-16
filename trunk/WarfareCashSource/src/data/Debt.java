package data;

public class Debt {

	// private Date expirationDate;
	private static int incrementableId = 0;
	private double interest;
	private int id, debtValue, initialValue;
	private boolean paid;

	public Debt(int ammount, double interest) {
		// this.expirationDate = expirationDate;
		this.debtValue = this.initialValue = ammount;
		this.interest = interest / 100;
		System.out.println("Created debt of " + ammount + " increasing "
				+ interest);
		this.id = incrementableId++;
		paid = false;
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

	public void paid() {
		paid = true;
	}

	public boolean isPaid() {
		return paid;
	}

}
