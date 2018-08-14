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

	/**this is a constructor for the HighlightTile class to copy the following things
	 *  which is used to highlight or un-highlight the grid, as well as highlight or
	 * un-hightlight the corresponding unit box that is hovered.
	 * 
	 * @param image the image that you want registered
	 * @param add the boolean value you want to register
	 * @param x the x location you want registered
	 * @param y the y location you want registered
	 * @param mapGUI the mapGUI object you want registered
	 * @param unitStatDisplays the ArrayList of GridPanel objects you want to register
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
		//if add is true, sets a effect on the image else doesnt do anything
		if (add)
			image.setEffect(shadow);
		else
			image.setEffect(null);
		int counter = 0;
		//iterates through the mapGUI if that spot in the mapGUI isnt empty
		//and adds effects to the corresponding unit stats if add is true.
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
			//else iterates through the GridPanel array unitStatDisplay
			//and sets all the effects in it to be nothing
			for (GridPane display : unitStatDisplays) {
				display.setEffect(null);
			}
		}
	}

}
