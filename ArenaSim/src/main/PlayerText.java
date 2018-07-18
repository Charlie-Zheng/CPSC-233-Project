package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PlayerText {
	private static Unit unit;

	public static boolean playerTurn() {
		while (playerHasUnmovedUnits()) {
			System.out.print("Please select a friendly unit by entering its name: ");
			unit = GameText.unitNameInput();
			while (!unit.isFriendly()) {
				System.out.println("That is not a friendly unit");
				unit = GameText.unitNameInput();
			}
			System.out.println("The selected unit is: \n" + unit);
			System.out.println("Enter an ~ to unselect.\nThe @ represents locations this unit can move to");
			GameText.getMap().displayAvailableMoves(unit);
			while (!playerMove())
				System.out.println("That is not a valid move");
			GameText.getMap().displayAttackOptions(unit);
			System.out.println("Select an enemy unit to attack: (Select self to not attack)");
			//TODO Add code here to ask for combat options and etc.
		}
		return false;
	}

	private static boolean playerMove() {
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

		if (GameText.getMap().checkMoveLegal(unit, xOffset, yOffset)) {
			GameText.getMap().moveHero(unit,xOffset,yOffset);
			unit.setHasMoved(true);
			return true;
		}

		return false;
	}

	private static boolean playerHasUnmovedUnits() {
		for (Unit unit : GameText.getMap().getUnitList()) {
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
