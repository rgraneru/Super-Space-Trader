package com.spacetrader.service.market;

import com.spacetrader.service.pilot.TradeException;


/**
 * Superclass for all classes that is able to trade in items, weapons and credits
 * @author roarg
 *
 */
public class Tradeable {
	private int credits; //number of credits currently available
	
	public Tradeable(int startingCredits){
		setCredits(startingCredits);
	}
	
	public void addCredits(int addedCredits){
		setCredits(getCredits() + addedCredits);
	}
	
	public void subTractCredits(int subtractedCredits) throws TradeException{
		if (subtractedCredits > getCredits()){
			throw new TradeException("You cannot subtract more credits than the pilot has. Tried to subtract "+subtractedCredits +" from "+getCredits());
		}
		setCredits(getCredits() - subtractedCredits); 
	}

	private void setCredits(int credits) {
		this.credits = credits;
	}



	public int getCredits() {
		return credits;
	}

}
