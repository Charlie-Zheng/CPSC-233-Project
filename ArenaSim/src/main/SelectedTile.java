package main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SelectedTile implements EventHandler<MouseEvent> {
	private int x, y;
	private PlayerGUI gui;
	private Map map;
	private MapGUI mapGUI;
	private static boolean selectingMove = false;
	private static Unit selectedUnit;
	private static boolean selectingAttack;

	public SelectedTile(int x, int y, Map map, MapGUI mapGUI) {
		this.map = map;
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
			selectedUnit = map.getUnitMap()[y][x];
			if (selectedUnit != null && selectedUnit.isFriendly()) {
				if (!selectedUnit.hasMoved()) {
					selectingMove = true;
					boolean[][] availableMoves = map.findAvailableMoves(selectedUnit);
					boolean[][] allAttacks = map.findAllAttacks(selectedUnit);
					for (int y = 0; y < map.MAXY; y++) {
						for (int x = 0; x < map.MAXX; x++) {
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
				boolean[][] AIAttacks = map.findAllAttacks(selectedUnit);
				mapGUI.removeAllColorsAndText();
				for (int y = 0; y < map.MAXY; y++) {
					for (int x = 0; x < map.MAXX; x++) {
						if (AIAttacks[y][x])
							mapGUI.addRed(y, x);
					}
				}
			}
		} else if (selectingMove) {
			
			mapGUI.removeAllColorsAndText();
			if (map.checkMoveLegal(selectedUnit, x - selectedUnit.getX(), y - selectedUnit.getY())
					&& !selectedUnit.hasMoved()) {
				map.moveHero(selectedUnit, x - selectedUnit.getX(), y - selectedUnit.getY());
				selectedUnit.setHasMoved(true);
				selectingAttack = true;
				boolean[][] AttackRange = map.findRange(selectedUnit);
				for (int y = 0; y < map.MAXY; y++) {
					for (int x = 0; x < map.MAXX; x++) {
						// shows the user the current unit's available attack range in red.
						if (AttackRange[y][x]) {
							mapGUI.addRed(y, x);
						}
						if(AttackRange[y][x] && x==selectedUnit.getX() &&y==selectedUnit.getY()) {
							mapGUI.removeColour(y, x);
						}
					}
				}
			}

			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors
			selectingMove = false;

		} else if (selectingAttack) {
			boolean[][] AttackRange = map.findRange(selectedUnit);
			if (AttackRange[y][x] && map.getUnitMap()[y][x] != null && !map.getUnitMap()[y][x].isFriendly()) {
				Combat.doCombat(selectedUnit, map.getUnitMap()[y][x]);
				map.updateHeroDeaths();
			}
			selectingAttack = false;
			selectedUnit = null;
			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors
			mapGUI.removeAllColorsAndText();
			if (!map.gameOver() && !map.factionHasUnmovedUnits(true)) {
				AI.computerTurn(map);
				mapGUI.updateUnitsOnMap();
				map.resetHasMoved(true);
			}
		}
	}

}
