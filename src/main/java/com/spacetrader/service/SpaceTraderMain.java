package com.spacetrader.service;

import com.spacetrader.cli.SpaceTraderCLI;
import java.io.IOException;

public class SpaceTraderMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException, Exception {
            SpaceTraderCLI spaceTraderCLI = new SpaceTraderCLI();
            spaceTraderCLI.run();
//		new Ship();

	}

}
