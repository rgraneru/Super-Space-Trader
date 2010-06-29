package com.spacetrader.service.ship.storagebay;

import java.util.HashMap;

import com.spacetrader.service.item.Item;

public class StorageBay {
	private int size;
	private HashMap<String, Item> contents;
	
	public StorageBay() {
		contents = new HashMap<String, Item>();
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	public int getSize() {
		return size;
	}
	
//	public String listContents(){
//		//TODO: implement this
//	}
	
	public void pushItem(Item item) throws StorageException{
		if (isItRoomFor(item)){
			contents.put(item.getName(), item);
		}
		else{
			throw new StorageException("Not enough room left in storage for "+item.getName());
		}
	}
	
	public Item popItem(String itemName) throws StorageException{
		if (contents.containsKey(itemName)){
			return contents.get(itemName);
		}
		else{
			throw new StorageException("Could not find "+itemName+" in the storage");
		}
	}
	
	private boolean isItRoomFor(Item item){
		if (item.getSize() <= this.getSize()){
			return true;
		}
		else{
			return false;
		}
	}
}
