package com.spacetrader.service.weapon;

public class LaserWeapon extends Weapon {

	public LaserWeapon() {
	}

	@Override
	public void setDefaultValues() {
		weaponType = WeaponType.LASER;
		weaponStrength = 20;
	}

}
