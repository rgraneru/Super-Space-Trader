package com.spacetrader.service.weapon;

public class LaserWeapon extends Weapon {

	public LaserWeapon() {
		weaponType = WeaponType.LASER;
		weaponStrength = 20;
	}

	@Override
	public int getStrength() {
		return this.weaponStrength;
	}

}
