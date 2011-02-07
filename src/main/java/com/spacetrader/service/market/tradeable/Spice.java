package com.spacetrader.service.market.tradeable;

public class Spice extends Tradeable {

//	Spice(String name, int defaultPrice, int spaceRequired) {
//		super(name, defaultPrice, spaceRequired);
//	}

//	public Spice(String name, int defaultPrice, int spaceRequired){
//		super(name, defaultPrice, spaceRequired);
//	}
//	
	public Spice(){
		String name = "Exotic Spices";
		int defaultPrice = 35;
		int spaceRequired = 2;
		setName(name);
		setDefaultPrice(defaultPrice);
		setSpaceRequired(spaceRequired);
	}
	

	
}
