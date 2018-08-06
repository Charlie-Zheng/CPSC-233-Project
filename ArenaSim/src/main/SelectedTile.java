package main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SelectedTile implements EventHandler<MouseEvent> {
	private int x, y;
	private Map map;
	private MapGUI mapGUI;
	private static boolean selectingMove = false;
	private static Unit selectedUnit;
	private static boolean selectingAttack;

	public SelectedTile(int x, int y, MapGUI mapGUI) {
		this.x = x;
		this.y = y;
		this.mapGUI = mapGUI;
	}

	// public SelectedTile(int x, int y) {
	// // TODO Auto-generated constructor stub
	// this.x = x;
	// this.y = y;
	// }

	@Override
	public void handle(MouseEvent e) {
		// Highlights moveable tiles to be blue
		if (!selectingMove && !selectingAttack) {// what happens when you click while not selecting a move or an attack
			mapGUI.updateUnitsOnMap();
			selectedUnit = mapGUI.getUnitMap()[y][x];
			if (selectedUnit != null && selectedUnit.isFriendly()) {
				if (!selectedUnit.hasMoved()) {
					selectingMove = true;
					boolean[][] availableMoves = mapGUI.findAvailableMoves(selectedUnit);
					boolean[][] allAttacks = mapGUI.findAllAttacks(selectedUnit);
					for (int y = 0; y < mapGUI.MAXY; y++) {
						for (int x = 0; x < mapGUI.MAXX; x++) {
							// shows the user the current unit's available attack range in red.

							if (allAttacks[y][x]) {
								mapGUI.addRed(y, x);
							}
							// Indicates what grid the user's unit is able to move on in blue
							if (availableMoves[y][x])
								mapGUI.addBlue(y, x);
						}
					}
				}else {
		
				}

			} else if (selectedUnit != null && !selectedUnit.isFriendly()) {
				// display the enemy's possible attack range in red. Will need to use
				// findAvailableMoves along with findRange to determine these tiles
				boolean[][] AIAttacks = mapGUI.findAllAttacks(selectedUnit);
				mapGUI.removeAllColorsAndText();
				for (int y = 0; y < mapGUI.MAXY; y++) {
					for (int x = 0; x < mapGUI.MAXX; x++) {
						if (AIAttacks[y][x])
							mapGUI.addRed(y, x);
					}
				}
			}
		} else if (selectingMove) {
			
			mapGUI.removeAllColorsAndText();
			if (mapGUI.checkMoveLegal(selectedUnit, x - selectedUnit.getX(), y - selectedUnit.getY())
					&& !selectedUnit.hasMoved()) {
				mapGUI.moveHero(selectedUnit, x - selectedUnit.getX(), y - selectedUnit.getY());
				selectedUnit.setHasMoved(true);
				selectingAttack = true;
				boolean[][] AttackRange = mapGUI.findRange(selectedUnit);
				for (int y = 0; y < mapGUI.MAXY; y++) {
					for (int x = 0; x < mapGUI.MAXX; x++) {
						// shows the user the current unit's available attack range in red.
						if (AttackRange[y][x]) {
							mapGUI.addRed(y, x);
						}
						/*if(AttackRange[y][x] && x==selectedUnit.getX() &&y==selectedUnit.getY()) {
							mapGUI.removeColour(y, x);
						}*/
					}
				}
			}

			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors
			selectingMove = false;

		} else if (selectingAttack) {
			boolean[][] AttackRange = mapGUI.findRange(selectedUnit);
			if (AttackRange[y][x] && mapGUI.getUnitMap()[y][x] != null && !mapGUI.getUnitMap()[y][x].isFriendly()) {
				Combat.doCombat(selectedUnit, mapGUI.getUnitMap()[y][x]);
				mapGUI.updateHeroDeaths();
			}
			selectingAttack = false;
			selectedUnit = null;
			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors
			mapGUI.removeAllColorsAndText();
			if (!mapGUI.gameOver() && !mapGUI.factionHasUnmovedUnits(true)) {
				AI.computerTurn(map);
				mapGUI.updateUnitsOnMap();
				mapGUI.resetHasMoved(true);
			}
		}
	}

}
