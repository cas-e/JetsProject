package com.skilldistillery.jets.app;

import java.util.Scanner;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import com.skilldistillery.jets.entities.AirField;


public class JetsApp {
	private AirField airfield;
	private Scanner scan;

	// for more readability in user interaction loops
	private final boolean keepLooping = true;
	private final boolean doneLooping = false;

	// some values to obtain from the user
	private double speed;
	private int range;
	private long price;
	
	
	public static void main(String[] args) {
		
		JetsApp app = new JetsApp();
		app.run();
	}

	private void run() {
		System.out.println("~~ Welcome to JetsApp!!!!! ~~");

		airfield = new AirField("jets.txt");
		scan = new Scanner(System.in);

		// the userLoop method just calls another method in a "do...while" loop
		// in this case, we want to keep calling doMenu() until the user quits
		userLoop(this::doMenu);

		scan.close();
		System.out.println("\n~~ Thank you for using JetsApp! Goodbye. ~~");
	}
	
	private void displayChoices() {
		System.out.println("\n~~ Please enter an option ~~\n");
		System.out.println("1) List Fleet                     5) Load All Cargo Jets");
		System.out.println("2) Fly All The Jets!              6) DOGFIGHT!!!");
		System.out.println("3) List Fastest Jet(s)            7) Add Jet to Fleet");
		System.out.println("4) List Jet(s) with Longest Range 8) Remove Jet from Fleet");
		System.out.println("\n~~  or enter (9) to quit  ~~");
	}

	public void displayUnrecognized() {
		System.out.println("Oops! We didn't understand that.");
	}
	
	private boolean doMenu() {
		displayChoices();
		
		// getUserInt() returns -1 for unrecognized input,
		// which falls through these cases, causing the loop to continue
		int userChose = getUserInt();

		switch (userChose) {
		case 1: airfield.listFleet();        break;
		case 2: airfield.flyAllJets();       break;
		case 3: airfield.listFastestJets();  break;
		case 4: airfield.listRangiestJets(); break;
		case 5: airfield.loadAllCargoJets(); break;
		case 6: airfield.dogfightFighters(); break;
		case 7: userLoop(this::getJetInfo);  break;
		case 8: userLoop(this::removeJet);   break;
		
		case 9 : 
			return doneLooping;
			
		default: 
			displayUnrecognized();
		}
		
		return keepLooping;
	}

	private boolean getJetInfo() {
		System.out.println("~~ Entering Data for a New Jet ~~\n");
	
		System.out.println("Please Enter a Number for the Jet Type: ");
		System.out.println("1) Passenger Jet");
		System.out.println("2) Fighter Jet");
		System.out.println("3) Cargo Plane");
		
		int type = getUserInt();
		if (type < 1 || type > 3) {
			displayUnrecognized();
			return keepLooping;
		}
		
		System.out.println("Please enter the model name: ");
		String model = scan.nextLine();
		
		// why so many loops? 
		// we never let the user see "error dump & exit" because of the scanner
		// instead we just keep looping until valid input is obtained.
		userLoop(this::getSpeed);
		userLoop(this::getRange);
		userLoop(this::getPrice);
		
		// if we made it this far, all the inputs have been validated
		airfield.addNewUserJet(type, model, speed, range, price);
		
		// in the unix tradition, a tool's success is reported with silence
		// however, most find this disturbing, therefore:
		System.out.println("\nJet Successfully added to fleet!!");
		
		return doneLooping;
	}
	
	private boolean getSpeed() {
		System.out.println("Please enter a decimal number for the speed: (MPH is assumed)");
		speed = getUserDouble();
		if (speed <= 0) {
			System.out.println("Speeds must be greater than zero in the form 123.45\n");
			return keepLooping;
		}
		return doneLooping;
	}
	
	private boolean getRange() {
		System.out.println("Please enter an integral value for the range: (mile units are assumed)");
		range = getUserInt();
		if (range <= 0) {
			System.out.println("Range must be greater that zero in the form 1234\n");
			return keepLooping;
		}
		return doneLooping;
	}
	
	private boolean getPrice() {
		System.out.println("Please enter the price of the jet in question: (USD is assumed)");
		price = getUserLong();
		if (price <= 0) {
			System.out.println("Price must be a whole number greater than zero.\n");
			return keepLooping;
		}
		return doneLooping;
	}
	
	private boolean removeJet() {
		System.out.println("~~ Jet Removal Screen ~~");
		
		int max = airfield.numberOfJets();
		
		// edge case!
		if (max == 0) {  
			System.out.println("Sorry, no more jets to remove. The airfield is empty!");
			return doneLooping;
		}
		airfield.listJets();
		System.out.println("\nPlease enter the number associated with the Jet to be removed: ");
		int rmv = getUserInt();
		
		if (rmv < 1 || rmv > max) {
			System.out.println("Oops! That number does not correspond to a jet. Please try again.");
			return keepLooping;
		}
		
		airfield.retireJet(rmv);
		System.out.println("\nJet successfully removed!");
		return doneLooping;
	}
	
	/*
	 * Helpers
	 */
	
	// general pattern for looping
	private void userLoop(BooleanSupplier looper) {
		boolean moreLoops;
		do {
			moreLoops = looper.getAsBoolean();
		} while (moreLoops);
	}
	
	// general pattern for scanning positive numbers
	private Number getUserNumber(BooleanSupplier hasNext, Supplier<Number> next) {
		if (hasNext.getAsBoolean()) {
			Number n = next.get();
			scan.nextLine(); // clear out extra inputs
			return n;
		}
		scan.nextLine();     // clear out result of wrong type
		return -1;           // signal for invalid
	}
	
	// concrete scanners for positive numbers
	// the .fooValue() methods are necessary, 
	// casting won't work because of the -1 invalid signal in getUserNumber
	private int getUserInt() { 
		return getUserNumber(scan::hasNextInt, scan::nextInt).intValue(); 
	}
	private long getUserLong() { 
		return getUserNumber(scan::hasNextLong, scan::nextLong).longValue(); 
	}
	private double getUserDouble() {
		return getUserNumber(scan::hasNextDouble, scan::nextDouble).doubleValue();
	}
}
