package data;


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
		x = origin.x;
		y = origin.y;
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
		if (Math.hypot(destination.y - y, destination.x - x) < speed) {
			x = destination.x;
			y = destination.y;
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
		return Math.hypot(destination.y - y, destination.x - x);
	}

	public float getDirection() {
		return (float) Math.atan2(destination.y - y, destination.x - x);
	}

	public boolean isSeller() {
		return sell;
	}

}
