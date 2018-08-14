package logic;

/**
 * A enumeration representing the movement types in the game, Cavalry, Armor,
 * Infantry, and Flier
 */
public enum MoveType {
	INFANTRY, CAVALRY, FLIER, ARMOR; // enum constants

	/**
	 * method called to get the shortened length of the enum constants basically
	 * returns a single character that is used in MoveType method to compare to the
	 * input character.
	 * 
	 * @return a string that is a shortened word of the constants (c, a, f, i)
	 */
	public String toShortenedString() {
		if (this.equals(MoveType.CAVALRY)) { // condition check if move is equal to cavalry
			return "c"; // returns "c" short for cavalry
		} else if (this.equals(MoveType.ARMOR)) {
			return "a"; // returns "a" for armor
		} else if (this.equals(MoveType.FLIER)) {
			return "f"; // returns "f" for flier
		}
		return "i"; // if none of above statements are met, returns i for infantry
	}

	/**
	 * method takes in an input and gets the move which is one of the above enum
	 * constants (INFANTRY, ARMOR, CAVALRY, FLIER)
	 * 
	 * @param input
	 *            which is a string usually of characters c, a, f, or i
	 * @return returns the move which is one of the above enum constants (INFANTRY,
	 *         ARMOR, CAVALRY, FLIER)
	 */
	public static MoveType getMoveType(String input) {
		for (MoveType move : MoveType.values()) { // iterates through each enum constants until exhausted
			if (move.toShortenedString().equalsIgnoreCase(input)) { // checks if input matches each enum constant
																	// shortened to a string
				return move; // returns move, which is enum constants from above
			}
		}
		return null;
	}
}
