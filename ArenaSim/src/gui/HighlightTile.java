package gui;

import javafx.scene.paint.Color;
import logic.Unit;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class HighlightTile implements EventHandler<MouseEvent> {
	private ImageView image;
	private boolean add = false;
	private InnerShadow shadow = new InnerShadow();
	private int x;
	private int y;
	private MapGUI mapGUI;
	private ArrayList<GridPane> unitStatDisplays;

	/**
	 * Used to highlight or un-highlight the grid, as well as highlight or
	 * un-hightlight the corresponding unit box that is hovered
	 * 
	 * @param image
	 * @param add
	 */
	public HighlightTile(ImageView image, boolean add, int x, int y, MapGUI mapGUI,
			ArrayList<GridPane> unitStatDisplays) {
		this.image = image;
		this.add = add;
		shadow.setColor(Color.WHITE);
		this.x = x;
		this.y = y;
		this.mapGUI = mapGUI;
		this.unitStatDisplays = unitStatDisplays;
	}

	@Override
	/**
	 * Highlights or un-highlights the hovered tile, as well as the unit stats box
	 * of the unit being hovered, if a unit is being hovered as well.
	 */
	public void handle(MouseEvent e) {
		if (add)
			image.setEffect(shadow);
		else
			image.setEffect(null);
		int counter = 0;
		if (mapGUI.getUnitMap()[y][x] != null) {
			for (Unit unit : mapGUI.getUnitList()) {
				if (unit.getName().equals(mapGUI.getUnitMap()[y][x].getName())) {
					if (add)
						unitStatDisplays.get(counter).setEffect(shadow);
					else
						unitStatDisplays.get(counter).setEffect(null);
				}
				counter++;
			}
		} else {
			for (GridPane display : unitStatDisplays) {
				display.setEffect(null);
			}
		}
	}

}
