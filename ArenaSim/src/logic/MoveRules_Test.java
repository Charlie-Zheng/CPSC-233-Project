package logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveRules_Test {
	/**
	 * Test case for initialMoves Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_InitialMovesTest() {
		//Checks to see if it returns the right integer values for specific MoveTypes initialMoves
		assertEquals(3,MoveRules.initialMoves(MoveType.CAVALRY));
		assertEquals(2,MoveRules.initialMoves(MoveType.INFANTRY));
		assertEquals(1,MoveRules.initialMoves(MoveType.ARMOR));
		assertEquals(2,MoveRules.initialMoves(MoveType.FLIER));
	}
	/**
	 * Test case for moveCost Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_MoveCostTest_1() {
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.CAVALRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.ARMOR));
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.INFANTRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.FLAT, MoveType.FLIER));
	}
	/**
	 * 2nd Test case for moveCost Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_MoveCostTest_2() {
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.TREE, MoveType.CAVALRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.TREE, MoveType.ARMOR));
		assertEquals(2,MoveRules.moveCost(TerrainType.TREE, MoveType.INFANTRY));
		assertEquals(1,MoveRules.moveCost(TerrainType.TREE, MoveType.FLIER));
	}
	/**
	 * 3rd Test case for moveCost Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_MoveCostTest_3() {
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(1, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.FLIER));
		assertEquals(Integer.MAX_VALUE, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.CAVALRY));
		assertEquals(Integer.MAX_VALUE, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.ARMOR));
		assertEquals(Integer.MAX_VALUE, MoveRules.moveCost(TerrainType.MOUNTAIN, MoveType.INFANTRY));
	}
	/**
	 * 4th Test case for moveCost Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_MoveCostTest_4() {
		//checks to see if it returns the correct move cost for each enum MoveType value
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.CAVALRY));
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.ARMOR));
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.INFANTRY));
		assertEquals(Integer.MAX_VALUE,MoveRules.moveCost(TerrainType.WALL, MoveType.FLIER));
	}
	/**
	 * Test case for canMove Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_CanMoveTest_1() {
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(true,MoveRules.canMove(MoveType.CAVALRY, TerrainType.FLAT, 2));
		assertEquals(true,MoveRules.canMove(MoveType.ARMOR, TerrainType.FLAT, 2));
		assertEquals(true,MoveRules.canMove(MoveType.INFANTRY, TerrainType.FLAT, 2));
		assertEquals(true,MoveRules.canMove(MoveType.FLIER, TerrainType.FLAT, 2));
	}
	/**
	 * 2nd Test case for canMove Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_CanMoveTest_2() {
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.TREE, 2));
		assertEquals(true,MoveRules.canMove(MoveType.ARMOR, TerrainType.TREE, 2));
		assertEquals(true,MoveRules.canMove(MoveType.INFANTRY, TerrainType.TREE, 3));
		assertEquals(true,MoveRules.canMove(MoveType.FLIER, TerrainType.TREE, 2));
	}
	/**
	 * 3rd Test case for canMove Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_CanMoveTest_3() {
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.MOUNTAIN, 2));
		assertEquals(false,MoveRules.canMove(MoveType.ARMOR, TerrainType.MOUNTAIN, 2));
		assertEquals(false,MoveRules.canMove(MoveType.INFANTRY, TerrainType.MOUNTAIN, 2));
		assertEquals(true,MoveRules.canMove(MoveType.FLIER, TerrainType.MOUNTAIN, 2));
	}
	/**
	 * 4th Test case for canMove Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_CanMoveTest_4() {
		//checks to see if a unit is able to move or not on that terrain.
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.WALL, 2));
		assertEquals(false,MoveRules.canMove(MoveType.ARMOR, TerrainType.WALL, 2));
		assertEquals(false,MoveRules.canMove(MoveType.INFANTRY, TerrainType.WALL, 2));
		assertEquals(false,MoveRules.canMove(MoveType.FLIER, TerrainType.WALL, 2));
	}
	/**
	 * 5th Test case for canMove Method in MoveRules
	 */
	@Test
	public void MoveRules_Test_CanMoveTest_5() {
		//checks if units are able to move if given 0 or less moves left
		assertEquals(false,MoveRules.canMove(MoveType.CAVALRY, TerrainType.FLAT, 0));
		assertEquals(false,MoveRules.canMove(MoveType.ARMOR, TerrainType.FLAT, -1));
		assertEquals(false,MoveRules.canMove(MoveType.INFANTRY, TerrainType.FLAT, -2));
		assertEquals(false,MoveRules.canMove(MoveType.FLIER, TerrainType.FLAT, 0));
	}

}
