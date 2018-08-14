/**
 * 
 */
package main;

import java.io.PrintStream;
import java.util.ArrayList;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.event.EventHandler;

/**
 * Class used to display the map
 * 
 * @author charl
 */
public class MapGUI extends Map {
	private ImageView[][] terrainDisplay;
	private ImageView[] unitDisplay;
	private Group root;
	private Rectangle[][] colourOverlay;
	private TextArea consoleText;
	private ArrayList<GridPane> unitStatDisplays = new ArrayList<GridPane>();
	private final int unitDisplayHeight = 75;
	private boolean isAnimating = false;

	/**
	 * @return the isAnimating
	 */
	protected boolean isAnimating() {
		return isAnimating;
	}

	/**
	 * @param isAnimating
	 *            the isAnimating to set
	 */
	protected void setAnimating(boolean isAnimating) {
		this.isAnimating = isAnimating;
	}
	//creates instance variable e which is a mouse event
	//creates a instance variable description which is a new stage.
	public MapGUI(String filename, Group root) {
		super(filename);
		this.root = root;

	}

	/**
	 * adds a blue overlay at the given tile
	 * 
	 * @param y
	 * @param x
	 */
	public void addBlue(int y, int x) {
		addColour(y, x, Color.hsb(210, 1, 1, 0.3));
	}

	/**
	 * adds a red overlay at the given tile
	 * 
	 * @param y
	 * @param x
	 */
	public void addRed(int y, int x) {
		addColour(y, x, Color.hsb(0, 1, 1, 0.3));
	}

	/**
	 * adds a yellow overlay at the given tile
	 * 
	 * @param y
	 * @param x
	 */
	public void addYellow(int y, int x) {
		addColour(y, x, Color.hsb(60, 1, 1, 0.3));
	}

	/**
	 * adds a green overlay at the given tile
	 * 
	 * @param y
	 * @param x
	 */
	public void addGreen(int y, int x) {
		addColour(y, x, Color.hsb(30, 1, 1, 0.3));
	}

	/**
	 * Removes all color overlays at the given tile
	 * 
	 * @param y
	 * @param x
	 */
	public void removeColour(int y, int x) {
		addColour(y, x, Color.hsb(30, 1, 1, 0.0));
	}

	/**
	 * Adds the given color at the given tile
	 * 
	 * @param y
	 * @param x
	 * @param color
	 */
	public void addColour(int y, int x, Color color) {
		colourOverlay[y][x].setFill(color);
	}

	/**
	 * Removes all colors from all the tiles
	 */
	public void removeAllColors() {
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				removeColour(y, x);
			}
		}
	}
/**
 * Display the given Node on the scene
 * @param toDisplay
 */
	public void display(Node toDisplay) {
		root.getChildren().add(toDisplay);
	}
/**
 * Remove the given Node from the scene
 * @param toHide
 */
	public void hide(Node toHide) {
		root.getChildren().remove(toHide);
	}

	/**
	 * gameEnd method tracking if the game is over if the game is over AND all
	 * friendly unit is dead => display you lose if the game is over AND all enemies
	 * unit is dead => display you win
	 */
	public void gameEnd() {

		boolean enemiesAllDead = true;
		boolean friendiesAllDead = true;

		// Iterates through the unit list,
		// Check if whether allies or enemies units are all dead

		for (Unit unit : this.getUnitList()) {
			unit.updateHpBar();
			if (unit.isAlive()) {
				if (unit.isFriendly())
					friendiesAllDead = false;
				else
					enemiesAllDead = false;
			}
		}

		// if the game is over and friendly units all dead

		if (gameOver() == true && friendiesAllDead == true) {

			// new label
			Label endGame = new Label();

			// label properties
			endGame.setText("YOU LOSE...");
			endGame.setTextFill(Color.web("#d10826"));
			endGame.setLayoutX(150);
			endGame.setLayoutY(350);
			endGame.setFont(Font.font("Chiller", 100));
			// display on map GUI
			this.display(endGame);

			// if the game is over and enemies units all dead
		} else if (gameOver() == true && enemiesAllDead == true) {
			Label endGame = new Label();
			// label properties
			endGame.setText("YOU WIN!");
			endGame.setTextFill(Color.web("#42f44e"));
			endGame.setLayoutX(150);
			endGame.setLayoutY(350);
			endGame.setFont(Font.font("Forte", 100));
			this.display(endGame);
		}
	}

	/**
	 * Updates the images on the GUI to correspond to those on the map
	 */
	@Override
	public void updateUnitsOnMap() {
		super.updateUnitsOnMap();
		for (int x = 0; x < unitDisplay.length; x++) {
			unitDisplay[x].setVisible(false);
			GridPane display = unitStatDisplays.get(x);
			if (x < getUnitList().size()) {
				Unit unit = getUnitList().get(x);
				if (unit.isAlive()) {
					unitDisplay[x].setLayoutX(unit.getX() * TerrainGUI.getImagewidth());
					unitDisplay[x].setLayoutY(unit.getY() * TerrainGUI.getImageheight());
					unitDisplay[x].setVisible(true);

				} else {
					if (unit.isFriendly()) {
						display.setBackground(new Background(
								new BackgroundFill(Color.hsb(210, 0.5, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

					} else {
						display.setBackground(new Background(
								new BackgroundFill(Color.hsb(0, 0.5, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

					}
				}

				// display.getChildren().clear();
				Label hp = (Label) display.getChildren().get(1);
				hp.setText("HP: " + unit.getCurrentHP() + "/" + unit.getBaseHP());
				gameEnd();
			}

		}

	}
/**
 * Load all the elements on the map, terrain, unit entities, and etc
 */
	public void loadMapGUI() {
		terrainDisplay = new ImageView[MAXY][MAXY];
		unitDisplay = new ImageView[getUnitList().size()];
		colourOverlay = new Rectangle[MAXY][MAXY];
		//Load and display the terrain
		GridPane terrain = new GridPane();
		TerrainGUI.initializeImages();
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {

				terrainDisplay[y][x] = new ImageView(TerrainGUI.getImage(getTerrainMap()[y][x]));

				terrain.add(terrainDisplay[y][x], x, y);
			}

		}
		root.getChildren().add(terrain);
		//Set up the color overlay
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				colourOverlay[y][x] = new Rectangle(x * TerrainGUI.getImagewidth(), y * TerrainGUI.getImageheight(),
						TerrainGUI.getImagewidth(), TerrainGUI.getImageheight());
				colourOverlay[y][x].setFill(Color.TRANSPARENT);
				colourOverlay[y][x].setMouseTransparent(false);
				colourOverlay[y][x].setOnMouseClicked(new SelectedTile(x, y, this));
				colourOverlay[y][x]
						.setOnMouseEntered(new HighlightTile(terrainDisplay[y][x], true, x, y, this, unitStatDisplays));
				colourOverlay[y][x]
						.setOnMouseExited(new HighlightTile(terrainDisplay[y][x], false, x, y, this, unitStatDisplays));
				root.getChildren().add(colourOverlay[y][x]);

			}
		}
		//Load and display the units
		UnitGUI.initializeImages();
		int counter = 0;
		for (Unit unit : getUnitList()) {
			unitDisplay[counter] = new ImageView();

			unitDisplay[counter].setImage(UnitGUI.getUnitImage(unit));

			UnitGUI.applyFactionColor(unitDisplay[counter], unit.isFriendly());
			unitDisplay[counter].setLayoutX(unit.getX() * TerrainGUI.getImagewidth());
			unitDisplay[counter].setLayoutY(unit.getY() * TerrainGUI.getImageheight());
			unitDisplay[counter].setMouseTransparent(true);
			root.getChildren().add(unitDisplay[counter]);
			counter++;

		}
		//Setup and display the stats of all the units
		counter = 0;
		for (Unit unit : getUnitList()) {
			GridPane unitStatDisplay = new GridPane();

			unitStatDisplay.setLayoutX(MAXX * TerrainGUI.getImagewidth());
			unitStatDisplay.setLayoutY(counter * unitDisplayHeight);
			unitStatDisplay.setPrefSize(250, 75);

			if (unit.isFriendly()) {
				unitStatDisplay.setStyle("-fx-background-color: #80bfff");
			} else {
				unitStatDisplay.setStyle("-fx-background-color: #ff8080");

			}

			Label name = new Label("Name: " + unit.getName());
			Label hp = new Label("HP: " + unit.getCurrentHP() + "/" + unit.getBaseHP());
			Label attack = new Label("Atk: " + unit.getAtk());
			Label speed = new Label("Spd: " + unit.getSpd());
			Label defense = new Label("Def: " + unit.getDef());
			Label range = new Label("Range: " + unit.getRange());
			Label moveType = new Label("Move Type: " + unit.getMoveType());
			ArrayList<Label> labelList = new ArrayList<Label>();
			labelList.add(name);
			labelList.add(hp);
			labelList.add(attack);
			labelList.add(speed);
			labelList.add(defense);
			labelList.add(range);
			labelList.add(moveType);
			String[] info = { "The name of the unit", "The unit dies if current hp drops below 1",
					"The more attack a unit has, the more damage it deals in it's attacks",
					"If this unit has 5 or more speed than it's oppent, this unit can attack twice",
					"The more def a unit has, the less damage it takes when it's attacked",
					"A unit can attack when it is this far away from its target",
					"Different movement types can enter different terrain, and may experience penalties for entering" };
			int[] yOffsets = { 15, 15, 32, 32, 49, 49, 66, };
			int[] xOffsets = { 0, 125, 0, 125, 0, 125, 0 };

			Label[] infoList = new Label[7];
			for (int x = 0; x < 7; x++) {
				infoList[x] = new Label(info[x]);
				infoList[x].setPrefWidth(125);
				infoList[x].setWrapText(true);
				infoList[x].setStyle("-fx-background-color: #ccffb2");
				infoList[x].setLayoutX(xOffsets[x] + MAXX * TerrainGUI.getImagewidth());
				infoList[x].setLayoutY(yOffsets[x] + counter * unitDisplayHeight);
				labelList.get(x).setOnMouseClicked(new StatInfoDisplay(infoList[x], this));

				if (unit.isFriendly()) {
					labelList.get(x).setStyle("-fx-background-color: transparent;");
				} else {
					labelList.get(x).setStyle("-fx-background-color: transparent;");

				}

			}

			moveType.setPrefWidth(125);
			unitStatDisplay.add(name, 0, 0);
			unitStatDisplay.add(hp, 1, 0);
			unitStatDisplay.add(attack, 0, 1);
			unitStatDisplay.add(speed, 1, 1);
			unitStatDisplay.add(defense, 0, 2);
			unitStatDisplay.add(range, 1, 2);
			unitStatDisplay.add(moveType, 0, 3);

			unitStatDisplay.setLayoutX(MAXX * TerrainGUI.getImagewidth());
			unitStatDisplay.setLayoutY(counter * unitDisplayHeight);
			unitStatDisplays.add(unitStatDisplay);
			root.getChildren().add(unitStatDisplay);
			counter++;
		}
		//Set up the console area
		consoleText = new TextArea();
		consoleText.setLayoutY(MAXY * TerrainGUI.getImagewidth());
		consoleText.setEditable(false);
		consoleText.setPrefSize(MAXX * TerrainGUI.getImageheight(), 150);
		PrintStream ps = System.out;
		System.setOut(new TextStreamGUI(consoleText, ps));
		root.getChildren().add(consoleText);

	}
/**
 * Returns the imageview of the given unit
 * @param unit
 * @return
 */
	public ImageView getUnitImage(Unit unit) {
		for (int x = 0; x < unitDisplay.length; x++) {
			if (x < getUnitList().size()) {
				if (getUnitList().get(x).equals(unit)) {
					return unitDisplay[x];
				}
			}
		}
		return null;
	}
/**
 * Display the HP Bar of the units
 */
	public void displayHPBar() {
		// Health Point bar for units
		// this is just the initial stage for showing it.
		// The hp bar won't follow the unit

		// mapGUI.updateUnitsOnMap();

		for (Unit unit : getUnitList()) {
			Rectangle hpBar = new Rectangle();
			hpBar.setWidth(unit.getCurrentHP() + (70 - unit.getCurrentHP()));
			hpBar.setHeight(10);

			if (unit.isFriendly() == true) {
				hpBar.setFill(Color.LIGHTGREEN);
			} else if (unit.isFriendly() == false) {
				hpBar.setFill(Color.RED);
			}
			hpBar.setX(unit.getX() * TerrainGUI.getImagewidth() + TerrainGUI.getImagewidth() / 8);
			hpBar.setY(unit.getY() * TerrainGUI.getImagewidth());
			unit.setHpBar(hpBar);

			display(hpBar);

		}

	}
}
