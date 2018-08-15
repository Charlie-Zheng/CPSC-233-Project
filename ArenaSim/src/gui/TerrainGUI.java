package gui;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import logic.TerrainType;

public final class TerrainGUI {
	//initializes the following class constants
	private static final double IMAGEWIDTH = 100;
	private static final double IMAGEHEIGHT = 100;
	//initializes the following image array instance variable terrainImages
	private static Image[] terrainImages = new Image[4];
	/**
	 * default constructor for TerrainGUI
	 */
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
	 * @param terrain is a TerrainType object that you want to get the image of
	 * @return returns a image from the terrainImages array based on the parameter terrain
	 */
	public static Image getImage(TerrainType terrain) {
		if(terrain==null) {
			return getImage(TerrainType.FLAT);
		}
		return terrainImages[terrain.ordinal()];
	}
	/**
	 * this function returns the value of what the constant IMAGEWIDTH is
	 * @return the the class constant double value of IMAGEWIDTH
	 */
	public static double getImagewidth() {
		return IMAGEWIDTH;
	}
	/**
	 * this function returns the value of what the constant IMAGEHEIGHT is
	 * @return the the class constant double value of IMAGEHEIGHT
	 */
	public static double getImageheight() {
		return IMAGEHEIGHT;
	}

}
