package gui;
//imports the following java libraries
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.Unit;

public final class UnitGUI {
	//initializes and creates the following instance variables/constants
	private static final double IMAGEWIDTH = 100;
	private static final double IMAGEHEIGHT = 100;
	private static Image[][] unitImages = new Image[2][4];
	private static DropShadow enemyEffect;
	private static DropShadow friendlyEffect;
	/**
	 * This function gets what the value of IMAGEWIDTH class constant
	 * @return the class constant double value IMAGEWIDTH
	 */
	public static double getImagewidth() {
		return IMAGEWIDTH;
	}
	/**
	 * {@code This function gets what the value of IMAGEHEIGHT class constant}
	 * @return the class constant double value IMAGEHEIGHT
	 */
	public static double getImageheight() {
		return IMAGEHEIGHT;
	}
	/**
	 * default constructor for the UnitGUI class
	 */
	private UnitGUI() {

	}

	/**
	 * Method does not return anything and it initializes the images to use for
	 * units
	 */
	public static void initializeImages() {
		try {
			unitImages[0][0] = new Image("file:src/assets/melee_infantry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[0][0] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[0][1] = new Image("file:src/assets/melee_cavalry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[0][1] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[0][2] = new Image("file:src/assets/melee_flier.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[0][2] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[0][3] = new Image("file:src/assets/melee_armor.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[0][3] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[1][0] = new Image("file:src/assets/ranged_infantry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[1][0] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[1][1] = new Image("file:src/assets/ranged_cavalry.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[1][1] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[1][2] = new Image("file:src/assets/ranged_flier.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[1][2] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		try {
			unitImages[1][3] = new Image("file:src/assets/ranged_armor.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		} catch (Exception e) {
			unitImages[1][3] = new WritableImage((int) IMAGEWIDTH, (int) IMAGEHEIGHT);
		}
		//assigns the dropShadow values to the following instance variables, 
		//for indicating which side the unit is on (blue for player's, red for enemies)
		friendlyEffect = new DropShadow(20, Color.CORNFLOWERBLUE);
		enemyEffect = new DropShadow(20, Color.RED);
	}

	/**
	 * Adds the faction color (red for enemy, blue for friendly) to the image
	 * 
	 * @param unitImage is the image you want to apply the faction color to
	 * @param isFriendly is a boolean value to determine what faction color the unit would have
	 */
	public static void applyFactionColor(ImageView unitImage, boolean isFriendly) {
		if (isFriendly)
			unitImage.setEffect(friendlyEffect);
		else
			unitImage.setEffect(enemyEffect);
	}

	/**
	 * Returns the image that represents the given unit
	 * 
	 * @param unit is a Unit object which you want to get the image from
	 * @return the image for the following parameter unit
	 */
	public static Image getUnitImage(Unit unit) {
		int rangeIndex = unit.getRange() - 1;
		if (rangeIndex > 1) {
			rangeIndex = 1;
		} else if (rangeIndex < 0) {
			rangeIndex = 0;
		}
		return unitImages[unit.getRange() - 1][unit.getMoveType().ordinal()];
	}

}
