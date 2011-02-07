package com.spacetrader.service.market;

import com.spacetrader.service.pilot.TradeException;

public interface CreditsTrader {
	public void setCredits(int credits);
	public int getCredits();
	public void addCredits(int addedCredits);
	public void subTractCredits(int subtractedCredits) throws TradeException;
}
