/**
 * 
 */
package gui;
//imports the following java libraries
import logic.AI;
import logic.AIMove;
import logic.Unit;

/**
 * @author charl
 */
public class AIGUI extends AI {
	//creates the following instance variables
	protected CombatGUI combatGUI;
	protected MapGUI mapGUI;
	/**
	 * a constructor for the AIGUI
	 * @param mapGUI the mapGUI object/map that you want to register
	 * @param combatGUI the CombatGUI object you want to register
	 */
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
	 * @return gameOver returns true if its the computer's turn
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
