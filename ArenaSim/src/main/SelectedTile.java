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

			} else if (selectedUnit != null && !selectedUnit.isFriendly()) {
				// display the enemy's possible attack range in red. Will need to use
				// findAvailableMoves along with findRange to determine these tiles
				boolean[][] AIAttacks = map.findAllAttacks(selectedUnit);
				mapGUI.removeAllColours();
				for (int y = 0; y < map.MAXY; y++) {
					for (int x = 0; x < map.MAXX; x++) {
						if (AIAttacks[y][x])
							mapGUI.addRed(y, x);
					}
				}
			}
		} else if (selectingMove) {
			// System.out.println(map.getUnitList());
//			map.findAvailableMoves(selectedUnit);
//			boolean[][] availableMoves = map.findAvailableMoves(selectedUnit);
//			for (int i = 0; i < map.MAXY; i++) {
//				for (int j = 0; j < map.MAXX; j++) {
//					if (availableMoves[i][j]) {
//						mapGUI.moveUnitsOnGUI(selectedUnit, y, x, availableMoves[y][x]);
//					}
//				}
//			}
			if(map.checkMoveLegal(selectedUnit, x-selectedUnit.getX(), y-selectedUnit.getY())) {
				map.moveHero(selectedUnit, x-selectedUnit.getX(),  y-selectedUnit.getY());
			}
		

			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors
			mapGUI.removeAllColours();
			boolean[][] AttackRange = map.findRange(selectedUnit);
			for (int y = 0; y < map.MAXY; y++) {
				for (int x = 0; x < map.MAXX; x++) {
					// shows the user the current unit's available attack range in red.

					if (AttackRange[y][x]) {
						mapGUI.addRed(y, x);
					}
	
				}
			}
			selectingMove = false;
			selectingAttack = true;
		} else if(selectingAttack) {
			boolean[][] AttackRange = map.findRange(selectedUnit);
			if(AttackRange[y][x] && map.getUnitMap()[y][x] != null && !map.getUnitMap()[y][x].isFriendly()) {
				Combat.doCombat(selectedUnit, map.getUnitMap()[y][x]);
				map.updateHeroDeaths();
			}
			selectingAttack  =false;
			selectedUnit = null;
			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors
			mapGUI.removeAllColours();
			
		}
	}

}
