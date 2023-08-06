package com.skilldistillery.jets.entities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AirField {
	private List<Jet> jets = new ArrayList<>();

	public AirField(String path) {
		try (BufferedReader reading = new BufferedReader(new FileReader(path))) {

			reading.lines().forEach(line -> addJetFrom(line));

		} catch (IOException e) {
			System.err.println(e);
		}
	}

	private void addJetFrom(String line) {
		String[] fields = line.split(",");
		String     type = fields[0];
		String    model = fields[1];
		double    speed = Double.parseDouble(fields[2]);
		int       range = Integer.parseInt(fields[3]);
		long      price = Long.parseLong(fields[4]);

		switch (type) {
		case "PassengerJet":
			jets.add(new PassengerJet(model, speed, range, price)); break;
			
		case "FighterJet":
			jets.add(new FighterJet(model, speed, range, price));   break;
			
		case "CargoPlane":
			jets.add(new CargoPlane(model, speed, range, price));   break;
		}
	}
	
	
	public void listFleet() {
		System.out.println("~~ All Jets in Fleet ~~\n");
		listJets();
	}
	// jets with associated numbering, in a one-based indexing scheme
	public void listJets() {
		for (int i = 1; i <= jets.size(); i++) {
			System.out.println(i + ") " + jets.get(i-1));
		}
	}
	public void flyAllJets() {
		System.out.println("~~ Flying all The Jets ~~\n");
		jets.stream().forEach(j -> j.fly());
	}

	public void listFastestJets() {
		System.out.println("~~ Fastest Jet(s) ~~\n");

		double top = jets.stream().map(j -> j.getSpeed())
				                  .reduce(0.0, (x, y) -> Math.max(x, y));

		jets.stream().filter(j -> j.getSpeed() == top)
		             .forEach(j -> System.out.println(j));
	}

	public void listRangiestJets() {
		System.out.println("~~ Jet(s) with Longest Range ~~\n");

		int top = jets.stream().map(j -> j.getRange())
				               .reduce(0, (x, y) -> Math.max(x, y));

		jets.stream().filter(j -> j.getRange() == top)
		             .forEach(j -> System.out.println(j));
	}

	public void loadAllCargoJets() {
		System.out.println("~~ All Cargo Jets Loading Cargo Now ~~\n");
		jets.stream().filter(j -> j instanceof CargoPlane)
		             .forEach(cp -> ((CargoPlane) cp).loadCargo());
	}
	
	public void dogfightFighters() {
		System.out.println("~~ All Fighter Jets Engaged in Combat ~~\n");
		jets.stream().filter(j -> j instanceof FighterJet)
		             .forEach(fj -> ((FighterJet) fj).fight());
	}

	public void addNewUserJet(int type, String model, double speed, int range, long price) {
		switch (type) {
		case 1: jets.add( new PassengerJet(model, speed, range, price)); break;
		case 2: jets.add( new FighterJet(model, speed, range, price));   break;
		case 3: jets.add( new CargoPlane(model, speed, range, price));   break;
		}
	}
	
	public int numberOfJets() {
		return jets.size();
	}
	
	// users enter numbers in a one based indexing scheme, we must translate
	public void retireJet(int indexPlusOne) {
		jets.remove(indexPlusOne - 1);
	}
}
