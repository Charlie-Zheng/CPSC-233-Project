package main;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

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
		try {
			terrainImages[0] = new Image("file:src/assets/flat.jpg", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			terrainImages[0] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			terrainImages[1] = new Image("file:src/assets/tree.jpg", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			terrainImages[1] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			terrainImages[2] = new Image("file:src/assets/mountain.jpg", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			terrainImages[2] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		
		terrainImages[3] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);

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
