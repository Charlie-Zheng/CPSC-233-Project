package main;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SelectedTile implements EventHandler<MouseEvent> {
	private int x, y;
	private PlayerGUI gui;
	public SelectedTile(int x, int y, PlayerGUI playerGUI) {
		gui = playerGUI;
		this.x = x;
		this.y = y;
	}
	/**
	 * @param x2
	 * @param y2
	 */
	public SelectedTile(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}
	@Override
	public void handle(MouseEvent e) {
		System.out.println("x: " + x + "\ty: " + y);
	}

}
