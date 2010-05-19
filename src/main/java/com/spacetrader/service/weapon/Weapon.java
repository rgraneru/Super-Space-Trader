package com.spacetrader.service.weapon;

abstract public class Weapon {
	protected WeaponType weaponType;
	protected int weaponStrength;
	
	public abstract int getStrength();
	
}
