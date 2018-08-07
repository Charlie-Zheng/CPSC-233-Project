package main;

import javafx.scene.image.Image;

public final class TerrainGUI {
	private static final double IMAGEWIDTH=100;
	private static final double IMAGEHEIGHT = 100;
	public static double getImagewidth() {
		return IMAGEWIDTH;
	}

	public static double getImageheight() {
		return IMAGEHEIGHT;
	}

	private static Image[] terrainImages = new Image[4];
	private TerrainGUI() {

	}
	
	public static void initializeImages() {
		terrainImages[0]=new Image("file:src/assets/flat.jpg",IMAGEWIDTH,IMAGEHEIGHT,false,true);
		terrainImages[1]=new Image("file:src/assets/tree.jpg",IMAGEWIDTH,IMAGEHEIGHT,false,true);
	}
	
	public static Image getTreeImage() {
		return terrainImages[1];
	}
	
	public static Image getFlatImage() {
		return terrainImages[0];
	}

}
