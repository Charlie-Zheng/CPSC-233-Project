package logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class TerrainType_Test {
	/**
	 * Test case for the toString Method in TerrainType
	 */
	@Test
	public void TerrainType_Test_toString() {
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

}
