package com.spacetrader.service.ship;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.spacetrader.service.pilot.Pilot;
import com.spacetrader.service.shield.Shield;
import com.spacetrader.service.shield.ShieldException;
import com.spacetrader.service.ship.exception.NoMoreRoomException;
import com.spacetrader.service.ship.exception.NoWeaponsException;
import com.spacetrader.service.weapon.Weapon;

public class Ship {
	private final int NULLINT = -1;
	//ship values
	private Shield shield;
	private int hullStrength = NULLINT;
	private int hullRemaining = NULLINT;
	private int numberOfWeaponPods; //max number of weapons allowed
	private ArrayList<Weapon> weapons;
	private Pilot pilot;
	private Random randomNumberGenerator;
	private Logger logger;
	
	protected ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	protected void initiateWeaponsArray() {
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
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("log4j.properties");		
		initializeShip();
	}

	
	//should be read from a config file
	private void initializeShip(){
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



	protected int getShieldStrength() throws ShieldException {
		return this.shield.getShieldStrength();
	}


//
//	protected void setShieldStrength(int shieldStrength) {
//		this.shield.setShieldStrength(shieldStrength);
//	}



	protected int getShieldRemaining() throws ShieldException {
		if (this.shield == null){
			throw new ShieldException("no shield assigned to ship");
		}
		return this.shield.getRemiainingShieldEnergy();
	}



//	protected void setShieldRemaining(int shieldRemaining) {
//		this.shieldRemaining = shieldRemaining;
//	}


	
	
	

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



	public void fireWeapons(Ship enemyShip) throws NoWeaponsException, ProbabilityOutOfBoundsException, ShieldException{
		ArrayList<Weapon> weapons = getWeapons();
		int numOfWeapons = weapons.size();
		if (numOfWeapons == 0){
			throw new NoWeaponsException("This ship doesn't have any weapons and cannot fire");
		}
		//fire all weapons of the ship		
		Weapon weapon;
		for (int i=0;i<weapons.size();i++){
			weapon = weapons.get(i);
			enemyShip.shotAtBy(this.pilot.getSkill(), weapon);			
		}						
	}

	/**
	 * Invoked by the ship being fired at
	 * @param pilotSkill
	 * @param weapon
	 * @return true if hit
	 * @throws ProbabilityOutOfBoundsException 
	 * @throws ShieldException 
	 */
	private boolean shotAtBy(int enemyPilotSkill, Weapon weapon) throws ProbabilityOutOfBoundsException, ShieldException {
		int myPilotSkill = this.pilot.getSkill();
		int hitProbability;
		boolean hit = false;
		
		//skilldifference is 10*percent chance of hitting from an average of 50%
		
		int skillDifference = getSkillDifference(enemyPilotSkill, myPilotSkill);
		hitProbability = getHitProbability(skillDifference);
		hit = hitOrNot(hitProbability);
		
		if (hit){
			this.lowerShieldAndMaybeHull(weapon);
		}
		
		return hit;
		
	}

	void lowerShieldAndMaybeHull(Weapon weapon) throws ShieldException {
		//System.out.println("resourcebundle"+logger.getResourceBundle().toString());
		int hullDamageTaken = 0;
		hullDamageTaken = this.shield.getStruckBy(weapon);
		if (hullDamageTaken > getHullRemaining()){
			setHullRemaining(0);//destroyed
		}
		else{
			lowerHullRemaining(hullDamageTaken);
		}
	}

	private void lowerHullRemaining(int hullDamage) {
		setHullRemaining(this.hullRemaining - hullDamage);
	}

	/**
	 * Returning a probability between 10 and 90 percent. Default when 2 have the same skill is 50%
	 * @param skillDifference
	 * @return
	 * @throws ProbabilityOutOfBoundsException
	 */
	int getHitProbability(int skillDifference) throws ProbabilityOutOfBoundsException{
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

	int getSkillDifference(int enemyPilotSkill, int myPilotSkill) {
		int skillDifference = enemyPilotSkill - myPilotSkill;		
		return skillDifference;
	}
	
	boolean hitOrNot(int probability){
		int randomNumber = randomNumberGenerator.nextInt(100);

		System.out.print("hotOrNot got random number "+randomNumber + "and probability was "+probability+". This was a ");
		if (randomNumber <= probability){
			System.out.print("hit\n");
			return true;
		}
		else{
			System.out.print("miss\n");
			return false;
		}
	}

	public void addShield(Shield shield) throws ShieldException {
		if (shield == null){
			throw new ShieldException("Shield is null (not initialized");
		}
		if (this.shield != null){
			throw new ShieldException("This ship already has a shield. It can only have one");
		}
		
		this.shield = shield;
	}
	
	//used by testing
	Shield getShield() throws ShieldException{
		if (this.shield == null){
			throw new ShieldException("Shield is null");
		}
		return this.shield;
	}
	
	public boolean isDestroyed() throws ShieldException{
		if (getHullRemaining() == 0){
			return true;
		}
		else{
			return false;
		}
	}

	
	
	
}
