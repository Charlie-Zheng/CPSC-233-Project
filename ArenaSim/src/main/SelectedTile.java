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
	@Override
	public void handle(MouseEvent e) {
		gui.setSelectedX(x);
		gui.setSelectedY(y);
		System.out.println("x: " + x + "\ty: " + y);
		gui.notify();
	}

}
