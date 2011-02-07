package com.spacetrader.service.ship.storagebay;

import java.util.HashMap;

import com.spacetrader.service.item.Item;
import com.spacetrader.service.market.GoodsTrader;

public class StorageBay extends GoodsTrader{
	public StorageBay(int size) {
		this.size = size;
		this.contents = new HashMap<String, Item>();
	}
//	public String listContents(){
//		//TODO: implement this
//	}
	
}
