/**
 * 
 */
package main;

/**
 * @author charl
 */
public class AIGUI extends AI {

	/**
	 * 
	 */
	protected CombatGUI combatGUI;
	protected MapGUI mapGUI;

	public AIGUI(MapGUI mapGUI, CombatGUI combatGUI) {
		// TODO Auto-generated constructor stub
		super(mapGUI);
		this.mapGUI = mapGUI;
		this.combatGUI = combatGUI;
	}

	@Override
	/**
	 * Starts the computer's turn, and resets the user's units movements when the AI is done
	 * 
	 * @return gameOver
	 */
	public boolean computerTurn() {

		boolean gameOver = super.computerTurn();
		mapGUI.resetHasMoved(true);
		return gameOver;
	}

	@Override
	/**
	 * Adds animations instead of printing IA movements 
	 */
	protected void applyAIMove(AIMove move) {
		Unit unit = move.getUnit();
		int unitX = unit.getX();
		int unitY = unit.getY();

		// Move the hero
		map.moveHero(unitX, unitY, move.getX(), move.getY());
		System.out.print(move.toString() + "\n");
		mapGUI.updateUnitsOnMap();
		unit.setHasMoved(true);
		Unit target = map.getUnitMap()[move.getJ()][move.getI()];
		if (target != unit) {
			// Apply the combat if the unit chose to attack
			combatGUI.doCombat(unit, target);
		}

	}

}
