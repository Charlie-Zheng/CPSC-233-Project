package logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Unit_Test {
	@Test
	/**
	 * Tests the creation of a unit in the unit class
	 */
	public void Unit_Test_Unit_Creation_1() {
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		assertEquals("  FRIENDLY	hero: 	HP: 20/20  Atk: 20  Spd: 20  Def: 20  Range: 2  Move Type: CAVALRY",friendly.toString());
		assertEquals(true,friendly.isAlive());
		assertEquals(true, friendly.isFriendly());
		friendly.setYX(2, 3);
		assertEquals(2,friendly.getY());
		assertEquals(3,friendly.getX());
	}
	/**
	 * Tests the creation of a unit using the copy constructor in the unit class
	 */
	@Test
	public void Unit_Test_Copy_Constructor() {
		//checks copy constructor
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);
		assertEquals("  FRIENDLY	hero: 	HP: 20/20  Atk: 20  Spd: 20  Def: 20  Range: 2  Move Type: CAVALRY",friendly2.toString());
		assertEquals(true,friendly.isAlive());
		assertEquals(true,friendly2.isFriendly());
		assertEquals(true,friendly2.isAlive());
		assertEquals(false,friendly2==friendly);
	}
	/**
	 * Tests setting a new x and y value for a unit
	 */
	@Test
	public void Unit_Test_SetYX_Test() {
		//checks if unit's friendly x and y values are set right
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);
		friendly2.setYX(5, 8);
		assertEquals(5,friendly2.getY());
		assertEquals(8,friendly2.getX());
		assertEquals(0,friendly.getY());
		assertEquals(0,friendly.getX());
	}
	/**
	 * Tests setting a new x value for a unit
	 */
	@Test
	public void Unit_Test_SetX_Test() {
		//checks if unit's friendly x values are set right
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);
		friendly2.setX(10);
		assertEquals(10,friendly2.getX());
		assertEquals(0,friendly2.getY());
		assertEquals(0,friendly.getY());
		assertEquals(0,friendly.getX());
	}
	/**
	 * Tests setting a new y value for a unit
	 */
	@Test
	public void Unit_Test_SetY_Test() {
		//checks if unit's friendly y values are set right
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);
		friendly2.setY(6);
		assertEquals(0,friendly2.getX());
		assertEquals(6,friendly2.getY());
		assertEquals(0,friendly.getY());
		assertEquals(0,friendly.getX());
	}
	/**
	 * Tests the boolean functions in the Unit class
	 */
	@Test
	public void Unit_Test_BooleanFunctions() {
		//checks boolean functions
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);
		assertEquals(false,friendly2.takeDamage(19));
		assertEquals(true,friendly.takeDamage(20));
		assertEquals(false,friendly.isAlive());
		assertEquals(true,friendly2.isAlive());
	}
	/**
	 * Tests the getter methods in the Unit class
	 */
	@Test
	public void Unit_Test_GetterMethods() {
		//checks getter methods for base stats and range
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);
		assertEquals(stats[0],friendly.getBaseHP());
		assertEquals(stats[1],friendly.getAtk());
		assertEquals(stats[2],friendly.getSpd());
		assertEquals(stats[3],friendly.getDef());
		assertEquals(2,friendly.getRange());
		assertEquals(2,friendly2.getRange());
	}
	/**
	 * Tests the getter method for the BaseStats instance variable in the Unit class
	 */
	@Test
	public void Unit_Test_GetterMethod_BaseStat() {
		//checks if unit's returned base stat array is the same as provided
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		int[] returnedStats = friendly.getBaseStats();
		assertEquals(stats[0],returnedStats[0]);
		assertEquals(stats[1],returnedStats[1]);
		assertEquals(stats[2],returnedStats[2]);
		assertEquals(stats[3],returnedStats[3]);
	}/**
	 * Tests the setter methods for the baseStats array in the Unit class
	 */
	@Test
	public void Unit_Test_SetterMethod_BaseStat() {
		//checks change in base stats
		int[] stats = {20,20,20,20};
		Unit friendly2 = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		int hp=10,atk=25,spd=30,def=40;
		friendly2.setBaseAtk(atk);
		friendly2.setBaseDef(def);
		friendly2.setBaseHP(hp);
		friendly2.setBaseSpd(spd);
		assertEquals(hp,friendly2.getBaseHP());
		assertEquals(atk,friendly2.getAtk());
		assertEquals(spd,friendly2.getSpd());
		assertEquals(def,friendly2.getDef());
	}/**
	 * Tests the getter method for the Unit's moveType in the Unit class
	 */
	@Test
	public void Unit_Test_MoveTypeGetter() {
		//checks the boolean function is friendly, and getter method for MoveType of a unit
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);		
		Unit enemy = new Unit("enemy", MoveType.ARMOR, false, stats, 2);
		assertEquals(false,enemy.isFriendly());
		assertEquals(MoveType.ARMOR,enemy.getMoveType());
		assertEquals(MoveType.CAVALRY,friendly.getMoveType());
		assertEquals(MoveType.CAVALRY,friendly2.getMoveType());
	}/**
	 * 2nd Test case for the getter methods in the Unit class
	 */
	@Test
	public void Unit_Test_GetterMethods2() {
		//checks getter methods for name and CurrentHP
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);		
		Unit enemy = new Unit("enemy", MoveType.ARMOR, false, stats, 2);
		friendly2.takeDamage(19);
		friendly.takeDamage(20);
		assertEquals("hero",friendly.getName());
		assertEquals("hero",friendly2.getName());
		assertEquals("enemy",enemy.getName());
		assertEquals(0,friendly.getCurrentHP());
		assertEquals(1,friendly2.getCurrentHP());
		assertEquals(20,enemy.getCurrentHP());
	}/**
	 * Tests the boolean functions in the Unit class
	 */
	@Test
	public void Unit_Test_BooleanFunctions2() {
		//checks if the right values are returned for the hasMoved function, 
		//after boolean values have been set.
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		Unit friendly2 = new Unit(friendly);		
		Unit enemy = new Unit("enemy", MoveType.ARMOR, false, stats, 2);
		friendly.setHasMoved(false);
		friendly2.setHasMoved(true);
		enemy.setHasMoved(true);
		assertEquals(false,friendly.hasMoved());
		assertEquals(true,friendly2.hasMoved());
		assertEquals(true,enemy.hasMoved());
	}
}
