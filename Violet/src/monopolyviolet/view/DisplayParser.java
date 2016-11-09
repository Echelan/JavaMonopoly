/*
 * Monopoly Violet - A University Project by Andres Movilla
 * MONOPOLY COPYRIGHT
 * the distinctive design of the gameboard
 * the four corner squares
 * the Mr. Monopoly name and character
 * and each of the distinctive elements of the board
 * are trademarks of Hasbro, Inc.
 * for its property trading game and game equipment.
 * COPYRIGHT 1999 Hasbro, Inc. All Rights Reserved.
 * No copyright or trademark infringement is intended in using Monopoly content on Monopoly Violet.
 */
package monopolyviolet.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class DisplayParser {
	
	public static BufferedImage displayImage() {
		BufferedImage display = new BufferedImage(Handler.SCREEN_SIZE_X, Handler.SCREEN_SIZE_Y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int counter = 0;
		while (counter < Handler.gameState.size()) {
			try {
				g.drawImage(Handler.gameState.get(counter).getDisplay(), 0, 0, null);
			} catch (IOException ex) {
			}
			counter = counter + 1;
		}

		return display;
	}
	
}
