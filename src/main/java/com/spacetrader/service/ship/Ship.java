package com.spacetrader.service.ship;

import java.util.ArrayList;
import java.util.Random;

import com.spacetrader.service.pilot.Pilot;
import com.spacetrader.service.ship.exception.NoMoreRoomException;
import com.spacetrader.service.ship.exception.NoWeaponsException;
import com.spacetrader.service.weapon.LaserWeapon;
import com.spacetrader.service.weapon.Weapon;
import org.apache.log4j.Logger;

public class Ship {
	//ship values
	
	private int shieldStrength; //How powerful the shield is
	private int shieldRemaining; //How much shield is left
	private int hullStrength;
	private int hullRemaining;
	private int numberOfWeaponPods; //max number of weapons allowed
	private ArrayList<Weapon> weapons;
	private Pilot pilot;
	private Random randomNumberGenerator;
	
	protected ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	protected void setWeapons() {
		weapons = new ArrayList<Weapon>();
	}

	protected int getCargoSpace() {
		return cargoSpace;
	}



	protected void setCargoSpace(int cargoSpace) {
		this.cargoSpace = cargoSpace;
	}



	private int cargoSpace; //max cargospace
	
	protected Pilot getPilot() {
		return pilot;
	}

	protected void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public Ship() {
		initializeShip();
	}

	
	//should be read from a config file
	private void initializeShip(){
		setShieldRemaining(100);
		setShieldStrength(1);
		setNumberOfWeaponPods(1);
		setHullStrength(1);
		setHullRemaining(100);
		setWeapons(); //setting no weapons
		pilot = new Pilot();
		pilot.initialize();
		randomNumberGenerator = new Random();
	}


	
	

	public void addWeapon(Weapon weapon) throws NoMoreRoomException{
		ArrayList<Weapon> weapons = getWeapons();
		int numberOfWeapons = weapons.size();
		int numberOfWeaponPods = getNumberOfWeaponPods();
		
		if (numberOfWeapons == numberOfWeaponPods){
			throw new NoMoreRoomException("There is no more weapon pods free on the ship.", null);
		}
		else if (numberOfWeapons > numberOfWeaponPods){
			throw new NoMoreRoomException("I suddenly have more weapons than allowed. Something has gone wrong.", null);			
		}
		else{
			weapons.add(weapon);
		}
	}



	protected int getShieldStrength() {
		return shieldStrength;
	}



	protected void setShieldStrength(int shieldStrength) {
		this.shieldStrength = shieldStrength;
	}



	protected int getShieldRemaining() {
		return shieldRemaining;
	}



	protected void setShieldRemaining(int shieldRemaining) {
		this.shieldRemaining = shieldRemaining;
	}



	protected int getHullStrength() {
		return hullStrength;
	}



	protected void setHullStrength(int hullStrength) {
		this.hullStrength = hullStrength;
	}



	protected int getHullRemaining() {
		return hullRemaining;
	}



	protected void setHullRemaining(int hullRemaining) {
		this.hullRemaining = hullRemaining;
	}



	protected int getNumberOfWeaponPods() {
		return numberOfWeaponPods;
	}



	protected void setNumberOfWeaponPods(int numberOfWeaponPods) {
		this.numberOfWeaponPods = numberOfWeaponPods;
	}



	public void fireWeapons(Ship enemyShip) throws NoWeaponsException, ProbabilityOutOfBoundsException{
		ArrayList<Weapon> weapons = getWeapons();
		int numOfWeapons = weapons.size();
		if (numOfWeapons == 0){
			throw new NoWeaponsException("This ship doesn't have any weapons and cannot fire");
		}
		//fire all weapons of the ship
		
		Weapon weapon;
		boolean hit;
		for (int i=0;i<weapons.size();i++){
			weapon = weapons.get(i);
			hit = enemyShip.shotAtBy(this.pilot.getSkill(), weapon);			
		}						
	}

	/**
	 * Invoked by the ship being fired at
	 * @param pilotSkill
	 * @param weapon
	 * @return true if hit
	 * @throws ProbabilityOutOfBoundsException 
	 */
	private boolean shotAtBy(int enemyPilotSkill, Weapon weapon) throws ProbabilityOutOfBoundsException {
		int myPilotSkill = this.pilot.getSkill();
		int hitProbability;
		boolean hit = false;
		
		//skilldifference is 10*percent chance of hitting from an average of 50%
		
		int skillDifference = getSkillDifference(enemyPilotSkill, myPilotSkill);
		hitProbability = getHitProbability(skillDifference);
		hit = hitOrNot(hitProbability);
		
		if (hit){
			if (this.getShieldRemaining() > weapon.getStrength()){
				this.lowerShield(weapon);
			}
				
			else if (this.getShieldRemaining() == 0){
				this.lowerHull(weapon);
			}
			else{
				this.lowerShieldAndHull(weapon);
			}
		}
		
		return hit;
		
	}

	private void lowerShieldAndHull(Weapon weapon) {
		
	}

	private void lowerHull(Weapon weapon) {
		int weaponStrength = weapon.getStrength();
		this.hullRemaining -= weaponStrength;
		
	}

	private void lowerShield(Weapon weapon) {
		int weaponStrength = weapon.getStrength();
		this.shieldRemaining -= weaponStrength;
	}

	/**
	 * Returning a probability between 10 and 90 percent. Default when 2 have the same skill is 50%
	 * @param skillDifference
	 * @return
	 * @throws ProbabilityOutOfBoundsException
	 */
	protected int getHitProbability(int skillDifference) throws ProbabilityOutOfBoundsException{
		int returnPercent;
		if (skillDifference >= 4){
			returnPercent = 90;
		}
		else if (skillDifference <=-4){
			returnPercent = 10;
		}
		else{
			returnPercent = (5+skillDifference)*10;
		}
		
		return returnPercent;
	}

	protected int getSkillDifference(int enemyPilotSkill, int myPilotSkill) {
		int skillDifference = enemyPilotSkill - myPilotSkill;		
		return skillDifference;
	}
	
	protected boolean hitOrNot(int probability){
		int randomNumber = randomNumberGenerator.nextInt(100);

		System.out.println("got random number "+randomNumber + "and probability was "+probability);
		if (randomNumber <= probability){
			//System.out.println("returning true");
			return true;
		}
		else{
			//System.out.println("returning false");
			return false;
		}
	}
	
	
	
}
