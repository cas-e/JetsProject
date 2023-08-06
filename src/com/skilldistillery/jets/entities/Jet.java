package com.skilldistillery.jets.entities;

public abstract class Jet {
	String model;
	double speed;  // MPH
	int    range;  // MILES
	long   price;  // USD
	
	Jet(String m, double s, int r, long p) {
		model = m;
		speed = s;
		range = r;
		price = p;
	}
	
	public double getSpeedInMach() {
		return speed / 767.3; // source: google unit converter
	}
	
	// Fly time has units HOURS
	public void fly() {
		double time = range / speed;
		System.out.println(this);
		System.out.printf("MESSAGE: Flying now. Can keep flying for %.3f Hours.%n%n",  time);
	}

	@Override
	public String toString() {
		return "[" + model + " | speed=" + speed + ", range=" + range + ", price=" + price + "]";
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public int getRange() {
		return range;
	}	
}
