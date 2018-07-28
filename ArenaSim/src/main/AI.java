package main;

import java.util.ArrayList;

/**
 * A static class which has all of the methods required to go through the
 * computer's turn
 */
public class AI {
	private static Map map;

	/**
	 * Starts the computer's turn
	 * 
	 * @param inputMap
	 *            The current state of the map
	 * @return whether the game has ended after the computer's turn
	 */
	public static boolean computerTurn(Map inputMap) {
		// set the instance variable to the input map
		map = inputMap;
		// initialize game over to be false
		boolean gameOver = false;
		// initialize all the units to not moved
		map.resetHasMoved(false);
		// repeatedly chooses a random move from the movelist until all AI units have
		// moved or the game ends
		while (map.factionHasUnmovedUnits(false) && !gameOver) {
			// Find the available moves
			ArrayList<AIMove> availableMoves = findAvailableMoves();
			// Choose and random move
			int moveNum = randInt(0, availableMoves.size() - 1);
			// Apply the randomly selected mvoe
			applyAIMove(availableMoves.get(moveNum));
			// update the map
			map.updateHeroDeaths();
			// check if the game is over
			gameOver = map.gameOver();

		}

		return gameOver;
	}

	/**
	 * Looks for all the possible moves and attacks the AI could do given the
	 * current map state
	 * 
	 * @return An ArrayList of AIMoves, a class representing moves and attacks the
	 *         AI can make
	 */
	private static ArrayList<AIMove> findAvailableMoves() {

		// initialize the list of moves and attacks
		ArrayList<AIMove> availableMoves = new ArrayList<AIMove>();

		// Store the list of units from the map
		ArrayList<Unit> unitList = map.getUnitList();
		// Iterate through each unit the AI can control
		for (Unit unit : unitList) {
			if (!unit.isFriendly() && !unit.hasMoved() && unit.isAlive()) {
				int unitX = unit.getX();
				int unitY = unit.getY();

				// get the list of movements the given unit can move to
				boolean[][] availableMovesOfUnit = map.findAvailableMoves(unit);
				// iterate through each possible move
				for (int y = 0, maxY = availableMovesOfUnit.length; y < maxY; y++) {
					for (int x = 0, maxX = availableMovesOfUnit[y].length; x < maxX; x++) {
						if (availableMovesOfUnit[y][x]) {
							// move to unit to the given location for that the available targets can be
							// found
							map.moveHero(unitX, unitY, x, y);
							// find the available targets if the unit had moved to the given location
							boolean[][] availableTargetsOfUnit = map.findRange(unit);

							for (int j = 0, maxJ = availableTargetsOfUnit.length; j < maxJ; j++) {
								for (int i = 0, maxI = availableTargetsOfUnit[j].length; i < maxI; i++) {
									// check if the target is something the unit can attack
									if (availableTargetsOfUnit[j][i] && map.getUnitMap()[j][i] != null
											&& map.getUnitMap()[j][i].isFriendly()) {
										// Add the move and attack to list of moves and attacks
										availableMoves.add(new AIMove(unit, x, y, i, j));
									}
								}
							}
							// also add the option of not attacking
							availableMoves.add(new AIMove(unit, x, y, x, y));
							// move the hero back to its starting location
							map.moveHero(x, y, unitX, unitY);
						}
					}
				}
			}
		}
		return availableMoves;
	}

	/**
	 * Reads the move represented by the given AIMove and applies it
	 * 
	 * @param move
	 *            THe AIMove to apply
	 */
	private static void applyAIMove(AIMove move) {
		Unit unit = move.getUnit();
		int unitX = unit.getX();
		int unitY = unit.getY();
		// Move the hero
		map.moveHero(unitX, unitY, move.getX(), move.getY());
		unit.setHasMoved(true);
		// Print the movement to the user
		System.out.print(move.toString() +"\n");
		Unit target = map.getUnitMap()[move.getJ()][move.getI()];
		if (target != unit) {
			// Apply the combat if the unit chose to attack
			Combat.doCombat(unit, target);
		}

	}



	/**
	 * Generates a random integer between the given max and min values
	 * 
	 * @param min
	 *            The minimum integer that can be generated.
	 * @param max
	 *            The maximum integer that can be generated.
	 * @return A random integer between min and min, inclusive.
	 */
	public static int randInt(int min, int max) {
		if (max < min) { // if max is smaller than min, swap them.
			int temp = max;
			max = min;
			min = temp;
		}
		return (int) (Math.random() * (max - min + 1) + min);// Generate a random integer between max and min
	}

}
