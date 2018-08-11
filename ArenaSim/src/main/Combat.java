package main;

/**
 * Creates a Class called Combat that determines a battle between 2 units on
 * different sides.
 */
public class Combat {

	/**
	 * Uses this function to check the combat results between 2 units.
	 * 
	 * @param initiator
	 *            is a Unit class object who is starting the battle against another
	 *            unit
	 * @param defender
	 *            is another Unit class object parameter who is defending against
	 *            the unit attacking it.
	 * @return
	 */
	public int[] calculateCombat(Unit initiator, Unit defender) {
		/*
		 * This function is used to calculate the battle between 2 units that haven't
		 * battled before. -The parameter initiator is the Unit object initiating the
		 * battle -The parameter defender is the Unit object defending against the
		 * initiator Unit. -It does this by going to another function called combat
		 * where it does all the operations for the first battle between 2 units: Where
		 * it takes the initiator unit and the defender unit, aswell as a boolean value
		 * that determines if the Units have battled before, since the battle hasn't
		 * started it is set to false.
		 */
		if (initiator != null && defender != null)
			return combat(initiator, defender, false);
		else
			return null;
	}

	/**
	 * This function actually applies the combat results
	 * 
	 * @param initiator
	 *            is a Unit class object who is starting the battle against another
	 *            unit
	 * @param defender
	 *            is another Unit class object parameter who is defending against
	 *            the unit attacking it.
	 */
	public void doCombat(Unit initiator, Unit defender) {
		/*
		 * This function is used to calculate the battle between 2 units that have
		 * battled before. -The parameter initiator is the Unit object initiating the
		 * battle -The parameter defender is the Unit object defending against the
		 * initiator Unit. -It does this by going to another function called combat
		 * where it does all the operations for a battle between 2 units: Where it takes
		 * the initiator unit and the defender unit, aswell as a boolean value that
		 * determines if the Units have battled before/just started, since the battle
		 * was initiated its set to true.
		 */
		if (initiator != null && defender != null)
			combat(initiator, defender, true);
	}

	/**
	 * This function does the combat between 2 units, with the option to actually
	 * apply the results of the combat or not
	 * 
	 * @param initiator
	 *            is the unit object parameter who is attacking the unit object
	 *            parameter defender.
	 * @param defender
	 *            is the unit object parameter who is getting attacked by the
	 *            initiator unit object parameter.
	 * @param applyCombat
	 *            determines whether to apply the results of combat.
	 * @return A integer array that contains the initiator and the defender's
	 *         currentHP's value (The initiator's currentHP value is the first index
	 *         of the array, while the defender's is the second index).
	 */
	protected int[] combat(Unit initiator, Unit defender, boolean applyCombat) {
		/*
		 * This function determines the combat between 2 units. -The parameter initiator
		 * is the Unit object that is attacking on that turn. -The parameter defender is
		 * the Unit object defending against the initiator Unit. -The parameter
		 * applyCombat determines whether it was the initiation round or not. This
		 * function does the following: -If the boolean value of applyCombat is false,
		 * it creates new Units for the initiator or defender, so that the actual units
		 * do not get modified
		 */
		if (!applyCombat) {
			defender = new Unit(defender);
			initiator = new Unit(initiator);
		}
		/*
		 * It then goes to this function with these parameters which determines how much
		 * damage the defender unit takes from the initiator unit attacking it.
		 */
		attack(initiator, defender, applyCombat);

		/*
		 * If the defender's has died (currentHP value is less than or equal to 0) it
		 * does the following: Initializes a integer array called temp and assigns
		 * initiator's currentHP instance value as the first index, and assigns a 0 for
		 * the second index, which represents the currentHP value of the defender. -It
		 * then returns the integer array of temp.
		 */
		if (defender.getCurrentHP() <= 0) {
			int[] temp = { initiator.getCurrentHP(), 0 };
			return temp;
		}
		/*
		 * Else If the defender unit's currentHP value is not less than or equal to 0 it
		 * does the following: -If it goes the function canCounterAttack with those
		 * parameter's and it returns true, it goes to the function attack with those
		 * parameters, indicating the defender parameter unit is within range to attack
		 * the initiator unit back.
		 */
		else if (canCounterAttack(initiator, defender)) {
			attack(defender, initiator, applyCombat);

		}
		/*
		 * If the initiator variable's getCurrentHP value is less than or equal to 0 it
		 * does the following: -It initializes a integer array called temp and assigns a
		 * 0 in the first index representing that initiator unit variable has been
		 * defeated, and then it registers the defender's currentHP value in the second
		 * index. -Then it returns the integer array temp.
		 */
		if (initiator.getCurrentHP() <= 0) {
			int[] temp = { 0, defender.getCurrentHP() };
			return temp;
		}
		/*
		 * Else if it goes the boolean function canDouble with those parameters and it
		 * returns true it does the following: -It goes to the function attack with
		 * those parameters allowing the initiator to attack the defending unit a second
		 * time in a single round if the condition in the canDouble function is returned
		 * as true.
		 */
		else if (canDouble(initiator, defender)) {
			attack(initiator, defender, applyCombat);
		}
		/*
		 * If the defender's currentHP instance value is equal to or less than 0 after
		 * that 2nd attack, it does the following: -It then initializes a integer array
		 * called temp, and register's the initiator's currentHP instance variable value
		 * to the first index of the array, and assigns a 0 to the 2nd index of the
		 * array representing that the defender unit has been defeated. -Then it returns
		 * the integer array temp.
		 */
		if (defender.getCurrentHP() <= 0) {
			int[] temp = { initiator.getCurrentHP(), 0 };
			return temp;
		}
		/*
		 * Else if it goes to the function canCounterAttack and canDouble and they both
		 * return true with those parameters, the defender attacks the initiator unit
		 * back.
		 */
		else if (canCounterAttack(initiator, defender) && canDouble(defender, initiator)) {
			attack(defender, initiator, applyCombat);
		}
		/**/
		/*-Then it initializes a integer array called temp, and registers the initiator unit's
		 * currentHP instance variable value into the first index, and registers the defender unit's
		 * currentHP instance variable into the second index.
		 * -It then returns the integer array temp*/
		if (applyCombat) {
			System.out.println("\n\n");

		}
		int[] temp = { initiator.getCurrentHP(), defender.getCurrentHP() };
		return temp;

	}

	/**
	 * This function determines the attacking phase for 2 units in battle, and how
	 * much damage the defender unit object takes after the initiator unit object
	 * attacks it.
	 * 
	 * @param initiator
	 *            is the unit object that is currently attacking against the
	 *            defender parameter unit.
	 * @param defender
	 *            is the unit object that is currently defending against the
	 *            attacking unit parameter initiator.
	 * @param applyCombat
	 *            is a boolean object that determines if it should display messages
	 *            for the battle between 2 units, such as what unit attacks another,
	 *            and if the defending unit defender has been defeated.
	 */

	protected void attack(Unit initiator, Unit defender, boolean applyCombat) {
		/*
		 * This function is used when the attack stage of a unit begins. -The unit
		 * object parameter initiator is the unit that is attacking on that turn. -The
		 * unit object parameter defender is the unit that is taking the attack by the
		 * initiator unit on that turn. -The boolean value of applyCombat determines on
		 * if the battle-log messages should display or not. This function does the
		 * following with the indicated parameters: -It goes to the function
		 * calculateDamage to determine the damage the defender parameter unit takes,
		 * and the value that is returned is registered under the integer variable
		 * damage. -It then calls the takeDamage method on the defender unit object
		 * parameter, where the defender unit takes the value of damage saved in the
		 * variable damage.
		 */
		int damage = calculateDamage(initiator, defender);
		defender.takeDamage(damage);

		/*
		 * -Then if the applyCombat boolean value is true, it displays a message saying
		 * what attacking unit (initiator unit parameter) attacked what defending
		 * unit(defender unit parameter), and how much damage was dealt onto the
		 * defender parameter unit on that turn. -Then if the defender's currentHP
		 * instance variable value reaches 0 upon the attack, it displays a message
		 * saying what defender parameter unit was defeated.
		 */
		if (applyCombat) {
			System.out.println(initiator.getName() + " attacks " + defender.getName() + " for " + damage);
			if (defender.getCurrentHP() <= 0) {
				System.out.println(defender.getName() + " has been defeated");
			}
		}

	}

	/**
	 * This function determines if the defender unit is able to attack the initiator
	 * unit back or not.
	 * 
	 * @param initiator
	 *            is the unit object that the defender can attack back, if the
	 *            initiator unit object is within the defender unit object's own
	 *            attack range.
	 * @param defender
	 *            is the unit object that will attack the initiator back, if it is
	 *            within the defender's attack range.
	 * @return true if the defender can counter attack, else it returns false
	 *         indicating that the defending unit cannot attack the initiator unit
	 *         back.
	 */
	protected static boolean canCounterAttack(Unit initiator, Unit defender) {
		/*
		 * This function determines if the defender unit is able to attack the initiator
		 * unit back. -It determines this by seeing if the attack range value of both
		 * the initiator and the defender unit are equivalent if so it returns true
		 * indicating that the defending unit is able to attack back, else it returns
		 * false.
		 */
		return defender.getRange() == initiator.getRange();
	}

	/**
	 * This function determines how much damage a attacker does against another
	 * unit.
	 * 
	 * @param initiator
	 *            is a unit object parameter who is damaging the defender unit
	 *            object parameter.
	 * @param defender
	 *            is a unit object parameter who is defending against the damage
	 *            done by the initiator unit.
	 * @return a integer value that determines how much damage the defender unit
	 *         object takes against the initiator unit.
	 */
	protected static int calculateDamage(Unit initiator, Unit defender) {
		/*
		 * This function determines how much damage the defending unit takes from the
		 * attacking unit. -It does this by subtracting the initiator's attack points by
		 * the defender's defensive points, and that value that is returns is how much
		 * damage the defending unit takes as a attack from the initiator unit.
		 */
		return initiator.getAtk() - defender.getDef();
	}

	/**
	 * This function determines if the initiator unit object is able to attack twice
	 * in a single turn.
	 * 
	 * @param initiator
	 *            is the unit object that is attacking against the defender unit
	 *            used to see if it can attack twice in a single turn, by see if the
	 *            initiator's speed is greater than or equal to 5, after it is
	 *            subtracted by the defender's speed.
	 * @param defender
	 *            is the unit object that is used to determine if the speed of the
	 *            initiator unit is greater than or equal to the defender unit by a
	 *            value of 5.
	 * @return a boolean value on if the speed points of the initiator unit is
	 *         greater than or equal to a value of 5, after the speed of the
	 *         initiator unit has been subtracted by the speed of the defending
	 *         unit.
	 */
	protected static boolean canDouble(Unit initiator, Unit defender) {
		/*
		 * This function determines if the current attacking unit(initiator unit
		 * parameter) can attack twice in a single round, against the defending
		 * unit(defender unit parameter). -It does this by seeing if the initiator
		 * unit's speed after being subtracted by the defender's speed value is greater
		 * than or equal to 5. If it is, it returns a boolean value of True indicating
		 * that the attacking unit is able to attack twice in a single round, else it
		 * returns False.
		 */
		return initiator.getSpd() - defender.getSpd() >= 5;
	}
}
