package data;

public class Debt {

	// private Date expirationDate;
	private double debtValue, interest, initialValue;

	public Debt(double ammount, double interest) {
		// this.expirationDate = expirationDate;
		this.debtValue = this.initialValue = ammount;
		this.interest = interest / 100;
		System.out.println("Created debt of " + ammount + " increasing "
				+ interest);
	}

	public void increase() {
		debtValue += initialValue * interest;
	}

	public double getValue() {
		return debtValue;
	}
}
