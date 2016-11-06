/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Handler;
import monopolyviolet.scenes.Scene;

/**
 *
 * @author Andres
 */
public class DisplayParser {
	private static final int ssX = Handler.SCREEN_SIZE_X, ssY = Handler.SCREEN_SIZE_Y;

	public static BufferedImage displayImage() {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int counter = 0;
		boolean done = false;
		
		if (!Handler.gameState.isEmpty()) {
			
			while (!done) {
				counter = counter + 1;
				Scene thisScene = ((Scene) Handler.gameState.get(Handler.gameState.size() - counter));

				done = thisScene.isFull();
			}
			
			for (int i = Handler.gameState.size() - counter; i < Handler.gameState.size(); i++) {
				try {
					g.drawImage(((Scene) Handler.gameState.get(i)).getDisplay(), 0, 0, null);
				} catch (IOException ex) {
				}
			}
		}

		return display;
	}
	
}
