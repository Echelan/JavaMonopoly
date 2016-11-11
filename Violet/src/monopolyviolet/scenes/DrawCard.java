/*
 *  Monopoly Violet - A University Project by Andres Movilla
 *  MONOPOLY COPYRIGHT
 *  the distinctive design of the gameboard
 *  the four corner squares
 *  the Mr. Monopoly name and character
 *  and each of the distinctive elements of the board
 *  are trademarks of Hasbro, Inc.
 *  for its property trading game and game equipment.
 *  COPYRIGHT 1999 Hasbro, Inc. All Rights Reserved.
 *  No copyright or trademark infringement is intended in using Monopoly content on Monopoly Violet.
 */
package monopolyviolet.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Button;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
import static monopolyviolet.scenes.Scene.ssX;

/**
 *
 * @author Andres
 */
public class DrawCard extends Scene {

	private Node<Button> buttons;
	private int selected;
	
	public DrawCard(Handler main) {
		super(main, "CARD", false);
		
		selected = -1;
		buttons = new Node();
	}

	@Override
	protected void clickEvent(int x, int y) {
		this.dispose();
	}

	@Override
	protected void moveEvent(int x, int y) {

        int placement = -1;
        int counter = 0;

        while (counter < buttons.size()) {
			if (buttons.get(counter).isContained(x, y)) {
				placement = counter;
				buttons.get(counter).setHovered(true);
			} else {
				buttons.get(counter).setHovered(false);
			}
			counter = counter + 1;
        }

        selected = placement;
	}

	@Override
	protected void dragEvent(int x, int y) {
		
	}

	@Override
	protected void pressEvent(int x, int y) {

	}

	@Override
	protected void releaseEvent(int x, int y) {

	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		

		return display;
	}
	
}
