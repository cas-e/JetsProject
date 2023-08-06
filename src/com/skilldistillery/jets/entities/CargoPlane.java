package com.skilldistillery.jets.entities;

public class CargoPlane extends Jet implements CargoCarrier {
	CargoPlane(String m, double s, int r, long p) {
		super(m, s, r, p);
	}

	@Override
	public void loadCargo() {
		System.out.println(this);
		System.out.println("MESSAGE: Cargo is loaded and ready to go!\n");	
	}

}
