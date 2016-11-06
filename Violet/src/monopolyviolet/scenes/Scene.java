/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.scenes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;

/**
 *
 * @author Andres
 */
public abstract class Scene extends Node{
	

	protected static final int ssX = (int) (monopolyviolet.model.Handler.SCREEN_SIZE_X);
	protected static final int ssY = (int) (monopolyviolet.model.Handler.SCREEN_SIZE_Y);
	protected final Handler main;
	private final String name;
	private final boolean full;

	public Scene(Handler main, String name, boolean full) {
		this.main = main;
		this.name = name;
		this.full = full;
	}

	public abstract void receiveAction(int action, int x, int y);

	public void dispose() {
		main.gameState.remove(main.gameState.size() - 1);
	}

	public abstract BufferedImage getDisplay() throws IOException;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the full
	 */
	public boolean isFull() {
		return full;
	}
	
	protected String[] genMultilineText(String originalText, int charsInLine) {
		java.util.ArrayList<String> listResult = new java.util.ArrayList<String>();
		
		for (int i = 0; i < (originalText.length() / charsInLine) + 1; i++) {
			String prefix = "", suffix = "";

			int thisLineFirstChar = i * charsInLine;
			int thisLinePrevChar = thisLineFirstChar - 1;
			int thisLineLastChar = ((i + 1) * charsInLine) - 2;
			int thisLineNextChar = thisLineLastChar + 1;

			if (thisLineFirstChar != 0){
				if (originalText.charAt(thisLinePrevChar) != ' ') {
					prefix = "" + originalText.charAt(thisLinePrevChar);
				}
			}

			if (thisLineNextChar < originalText.length()) {
				if (originalText.charAt(thisLineNextChar) != ' ' && originalText.charAt(thisLineLastChar) != ' ') {
					suffix = "-";
				}
			} else {
				thisLineLastChar = originalText.length() - 1;
			}
			listResult.add(prefix + originalText.substring(thisLineFirstChar, thisLineLastChar+1) + suffix);
		}
		
		String[] result = new String[listResult.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = listResult.get(i);
		}
		
		return result;
	}

}
