package com.spacetrader.service.ship;


import com.spacetrader.service.pilot.Pilot;
import com.spacetrader.service.shield.ShieldException;
import com.spacetrader.service.ship.exception.NoMoreRoomException;
import com.spacetrader.service.ship.exception.NoWeaponsException;
import com.spacetrader.service.weapon.LaserWeapon;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShipTest {
	Ship ship;
	Ship enemyShip;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.ship = new Ship();
		this.enemyShip = new Ship();
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
		int pilotSkill = pilot.getSkill();
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
			System.out.println("hit was "+resultHit);
		}
		
		for (int i=0; i<20;i++){
			boolean resultHit = this.ship.hitOrNot(10);
			System.out.println("hit was "+resultHit);
		}

		for (int i=0; i<20;i++){
			boolean resultHit = this.ship.hitOrNot(90);
			System.out.println("hit was "+resultHit);
		}
	}
	
	public void testGetHitLowerShield(){
		
		
	}
	
}
