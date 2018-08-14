package logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class MoveType_Test {
	/**
	 * Test case for toShortenedString Method in MoveType
	 */
	@Test
	public void MoveType_Test_GetShortenedString() {
		//checks if it returns the correct shortened string values for a enum MoveType value
		assertEquals("c",MoveType.CAVALRY.toShortenedString());
		assertEquals("a",MoveType.ARMOR.toShortenedString());
		assertEquals("i",MoveType.INFANTRY.toShortenedString());
		assertEquals("f",MoveType.FLIER.toShortenedString());
	}
	/**
	 * Test case for getMoveType Method in MoveType
	 */
	@Test
	public void MoveType_Test_GetMoveType() {
		//checks if it returns the correct enum MoveType if its shortened string value is provided
		assertEquals(MoveType.CAVALRY,MoveType.getMoveType("c"));
		assertEquals(MoveType.ARMOR,MoveType.getMoveType("a"));
		assertEquals(MoveType.FLIER,MoveType.getMoveType("f"));
		assertEquals(MoveType.INFANTRY,MoveType.getMoveType("i"));
		assertEquals(null,MoveType.getMoveType("d"));
	}
}
