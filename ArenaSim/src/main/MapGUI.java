/**
 * 
 */
package main;

import java.io.PrintStream;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class used to display the map
 * 
 * @author charl
 */
public class MapGUI extends Map{
	private ImageView[][] terrainDisplay;
	private ImageView[] unitDisplay;
	private Group root;
	private Rectangle[][] colourOverlay;
	private TextArea consoleText;
	private ArrayList<StackPane> unitStatDisplays = new ArrayList<StackPane>();
	private final int unitDisplayHeight = 75;

	

	public MapGUI(String filename, Group root) {
		super(filename);
		this.root = root;

	}

	public void addBlue(int y, int x) {
		addColour(y, x, Color.hsb(210, 1, 1, 0.3));
	}

	public void addRed(int y, int x) {
		addColour(y, x, Color.hsb(0, 1, 1, 0.3));
	}

	public void addYellow(int y, int x) {
		addColour(y, x, Color.hsb(60, 1, 1, 0.3));
	}

	public void addGreen(int y, int x) {
		addColour(y, x, Color.hsb(30, 1, 1, 0.3));
	}

	public void removeColour(int y, int x) {
		addColour(y, x, Color.hsb(30, 1, 1, 0.0));
	}

	public void addColour(int y, int x, Color color) {
		colourOverlay[y][x].setFill(color);
	}

	public void removeAllColorsAndText() {
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
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
			StackPane display = unitStatDisplays.get(x);
			if (x < getUnitList().size()) {
				Unit unit = getUnitList().get(x);
				if (unit.isAlive()) {
					unitDisplay[x].setX(unit.getX() * TerrainGUI.getImagewidth());
					unitDisplay[x].setY(unit.getY() * TerrainGUI.getImageheight());
					unitDisplay[x].setVisible(true);
				}else {
					if (unit.isFriendly()) {
						display.setBackground(
								new Background(new BackgroundFill(Color.hsb(210, 0.5, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));
					} else {
						display.setBackground(
								new Background(new BackgroundFill(Color.hsb(0, 0.5, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));
					}
				}
				
				Text stats = (Text) display.getChildren().get(0);
				stats.setText(unit.getName() + "\nHP: " + unit.getCurrentHP() + "/" + unit.getBaseHP() + "\t\tAtk: "
						+ unit.getAtk() + "\nSpd: " + unit.getSpd() + "\t\tDef: " + unit.getDef() + "\nRange: "
						+ unit.getRange() + "\t\tMove Type: " + unit.getMoveType());
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
	// moveHero(currentX, currentY, x, y);
	// }
	// }

	public void loadMapGUI() {
		terrainDisplay = new ImageView[MAXY][MAXY];
		unitDisplay = new ImageView[getUnitList().size()];
		colourOverlay = new Rectangle[MAXY][MAXY];
		GridPane terrain = new GridPane();
		TerrainGUI.initializeImages();
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				switch (getTerrainMap()[y][x]) {
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
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				colourOverlay[y][x] = new Rectangle(x * TerrainGUI.getImagewidth(), y * TerrainGUI.getImageheight(),
						TerrainGUI.getImagewidth(), TerrainGUI.getImageheight());
				colourOverlay[y][x].setFill(Color.TRANSPARENT);
				colourOverlay[y][x].setMouseTransparent(false);
				colourOverlay[y][x].setOnMouseClicked(new SelectedTile(x, y, this));
				colourOverlay[y][x].setOnMouseEntered(new HighlightTile(terrainDisplay[y][x], true,x,y,this,unitStatDisplays));
				colourOverlay[y][x].setOnMouseExited(new HighlightTile(terrainDisplay[y][x], false,x,y,this,unitStatDisplays));
				root.getChildren().add(colourOverlay[y][x]);

			}
		}
		UnitGUI.initializeImages();
		int counter = 0;
		for (Unit unit : getUnitList()) {
			unitDisplay[counter] = new ImageView();


				unitDisplay[counter].setImage(UnitGUI.getUnitImage(unit));

			
			UnitGUI.applyFactionColor(unitDisplay[counter], unit.isFriendly());
			unitDisplay[counter].setX(unit.getX() * TerrainGUI.getImagewidth());
			unitDisplay[counter].setY(unit.getY() * TerrainGUI.getImageheight());
			unitDisplay[counter].setMouseTransparent(true);
			root.getChildren().add(unitDisplay[counter]);
			counter++;

		}
		counter = 0;
		for (Unit unit : getUnitList()) {
			StackPane unitStatDisplay = new StackPane();

			unitStatDisplay.setLayoutX(MAXX * TerrainGUI.getImagewidth());
			unitStatDisplay.setLayoutY(counter * unitDisplayHeight);
			unitStatDisplay.setPrefSize(250, 75);

			if (unit.isFriendly()) {
				unitStatDisplay.setBackground(
						new Background(new BackgroundFill(Color.hsb(210, 0.5, 1), CornerRadii.EMPTY, Insets.EMPTY)));
			} else {
				unitStatDisplay.setBackground(
						new Background(new BackgroundFill(Color.hsb(0, 0.5, 1), CornerRadii.EMPTY, Insets.EMPTY)));
			}
			Text stats = new Text();
			stats.setText(unit.getName() + "\nHP: " + unit.getCurrentHP() + "/" + unit.getBaseHP() + "\t\tAtk: "
					+ unit.getAtk() + "\nSpd: " + unit.getSpd() + "\t\tDef: " + unit.getDef() + "\nRange: "
					+ unit.getRange() + "\t\tMove Type: " + unit.getMoveType());
			unitStatDisplay.getChildren().add(stats);
			unitStatDisplay.setLayoutX(MAXX * TerrainGUI.getImagewidth());
			unitStatDisplay.setLayoutY(counter * unitDisplayHeight);
			unitStatDisplays.add(unitStatDisplay);

			root.getChildren().add(unitStatDisplay);
			counter++;
		}
		consoleText = new TextArea();
		consoleText.setLayoutY(MAXY * TerrainGUI.getImagewidth());
		consoleText.setEditable(false);
		consoleText.setPrefSize(MAXX * TerrainGUI.getImageheight(), 150);
		PrintStream ps = System.out;
		System.setOut(new TextStreamGUI(consoleText, ps));
		root.getChildren().add(consoleText);
		
	}

}
