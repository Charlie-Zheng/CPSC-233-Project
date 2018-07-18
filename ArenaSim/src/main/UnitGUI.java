package main;

import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public final class UnitGUI {
	private static final double IMAGEWIDTH = 100;
	private static final double IMAGEHEIGHT = 100;
	private static Image[] unitImages = new Image[4];
	private static DropShadow enemyEffect;
	private static DropShadow friendlyEffect;

	public static double getImagewidth() {
		return IMAGEWIDTH;
	}

	public static double getImageheight() {
		return IMAGEHEIGHT;
	}

	private UnitGUI() {

	}

	public static void initializeImages() {
		unitImages[0] = new Image("file:src/assets/sword.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		unitImages[1] = new Image("file:src/assets/horse.png", IMAGEWIDTH, IMAGEHEIGHT, false, true);
		friendlyEffect=new DropShadow(20, Color.CORNFLOWERBLUE);
		enemyEffect = new DropShadow(20,Color.RED);
	}

	public static void applyFactionColor(ImageView unitImage, boolean isFriendly) {
		if (isFriendly)
			unitImage.setEffect(friendlyEffect);
		else
			unitImage.setEffect(enemyEffect);
	}

	public static ImageView getSword() {
		return new ImageView(unitImages[0]);
	}

	public static ImageView getHorse() {
		return new ImageView(unitImages[1]);
	}
}
