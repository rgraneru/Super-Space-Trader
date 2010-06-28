package com.spacetrader.service.weapon;

abstract public class Weapon {
	protected WeaponType weaponType;
	protected int weaponStrength;
	
	public Weapon(){
		setDefaultValues();
	}
	
	public WeaponType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public int getWeaponStrength() {
		return weaponStrength;
	}

	public void setWeaponStrength(int weaponStrength) {
		this.weaponStrength = weaponStrength;
	}

	abstract public void setDefaultValues();
}
