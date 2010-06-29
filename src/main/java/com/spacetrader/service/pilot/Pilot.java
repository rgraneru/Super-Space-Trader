package com.spacetrader.service.pilot;

import com.spacetrader.service.market.Tradeable;

public class Pilot extends Tradeable{

	public Pilot(int startingCredits) {
		super(startingCredits);
	}

	private int combatSkill;
	
	public void initialize(){
		setCombatSkill(10);
	}

	public int getCombatSkill() {
		return combatSkill;
	}

	public void setCombatSkill(int skill) {
		this.combatSkill = skill;
	}

	
}
