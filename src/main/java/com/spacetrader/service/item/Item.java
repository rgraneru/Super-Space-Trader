package com.spacetrader.service.item;

public class Item {

	private String name;
	private int size;
	
	public Item(String name, int size){
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return this.size;
	}

}
