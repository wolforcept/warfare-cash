package main;

import main.Level.City;

public class Truck {
	private int[] ammounts;
	private City origin, destination;
	private int x, y;
	private boolean arrived;

	public Truck(int[] ammount, City origin, City destination) {
		this.ammounts = ammount;
		this.origin = origin;
		this.destination = destination;
		x = origin.x;
		y = origin.y;
		arrived = false;
	}

	private void translate(double d, double e) {
		x += d;
		y += e;
	}

	public void approach() {
		double dir = Math.atan2(destination.y - y, destination.x - x);
		if (Math.hypot(destination.y - y, destination.x - x) < 5) {
			x = destination.x;
			y = destination.y;
			arrived = true;
		} else {

			translate(Math.cos(dir) * 5, Math.sin(dir) * 5);
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

}
