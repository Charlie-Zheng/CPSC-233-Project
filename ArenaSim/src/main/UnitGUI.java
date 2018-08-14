package main;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public final class UnitGUI {
	private static final double IMAGEWIDTH = 100;
	private static final double IMAGEHEIGHT = 100;
	private static Image[][] unitImages = new Image[2][4];
	private static DropShadow enemyEffect;
	private static DropShadow friendlyEffect;

	/**
	 * Method basically gets the width of the image
	 * 
	 * @return a double that represents the width of image for units
	 */
	public static double getImagewidth() {
		return IMAGEWIDTH;
	}

	/**
	 * Method gets the height of the image for units
	 * 
	 * @return a double that represents the height of image for units
	 */
	public static double getImageheight() {
		return IMAGEHEIGHT;
	}
	
	
	private UnitGUI() {

	}

	/**
	 * Method does not return anything and it initializes the images to use for units
	 */
	public static void initializeImages() {
		unitImages[0][0] = new Image("file:src/assets/melee_infantry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[0][1] = new Image("file:src/assets/melee_cavalry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[0][2] = new Image("file:src/assets/melee_flier.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[0][3] = new Image("file:src/assets/melee_armor.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[1][0] = new Image("file:src/assets/ranged_infantry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[1][1] = new Image("file:src/assets/ranged_cavalry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[1][2] = new Image("file:src/assets/ranged_flier.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[1][3] = new Image("file:src/assets/ranged_armor.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);

		friendlyEffect = new DropShadow(20, Color.CORNFLOWERBLUE); //blue glow effect
		enemyEffect = new DropShadow(20, Color.RED); //red glow effect
	}

	/**
	 * Applies glow the enemy and friendly units, red glow for enemy and blue for friendly
	 * 
	 * @param unitImage takes in unitImage to apply effect
	 * @param isFriendly boolean to see if the unit is friendy or not.
	 */
	public static void applyFactionColor(ImageView unitImage, boolean isFriendly) {
		if (isFriendly)
			unitImage.setEffect(friendlyEffect); //applies blue glow to friendly units
		else
			unitImage.setEffect(enemyEffect); //applies red glow to enemy units
	}

	/**
	 * gets unit image for the unit, takes in parameter unit which represents what type of unit.
	 * 
	 * @param unit represents what type of unit image
	 * @return returns unitImages determined by range and movetype
	 */
	public static Image getUnitImage(Unit unit) {
		int rangeIndex = unit.getRange() - 1;
		if (rangeIndex > 1) {
			rangeIndex = 1;
		} else if (rangeIndex < 0) {
			rangeIndex = 0;
		}
		return unitImages[unit.getRange() - 1][unit.getMoveType().ordinal()]; //returns unitImage
	}

}
