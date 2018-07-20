package main;

import java.util.ArrayList;

public abstract class TurnGUI {
	private Map map;
	private boolean isFriendly;

	public TurnGUI(Map inputMap, boolean isFriendly) {
		map = inputMap;
		this.isFriendly = isFriendly;
	}

	public boolean turn() {
		boolean gameOver = false;
		resetHasMoved();
		while (playerHasUnmovedUnits() && !gameOver) {
			getOneMove();
			gameOver = gameOver();
		}

		return gameOver;
	}

	abstract protected void getOneMove();

	private void resetHasMoved() {
		ArrayList<Unit> unitList = map.getUnitList();
		for (Unit unit : unitList) {
			if (unit.isFriendly() == isFriendly)
				unit.setHasMoved(false);
		}
	}

	private boolean playerHasUnmovedUnits() {
		for (Unit unit : map.getUnitList()) {
			if (unit.isFriendly() == isFriendly && !unit.hasMoved()) {
				return true;
			}
		}
		return false;
	}

	private boolean gameOver() {
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
}
