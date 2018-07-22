package main;

/*Here we import this classes in order for the this file to operate the way it's supposed to.*/
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * A static class which has all of the methods required to go through the
 * player's turn
 */

public class PlayerText {

	/*
	 * Here we create a instance variable of this class which is of the object type
	 * Map from the map class.
	 */
	private static Map map;

	/**
	 * Here we created this method takes an object of the class Map, and in turn it
	 * returns boolean representing whether the game has ended.
	 * 
	 * @param inputMap
	 *            the current map state
	 * @return whether the game has ended after the player's turn
	 */
	public static boolean playerTurn(Map inputMap) {
		/*
		 * Here we at first assign the instance variable map to inputMap which was the
		 * parameter object that method takes in order for it to run. Afterwards in this
		 * method it will create object called selectedUnit of the Unit class. Now, this
		 * method will create a boolean variable called gameOver which will be
		 * initialized to false initially. Afterwards, this method will called another
		 * method of this particular class called resetHasMoved, more will be explained
		 * later for this method does in particular.
		 */
		map = inputMap;
		Unit selectedUnit;
		boolean gameOver = false;
		resetHasMoved();
		/*
		 * Here we have this loop which only gets executed when boolean value that we
		 * from the method of this class called playerHasUnmovedUnits is true and
		 * whenever the boolean value gameOver is set to true. When the loop gets those
		 * values then loop get's executed, when it is first executed, it will first
		 * display the map that the object map has to the player, afterwards it will
		 * prompt the user for which types of unit the player has available, that they
		 * want to place in the map, this user input will first get stored in the object
		 * selectedUnit by using a method from the GameText class. Afterwards it will
		 * enter it's first nested loop, in this loop it will check for valid inputs, if
		 * it notices that the input is not valid it will then prompt the user to enter
		 * a valid input until the user has entered a valid input, then it will exit the
		 * first nested loop. After it exists the first nested loop, it will then show
		 * the player what unit they selected to play and afterwards it will prompt the
		 * player to enter where it wants that particular unit to move the unit to
		 * certain place in the map shown to player. Now after that input it will enter
		 * another nested loop where it will check and see if the moves that they enter
		 * is valid or not, if it is not valid then it will prompt the user multiple
		 * times until the player enters a valid move, afterwards it will exit the
		 * nested loop. Now it will show player where the unit that they chose has moved
		 * to. Also note that during that particular time this method will show the
		 * player available moves that the player has using the the
		 * displayAvailableMoves method which is found in the map class. Now, this loop
		 * will continue by showing the player the available attacking options by
		 * calling the displayAttackOptions method found in the map class. Afterwards,
		 * inside this loop we will create and initialize a two dimensional array on the
		 * boolean type where in that array it will store all the moves that are
		 * possible that could lead to a target in that particular place that player has
		 * moved into by using the findAvailableTargets found in the map class.
		 * Afterwards, it will prompt the player what enemy the player wants to attacks.
		 * This class will store the player's input into an object of the unit class,
		 * and the input will be taken by using the unitGameInput method found in the
		 * GameText class. Afterwards, it will enter another nested loop, however this
		 * time it will try to search and see if the enemy that the player wants to
		 * attack is either an enemy or not, if it is not then this loop will prompt the
		 * user into entering an enemy that this game has the information of until the
		 * user enters a valid enemy. Also inside this nested loop it will try to find
		 * whether the enemy is in target range when near the player's unit range or
		 * not, if it's not in the range then it will at that case also prompts the user
		 * to attack a valid enemy, the only way this loop is exited is by a valid
		 * player input. Now this loop will encounter a conditional where it will check
		 * and see if the target the player attacks does is not a one of the selected
		 * units, then it will execute the doCombat method found in in the combat class.
		 * After this it will update the contents of our map objects to see the how much
		 * abilities the player now has using the updateHeroDeaths method found in the
		 * map class. Afterwards, this loop will assign the boolean gameOver variable to
		 * what the gameOver method returns. All of those things will continue inside
		 * this loop until one of those this that this loops consider to execute is
		 * proven false. Afterwards, this method will return whatever the last boolean
		 * value was on the loops last iteration.
		 */
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

	/**
	 * Allows all friendly units to move again
	 */
	private static void resetHasMoved() {
		/*
		 * This method returns nothing, however this method will execute some every
		 * important things in order for this game to be playable. First of all this
		 * method initializes an arrayList which will store objects of the class Unit.
		 * then it will enter a loop where it will go through a conditional statement
		 * which is set up for whether or not the movement made in this game was made by
		 * the units that are available to the player, if it was then it will set all of
		 * those unit movements back to false.
		 */
		ArrayList<Unit> unitList = map.getUnitList();
		for (Unit unit : unitList) {
			if (unit.isFriendly())
				unit.setHasMoved(false);
		}
	}

	/**
	 * This method checks if the game is over, where either the player has lost by
	 * losing all friendly units, or the player has won by defeating all enemy units
	 * 
	 * @return A boolean representing if the game is over
	 */
	private static boolean gameOver() {
		/*
		 * This method will return a boolean value for the player which can be used to
		 * determine if the user will still be allowed to continue the game or not. In
		 * this method it will first create an arrayList where it contain all object of
		 * the type Unit, afterwards it will declare and initialize boolean variables
		 * called enemiesAllDead and friendiesAllDead. Now it will enter a loop where
		 * inside this loops it will check and see which units have been dead, if it's
		 * an unit that belongs to the player then it will assign friendiesAllDead to
		 * false, but if it's an enemy unit then it will assign enemiesAllDead to false.
		 * Then this method will return a boolean value of true since this method will
		 * check and see which two boolean variables is set to true or not.
		 */
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
	 * Prompts the user for a move and returns if the move is legal for the given
	 * unit
	 * 
	 * @param selectedUnit
	 *            The unit that the user wants to move
	 * @return Whether the move is legal
	 */
	private static boolean playerMove(Unit selectedUnit) {
		/*
		 * This method will also return a boolean value after this method gets executed.
		 * In this method it will except an object of the class Unit. This method will
		 * prompt user to enter where it wants to move and then it will check and see if
		 * the move is valid or not using the errorTrap method. When the move is valid,
		 * then it will move to a certain y-direction, then it will do those previous
		 * things again but this time it will move to a certain x-diraction. So by that
		 * it means that the player unit will move to a certain point in the map. In
		 * that point is a legal move, then the method will return boolean value of
		 * true, if not then it returns false.
		 */
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

	/**
	 * Checks if the player still has units to move
	 * 
	 * @return True if the player still hsa units to mvoe
	 */
	private static boolean playerHasUnmovedUnits() {
		/*
		 * This method will also return a boolean method after it gets executed. In this
		 * method it will check and see what are the available moves for the player
		 * units. It will check those moves by going to a loop and finding the available
		 * direction that is open for the player units. and return true or false based
		 * upon it.
		 */
		for (Unit unit : map.getUnitList()) {
			if (unit.isFriendly() && !unit.hasMoved()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is created to check for valid inputs for when the player has to
	 * move the units available to it.
	 * 
	 * @param max
	 *            The maximum integer the user can input.
	 * @param min
	 *            The minimum integer the user can input.
	 * @return The integer the user input, which is between max and min, inclusive.
	 */
	public static String errorTrapInt(int max, int min) {
		Scanner input = new Scanner(System.in);
		// The swap algorithim is used in order for the legal valid moves to be in order
		// where it's greater than the min value and less then the max value
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
