package main;

public final class Combat {

	public static void calculateCombat(Unit initiator, Unit defender) {
		combat(initiator, defender, false);
	}

	public static void doCombat(Unit initiator, Unit defender) {
		combat(initiator, defender, true);
	}

	private static int[] combat(Unit initiator, Unit defender, boolean applyCombat) {
		// First attack of initiator
		if (!applyCombat) {
			defender = new Unit(defender);
			initiator = new Unit(initiator);
		}
		attack(initiator, defender, applyCombat);
		// First attack of defender, if able
		if (defender.getCurrentHP() < 0) {
			int[] temp = { initiator.getCurrentHP(), 0 };
			return temp;
		} else if (canCounterAttack(initiator, defender)) {
			attack(defender, initiator, applyCombat);
		}
		// second attack of initiator, if able
		if (initiator.getCurrentHP() < 0) {
			int[] temp = { 0, defender.getCurrentHP() };
			return temp;
		} else if (canDouble(initiator, defender)) {
			attack(initiator, defender, applyCombat);
		}
		// second attack of defender, if able
		System.out.println(defender.getSpd() - initiator.getSpd());
		if (defender.getCurrentHP() < 0) {
			int[] temp = { initiator.getCurrentHP(), 0 };
			return temp;
		} else if (canCounterAttack(initiator, defender) && canDouble(defender, initiator)) {
			attack(defender, initiator, applyCombat);
		}
		int[] temp = { initiator.getCurrentHP(), defender.getCurrentHP() };
		return temp;

	}

	private static void attack(Unit initiator, Unit defender, boolean applyCombat) {
		int damage = calculateDamage(initiator, defender);
		defender.takeDamage(damage);
		if (applyCombat) {
			System.out.println(initiator.getName() + " attacks " + defender.getName() + " for " + damage);
			if (defender.getCurrentHP() <= 0) {
				System.out.println(defender.getName() + " has been defeated");
			}
		}

	}

	private static boolean canCounterAttack(Unit initiator, Unit defender) {
		return defender.getRange() == initiator.getRange();
	}

	private static int calculateDamage(Unit initiator, Unit defender) {
		return initiator.getAtk() - defender.getDef();
	}

	private static boolean canDouble(Unit initiator, Unit defender) {
		return initiator.getSpd() - defender.getSpd() >= 5;
	}
}
