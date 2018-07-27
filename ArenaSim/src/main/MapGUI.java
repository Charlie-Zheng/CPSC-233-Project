/**
 * 
 */
package main;

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

	public MapGUI(Map map, ImageView[][] terrainDisplay, ImageView[] unitDisplay, Group root) {
		this.map = map;
		this.terrainDisplay = terrainDisplay;
		this.unitDisplay = unitDisplay;
		this.root = root;
		// TODO Auto-generated constructor stub
	}

	public void addBlue(int y, int x) {
		colourOverlay[y][x].setFill(Color.hsb(210, 1, 1, 0.5));
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
		int counter = 0;
		for (Unit unit : map.getUnitList()) {
			unitDisplay[counter].setX(unit.getX() * TerrainGUI.getImagewidth());
			unitDisplay[counter].setY(unit.getY() * TerrainGUI.getImageheight());
			counter++;
		}
	}

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

	}
}
