package com.spacetrader.service.market;

import java.util.HashMap;

import com.spacetrader.service.item.Item;
import com.spacetrader.service.pilot.TradeException;
import com.spacetrader.service.ship.storagebay.StorageException;


/**
 * Superclass for all classes that is able to trade in items, weapons and credits
 * @author roarg
 *
 */
public class GoodsTrader {
	protected int size;
	protected HashMap<String, Item> contents;
	
	public int getSize() {
		return size;
	}
	
	public GoodsTrader(){
	}
	
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
