package main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GameText {
	private static Map map;
	public static void main(String[] args) {
		boolean gameOver = false;
				map = new Map("src/assets/map_1_1.txt");
				map.displayMap();
				PlayerText.playerTurn();
			
	}
	

	
	
	
	public static Unit unitNameInput() {
		Scanner input = new Scanner(System.in);
		
		Unit unit = getMap().getUnit(input.nextLine());
		while(unit == null) {
			System.out.println("That is not a valid unit name");
			unit = getMap().getUnit(input.nextLine());
		}
		return unit;
		
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
		return userInput;// user has succesfully entered a valid integer, return that integer.
	}





	public static Map getMap() {
		return map;
	}





}
