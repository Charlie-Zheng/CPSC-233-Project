/**
 * 
 */
package main;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * @author charl
 */
public class StatInfoDisplay implements EventHandler<MouseEvent> {

	private boolean display = false;
	private static ArrayList<Label> infoList = new ArrayList<Label>();
	private Label info;
	private MapGUI mapGUI;

	public StatInfoDisplay(Label info, MapGUI mapGUI) {
		this.mapGUI = mapGUI;
		this.info = info;
		infoList.add(info);
	}

	@Override
	public void handle(MouseEvent event) {
		if (display) {
			mapGUI.hide(info);
			display = false;
		} else {
			mapGUI.display(info);
			for(Label label:infoList) {
				if(!label.equals(info)) {
					mapGUI.hide(label);
				}
			}
			display = true;
		}
	}

}
