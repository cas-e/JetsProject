package com.skilldistillery.jets.entities;

public class FighterJet extends Jet implements CombatReady {
	
	FighterJet(String m, double s, int r, long p) {
		super(m, s, r, p);
	}

	@Override
	public void fight() {
		System.out.println(this);
		System.out.println("MESSAGE: Engaged in Combat! PEW PEW!\n");
	}

}
