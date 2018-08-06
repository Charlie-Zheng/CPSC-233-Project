package main;

import javafx.scene.paint.Color;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class HighlightTile implements EventHandler<MouseEvent> {
	private ImageView image;
	private boolean add = false;
	private InnerShadow shadow = new InnerShadow();
	private int x;
	private int y;
	private MapGUI mapGUI;
	private ArrayList<StackPane> unitStatDisplays;

	public HighlightTile(ImageView image, boolean add) {
		this.image = image;
		this.add = add;
		shadow.setColor(Color.WHITE);
	}

	public HighlightTile(ImageView image, boolean add, int x, int y, MapGUI mapGUI, ArrayList<StackPane> unitStatDisplays) {
		this.image = image;
		this.add = add;
		shadow.setColor(Color.WHITE);
		this.x = x;
		this.y = y;
		this.mapGUI = mapGUI;
		this.unitStatDisplays = unitStatDisplays;
	}

	@Override
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
			for (StackPane display : unitStatDisplays) {
				display.setEffect(null);
			}
		}
	}

}
