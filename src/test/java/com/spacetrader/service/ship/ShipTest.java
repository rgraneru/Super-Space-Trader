package com.spacetrader.service.ship;


import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.spacetrader.service.pilot.Pilot;
import com.spacetrader.service.pilot.TradeException;
import com.spacetrader.service.shield.Shield;
import com.spacetrader.service.shield.ShieldException;
import com.spacetrader.service.shield.ShieldType;
import com.spacetrader.service.ship.exception.NoMoreRoomException;
import com.spacetrader.service.ship.exception.NoWeaponsException;
import com.spacetrader.service.weapon.LaserWeapon;
import com.spacetrader.service.weapon.Weapon;

public class ShipTest {
	Ship ship;
	Ship enemyShip;
	private Logger logger;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure("log4j.properties");		

		this.ship = new Ship();
		this.enemyShip = new Ship();
		initiateMockShip(this.ship);
		initiateMockShip(this.enemyShip);
	}

	private void initiateMockShip(Ship mockShip) {
		mockShip.setNumberOfWeaponPods(1);
		mockShip.setHullStrength(1);
		mockShip.setHullRemaining(100);
		mockShip.initiateWeaponsArray(); //setting no weapons
		Pilot pilot = new Pilot();
		pilot.initialize();
		mockShip.setPilot(pilot);
		Shield shield = new Shield();
		shield.setRemiainingShieldEnergy(100);
		shield.setShieldStrength(1);
		shield.setShieldType(ShieldType.LIGHT);
		try{
			mockShip.addShield(shield);
		}
		catch(ShieldException e){
			System.out.println(e);
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAddWeapon() throws NoMoreRoomException{
			ship.addWeapon(new LaserWeapon());
		
			boolean caughtException = false;
			try{
				ship.addWeapon(new LaserWeapon());
			}
			catch(NoMoreRoomException e){
				caughtException = true;
			}
			Assert.assertEquals("expecting an exception when adding a 2nd weapon", true, caughtException);
	}
	@Test
	public void testFireWeapons() throws NoWeaponsException, NoMoreRoomException, ProbabilityOutOfBoundsException, ShieldException{
		boolean caughtException = false;
		try{
			ship.fireWeapons(enemyShip);
		}
		catch(NoWeaponsException e){
			caughtException = true;
		}
		Assert.assertTrue("expected the first weaponadd to throw NoWeaponsException", caughtException);
		
		ship.addWeapon(new LaserWeapon());
		
		ship.fireWeapons(enemyShip);
				
	}
	
	@Test
	public void testGetPilotSkill(){
		int expectedStartingSkill = 10;
		Pilot pilot = ship.getPilot();
		int pilotSkill = pilot.getCombatSkill();
		Assert.assertEquals("Expected starting pilotskill to be "+expectedStartingSkill, expectedStartingSkill, pilotSkill);
	}

	@Test
	public void testGetHitProbability() throws ProbabilityOutOfBoundsException{
		int skillDiff10min10 = this.ship.getSkillDifference(10, 10);
		Assert.assertEquals(0, skillDiff10min10);
		int hit50 = this.ship.getHitProbability(skillDiff10min10);
		Assert.assertEquals(50, hit50);

		int skillDiff10min6 = this.ship.getSkillDifference(10, 6);
		Assert.assertEquals(4, skillDiff10min6);	
		int hit90 = this.ship.getHitProbability(skillDiff10min6);
		Assert.assertEquals(90, hit90);

		int skillDiff7min10 = this.ship.getSkillDifference(7, 10);
		Assert.assertEquals(-3, skillDiff7min10);
		int hit20 = this.ship.getHitProbability(skillDiff7min10);
		Assert.assertEquals(20, hit20);
		
		int hit10rounded = this.ship.getHitProbability(-8);
		Assert.assertEquals(10, hit10rounded);
		
		int hit90rounded = this.ship.getHitProbability(9);
		Assert.assertEquals(90, hit90rounded);
	}
	
	//@Test
	public void testGetHitOrNot(){
		for (int i=0; i<20;i++){
			boolean resultHit = this.ship.hitOrNot(50);
			logger.debug("hit was "+resultHit);
		}
		
		for (int i=0; i<20;i++){
			boolean resultHit = this.ship.hitOrNot(10);
			logger.debug("hit was "+resultHit);
		}

		for (int i=0; i<20;i++){
			this.ship.hitOrNot(90);
		}
	}
	
	@Test
	public void testGetHitLowerShield(){
		Weapon weapon = new LaserWeapon();
		weapon.setDefaultValues();

		Ship emptyShip = new Ship();
		boolean caughtException = false;
		try{
			emptyShip.getShield().getStruckBy(weapon);	
		}
		catch (ShieldException e){
			//System.out.println(e.getMessage());
			caughtException = true;
		}
		Assert.assertTrue("Expected to get a ShieldException stating that the shield is null", caughtException);
		
		caughtException = false;
		Shield uninitializedShield = new Shield();
		try {
			emptyShip.addShield(uninitializedShield);
			emptyShip.getShield().getStruckBy(weapon);
		} catch (ShieldException e) {
			//System.out.println(e.getMessage());
			caughtException = true;
		}
		Assert.assertTrue("Supposed to get a ShieldExeption saying that the shield is uninitialized", caughtException);
		
		
		int shieldRemainingBeforeHit = -1;
		int shieldRemainingAfterHit = -1;
		try{
			shieldRemainingBeforeHit = ship.getShieldRemaining();
			ship.getShield().getStruckBy(weapon);
			shieldRemainingAfterHit = ship.getShieldRemaining();
		}
		catch(Exception e){
			System.out.println(e);
			Assert.assertFalse("Caught unexpected exception: "+e.getMessage(), true);
		}
	
		Assert.assertTrue("Shield strenghtRemaining is supposed to be less after a hit", shieldRemainingBeforeHit > shieldRemainingAfterHit);
		
	}
	
	@Test
	public void testGetHitLowerShieldAndHull(){
		Weapon weapon = new LaserWeapon();
		weapon.setDefaultValues();
		Ship damagedShip = new Ship();
		Shield damagedShield = new Shield();
		damagedShield.setRemiainingShieldEnergy(5);
		damagedShield.setShieldType(ShieldType.LIGHT);
		damagedShield.setShieldStrength(1);
		int hullDamage = 0;
		
		try {
			damagedShip.addShield(damagedShield);
			hullDamage = damagedShip.getShield().getStruckBy(weapon);
		
		
		} catch (ShieldException e) {
			e.printStackTrace();
			Assert.assertFalse("unexpected exception: "+e.getMessage(), true);
		}
		
		Assert.assertEquals("Did not get expected hulldamage", 15, hullDamage);
		
		hullDamage = 0;
		try {
			damagedShip.getShield().setRemiainingShieldEnergy(0);
			hullDamage = damagedShip.getShield().getStruckBy(weapon);
		} catch (ShieldException e) {
			Assert.assertFalse("unexpected exception: "+e.getMessage(), true);
		}
		Assert.assertEquals("Did not get expected hulldamage", 20, hullDamage);
	}
	
	@Test
	public void testAddShield(){
		Shield shield = null;
		Ship emptyShip = new Ship();
		boolean exceptionCaught = false;
		try{
			emptyShip.addShield(shield); //should fail because shield is nill
		}
		catch(ShieldException shieldException){
			exceptionCaught = true;			
		}
		Assert.assertTrue("Adding an empty shield should throw a ShieldException" ,exceptionCaught);

		exceptionCaught = false;
		shield = new Shield();
		shield.setRemiainingShieldEnergy(100);
		shield.setShieldStrength(1);
		shield.setShieldType(ShieldType.LIGHT);
		
		try{
			emptyShip.addShield(shield);
		}
		catch(ShieldException e){
			exceptionCaught = true;
		}
		Assert.assertFalse("Shouuld not throw exception when adding non-empty shield to a ship with no shields", exceptionCaught);
		
		exceptionCaught = false;
		try{
			emptyShip.addShield(shield);
		}
		catch(ShieldException e){
			exceptionCaught = true;
		}
		Assert.assertTrue("Should throw shieldexception when trying to add more than one shield to a ship", exceptionCaught);
	}
	
	@Test
	public void testIsDestroyed(){
		Weapon weapon = new LaserWeapon();
		weapon.setDefaultValues();
		Ship damagedShip = new Ship();
		Shield damagedShield = new Shield();
		damagedShield.setRemiainingShieldEnergy(25);
		damagedShield.setShieldType(ShieldType.LIGHT);
		damagedShield.setShieldStrength(1);
		damagedShip.setHullRemaining(5);
		try {
			damagedShip.addShield(damagedShield);
			damagedShip.lowerShieldAndMaybeHull(weapon);
			Assert.assertFalse("expected ship not to be destroyed", damagedShip.isDestroyed());
		} catch (ShieldException e) {
			e.printStackTrace();
			Assert.assertFalse("Should not get exception here: "+e.getMessage(), true);
		}
		
		
		try {
			damagedShip.lowerShieldAndMaybeHull(weapon);
			Assert.assertTrue("expected ship to be destroyed", damagedShip.isDestroyed());
		} catch (ShieldException e) {
			e.printStackTrace();
			Assert.assertFalse("Should not get exception here: "+e.getMessage(), true);
		}

		
	}
	
	@Test
	public void testSimulatedShipCombat() throws NoWeaponsException, ProbabilityOutOfBoundsException, ShieldException, NoMoreRoomException{
		logger.info("starting combat simulation");
		boolean shipDestroyed = false;
		int round = 0;
		
		Pilot worsePilot = new Pilot();
		
		worsePilot.setCombatSkill(9);
		enemyShip.setPilot(worsePilot);
		
		ship.addWeapon(new LaserWeapon());
		enemyShip.addWeapon(new LaserWeapon());
		
		int counter = 0;
		while(!shipDestroyed && counter < 200){//while not
			counter++;
			logger.info("Round: "+round++);
			ship.fireWeapons(enemyShip);
			if (enemyShip.isDestroyed()){
				logger.info("enemyship was destroyed");
				shipDestroyed = true;					
			}
			else{
				enemyShip.fireWeapons(ship);
			}
			if (ship.isDestroyed()){
				logger.info("ship was destroyed");
				shipDestroyed = true;
			}

		}
	}
	
	@Test
	public void testAddingAndSubtractingCredits(){
		Pilot poorPilot = new Pilot(0);
		Assert.assertEquals("expected the pilot to have zero credits", 0, poorPilot.getCredits());

		boolean caughtException = false;
		try {
			poorPilot.subTractCredits(1);
		} catch (TradeException e1) {
			caughtException = true;
		}
		Assert.assertTrue("Expected to catch a pilotExceiption", caughtException);
		
		poorPilot.addCredits(30);
		poorPilot.addCredits(40);
		try {
			poorPilot.subTractCredits(15);
		} catch (TradeException e) {
			Assert.assertFalse("Should not get exception here: "+e.getMessage(), true);
		}
		
		Assert.assertEquals(55, poorPilot.getCredits());
		
	}
}
