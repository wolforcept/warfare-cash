package data;

import data.enums.Cargo;

public class Truck {
	private int[] ammounts;
	private City origin, destination;
	private int x, y;
	private boolean arrived, sell;

	public Truck(int[] ammount, City origin, City destination) {
		this(ammount, origin, destination, false);
	}

	public Truck(int[] ammount, City origin, City destination, boolean sell) {
		this.ammounts = ammount;
		this.origin = origin;
		this.destination = destination;
		x = origin.getX();
		y = origin.getY();
		arrived = false;
		this.sell = sell;
	}

	private void translate(double d, double e) {
		x += d;
		y += e;
	}

	public void approach() {
		double speed = Math.random() * 5 + 5;
		double dir = getDirection();
		if (Math.hypot(destination.getY() - y, destination.getX() - x) < speed) {
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getCargoAmmount(Cargo c) {
		return ammounts[c.ordinal()];
	}

	public int getCargoAmmount(int c) {
		return ammounts[c];
	}

	public int[] getAmmounts() {
		return ammounts;
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

}
