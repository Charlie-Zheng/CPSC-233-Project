package main;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SelectedTile implements EventHandler<MouseEvent> {
	private int x, y;
	private MapGUI mapGUI;
	private static boolean selectingMove = false;
	private static Unit selectedUnit;
	private static boolean selectingAttack;
	private ArrayList<StackPane> unitStatDisplays;
	private CombatGUI combat;

	public SelectedTile(int x, int y, MapGUI mapGUI) {
		this.x = x;
		this.y = y;
		this.mapGUI = mapGUI;
		combat = new CombatGUI(mapGUI);
	}

	// public SelectedTile(int x, int y) {
	// // TODO Auto-generated constructor stub
	// this.x = x;
	// this.y = y;
	// }

	@SuppressWarnings("unused")
	@Override
	public void handle(MouseEvent e) {
		if (!mapGUI.isAnimating()) {
			// Highlights moveable tiles to be blue
			if (!selectingMove && !selectingAttack) {// what happens when you click while not selecting a move or an
														// attack
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
					} else {

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
				// User clicked a place to move first
				if (mapGUI.checkMoveLegal(selectedUnit, x - selectedUnit.getX(), y - selectedUnit.getY())
						&& !selectedUnit.hasMoved()) {
					mapGUI.moveHero(selectedUnit, x - selectedUnit.getX(), y - selectedUnit.getY());
					selectedUnit.setHasMoved(true);
					selectingAttack = true;
					mapGUI.updateUnitsOnMap();
					boolean[][] AttackRange = mapGUI.findRange(selectedUnit);
					for (int y = 0; y < mapGUI.MAXY; y++) {
						for (int x = 0; x < mapGUI.MAXX; x++) {
							// shows the user the current unit's available attack range in red.
							if (AttackRange[y][x]) {
								mapGUI.addRed(y, x);
							}
							/*
							 * if(AttackRange[y][x] && x==selectedUnit.getX() &&y==selectedUnit.getY()) {
							 * mapGUI.removeColour(y, x); }
							 */
						}
					}
				} else if (mapGUI.findAllAttacks(selectedUnit)[y][x] && mapGUI.getUnitMap()[y][x] != null) {
					int[] movementTile = mapGUI.findClosestAttackLocation(selectedUnit, mapGUI.getUnitMap()[y][x]);
					if (movementTile != null) {
						mapGUI.moveHero(selectedUnit, movementTile[0] - selectedUnit.getX(),
								movementTile[1] - selectedUnit.getY());
						mapGUI.updateUnitsOnMap();
						combat.doCombat(selectedUnit, mapGUI.getUnitMap()[y][x]);
						selectedUnit.setHasMoved(true);
						selectedUnit = null;

						// What happens when you are selecting a move
						// Move the unit to somewhere or remove all the colors
						mapGUI.removeAllColorsAndText();
					}
				}

				// User clicked a location to attack
				// use a breadth first search to find the first location at which the selected
				// unit can attack the selected unit

				// What happens when you are selecting a move
				// Move the unit to somewhere or remove all the colors
				selectingMove = false;

			} else if (selectingAttack) {
				boolean[][] AttackRange = mapGUI.findRange(selectedUnit);
				if (AttackRange[y][x] && mapGUI.getUnitMap()[y][x] != null && !mapGUI.getUnitMap()[y][x].isFriendly()) {
					combat.doCombat(selectedUnit, mapGUI.getUnitMap()[y][x]);
				} else if (!mapGUI.gameOver() && !mapGUI.factionHasUnmovedUnits(true)) {
					turn();
					new AIGUI(mapGUI, combat).computerTurn();

				}
				selectingAttack = false;
				selectedUnit = null;
				// What happens when you are selecting a move
				// Move the unit to somewhere or remove all the colors
				mapGUI.removeAllColorsAndText();
			}
		}
	}

	public void turn() {
		PauseTransition visiblePause = new PauseTransition(Duration.seconds(3));
		Label playerTurn = new Label("Player's Turn");
		Label computerTurn = new Label();
		computerTurn.setText("Computer's" + "\n" + "Turn");
		playerTurn.setLayoutX(630);
		playerTurn.setLayoutY(850);
		playerTurn.setTextFill((Color.web("#124e8e")));
		playerTurn.setFont(Font.font("Ravie", 30));
		computerTurn.setLayoutX(613);
		computerTurn.setLayoutY(850);
		computerTurn.setTextFill((Color.web("#124e8e")));
		computerTurn.setFont(Font.font("Ravie", 30));

		if (!mapGUI.gameOver() && !mapGUI.factionHasUnmovedUnits(true)) {
			mapGUI.display(computerTurn);
			visiblePause.setOnFinished(event -> mapGUI.hide(computerTurn));
			visiblePause.play();
			// mapGUI.hide(computerTurn);
		}
	}

}
