package com.spacetrader.cli;

import com.spacetrader.service.pilot.Pilot;
import com.spacetrader.service.shield.Shield;
import com.spacetrader.service.shield.ShieldException;
import com.spacetrader.service.ship.Hawk;
import com.spacetrader.service.ship.ProbabilityOutOfBoundsException;
import com.spacetrader.service.ship.Ship;
import com.spacetrader.service.ship.Sparrow;
import com.spacetrader.service.ship.exception.NoMoreRoomException;
import com.spacetrader.service.ship.exception.NoWeaponsException;
import com.spacetrader.service.weapon.LaserWeapon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author roarg
 */
public class SpaceTraderCLI {
    HashMap<String, Command> commands = new HashMap<String, Command>();
    Ship ship;
    Ship enemyShip;
    Pilot pilot;


    public SpaceTraderCLI() throws NoMoreRoomException {
        ship = new Hawk();
        pilot = new Pilot(100);
        pilot.initialize();
        ship.setPilot(pilot);

        LaserWeapon laserWeapon = new LaserWeapon();
        ship.addWeapon(laserWeapon);

        setupEnemyShip();

        initiateCommands();
    }



    public void run() throws IOException, Exception {
        String input = "";
        InputStreamReader converter = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(converter);
        while (!input.equals("exit")){
            input = in.readLine();
            parseInput(input);
        }
    }

    private void parseInput(String input) throws Exception {
        if (commands.containsKey(input)){
            Command command = commands.get(input);
            command.execute();
        }
    }

    private void initiateCommands(){
        commands.put("help", new DefaultCommand(ship) {
            @Override
            public void execute() {
                System.out.println("System commands:");
                Set<String> keys = commands.keySet();
                for (Iterator<String> it = keys.iterator(); it.hasNext();) {
                    String commandString = it.next();
                    System.out.println(commandString);
                }
            }
        });

        commands.put("money", new DefaultCommand(ship){
            @Override
            public void execute(){
                System.out.println("Your amount of credits is: "+ship.getPilot().getCredits());
            }
        });
        commands.put("stats", new DefaultCommand(ship) {

            @Override
            public void execute() {
                System.out.println("Your stats:");
                System.out.println("CombatSkills: " + ship.getPilot().getCombatSkill());
            }
        });
        commands.put("shot", new DefaultCommand(ship) {

            @Override
            public void execute() throws Exception {
                ship.fireWeapons(enemyShip);
            }
        });
            
        
    }

    private void setupEnemyShip() throws ShieldException {
        enemyShip = new Sparrow();
        Pilot enemyPilot = new Pilot(50);
        enemyShip.setPilot(enemyPilot);
        enemyShip.addShield(new Shield());
    }




}
