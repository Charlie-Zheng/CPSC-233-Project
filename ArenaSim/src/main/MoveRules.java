package main;
/**
 * A class which contains the rules regarding movements
 *
 */
public final class MoveRules {

	private MoveRules() {

	}

	/**
	 * Checks if the moving onto a certain terrain is possible
	 * 
	 * @param moveType
	 *            passes in character type (ARMOR, INFANTRY, CAVALRY, FLIER)
	 * @param terrain
	 *            passes in terrain type (flat, tree, mountain)
	 * @param movesLeft
	 *            an integer of number of moves left for character
	 * @return A boolean true or false based on if the unit can move onto the given
	 *         terrain
	 */
	public static boolean canMove(MoveType moveType, TerrainType terrain, int movesLeft) {
		return movesLeft > moveCost(terrain, moveType); // returns a true or false based on if it moveCost is not less
														// than movesLeft
	}

	/**
	 * Returns the number of initial moves a type of character
	 * has
	 * 
	 * @param moveType
	 *             passes in character type (ARMOR, INFANTRY, CAVALRY, FLIER)
	 * @return an int that represents number of initial moves the character type has
	 */
	public static int initialMoves(MoveType moveType) { // takes argument moveType of type MoveType class
		switch (moveType) { // switch goes through cases until moveType matches with any, then returns the
							// initial move amount
		case ARMOR:
			return 1; // gives armor 1 move
		case INFANTRY:
			return 2; // gives infantry 2 moves
		case CAVALRY:
			return 3; // gives cavalry 3 moves
		case FLIER:
			return 2; // gives flier 2 moves
		default:
			return 0;
		}
	}

	/**
	 * method is used to calculate the cost of moving across certain terrains such
	 * as TREE where INFANTRY requires 2 moves to cross it
	 * 
	 * @param terrain
	 *            passes in terrain type which are enum values from TerrainType.java
	 *            (FLAT, TREE, MOUNTAIN, WALL) 
	 * @param moveType
	 *            moveType holds the enum values from MoveType.java (ARMOR,
	 *            INFANTRY, CAVALRY, FLIER)
	 * @return returns int number costs to move across
	 */
	public static int moveCost(TerrainType terrain, MoveType moveType) { // takes arguments terrain and moveType of type
																			// TerrainType and MoveType
		switch (terrain) { // switch takes in terrain type, and goes through cases, each terrain type only
							// some character types can go through
		case FLAT: // in case of Flat, all characters only need 1 move to cross
			switch (moveType) {
			case ARMOR:
				return 1; // costs 1 to move across
			case INFANTRY:
				return 1; // costs 1 to move across
			case CAVALRY:
				return 1; // costs 1 to move across
			case FLIER:
				return 1; // costs 1 to move across
			default:
				return Integer.MAX_VALUE; // If none of above cases work, it will return a large number, which doesn't
											// run some code in map.java
			}
		case TREE: // in case of tree, some characters need more moves to cross over.
			switch (moveType) {
			case ARMOR: // in case of Armor
				return 1; // costs 1 to move across
			case INFANTRY:
				return 2; // costs 2 to move across
			case FLIER:
				return 1; // costs 1 to move across
			default:
				return Integer.MAX_VALUE;
			}
		case MOUNTAIN: // in case of mountain, only the flier is able to cross over,
			switch (moveType) {
			case FLIER:
				return 1;
			default:
				return Integer.MAX_VALUE; // returns max value so other characters do not have enough moves to cross
			}
		default:
			return Integer.MAX_VALUE;
		}
	}

}
