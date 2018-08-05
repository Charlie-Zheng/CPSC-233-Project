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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
	private MapGUI mapGUI;
	
	Stage window;
	Scene scene1;
	
	public static void main(String[] args) {

		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		
		boolean gameOver = false;
		
		
		GridPane grid = new GridPane();							
		grid.setPadding(new Insets(10, 10, 10, 10));			
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label label1 = new Label("ARENA SIMULATOR V.1.01 ALPHA");
		GridPane.setConstraints(label1, 25, 0);
		
		
		Button button1 = new Button("NEW GAME");
		GridPane.setConstraints(button1,25,25);
		button1.setOnAction(e -> window.setScene(scene));
		grid.getChildren().addAll(label1, button1);
		
		//Layout1 
		scene1 = new Scene(grid, 800, 600);
		
		
		map = new Map("src/assets/map_1_1.txt");
		root = new Group();
		mapGUI = new MapGUI(map, root);
		mapGUI.loadMapGUI();
		scene = new Scene(root, Color.hsb(255 * 0.0, 0, 0.5, 1));
		primaryStage.setTitle("Arena Sim");
		primaryStage.setScene(scene1);

		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
		
		
		
		
	
		
//		
//		window.setScene(scene1);
		
		window.show();

	}

}
