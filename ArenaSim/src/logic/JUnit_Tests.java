/**
 * General test cases file that contains all the tests from the other test files.
 */
package logic;
//imports the following libraries
import static org.junit.Assert.*;

import org.junit.Test;

public class JUnit_Tests {
	/**
	 * All test cases from the Unit_Test file combined into 1 function
	 */
	@Test
	public void Unit_Test() {
		//checks the creation of a friendly unit
		int[] stats = {20,20,20,20};
		Unit friendly = new Unit("hero", MoveType.CAVALRY, true, stats, 2);
		assertEquals("  FRIENDLY	hero: 	HP: 20/20  Atk: 20  Spd: 20  Def: 20  Range: 2  Move Type: CAVALRY",friendly.toString());
		assertEquals(true,friendly.isAlive());
		assertEquals(true, friendly.isFriendly());
		friendly.setYX(2, 3);
		assertEquals(2,friendly.getY());
		assertEquals(3,friendly.getX());
		
		//checks copy constructor
		Unit friendly2 = new Unit(friendly);
		assertEquals("  FRIENDLY	hero: 	HP: 20/20  Atk: 20  Spd: 20  Def: 20  Range: 2  Move Type: CAVALRY",friendly2.toString());
		assertEquals(true,friendly.isAlive());
		assertEquals(true,friendly2.isFriendly());
		assertEquals(true,friendly2.isAlive());
		assertEquals(false,friendly2==friendly);
		
		//checks if unit's friendly x and y values are set right
		friendly2.setYX(5, 8);
		assertEquals(5,friendly2.getY());
		assertEquals(8,friendly2.getX());
		assertEquals(2,friendly.getY());
		assertEquals(3,friendly.getX());
		
		//checks if unit's friendly x values are set right
		friendly2.setX(0);
		assertEquals(0,friendly2.getX());
		assertEquals(5,friendly2.getY());
		assertEquals(2,friendly.getY());
		assertEquals(3,friendly.getX());
		
		//checks if unit's friendly y values are set right
		friendly2.setY(0);
		assertEquals(0,friendly2.getX());
		assertEquals(0,friendly2.getY());
		assertEquals(2,friendly.getY());
		assertEquals(3,friendly.getX());
		
		//checks boolean functions
		assertEquals(false,friendly2.takeDamage(19));
		assertEquals(true,friendly.takeDamage(20));
		assertEquals(false,friendly.isAlive());
		assertEquals(true,friendly2.isAlive());
		
		//checks getter methods for base stats and range
		assertEquals(stats[0],friendly.getBaseHP());
		assertEquals(stats[1],friendly.getAtk());
		assertEquals(stats[2],friendly.getSpd());
		assertEquals(stats[3],friendly.getDef());
		assertEquals(2,friendly.getRange());
		assertEquals(2,friendly2.getRange());
		
		//checks if unit's returned base stat array is the same as provided
		int[] returnedStats = friendly.getBaseStats();
		assertEquals(stats[0],returnedStats[0]);
		assertEquals(stats[1],returnedStats[1]);
		assertEquals(stats[2],returnedStats[2]);
		assertEquals(stats[3],returnedStats[3]);
		
		//checks change in base stats
		int hp=10,atk=25,spd=30,def=40;
		friendly2.setBaseAtk(atk);
		friendly2.setBaseDef(def);
		friendly2.setBaseHP(hp);
		friendly2.setBaseSpd(spd);
		assertEquals(hp,friendly2.getBaseHP());
		assertEquals(atk,friendly2.getAtk());
		assertEquals(spd,friendly2.getSpd());
		assertEquals(def,friendly2.getDef());
		
		//checks the boolean function is friendly, and getter method for MoveType of a unit
		Unit enemy = new Unit("enemy", MoveType.ARMOR, false, stats, 2);
		assertEquals(false,enemy.isFriendly());
		assertEquals(MoveType.ARMOR,enemy.getMoveType());
		assertEquals(MoveType.CAVALRY,friendly.getMoveType());
		assertEquals(MoveType.CAVALRY,friendly2.getMoveType());
		
		//checks getter methods for name and CurrentHP
		assertEquals("hero",friendly.getName());
		assertEquals("hero",friendly2.getName());
		assertEquals("enemy",enemy.getName());
		assertEquals(0,friendly.getCurrentHP());
		assertEquals(1,friendly2.getCurrentHP());
		assertEquals(20,enemy.getCurrentHP());
		
		//checks if the right values are returned for the hasMoved function, 
		//after boolean values have been set.
		friendly.setHasMoved(false);
		friendly2.setHasMoved(true);
		enemy.setHasMoved(true);
		assertEquals(false,friendly.hasMoved());
		assertEquals(true,friendly2.hasMoved());
		assertEquals(true,enemy.hasMoved());
	}
	/**
	 * All test cases from the TerrainType_Test file combined into 1 function
	 */
	@Test
	public void TerrainType_Test() {
		//checks if it returns the correct string type for a TerrainType
		TerrainType Current=TerrainType.TREE;
		assertEquals("t",Current.toString());
		Current=TerrainType.FLAT;
		assertEquals("-",Current.toString());
		Current=TerrainType.MOUNTAIN;
		assertEquals("m",Current.toString());
		Current=TerrainType.WALL;
		assertEquals("w",Current.toString());
	}
	/**
	 * All test cases from the MoveType_Test file combined into 1 function
	 */
	@Test
	public void MoveType_Test() {
		//checks if it returns the correct shortened string values for a enum MoveType value
		assertEquals("c",MoveType.CAVALRY.toShortenedString());
		assertEquals("a",MoveType.ARMOR.toShortenedString());
		assertEquals("i",MoveType.INFANTRY.toShortenedString());
		assertEquals("f",MoveType.FLIER.toShortenedString());
		
		//checks if it returns the correct enum MoveType if its shortened string value is provided
		assertEquals(MoveType.CAVALRY,MoveType.getMoveType("c"));
		assertEquals(MoveType.ARMOR,MoveType.getMoveType("a"));
		assertEquals(MoveType.FLIER,MoveType.getMoveType("f"));
		assertEquals(MoveType.INFANTRY,MoveType.getMoveType("i"));
		assertEquals(null,MoveType.getMoveType("d"));
	}

	/**
	 * All test cases from the MoveRules_Test file combined into 1 function
	 */
	@Test
	public void MoveRules_Test() {
		//Checks to see if it returns the right integer values for specific MoveTypes initialMoves
		assertEquals(3,MoveRules.initialMoves(MoveType.CAVALRY));
		assertEquals(2,MoveRules.initialMoves(MoveType.INFANTRY));
		assertEquals(1,MoveRules.initialMoves(MoveType.ARMOR));
		assertEquals(2,MoveRules.initialMoves(MoveType.FLIER));
		
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.CAVALRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.ARMOR));
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.INFANTRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.FLIER));
		
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.TREE, MoveType.CAVALRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.TREE, MoveType.ARMOR));
		assertEquals(2,MoveRules.moveCost(TerrainType.TREE, MoveType.INFANTRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.TREE, MoveType.FLIER));
		
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(1, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.FLIER));
		assertEquals(Integer.MAX_VALUE, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.CAVALRY));
		assertEquals(Integer.MAX_VALUE, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.ARMOR));
		assertEquals(Integer.MAX_VALUE, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.INFANTRY));
		
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.CAVALRY));
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.ARMOR));
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.INFANTRY));
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.FLIER));
		
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(true,MoveRules.canMove(MoveType.CAVALRY, TerrainType.FLAT, 2));
		assertEquals(true,MoveRules.canMove(MoveType.ARMOR, TerrainType.FLAT, 2));
		assertEquals(true,MoveRules.canMove(MoveType.INFANTRY, TerrainType.FLAT, 2));
		assertEquals(true,MoveRules.canMove(MoveType.FLIER, TerrainType.FLAT, 2));
		
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.TREE, 2));
		assertEquals(true,MoveRules.canMove(MoveType.ARMOR, TerrainType.TREE, 2));
		assertEquals(true,MoveRules.canMove(MoveType.INFANTRY, TerrainType.TREE, 3));
		assertEquals(true,MoveRules.canMove(MoveType.FLIER, TerrainType.TREE, 2));
		
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.MOUNTAIN, 2));
		assertEquals(false,MoveRules.canMove(MoveType.ARMOR, TerrainType.MOUNTAIN, 2));
		assertEquals(false,MoveRules.canMove(MoveType.INFANTRY, TerrainType.MOUNTAIN, 2));
		assertEquals(true,MoveRules.canMove(MoveType.FLIER, TerrainType.MOUNTAIN, 2));
		
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.WALL, 2));
		assertEquals(false,MoveRules.canMove(MoveType.ARMOR, TerrainType.WALL, 2));
		assertEquals(false,MoveRules.canMove(MoveType.INFANTRY, TerrainType.WALL, 2));
		assertEquals(false,MoveRules.canMove(MoveType.FLIER, TerrainType.WALL, 2));
		
		//checks if units are able to move if given 0 or less moves left
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.FLAT, 0));
		assertEquals(false,MoveRules.canMove(MoveType.ARMOR, TerrainType.FLAT, -1));
		assertEquals(false,MoveRules.canMove(MoveType.INFANTRY, TerrainType.FLAT, -2));
		assertEquals(false,MoveRules.canMove(MoveType.FLIER, TerrainType.FLAT, 0));
	}
}
