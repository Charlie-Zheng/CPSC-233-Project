package main;

import javafx.scene.paint.Color;

import javafx.event.EventHandler;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HighlightTile implements EventHandler<MouseEvent> {
	private ImageView image;
	private boolean add = false;
	private InnerShadow shadow = new InnerShadow();

	public HighlightTile(ImageView image, boolean add) {
		this.image = image;
		this.add = add;
		shadow.setColor(Color.WHITE);
	}

	@Override
	public void handle(MouseEvent e) {
		if (add)
			image.setEffect(shadow);
		else
			image.setEffect(null);
	}

}
