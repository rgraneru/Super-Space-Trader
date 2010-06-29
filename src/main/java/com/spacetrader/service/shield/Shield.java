package com.spacetrader.service.shield;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.spacetrader.service.weapon.Weapon;
import com.spacetrader.service.weapon.WeaponType;

public class Shield {
	private final int NULLINT = -1; 
	ShieldType shieldType;
	private int shieldStrength = NULLINT;
	private int remiainingShieldEnergy = NULLINT;
	private Logger logger;
	
	public Shield(){
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("log4j.properties");		
	}
	
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
		logger.debug("Shield getting struck by weapon with strength "+weaponStrength+" and remaining shield energy is "+getRemiainingShieldEnergy());
				
		if (getRemiainingShieldEnergy() == NULLINT){
			throw new ShieldException("cannot shoot at an uninitialized shield");
		}
		else if (getRemiainingShieldEnergy() > weaponStrength){
			setRemiainingShieldEnergy(getRemiainingShieldEnergy() - weaponStrength);
		}
		else{//hit will drain shields completely, and surplus damage might get through to the hull.
			damageToHull = weaponStrength - getRemiainingShieldEnergy();
			setRemiainingShieldEnergy(0);
		}
		
		return damageToHull;
		
	}
}
