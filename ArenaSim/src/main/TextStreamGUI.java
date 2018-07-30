package main;

import java.io.OutputStream;
import java.io.PrintStream;
import javafx.scene.control.TextArea;

/**
 * redirects System.out OutputStream to a textArea created.
 * 
 * @author Peter
 *
 */

public class TextStreamGUI extends PrintStream {
	private TextArea console;

	/**
	 * constructor takes in TextArea of area from GameGUI which is a pre-made TextArea
	 * @param area a pre-made TextArea from GameGUI.java
	 * @param out
	 */
	public TextStreamGUI(TextArea area, OutputStream out) {
		super(out); //calls super constructor 
		console = area;
	}
	/**
	 * takes in text and appends it to the textarea called console
	 * adds new line after printing text
	 * @param text a string of text from print statement
	 */
	public void println(String text) {
		console.appendText(text + "\n"); //appends text to the TextArea
	}

	/**
	 * takes in text and appends it to the text area called console
	 * does not add new line after printing text
	 * @param text a string of text from print statement
	 */
	public void print(String text) {
		console.appendText(text); //appends text to the TextArea
	}

}
