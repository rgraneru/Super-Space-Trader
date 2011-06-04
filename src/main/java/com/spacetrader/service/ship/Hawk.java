package com.spacetrader.service.ship;

import com.spacetrader.service.ship.storagebay.StorageBay;

public class Hawk extends Ship {
	private int sizeOfCargoSpace = 30;
	private int numberOfWeaponPods = 3;
	private HullStrengthType hullStrength = HullStrengthType.LIGHT;
	
	public Hawk() {
		super();
		initialize();
	}

	@Override
	protected void initialize() {
		setNumberOfWeaponPods(numberOfWeaponPods);
		setHullStrength(hullStrength);
		setHullRemaining(100);
		initiateWeaponsArray(); //setting no weapons		
		StorageBay storageBay = new StorageBay(sizeOfCargoSpace);
		setStorageBay(storageBay);
	}

}
