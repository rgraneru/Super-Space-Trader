package com.spacetrader.service.pilot;

import com.spacetrader.service.market.CreditsTrader;
import com.spacetrader.service.market.GoodsTrader;

public class Pilot implements CreditsTrader{
	private int combatSkill;
	private int credits;

	public Pilot(int startingCredits) {
		setCredits(startingCredits);
	}

	public void initialize(){
		setCombatSkill(10);
	}

	public int getCombatSkill() {
		return combatSkill;
	}

	public void setCombatSkill(int skill) {
		this.combatSkill = skill;
	}

	@Override
	public void setCredits(int credits) {
		this.credits = credits;
	}

	@Override
	public int getCredits() {
		return credits;
	}
	
	@Override
	public void addCredits(int addedCredits){
		setCredits(getCredits() + addedCredits);
	}
	
	@Override
	public void subTractCredits(int subtractedCredits) throws TradeException{
		if (subtractedCredits > getCredits()){
			throw new TradeException("You cannot subtract more credits than the pilot has. Tried to subtract "+subtractedCredits +" from "+getCredits());
		}
		setCredits(getCredits() - subtractedCredits); 
	}

	
}
