package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameText { // new class Game Text
	private static Map map; // creating new object map

	/**
	 * main method, use to display the map while the game can still continue,
	 * display actions of both sides
	 * 
	 * @param args
	 */
	public static void main(String[] args) { // main method
		boolean gameOver = false; // while the game is not yet finished
		map = new Map("src/assets/map_1_1.txt"); // new Map as read from the file, creating new map object
		Scanner wait = new Scanner(System.in);
		while (!gameOver) { // checking the turn
			gameOver = PlayerText.playerTurn(map);
			if (!gameOver) { // if the turn is over
				System.out.println("Your turn is over, now it's the AI's turn, press enter to continue"); // messages
				wait.nextLine(); // wait for AI move
				gameOver = AI.computerTurn(map);
			}
		}
		map.displayMap(); // display the map after both friendly and enemies units have moved

	}

	/**
	 * Prompts the user to selected a unit, and keeps looping until the user has
	 * selected a valid unit
	 * 
	 * @return an unit object from the unit name the user has selected
	 */
	public static Unit unitNameInput() { // get Unit name method
		Scanner input = new Scanner(System.in);

		Unit unit = getMap().getUnit(input.nextLine()); // get Unit name on the console
		while (unit == null) { // if the name of the unit is not empty
			System.out.println("That is not a valid unit name"); // message if the unit name doesn't match the name on
																	// the map
			unit = getMap().getUnit(input.nextLine()); // get new unit name on the console until the name match
		}
		return unit; // return unit if that's the case (while terminte the loop)

	}

	/**
	 * @param max
	 *            The maximum integer the user can input.
	 * @param min
	 *            The minimum integer the user can input.
	 * @return The integer the user input, which is between max and min, inclusive.
	 */
	public static int errorTrapInt(int max, int min) {
		Scanner input = new Scanner(System.in);
		if (max < min) { // if max is smaller than min, swap them.
			int temp = max;
			max = min;
			min = temp;
		}
		int userInput = 0;
		boolean hasFailedInput = false;
		do {
			try {
				if (hasFailedInput) // only displays if the the user has failed one input already.
					System.out.println("Please enter a number from " + min + " to " + max + ":");
				hasFailedInput = false; // reset the failed boolean.
				userInput = input.nextInt(); // take in new input.

			} catch (InputMismatchException error) {// Catch any input mismatch errors that might have occurred while
													// trying to take in input,
													// which will usually be the user entering a letter instead of an
													// integer.

				input.nextLine(); // clean the input buffer in case the user has more than one word inputted in
									// the terminal.
				hasFailedInput = true;
			}
			if (!hasFailedInput && (userInput < min || userInput > max))// if the user hasn't failed again and userInput
																		// is out of range, set the 'failed' boolean to
																		// be true.
				hasFailedInput = true;
		} while (hasFailedInput); // if the user failed the input, loop back and ask them for input again.
		return userInput;// user has successfully entered a valid integer, return that integer.
	}

	/**
	 * getter method of Map class, return an map type object
	 * 
	 * @return map
	 */
	public static Map getMap() {
		return map;
	}

}
