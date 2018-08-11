/**
 * 
 */
package main;

/**
 * @author charl
 *
 */
public class AIGUI extends AI{

	/**
	 * 
	 */
	protected CombatGUI combatGUI;
	public AIGUI(MapGUI mapGUI) {
		// TODO Auto-generated constructor stub
		super(mapGUI);
		combatGUI = new CombatGUI(mapGUI);
	}
	
	@Override
	protected void applyAIMove(AIMove move) {
		Unit unit = move.getUnit();
		int unitX = unit.getX();
		int unitY = unit.getY();

		// Move the hero
		map.moveHero(unitX, unitY, move.getX(), move.getY());
		unit.setHasMoved(true);
		// Print the movement to the user
		Unit target = map.getUnitMap()[move.getJ()][move.getI()];
		if (target != unit) {
			// Apply the combat if the unit chose to attack
			combatGUI.doCombat(unit, target);
		}

	}

}
