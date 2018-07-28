package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
	private Stage stage;
	private PlayerGUI player;
	private MapGUI mapGUI;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		boolean gameOver = false;
		map = new Map("src/assets/map_1_1.txt");
		player = new PlayerGUI(map);
		sceneWidth = (int) (map.MAXX * TerrainGUI.getImagewidth());
		sceneHeight = (int) (map.MAXY * TerrainGUI.getImageheight());
		root = new Group();
		mapGUI = new MapGUI(map, root);
		mapGUI.loadMapGUI();

		scene = new Scene(root, sceneWidth, sceneHeight, Color.hsb(255 * 0.0, 0, 0.5, 1));
		primaryStage.setTitle("Arena Sim");
		primaryStage.setScene(scene);

		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
		// while (!gameOver) {
		// gameOver = player.turn();
		// if (!gameOver) {
		// gameOver = AI.computerTurn(map);
		// }
		// }

	}

}
