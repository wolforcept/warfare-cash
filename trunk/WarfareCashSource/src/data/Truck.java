package data;

public class Truck {
	private int cargoAmmount;
	private City origin, destination;
	private int x, y;
	private boolean arrived, sell;
	private int cargoIndex;
	private int money;
	private boolean isDead;

	public Truck(int index, int ammount, City origin, City destination,
			boolean sell, int money) {
		this.cargoAmmount = ammount;
		this.origin = origin;
		this.destination = destination;
		this.cargoIndex = index;
		this.x = origin.getX();
		this.y = origin.getY();
		this.arrived = false;
		this.sell = sell;
		this.money = money;
		this.isDead = false;
	}

	private void translate(double d, double e) {
		x += d;
		y += e;
	}

	public void approach() {
		double speed = Math.random() * 5 + 5;
		double dir = getDirection();
		money -= Math.random() * Data.TRUCK_CONSUMPTION * 0.2 + Data.TRUCK_CONSUMPTION * 0.8;
		if (money < 0) {
			isDead = true;
		} else if (Math.hypot(destination.getY() - y, destination.getX() - x) < speed) {
			x = destination.getX();
			y = destination.getY();
			arrived = true;
		} else {
			translate(Math.cos(dir) * speed, Math.sin(dir) * speed);
		}
	}

	public boolean isArrived() {
		return arrived;
	}

	public boolean isDead() {
		return isDead;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	// public int getCargoAmmount(Cargo c) {
	// return ammounts[c.ordinal()];
	// }

	public int getCargoAmmount() {
		return cargoAmmount;
	}

	public City getOrigin() {
		return origin;
	}

	public City getDestination() {
		return destination;
	}

	public double getDistanceLeft() {
		return Math.hypot(destination.getY() - y, destination.getX() - x);
	}

	public float getDirection() {
		return (float) Math.atan2(destination.getY() - y, destination.getX()
				- x);
	}

	public boolean isSeller() {
		return sell;
	}

	public int getCargoIndex() {
		return cargoIndex;
	}

}
