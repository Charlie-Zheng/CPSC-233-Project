/**
 * 
 */
package main;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Class used to display the map
 * 
 * @author charl
 */
public class MapGUI {
	private ImageView[][] terrainDisplay;
	private ImageView[] unitDisplay;
	private Group root;
	private Map map;
	private Rectangle[][] colourOverlay;

	private ArrayList<Rectangle> unitStatDisplays = new ArrayList<Rectangle>();

	public MapGUI(Map map, Group root) {
		this.map = map;
		this.root = root;

	}

	public void addBlue(int y, int x) {
		colourOverlay[y][x].setFill(Color.hsb(210, 1, 1, 0.5));
	}

	public void addRed(int y, int x) {
		colourOverlay[y][x].setFill(Color.hsb(0, 1, 1, 0.5));
	}

	public void addYellow(int y, int x) {
		colourOverlay[y][x].setFill(Color.hsb(60, 1, 1, 0.4));
	}

	public void addGreen(int y, int x) {
		colourOverlay[y][x].setFill(Color.hsb(30, 1, 1, 0.4));
	}

	public void removeColour(int y, int x) {
		colourOverlay[y][x].setFill(Color.hsb(210, 1, 1, 0.0));
	}

	public void removeAllColours() {
		for (int y = 0; y < map.MAXY; y++) {
			for (int x = 0; x < map.MAXX; x++) {
				removeColour(y, x);
			}
		}
	}

	/**
	 * Updates the images on the GUI to correspond to those on the map
	 */
	public void updateUnitsOnMap() {
		for (int x = 0; x < unitDisplay.length; x++) {
			unitDisplay[x].setVisible(false);
			if (x < map.getUnitList().size()) {
				Unit unit = map.getUnitList().get(x);
				if (unit.isAlive()) {
					unitDisplay[x].setX(unit.getX() * TerrainGUI.getImagewidth());
					unitDisplay[x].setY(unit.getY() * TerrainGUI.getImageheight());
					unitDisplay[x].setVisible(true);
				}
			}
		}
	}

	// /**
	// * move Units on GUI map
	// *
	// * @param y: new y position of unit
	// * @param x: new x position of unit
	// * @param okay: boolean value, check if unit can have legal move
	// */
	// public void moveUnitsOnGUI(Unit currentUnit, int y, int x, boolean okay) {
	// if (currentUnit.isFriendly() && okay) {
	// Unit friendlyUnit = currentUnit;
	// int currentX = friendlyUnit.getX();
	// int currentY = friendlyUnit.getY();
	// map.moveHero(currentX, currentY, x, y);
	// }
	// }

	public void loadMapGUI() {
		terrainDisplay = new ImageView[map.MAXY][map.MAXY];
		unitDisplay = new ImageView[map.getUnitList().size()];
		colourOverlay = new Rectangle[map.MAXY][map.MAXY];
		GridPane terrain = new GridPane();
		TerrainGUI.initializeImages();
		for (int y = 0; y < map.MAXY; y++) {
			for (int x = 0; x < map.MAXX; x++) {
				switch (map.getTerrainMap()[y][x]) {
				case TREE:
					terrainDisplay[y][x] = new ImageView(TerrainGUI.getTreeImage());
					break;
				case FLAT:
					terrainDisplay[y][x] = new ImageView(TerrainGUI.getFlatImage());
				default:
					break;
				}
				// terrainDisplay[y][x].setX(x * TerrainGUI.getImagewidth());
				// terrainDisplay[y][x].setY(y * TerrainGUI.getImageheight());

				terrain.add(terrainDisplay[y][x], x, y);
			}

		}
		root.getChildren().add(terrain);
		for (int y = 0; y < map.MAXY; y++) {
			for (int x = 0; x < map.MAXX; x++) {
				colourOverlay[y][x] = new Rectangle(x * TerrainGUI.getImagewidth(), y * TerrainGUI.getImageheight(),
						TerrainGUI.getImagewidth(), TerrainGUI.getImageheight());
				colourOverlay[y][x].setFill(Color.TRANSPARENT);
				colourOverlay[y][x].setMouseTransparent(false);
				colourOverlay[y][x].setOnMouseClicked(new SelectedTile(x, y, map, this));
				colourOverlay[y][x].setOnMouseEntered(new HighlightTile(terrainDisplay[y][x], true));
				colourOverlay[y][x].setOnMouseExited(new HighlightTile(terrainDisplay[y][x], false));
				root.getChildren().add(colourOverlay[y][x]);

			}
		}
		UnitGUI.initializeImages();
		int counter = 0;
		for (Unit unit : map.getUnitList()) {
			unitDisplay[counter] = new ImageView();

			switch (unit.getMoveType()) {
			case CAVALRY:
				unitDisplay[counter].setImage(UnitGUI.getHorse());

				break;
			case INFANTRY:
				unitDisplay[counter].setImage(UnitGUI.getSword());
			default:
				break;
			}
			UnitGUI.applyFactionColor(unitDisplay[counter], unit.isFriendly());
			unitDisplay[counter].setX(unit.getX() * TerrainGUI.getImagewidth());
			unitDisplay[counter].setY(unit.getY() * TerrainGUI.getImageheight());
			unitDisplay[counter].setMouseTransparent(true);
			root.getChildren().add(unitDisplay[counter]);
			counter++;

		}
		counter = 0;
		for (Unit unit : map.getUnitList()) {
			Rectangle unitStatDisplay = new Rectangle(map.MAXX * TerrainGUI.getImagewidth(), counter * 75, 250, 75);
			counter++;
			if (unit.isFriendly())
				unitStatDisplay.setFill(Color.hsb(210, 0.5, 1));
			else
				unitStatDisplay.setFill(Color.hsb(0, 0.5, 1));
			unitStatDisplay.setStroke(Color.BLACK);
			unitStatDisplays.add(unitStatDisplay);
			root.getChildren().add(unitStatDisplay);
		}

	}

}
