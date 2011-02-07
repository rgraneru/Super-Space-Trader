package com.spacetrader.service.market.tradeable;

public abstract class Tradeable {
	String name;
	int defaultPrice;
	int spaceRequired;
	boolean legal = true;
	boolean alive = false; //not using alive yet
	
	public int getDefaultPrice() {
		return defaultPrice;
	}
	public int getSpaceRequired() {
		return spaceRequired;
	}
	public boolean isLegal() {
		return legal;
	}
	public boolean isAlive() {
		return alive;
	}
	public String getName() {
		return name;
	}	
	public void setLegal(boolean legal) {
		this.legal = legal;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDefaultPrice(int defaultPrice) {
		this.defaultPrice = defaultPrice;
	}
	public void setSpaceRequired(int spaceRequired) {
		this.spaceRequired = spaceRequired;
	}
	
	
//	Tradeable(String name, int defaultPrice, int spaceRequired){
//		this.name = name;
//		this.defaultPrice = defaultPrice;
//		this.spaceRequired = spaceRequired;
//	}
	
}
