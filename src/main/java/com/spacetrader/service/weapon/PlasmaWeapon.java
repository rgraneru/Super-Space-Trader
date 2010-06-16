package com.spacetrader.service.weapon;

public class PlasmaWeapon extends Weapon {
	
	
	public PlasmaWeapon() {
	}

	@Override
	public void setDefaultValues() {
		weaponType = WeaponType.PLASMA;
		weaponStrength = 40;
	}

}
