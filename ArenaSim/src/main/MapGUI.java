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
	MouseEvent e;
	Stage description = new Stage();
	Rectangle hpBar = new Rectangle();
	
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

	public void display(Node toDisplay) {
		root.getChildren().add(toDisplay);
	}

	public void hide(Node toHide) {
		root.getChildren().remove(toHide);
	}

	/**
	 * Updates the images on the GUI to correspond to those on the map
	 */
	public void updateUnitsOnMap() {
		
		
		for (int x = 0; x < unitDisplay.length; x++) {
			unitDisplay[x].setVisible(false);
			GridPane display = unitStatDisplays.get(x);
			if (x < getUnitList().size()) {
				Unit unit = getUnitList().get(x);
				if (unit.isAlive()) {
					unitDisplay[x].setX(unit.getX() * TerrainGUI.getImagewidth());
					
					unitDisplay[x].setY(unit.getY() * TerrainGUI.getImageheight());
					
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

				//display.getChildren().clear();
				Label hp = (Label)display.getChildren().get(1);
				hp.setText("HP: " + unit.getCurrentHP() + "/" + unit.getBaseHP());

				// Text stats = (Text) display.getChildren().get(0);
				// stats.setText(unit.getName() + "\nHP: " + unit.getCurrentHP() + "/" +
				// unit.getBaseHP() + "\t\tAtk: "
				// + unit.getAtk() + "\nSpd: " + unit.getSpd() + "\t\tDef: " + unit.getDef() +
				// "\nRange: "
				// + unit.getRange() + "\t\tMove Type: " + unit.getMoveType());
			}

		}

	}
	

	public void loadMapGUI() {
		terrainDisplay = new ImageView[MAXY][MAXY];
		unitDisplay = new ImageView[getUnitList().size()];
		colourOverlay = new Rectangle[MAXY][MAXY];
		GridPane terrain = new GridPane();
		TerrainGUI.initializeImages();
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {

				terrainDisplay[y][x] = new ImageView(TerrainGUI.getImage(getTerrainMap()[y][x]));

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
				colourOverlay[y][x]
						.setOnMouseEntered(new HighlightTile(terrainDisplay[y][x], true, x, y, this, unitStatDisplays));
				colourOverlay[y][x]
						.setOnMouseExited(new HighlightTile(terrainDisplay[y][x], false, x, y, this, unitStatDisplays));
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
			GridPane unitStatDisplay = new GridPane();

			unitStatDisplay.setLayoutX(MAXX * TerrainGUI.getImagewidth());
			unitStatDisplay.setLayoutY(counter * unitDisplayHeight);
			unitStatDisplay.setPrefSize(250, 75);

			if (unit.isFriendly()) {
				unitStatDisplay.setStyle("-fx-background-color: #80bfff");
			} else {
				unitStatDisplay.setStyle("-fx-background-color: #ff8080");

			}
			// Text stats = new Text();
			// stats.setText(unit.getName() + "\nHP: " + unit.getCurrentHP() + "/" +
			// unit.getBaseHP() + "\t\tAtk: "
			// + unit.getAtk() + "\nSpd: " + unit.getSpd() + "\t\tDef: " + unit.getDef() +
			// "\nRange: "
			// + unit.getRange() + "\t\tMove Type: " + unit.getMoveType());
			// unitStatDisplay.getChildren().add(stats);

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
		consoleText = new TextArea();
		consoleText.setLayoutY(MAXY * TerrainGUI.getImagewidth());
		consoleText.setEditable(false);
		consoleText.setPrefSize(MAXX * TerrainGUI.getImageheight(), 150);
		PrintStream ps = System.out;
		System.setOut(new TextStreamGUI(consoleText, ps));
		root.getChildren().add(consoleText);

	}

}
