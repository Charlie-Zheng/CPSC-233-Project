/**
 * 
 */
package gui;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class StatInfoDisplay implements EventHandler<MouseEvent> {

	private boolean display = false;
	private static ArrayList<Label> infoList = new ArrayList<Label>();
	private Label info;
	private MapGUI mapGUI;
/**
 * Used to display info of the given stat when clicked
 * @param info
 * @param mapGUI
 */
	public StatInfoDisplay(Label info, MapGUI mapGUI) {
		this.mapGUI = mapGUI;
		this.info = info;
		infoList.add(info);
	}

	@Override
	/**
	 * Displays or hides information about the clicked stat
	 */
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