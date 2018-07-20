package main;

import java.util.ArrayList;

public class AI {
	private static Map map;

	public static boolean computerTurn(Map inputMap) {

		map = inputMap;
		boolean gameOver = false;
		resetHasMoved();
		while (AIHasUnmovedUnits() && !gameOver) {

			ArrayList<AIMove> availableMoves = findAvailableMoves();
			int moveNum = randInt(0, availableMoves.size()-1);
			applyAIMove(availableMoves.get(moveNum));
			map.updateHeroDeaths();
			gameOver = gameOver();

		}

		return gameOver;
	}

	private static ArrayList<AIMove> findAvailableMoves() {

		ArrayList<AIMove> availableMoves = new ArrayList<AIMove>();
		ArrayList<Unit> unitList = map.getUnitList();
		for (Unit unit : unitList) {
			if (!unit.isFriendly() && !unit.hasMoved()) {
				int unitX = unit.getX();
				int unitY = unit.getY();
				boolean[][] availableMovesOfUnit = map.findAvailableMoves(unit);
				for (int y = 0, maxY = availableMovesOfUnit.length; y < maxY; y++) {
					for (int x = 0, maxX = availableMovesOfUnit[y].length; x < maxX; x++) {
						if (availableMovesOfUnit[y][x]) {
							map.moveHero(unitX, unitY, x, y);
							boolean[][] availableTargetsOfUnit = map.findAvailableTargets(unit);
							for (int j = 0, maxJ = availableTargetsOfUnit.length; j < maxJ; j++) {
								for (int i = 0, maxI = availableTargetsOfUnit[j].length; i < maxI; i++) {
									if (availableTargetsOfUnit[j][i] && map.getUnitMap()[j][i] != null) {
										availableMoves.add(new AIMove(unit, x, y, i, j));
									}
								}
							}
							availableMoves.add(new AIMove(unit, x, y, x, y));
							// System.out.println("X: " + unit.getX() + "\tY: " + unit.getY());
							map.moveHero(x, y, unitX, unitY);
						}
					}
				}
			}
		}
		return availableMoves;
	}

	private static void applyAIMove(AIMove move) {
		Unit unit = move.getUnit();
		int unitX = unit.getX();
		int unitY = unit.getY();

		map.moveHero(unitX, unitY, move.getX(), move.getY());
		Unit target = map.getUnitMap()[move.getJ()][move.getI()];
		if (target != unit) {
			Combat.doCombat(unit, target);
		}
		System.out.println(move);
		unit.setHasMoved(true);

	}

	private static boolean AIHasUnmovedUnits() {
		for (Unit unit : map.getUnitList()) {
			if (!unit.isFriendly() && !unit.hasMoved()) {
				return true;
			}
		}
		return false;
	}

	private static void resetHasMoved() {
		ArrayList<Unit> unitList = map.getUnitList();
		for (Unit unit : unitList) {
			if (!unit.isFriendly())
				unit.setHasMoved(false);
		}
	}

	private static boolean gameOver() {
		ArrayList<Unit> unitList = map.getUnitList();
		boolean enemiesAllDead = true;
		boolean friendiesAllDead = true;
		for (Unit unit : unitList) {
			if (unit.isFriendly())
				friendiesAllDead = false;
			else
				enemiesAllDead = false;
		}
		return friendiesAllDead || enemiesAllDead;

	}

	/**
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
