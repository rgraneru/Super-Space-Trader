/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.spacetrader.cli;

import com.spacetrader.service.ship.Ship;

/**
 *
 * @author roarg
 */
public abstract class DefaultCommand implements Command{
    private Ship ship;

     DefaultCommand(Ship ship) {
        this.ship = ship;
    }

}
