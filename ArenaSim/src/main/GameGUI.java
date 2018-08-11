package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameGUI extends Application {

	public int sceneHeight;
	public int sceneWidth;
	public final static double FPS = 120;
	private Scene scene;
	private Group root;
	private Map map;
	private Stage stage;
	private MapGUI mapGUI;

	Stage window;
	Scene scene1;

	// Stuff for the choosing Stages only
	Stage windowX;
	Scene maps;
	Button map1, map2, map3;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		window = primaryStage;
		// System.out.println(javafx.scene.text.Font.getFamilies());
		boolean gameOver = false;

		root = new Group();
		mapGUI = new MapGUI("src/assets/map_1_1.txt", root);
		mapGUI.loadMapGUI();
		scene = new Scene(root, Color.hsb(255 * 0.0, 0, 0.5, 1));

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setId("pane");

		grid.setVgap(8);
		grid.setHgap(10);

		Label label1 = new Label();
		label1.setText("ARENA SIMULATOR V.1.01 ALPHA");
		label1.setTextFill(Color.web("#0076a3"));
		label1.setFont(Font.font("Berlin Sans FB", 18));

		GridPane.setConstraints(label1, 25, 0);

		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setStyle("-fx-background-color: linear-gradient(#99bbff, #99ff99)");
		mapGUI.display(backButton);

		backButton.setOnAction(e -> primaryStage.setScene(scene1));

		Button button1 = new Button();
		button1.setText("NEW GAME");
		button1.setFont(Font.font("Kristen ITC", 25));
		button1.setStyle("-fx-background-color: linear-gradient(#99bbff, #99ff99)");
		GridPane.setConstraints(button1, 25, 25);
		button1.setOnAction(e -> window.setScene(scene));

		// Button 2 stuffs
		Button button2 = new Button();
		button2.setText("CHOOSE A STAGE");
		button2.setFont(Font.font("Kristen ITC", 25));
		button2.setStyle("-fx-background-color: linear-gradient(#99bbff, #99ff99)");
		GridPane stagesGrid = new GridPane();
		stagesGrid.setId("stages");
		stagesGrid.setPadding(new Insets(10, 10, 10, 10));
		stagesGrid.setVgap(8);
		stagesGrid.setHgap(10);

		// picking button2 will result in a new screen with 3 other buttons

		// 3 other buttons are called map1, map2 and map3 respectively. They will choose
		// different maps for you.

		// buttons for maps picking
		Button map1 = new Button();
		map1.setText("Map_1_1");
		map1.setFont(Font.font("Comic Sans MS", 25));
		map1.setStyle("-fx-background-color: linear-gradient(#66ff8c, #6666ff)");
		map1.setOnAction(e -> window.setScene(scene));
		GridPane.setConstraints(map1, 25, 25);

		// picking map 2
		Button map2 = new Button();
		map2.setText("Map_1_2");
		map2.setFont(Font.font("Comic Sans MS", 25));
		map2.setStyle("-fx-background-color: linear-gradient(#66ff8c, #6666ff)");
		GridPane.setConstraints(map2, 25, 27);
		map2.setOnAction((event) -> {
			root = new Group();
			mapGUI = new MapGUI("src/assets/map_1_2.txt", root);
			mapGUI.loadMapGUI();
			scene = new Scene(root, Color.hsb(255 * 0.0, 0, 0.5, 1));
			mapGUI.display(backButton);
			window.setScene(scene);

		});

		// picking map 3
		Button map3 = new Button();
		map3.setText("Map_1_3");
		map3.setFont(Font.font("Comic Sans MS", 25));
		map3.setStyle("-fx-background-color: linear-gradient(#66ff8c, #6666ff)");
		GridPane.setConstraints(map3, 25, 29);
		map3.setOnAction((event) -> {
			root = new Group();
			mapGUI = new MapGUI("src/assets/map_1_3.txt", root);
			mapGUI.loadMapGUI();
			scene = new Scene(root, Color.hsb(255 * 0.0, 0, 0.5, 1));
			mapGUI.display(backButton);
			window.setScene(scene);

		});

		// button to get back to main screen
		Button back = new Button();
		back.setText("Back");
		back.setFont(Font.font("Comic Sans MS", 25));
		back.setStyle("-fx-background-color: linear-gradient(#66ff8c, #6666ff)");
		GridPane.setConstraints(back, 25, 31);

		// grid for the map picking
		stagesGrid.getChildren().addAll(map1, map2, map3, back);

		// new scene = stages picking
		maps = new Scene(stagesGrid, 850, 950);
		maps.getStylesheets().addAll("/assets/style.css");

		button2.setOnAction(e -> window.setScene(maps));
		GridPane.setConstraints(button2, 25, 27);

		// stuff for button 3 and anything else related (button2 is no longer apply
		// here)
		Button button3 = new Button();
		button3.setText("QUIT GAME");
		button3.setFont(Font.font("Kristen ITC", 25));
		button3.setStyle("-fx-background-color: linear-gradient(#9999ff, #99ff99)");
		button3.setOnAction(e -> window.hide());
		GridPane.setConstraints(button3, 25, 29);

		grid.getChildren().addAll(label1, button1, button2, button3);

		// Layout1
		scene1 = new Scene(grid, 850, 950);
		scene1.getStylesheets().addAll("/assets/style.css");

		// this is for the back button of the choosing Stages, dont get confuse
		back.setOnAction(e -> window.setScene(scene1));

		primaryStage.setTitle("Arena Sim");
		primaryStage.setScene(scene1);

		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		for (Unit unit : mapGUI.getUnitList()) {
			Rectangle hpBar = new Rectangle();
			hpBar.setWidth(70);
			hpBar.setHeight(10);
			hpBar.setFill(Color.LIGHTGREEN);
			hpBar.setX(unit.getX() * TerrainGUI.getImagewidth());
			hpBar.setY(unit.getY() * TerrainGUI.getImagewidth());
			unit.setHpBar(hpBar);
			System.out.println(unit.getX() + "and" + unit.getY());
			mapGUI.display(hpBar);
		}
		primaryStage.show();

	}

	public void displayHPBar() {
		// Health Point bar for units
		// this is just the initial stage for showing it. 
		//The hp bar won't follow the unit
		
		mapGUI.updateUnitsOnMap();

		for (Unit unit : mapGUI.getUnitList()) {
			Rectangle hpBar = new Rectangle();
			hpBar.setWidth(unit.getBaseHP());
			hpBar.setHeight(10);
			hpBar.setFill(Color.LIGHTGREEN);
			hpBar.setX(unit.getX() * TerrainGUI.getImagewidth());
			hpBar.setY(unit.getY() * TerrainGUI.getImagewidth());
			//unit.setHpBar(hpBar);
			System.out.println(unit.getX() + "and" + unit.getY());
			mapGUI.display(hpBar);
		}
		
	}
}
