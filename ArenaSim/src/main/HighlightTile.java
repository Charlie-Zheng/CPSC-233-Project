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
	private Map map;
	private ArrayList<StackPane> unitStatDisplays;

	public HighlightTile(ImageView image, boolean add) {
		this.image = image;
		this.add = add;
		shadow.setColor(Color.WHITE);
	}

	public HighlightTile(ImageView image, boolean add, int x, int y, Map map, ArrayList<StackPane> unitStatDisplays) {
		this.image = image;
		this.add = add;
		shadow.setColor(Color.WHITE);
		this.x = x;
		this.y = y;
		this.map = map;
		this.unitStatDisplays = unitStatDisplays;
	}

	@Override
	public void handle(MouseEvent e) {
		if (add)
			image.setEffect(shadow);
		else
			image.setEffect(null);
		int counter = 0;
		if(map.getUnitMap()[y][x]!= null) {
			for(Unit unit : map.getUnitList()) {
				if(unit.getName().equals(map.getUnitMap()[y][x].getName())) {
					if (add)
						unitStatDisplays.get(counter).setEffect(shadow);
					else
						unitStatDisplays.get(counter).setEffect(null);
				}
				counter++;
			}		
		}
	}

}
