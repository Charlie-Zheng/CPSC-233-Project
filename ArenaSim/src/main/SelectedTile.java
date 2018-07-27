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
		if (!selectingMove && !selectingAttack) {
			mapGUI.updateUnitsOnMap();
			selectedUnit = map.getUnitMap()[y][x];
			if (selectedUnit != null && selectedUnit.isFriendly()) {
				selectingMove = true;
				boolean[][] availableMoves = map.findAvailableMoves(selectedUnit);
				for (int y = 0; y < map.MAXY; y++) {
					for (int x = 0; x < map.MAXX; x++) {
						if (availableMoves[y][x])
							mapGUI.addBlue(y, x);
					}
				}

			} else if (selectedUnit != null && !selectedUnit.isFriendly()) {
				// display the enemy's possible attack range in red. Will need to use
				// findAvailableMoves along with findRange to determine these tiles
				boolean[][] canAttack = new boolean[map.MAXY][map.MAXX];
				for (int y = 0; y < map.MAXY; y++) {

				}
			}
		} else {// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors

			mapGUI.removeAllColours();
			selectingMove = false;
		}
	}

}
