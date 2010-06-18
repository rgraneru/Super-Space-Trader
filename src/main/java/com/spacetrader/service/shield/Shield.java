package com.spacetrader.service.shield;

import com.spacetrader.service.weapon.Weapon;
import com.spacetrader.service.weapon.WeaponType;

public class Shield {
	private final int NULLINT = -1; 
	ShieldType shieldType;
	private int shieldStrength = NULLINT;
	private int remiainingShieldEnergy = NULLINT;
	
	public ShieldType getShieldType() throws ShieldException {
		if (shieldType == null){
			throw new ShieldException("Shield type is not set");
		}
		return shieldType;
	}
	public void setShieldType(ShieldType shieldType) {
		this.shieldType = shieldType;
	}
	public int getShieldStrength() throws ShieldException {
		if (shieldStrength == NULLINT){
			throw new ShieldException("Shield strength is not set");
		}
		return shieldStrength;
	}
	public void setShieldStrength(int shieldStrength) {
		this.shieldStrength = shieldStrength;
	}
	public void setRemiainingShieldEnergy(int remiainingShieldEnergy) {
		this.remiainingShieldEnergy = remiainingShieldEnergy;
	}
	public int getRemiainingShieldEnergy() throws ShieldException {
		if (remiainingShieldEnergy == NULLINT){
			throw new ShieldException("Shield has no energy");
		}
		return remiainingShieldEnergy;
	}
	public int getStruckBy(Weapon weapon) throws ShieldException {
		int weaponStrength = weapon.getWeaponStrength();
		int damageToHull = 0;
//		System.out.println("weaponStrength: "+weaponStrength);
//		System.out.println("remainingShieldEnergy: "+this.remiainingShieldEnergy);
				
		if (this.remiainingShieldEnergy == NULLINT){
//			System.out.println("not set remainingshield");
			throw new ShieldException("cannot shoot at an uninitialized shield");
		}
		else if (this.remiainingShieldEnergy > weaponStrength){
//			System.out.println("draing a shield with energy "+this.remiainingShieldEnergy+" bringing it down with "+weaponStrength);
			this.remiainingShieldEnergy -= weaponStrength;
		}
		else{//hit will drain shields completely, and surplus damage might get through to the hull.
//			System.out.println("Remaining shield energy of "+this.remiainingShieldEnergy+" is less than "+weaponStrength+" draining the rest of the shield and returning hulldamage");
			damageToHull = weaponStrength - this.remiainingShieldEnergy;
			this.remiainingShieldEnergy = 0;
		}
		
		return damageToHull;
		
	}
}
