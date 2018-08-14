package main;

import javafx.scene.image.Image;

public final class TerrainGUI {
	private static final double IMAGEWIDTH = 100;
	private static final double IMAGEHEIGHT = 100;

	private static Image[] terrainImages = new Image[4];

	private TerrainGUI() {

	}

	/**
	 * Loads the terrain images
	 */
	public static void initializeImages() {
		terrainImages[0] = new Image("file:src/assets/flat.jpg", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		terrainImages[1] = new Image("file:src/assets/tree.jpg", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		terrainImages[2] = new Image("file:src/assets/mountain.jpg", IMAGEWIDTH, IMAGEHEIGHT, false, true);
	}

	/**
	 * Returns the image of the given terrain
	 * 
	 * @param terrain
	 * @return
	 */
	public static Image getImage(TerrainType terrain) {
		return terrainImages[terrain.ordinal()];
	}

	public static double getImagewidth() {
		return IMAGEWIDTH;
	}

	public static double getImageheight() {
		return IMAGEHEIGHT;
	}

}
