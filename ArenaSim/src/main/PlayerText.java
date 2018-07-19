package main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayerText {

	private static Map map;

	public static boolean playerTurn(Map inputMap) {
		map = inputMap;
		Unit selectedUnit;
		boolean gameOver = false;
		resetHasMoved();
		while (playerHasUnmovedUnits() && !gameOver) {
			map.displayMap();
			System.out.print("Please select a friendly unit by entering its name: ");
			selectedUnit = GameText.unitNameInput();
			while (!selectedUnit.isFriendly()) {
				System.out.println("That is not a friendly unit");
				selectedUnit = GameText.unitNameInput();
			}
			System.out.println("The selected unit is: \n" + selectedUnit);
			System.out.println("Enter an ~ to unselect.\nThe @ represents locations this unit can move to");
			map.displayAvailableMoves(selectedUnit);
			while (!playerMove(selectedUnit))
				System.out.println("That is not a valid move");
			map.displayAttackOptions(selectedUnit);
			boolean[][] availableAttacks = map.findAvailableTargets(selectedUnit);
			System.out.println("Select an enemy unit to attack: (Select self to not attack)");
			Unit target = GameText.unitNameInput();

			while ((target.isFriendly() || !availableAttacks[target.getY()][target.getX()]) && target != selectedUnit) {
				if (availableAttacks[target.getY()][target.getX()])
					System.out.println("That is not an enemy unit");
				else
					System.out.println("Target out of range");
				target = GameText.unitNameInput();
			}
			if (target != selectedUnit) {
				Combat.doCombat(selectedUnit, target);
			}
			map.updateHeroDeaths();
			gameOver = gameOver();
		}

		return gameOver;
	}

	private static void resetHasMoved() {
		ArrayList<Unit> unitList = map.getUnitList();
		for (Unit unit : unitList) {
			if (unit.isFriendly())
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

	private static boolean playerMove(Unit selectedUnit) {
		System.out.println("How far would you like to move up?");
		String temp = errorTrapInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		if (temp.equals("~")) {
			return true;
		}
		int yOffset = -Integer.parseInt(temp);
		System.out.println("How far would you like to move right?");
		temp = errorTrapInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
		if (temp.equals("~")) {
			return true;
		}
		int xOffset = Integer.parseInt(temp);

		if (map.checkMoveLegal(selectedUnit, xOffset, yOffset)) {
			map.moveHero(selectedUnit, xOffset, yOffset);
			selectedUnit.setHasMoved(true);
			return true;
		}

		return false;
	}

	private static boolean playerHasUnmovedUnits() {
		for (Unit unit : map.getUnitList()) {
			if (unit.isFriendly() && !unit.hasMoved()) {
				return true;
			}
		}
		return false;
	}

	public static String errorTrapInt(int max, int min) {
		Scanner input = new Scanner(System.in);
		if (max < min) { // if max is smaller than min, swap them.
			int temp = max;
			max = min;
			min = temp;
		}
		String userInput = "";
		boolean hasFailedInput = false;
		do {
			try {
				if (hasFailedInput) // only displays if the the user has failed one input already.
					System.out.println("Please enter a number from " + min + " to " + max + ":");
				hasFailedInput = false; // reset the failed boolean.
				userInput = input.next(); // take in new input.
				if (userInput.equals("~"))
					return "~";
				// if the user hasn't failed again and userInputis out of range, set the
				// 'failed' boolean to be true.
				else if (!hasFailedInput && (Integer.parseInt(userInput) < min || Integer.parseInt(userInput) > max))
					hasFailedInput = true;

			} catch (NumberFormatException error) {// Catch any input mismatch errors that might have occurred while
													// trying to take in input,
													// which will usually be the user entering a letter instead of an
													// integer.

				input.nextLine(); // clean the input buffer in case the user has more than one word inputted in
									// the terminal.

				hasFailedInput = true;
			}

		} while (hasFailedInput); // if the user failed the input, loop back and ask them for input again.
		return userInput;// user has succesfully entered a valid integer, return that integer.
	}
}
