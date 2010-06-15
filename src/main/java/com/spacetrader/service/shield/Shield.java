package com.spacetrader.service.shield;

import com.spacetrader.service.weapon.Weapon;

public class Shield {
	ShieldType shieldType;
	private int shieldStrength;
	private int remiainingShieldEnergy;
	
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
		if (shieldStrength == 0){
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
		if (remiainingShieldEnergy == 0){
			throw new ShieldException("Shield has no energy");//can a shield have 0 energy?
		}
		return remiainingShieldEnergy;
	}
	public void getStruckBy(Weapon weapon) {
		// TODO Auto-generated method stub
		
	}

}
