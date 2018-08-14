/**
 * 
 */
package main;
//imports the following java libraries
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
	//creates and initializes the following instance variables.
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
	 * {@code this function sets instance variable setAnimating boolean value}
	 * @param isAnimating the isAnimating to set
	 */
	protected void setAnimating(boolean isAnimating) {
		this.isAnimating = isAnimating;
	}
	//creates instance variable e which is a mouse event
	//creates a instance variable description which is a new stage.
	MouseEvent e;
	Stage description = new Stage();
	
	/**
	 * {@code this function gets the map}
	 * @param filename is the title of a txt file of a map you want to load
	 * @param root is the Group instance variable you want it to register in the root instance variable
	 */
	public MapGUI(String filename, Group root) {
		//goes to the super constructor to load the file
		super(filename);
		//assigns the Group root instance variable to reference the given parameter root.
		this.root = root;

	}
	/**
	 * {@code this function adds the color blue to the desired x and y location on the map}
	 * @param y is the y location you want the color to be added
	 * @param x is the x location you want the color to be added
	 */
	public void addBlue(int y, int x) {
		//it goes to the function addColour with these parameters,
		//to add the color at that location
		addColour(y, x, Color.hsb(210, 1, 1, 0.3));
	}
	/**
	 * {@code this function adds the color red to the desired x and y location on the map}
	 * @param y is the y location you want the color to be added
	 * @param x is the x location you want the color to be added
	 */
	public void addRed(int y, int x) {
		//it goes to the function addColour with these parameters,
		//to add the color at that location
		addColour(y, x, Color.hsb(0, 1, 1, 0.3));
	}

	/*public void addYellow(int y, int x) {
		addColour(y, x, Color.hsb(60, 1, 1, 0.3));
	}*/
	/*public void addGreen(int y, int x) {
		addColour(y, x, Color.hsb(30, 1, 1, 0.3));
	}*/
	/**
	 * {@code this function removes a highlighted tile color on the map at a x and y location}
	 * @param y a y coordinate map value that you want the color removed at
	 * @param x a x coordinate map value that you want the color removed at
	 */
	public void removeColour(int y, int x) {
		addColour(y, x, Color.hsb(30, 1, 1, 0.0));
	}
/**
 * {@code adds a certain color into the map used to display a highlighted tile
 * of either friendly or enemy unit's range and attack range.}
 * @param y a y coordinate map value you want that color at
 * @param x a x coordinate map value you want that color at
 * @param color what color you want that desired tile to be.
 */
	public void addColour(int y, int x, Color color) {
		//adds a given color at that desired location on the map.
		colourOverlay[y][x].setFill(color);
	}
	/**
	 * {@code removes all the highlighted colored boxes on the map}
	 */
	public void removeAllColorsAndText() {
		//iterates through the map, and removes all the color.
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				removeColour(y, x);
			}
		}
	}
	/**
	 * {@code This function determines what would be displayed on the map,
	 * it does this by adding the Node parameter toDisplay into the current root 
	 * group Instance variable.}
	 * 
	 * @param toDisplay is the Node parameter that you want to display onto the map
	 * which gets added into the current root.
	 */
	public void display(Node toDisplay) {
		root.getChildren().add(toDisplay);
	}
	/**
	 * {@code this function determines what will be hidden on the map, it does this
	 * by removing the Node parameter toHide 
	 * from the current root group instance variable.}
	 * @param toHide is the Node parameter that will be removed from the current root
	 */
	public void hide(Node toHide) {
		root.getChildren().remove(toHide);
	}

	/**
	 * gameEnd method tracking if the game is over
	 * 
	 * if the game is over AND all friendly unit is dead => display you lose if the
	 * game is over AND all enemies unit is dead => display you win
	 */
	public void gameEnd() {

		//Boolean values to that determine who won
		boolean enemiesAllDead = true;
		boolean friendiesAllDead = true;
		
		// Iterates through the unit list,
		// Check if whether allies or enemies units are all dead
		//if unit a unit on either side is not dead, it sets one of the conditions to false
		for (Unit unit : this.getUnitList()) {
			unit.updateHpBar();
			if (unit.isAlive()) {
				if (unit.isFriendly())
					friendiesAllDead = false;
				else
					enemiesAllDead = false;
			}
		}
		//if the gameOver method returns true, and all player's units are dead
		//it displays to the user that they lost the game.
		if (gameOver() == true && friendiesAllDead == true) {

			Label endGame = new Label();
			endGame.setText("YOU LOSE...");
			endGame.setTextFill(Color.web("#d10826"));
			endGame.setLayoutX(150);
			endGame.setLayoutY(350);
			endGame.setFont(Font.font("Chiller", 100));
			this.display(endGame);
			
			//else if the the gameOver method returns true, and the AI's unit are dead
			//it displays text indicating that the user has won.
		} else if (gameOver() == true && enemiesAllDead == true) {
			Label endGame = new Label();
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
	public void updateUnitsOnMap() {
		//iterates through the unit's image array
		for (int x = 0; x < unitDisplay.length; x++) {
			//changes the visibility of all the images in the unitDisplay array to false
			unitDisplay[x].setVisible(false);
			//sets the current gripPane display value to be equal to the unit image at the x'th value. 
			GridPane display = unitStatDisplays.get(x);
			//checks to see if the current temporary value x is less than the size of the current unitList
			if (x < getUnitList().size()) {
				//if it is, assigns the Unit instance unit to reference the unit at the x value's position.
				Unit unit = getUnitList().get(x);
				//checks to see if that current Unit assigned to the variable unit is alive
				if (unit.isAlive()) {
					//if so it updates the image of the unit to be at the current Unit's location
					unitDisplay[x].setLayoutX(unit.getX() * TerrainGUI.getImagewidth());
					unitDisplay[x].setLayoutY(unit.getY() * TerrainGUI.getImageheight());
					unitDisplay[x].setVisible(true);

				} else {
					//else if the current unit apart of the player's side
					if (unit.isFriendly()) {
						//sets a new background
						display.setBackground(new Background(
								new BackgroundFill(Color.hsb(210, 0.5, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

					} else {
						//sets a new background
						display.setBackground(new Background(
								new BackgroundFill(Color.hsb(0, 0.5, 0.3), CornerRadii.EMPTY, Insets.EMPTY)));

					}
				}

				//updates the label on the side which indicate the unit's current Hp value.
				//then goes to the function gameEnd to check if the game has ended
				Label hp = (Label) display.getChildren().get(1);
				hp.setText("HP: " + unit.getCurrentHP() + "/" + unit.getBaseHP());
				gameEnd();
			}

		}

	}
	/**
	 * {@code this function loads the map, terrains, unit entities, and etc.}
	 * this method will be called constantly
	 * ensured that all the unit's stats are being updated for each action
	 */
	public void loadMapGUI() {
		//Assigns the values to the following instance variables.
		terrainDisplay = new ImageView[MAXY][MAXY];
		unitDisplay = new ImageView[getUnitList().size()];
		colourOverlay = new Rectangle[MAXY][MAXY];
		//initializes a new GridPane object called terrain
		GridPane terrain = new GridPane();
		//goes to this function in the object TerrainGUI's class to initialize images onto the map
		TerrainGUI.initializeImages();
		//iterates through the entire map
		for (int y = 0; y < MAXY; y++) {
			for (int x = 0; x < MAXX; x++) {
				//displays images on the map at that x and y coordinate on the map
				terrainDisplay[y][x] = new ImageView(TerrainGUI.getImage(getTerrainMap()[y][x]));
				//goes to add that location and the image into the gridPane terrain
				terrain.add(terrainDisplay[y][x], x, y);
			}

		}
		//adds the gridPanel terrain into the current instance variable's root.
		root.getChildren().add(terrain);
		//iterates through the map again
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
		//loop through unit list
		for (Unit unit : getUnitList()) {
			
			//get correct images for each unit
			unitDisplay[counter] = new ImageView();
			unitDisplay[counter].setImage(UnitGUI.getUnitImage(unit));
			//check unit factions and apply corresponding color
			UnitGUI.applyFactionColor(unitDisplay[counter], unit.isFriendly());
			unitDisplay[counter].setLayoutX(unit.getX() * TerrainGUI.getImagewidth());
			unitDisplay[counter].setLayoutY(unit.getY() * TerrainGUI.getImageheight());
			unitDisplay[counter].setMouseTransparent(true);
			//add images to dispaly on map
			root.getChildren().add(unitDisplay[counter]);
			
			//counter +1 so, continue on the next unit in the list
			counter++;

		}
		counter = 0;
		for (Unit unit : getUnitList()) {
			GridPane unitStatDisplay = new GridPane();

			unitStatDisplay.setLayoutX(MAXX * TerrainGUI.getImagewidth());
			unitStatDisplay.setLayoutY(counter * unitDisplayHeight);
			
			//display unit stat's box
			unitStatDisplay.setPrefSize(250, 75);
			
			//condition to color the box
			if (unit.isFriendly()) {
				unitStatDisplay.setStyle("-fx-background-color: #80bfff");
			} else {
				unitStatDisplay.setStyle("-fx-background-color: #ff8080");

			}

			//creates labels for the stat's boxes
			Label name = new Label("Name: " + unit.getName());
			Label hp = new Label("HP: " + unit.getCurrentHP() + "/" + unit.getBaseHP());
			Label attack = new Label("Atk: " + unit.getAtk());
			Label speed = new Label("Spd: " + unit.getSpd());
			Label defense = new Label("Def: " + unit.getDef());
			Label range = new Label("Range: " + unit.getRange());
			Label moveType = new Label("Move Type: " + unit.getMoveType());
			ArrayList<Label> labelList = new ArrayList<Label>();
			
			//adding corresponded information in the labels
			labelList.add(name);
			labelList.add(hp);
			labelList.add(attack);
			labelList.add(speed);
			labelList.add(defense);
			labelList.add(range);
			labelList.add(moveType);
			
			//info displayed when clicked
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
			//each stats info position
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
		//creates new text area in the console space(white box at the bottom of screen)
		//to show what has occurred in the game after each turn.
		consoleText = new TextArea();
		consoleText.setLayoutY(MAXY * TerrainGUI.getImagewidth());
		consoleText.setEditable(false);
		consoleText.setPrefSize(MAXX * TerrainGUI.getImageheight(), 150);
		PrintStream ps = System.out;
		System.setOut(new TextStreamGUI(consoleText, ps));
		root.getChildren().add(consoleText);

	}
/**
 * {@code this function returns a ImageView object 
 * from a certain unit of the parameter unit.}
 * @param unit is a Unit class instance object, that you want to get the image of.
 * @return the ImageView object of the desired unit given in the parameters, if that unit
 * isn't a valid unit, it returns null.
 */
	public ImageView getUnitImage(Unit unit) {
		//loops through the ImageView array unitDisplay
		for (int x = 0; x < unitDisplay.length; x++) {
			//then it goes through the list of the current units
			if (x < getUnitList().size()) {
			//if the unit is valid and is equivalent to the unit given in the parameters	
				if (getUnitList().get(x).equals(unit)) {
					//returns the Image of that unit
					return unitDisplay[x];
				}
			}
		}
		//else if the unit is not valid/isnt found, it returns null.
		return null;
	}

}
