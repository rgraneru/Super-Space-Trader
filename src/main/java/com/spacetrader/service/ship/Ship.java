package com.spacetrader.service.ship;

import java.util.ArrayList;
import java.util.Random;

import com.spacetrader.service.pilot.Pilot;
import com.spacetrader.service.shield.Shield;
import com.spacetrader.service.shield.ShieldException;
import com.spacetrader.service.ship.exception.NoMoreRoomException;
import com.spacetrader.service.ship.exception.NoWeaponsException;
import com.spacetrader.service.ship.storagebay.StorageBay;
import com.spacetrader.service.weapon.Weapon;

public abstract class Ship {
	private final int NULLINT = -1;
	//ship values
	private Shield shield;
	private HullStrengthType hullStrength = null;
	private int hullRemaining = NULLINT;
	private int numberOfWeaponPods; //max number of weapons allowed
	private ArrayList<Weapon> weapons;
	private Pilot pilot;
	private StorageBay storageBay;
	private Random randomNumberGenerator;
//	private Logger logger;

	abstract protected void initialize();
	
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	public void initiateWeaponsArray() {
		weapons = new ArrayList<Weapon>();
	}

//	protected int getCargoSpace() {
//		return cargoSpace;
//	}
//
//
//
//	protected void setCargoSpace(int cargoSpace) {
//		this.cargoSpace = cargoSpace;
//	}
	
	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	public Ship() {
//		logger = Logger.getLogger(this.getClass());
//		PropertyConfigurator.configure("log4j.properties");		
		initializeRandomNumberGenerator();
		weapons = new ArrayList<Weapon>();
	}

	
	//should be read from a config file
	private void initializeRandomNumberGenerator(){
		randomNumberGenerator = new Random();
	}

	public void addWeapon(Weapon weapon) throws NoMoreRoomException{
//		logger.debug("Adding weapon: "+weapon.getWeaponType() + " to the weaponArray");
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


	
	
	

	protected HullStrengthType getHullStrength() {
		return hullStrength;
	}



	protected void setHullStrength(HullStrengthType hullStrength) {
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
//		logger.debug("Firing weapons at opponent");
		ArrayList<Weapon> weapons = getWeapons();
		int numOfWeapons = weapons.size();
		if (numOfWeapons == 0){
			throw new NoWeaponsException("This ship doesn't have any weapons and cannot fire");
		}
		//fire all weapons of the ship		
		Weapon weapon;
		for (int i=0;i<weapons.size();i++){
			weapon = weapons.get(i);
//			logger.debug("Firing weapon: "+weapon.getWeaponType());
			enemyShip.shotAtBy(this.pilot.getCombatSkill(), weapon);			
		}						
	}

	private void shotAtBy(int attackerPilotSkill, Weapon weapon) throws ProbabilityOutOfBoundsException, ShieldException {
		int defenderPilotSkill = this.pilot.getCombatSkill();
		int hitProbability;
		boolean hit = false;
		
		//skilldifference is 10*percent chance of hitting from an average of 50%
//		logger.debug("The defenders pilot skill is "+defenderPilotSkill +". The attackers pilot skill is "+attackerPilotSkill);
		int skillDifference = getSkillDifference(attackerPilotSkill, defenderPilotSkill);
//		logger.debug("Skilldifference between pilots are "+skillDifference);
		hitProbability = getHitProbability(skillDifference);
//		logger.debug("Hitting probability is "+hitProbability);
		hit = hitOrNot(hitProbability);
//		logger.debug("The result of hitOrNot was "+hit);
		
		if (hit){
			this.lowerShieldAndMaybeHull(weapon);
		}		
	}

	void lowerShieldAndMaybeHull(Weapon weapon) throws ShieldException {
		int hullDamageTaken = 0;
		hullDamageTaken = this.shield.getStruckBy(weapon);
//		logger.debug("Lowering hulldamage by "+hullDamageTaken);
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

		if (randomNumber <= probability){
			return true;
		}
		else{
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
//		logger.debug("adding shield "+" with remaining energy: "+shield.getRemiainingShieldEnergy());		
		this.shield = shield;
	}
		
	public boolean isDestroyed() throws ShieldException{
		if (getHullRemaining() == 0){
			return true;
		}
		else{
			return false;
		}
	}

	//used by testing
	Shield getShield() throws ShieldException{
		if (this.shield == null){
			throw new ShieldException("Shield is null");
		}
		return this.shield;
	}

	public void setStorageBay(StorageBay storageBay) {
		this.storageBay = storageBay;
	}

	public StorageBay getStorageBay() {
		return storageBay;
	}	
}
