package logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A static class which has all of the methods required to go through the
 * computer's turn
 */
public class AI {
	protected Map map;
	private Combat combat = new Combat();

	/**
	 * @param map2
	 */
	public AI(Map inputMap) {
		// TODO Auto-generated constructor stub
		map = inputMap;
	}

	/**
	 * Starts the computer's turn
	 * 
	 * @param inputMap
	 *            The current state of the map
	 * @return whether the game has ended after the computer's turn
	 */
	public boolean computerTurn() {
		// set the instance variable to the input map
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
			// int moveNum = randInt(0, availableMoves.size() - 1);
			sortMoves(availableMoves);
			// Apply the randomly selected move
			applyAIMove(availableMoves.get(availableMoves.size() - 1));
			// update the map
			map.updateUnitsOnMap();
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
	private ArrayList<AIMove> findAvailableMoves() {

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
	protected void applyAIMove(AIMove move) {
		Unit unit = move.getUnit();
		int unitX = unit.getX();
		int unitY = unit.getY();

		// Move the hero
		map.moveHero(unitX, unitY, move.getX(), move.getY());
		unit.setHasMoved(true);
		// Print the movement to the user
		System.out.print(move.toString() + "\n");
		Unit target = map.getUnitMap()[move.getJ()][move.getI()];
		if (target != unit) {
			// Apply the combat if the unit chose to attack
			new Combat().doCombat(unit, target);
		}

	}

	/**
	 * Finds the number of turns to be in a location to attack to the target unit,
	 * the unit that the AI unit wants to attack, for the given AI unit at the given
	 * location
	 * 
	 * @param unit
	 * @param y
	 *            y coordinate to find the distance from
	 * @param x
	 *            x coordinate to find the distance from
	 * @return
	 */
	private int distanceToTarget(Unit unit, int y, int x) {
		// target is the unit that will take the most damage
		Unit target = findTarget(unit);
		// Breadth-first search to find shortest distance to target
		Queue<int[]> queue = new LinkedList<int[]>();
		boolean[][] availableMoves = new boolean[map.MAXY][map.MAXY];
		availableMoves[y][x] = true; // set value of the position to True to prepare for the loop
		int[] temp = { x, y, 0 }; // creating temp as an integer array that
									// contain x and y positions, also get
									// the unit moves type
		queue.add(temp); // add temp as the first value in the queue
		TerrainType[][] terrainMap = map.getTerrainMap();
		Unit[][] unitMap = map.getUnitMap();
		while (!queue.isEmpty()) { // checking if the queue is empty or not
			int[] values = queue.remove(); // start again with the new loop, delete the first temp element
			for (int i = 0; i < 4; i++) { // 4 directions to move, so loop from 0 to 3
				int newX = values[0] + (int) Math.round(Math.cos(i / 2d * Math.PI)); // math to calculate where is
																						// the suitable moving
																						// position
				int newY = values[1] + (int) Math.round(Math.sin(i / 2d * Math.PI));
				int movesUsed = values[2];

				if (0 <= newX && newX < map.MAXX && 0 <= newY && newY < map.MAXY && !availableMoves[newY][newX]) {
					int moveCost = MoveRules.moveCost(terrainMap[newY][newX], unit.getMoveType()); // get the
																									// movement cost
																									// by find out
																									// what is the
																									// unit's type

					if (unitMap[newY][newX] == null || !unitMap[newY][newX].equals(target)) {
						if (unitMap[newY][newX] != unit && unitMap[newY][newX] != null) { // checking if the newX
																							// and newY position has
																							// // an unit in in yet
							availableMoves[newY][newX] = false;
						} else
							availableMoves[newY][newX] = true;
						if (movesUsed < 20 && (unitMap[newY][newX] == null
								|| unitMap[newY][newX].isFriendly() == unit.isFriendly())) {
							int[] temp2 = { newX, newY, movesUsed + moveCost };
							queue.add(temp2); // add another temp element to the queue
						}
					} else {
						return movesUsed + moveCost;
					}
				}
			}
		}

		return Integer.MAX_VALUE;
	}

	/**
	 * Finds the distance to the target unit, the unit that the AI wants to attack,
	 * for the given move represented by AIMove
	 * 
	 * @param move
	 * @return
	 */
	private int distanceToTarget(AIMove move) {
		return distanceToTarget(move.getUnit(), move.getY(), move.getX());
	}

	/**
	 * Finds the target unit, the unit that the AI wants to attack, for the given AI
	 * unit.
	 * 
	 * @param unit
	 *            the given AI unit
	 * @return the target unit
	 */
	private Unit findTarget(Unit unit) {
		Unit target = null;
		int maxDamageDealt = 0;
		for (Unit temp : map.getUnitList()) {
			if (temp.isAlive() && temp.isFriendly() != unit.isFriendly()
					&& temp.getCurrentHP() - combat.calculateCombat(unit, temp)[1] > maxDamageDealt) {
				target = temp;
				maxDamageDealt = temp.getCurrentHP() - combat.calculateCombat(unit, temp)[1];
			}
		}
		return target;
	}

	/**
	 * sort the moves so that the best move can be chosen
	 * 
	 * @param availableMoves
	 */
	private void sortMoves(ArrayList<AIMove> availableMoves) {

		availableMoves.sort((m1, m2) ->

		{// Returns 1 if m1 is better than m2
			Unit[][] unitMap = map.getUnitMap();
			int[] healthMove1 = combat.calculateCombat(m1.getUnit(), unitMap[m1.getJ()][m1.getI()]);
			int[] healthMove2 = combat.calculateCombat(m2.getUnit(), unitMap[m2.getJ()][m2.getI()]);
			if (healthMove1 != null && healthMove2 != null) {
				int damageDealtM1 = unitMap[m1.getJ()][m1.getI()].getCurrentHP() - healthMove1[1];
				int damageDealtM2 = unitMap[m2.getJ()][m2.getI()].getCurrentHP() - healthMove2[1];
				int damageTakenM1 = m1.getUnit().getCurrentHP() - healthMove1[0];
				int damageTakenM2 = m2.getUnit().getCurrentHP() - healthMove2[0];
				if (healthMove1[1] <= 0 && healthMove2[1] > 0) {
					return 1;
				}
				if (healthMove1[1] > 0 && healthMove2[1] <= 0) {
					return -1;
				}
				if (damageDealtM1 > damageDealtM2) {
					return 1;
				}
				if (damageDealtM1 < damageDealtM2) {
					return -1;
				}
				if (damageTakenM1 > damageTakenM2) {
					return -1;
				}
				if (damageTakenM1 < damageDealtM2) {
					return 1;
				}
			}
			if (m1.isAttacking() && !m2.isAttacking()) {
				return 1;
			}
			if (!m1.isAttacking() && m2.isAttacking()) {
				return -1;
			}
			// prioritize moving closer to the target unit
			if (m1.getUnit().equals(m2.getUnit())) {
				if (distanceToTarget(m1) > distanceToTarget(m2)) {
					return -1;
				}
				if (distanceToTarget(m1) < distanceToTarget(m2)) {
					return 1;
				}
			}

			// Both compared moves don't attack, or both attacks are equal in damage taken
			// and damage recieved

			// Melee move first, then ranged
			if (m1.getUnit().getRange() < m2.getUnit().getRange()) {
				return 1;
			}
			if (m1.getUnit().getRange() > m2.getUnit().getRange()) {
				return -1;
			}
			return 0;
		}

		);
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
	public int randInt(int min, int max) {
		if (max < min) { // if max is smaller than min, swap them.
			int temp = max;
			max = min;
			min = temp;
		}
		return (int) (Math.random() * (max - min + 1) + min);// Generate a random integer between max and min
	}

}
