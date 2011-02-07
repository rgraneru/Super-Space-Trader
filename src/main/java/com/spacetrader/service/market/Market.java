package com.spacetrader.service.market;

import java.util.HashMap;

import com.spacetrader.service.item.Item;
import com.spacetrader.service.market.tradeable.Spice;
import com.spacetrader.service.market.tradeable.Tradeable;
import com.spacetrader.service.pilot.TradeException;

public class Market extends GoodsTrader implements CreditsTrader{
	private int credits; //number of credits currently available
	HashMap<Item, Integer> tradeableGoods;
	public Market(int startingCredits) {
		tradeableGoods = new HashMap<Item, Integer>();
		populateMarket(MarketType.POOR);
		setCredits(credits);
		this.size = Integer.MAX_VALUE; //no space restriction in a market
	}
	
	private void populateMarket(MarketType poor) {
		//not using parameter yet
		
		Tradeable spice = new Spice();
//		Tradeable liqour = new Liquor();
		
		
		
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
