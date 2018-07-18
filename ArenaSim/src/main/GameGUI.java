package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameGUI extends Application {

	public int sceneHeight;
	public int sceneWidth;
	public final static double FPS = 120;
	private Scene scene;
	private Group root;
	private Map map;
	private ImageView[][] terrainDisplay;
	private ImageView[][] unitDisplay;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		map = new Map("src/assets/map_1_1.txt");
		sceneWidth = (int) (map.MAXX * TerrainGUI.getImagewidth());
		sceneHeight = (int) (map.MAXY * TerrainGUI.getImageheight());
		root = new Group();
		loadMapGUI();

		scene = new Scene(root, sceneWidth, sceneHeight, Color.hsb(255 * 0.0, 0, 0.5, 1));
		primaryStage.setTitle("Arena Sim");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);

		primaryStage.show();

	}

	public void loadMapGUI() {
		terrainDisplay = new ImageView[map.MAXY][map.MAXY];
		unitDisplay = new ImageView[map.MAXY][map.MAXY];

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
				terrainDisplay[y][x].setX(x * TerrainGUI.getImagewidth());
				terrainDisplay[y][x].setY(y * TerrainGUI.getImageheight());
				root.getChildren().add(terrainDisplay[y][x]);
			}
		}
		UnitGUI.initializeImages();
		for (int y = 0; y < map.MAXY; y++) {
			for (int x = 0; x < map.MAXX; x++) {
				if (map.getUnitMap()[y][x] != null) {
					switch (map.getUnitMap()[y][x].getMoveType()) {
					case CAVALRY:
						unitDisplay[y][x] = UnitGUI.getHorse();

						break;
					case INFANTRY:
						unitDisplay[y][x] = UnitGUI.getSword();
					default:
						break;
					}
					UnitGUI.applyFactionColor(unitDisplay[y][x],map.getUnitMap()[y][x].isFriendly());
					unitDisplay[y][x].setX(x * UnitGUI.getImagewidth());
					unitDisplay[y][x].setY(y * UnitGUI.getImageheight());
					root.getChildren().add(unitDisplay[y][x]);
				}
			}

		}

	}

}
