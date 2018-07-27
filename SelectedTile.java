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
				boolean[][] AttackRange = map.findRange(selectedUnit);
				for (int y = 0; y < map.MAXY; y++) {
					for (int x = 0; x < map.MAXX; x++) {
						//highlights the Player unit's available movement in blue
						if (availableMoves[y][x])
							mapGUI.addBlue(y, x);
						
						//highlights the player unit's attack range in red.
						if(AttackRange[y][x]) {
							mapGUI.addRed(y, x);
						}
					}
				}

			} else if (selectedUnit != null && !selectedUnit.isFriendly()) {
				// display the enemy's possible attack range in red. Will need to use
				// findAvailableMoves along with findRange to determine these tiles
				boolean[][] canAttack = new boolean[map.MAXY][map.MAXX];
				boolean[][] AttackRangeAI = map.findRange(selectedUnit);
				boolean[][] AIMove = map.findAvailableMoves(selectedUnit);
				mapGUI.removeAllColours();
				for(int y=0; y<map.MAXY;y++) {
					for(int x=0;x<map.MAXX;x++) {
						//displays a enemy unit's available move range(in yellow)
						if(AIMove[y][x]) {
							mapGUI.addYellow(y, x);
						}
						//displays a enemy unit's attack range(in red)
						if(AttackRangeAI[y][x]) {
							mapGUI.addRed(y,x);
						}
					}
				}
			}
		} else {
			System.out.println(x + " " + y);
			// System.out.println(map.getUnitList());
			map.findAvailableMoves(selectedUnit);
			boolean[][] availableMoves = map.findAvailableMoves(selectedUnit);
			for (int i = 0; i < map.MAXY; i++) {
				for (int j = 0; j < map.MAXX; j++) {
					if (availableMoves[i][j]) {
						mapGUI.moveUnitsOnGUI(y, x, availableMoves[y][x]);
					}
				}
			}

			
			mapGUI.updateUnitsOnMap();
			// What happens when you are selecting a move
			// Move the unit to somewhere or remove all the colors

			mapGUI.removeAllColours();
			selectingMove = false;
		}
	}

}
