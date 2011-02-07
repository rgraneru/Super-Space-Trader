package com.spacetrader.service.ship;

import com.spacetrader.service.ship.storagebay.StorageBay;

public class Sparrow extends Ship {
	private int sizeOfCargoSpace = 100;
	private int numberOfWeaponPods = 1;
	
	
	public Sparrow() {
		super();
		initialize();
	}

	@Override
	protected void initialize() {
		setNumberOfWeaponPods(numberOfWeaponPods);
		setHullStrength(sizeOfCargoSpace);
		setHullRemaining(100);
		initiateWeaponsArray(); //setting no weapons		
		StorageBay storageBay = new StorageBay(sizeOfCargoSpace);
		setStorageBay(storageBay);
	}

}
