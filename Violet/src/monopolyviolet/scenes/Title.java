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
package monopolyviolet.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopolyviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class Title extends Scene {

	private int blinkRate;
	
	public Title(Handler main) {
		super(main, "TITLE", true);
		blinkRate = 0;
	}

	@Override
	protected void clickEvent(int x, int y) {
		this.dispose();
		main.gameState.add(new Setup(main));
	}

	@Override
	protected void moveEvent(int x, int y) {

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
		
		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, ssX, ssY, null);
		
		g.setColor(Color.black);
		g.fillRect(0, ssY - 100, ssX, 100);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("José Luis Martinez & Andrés Movilla", 20, ssY - 25);
		
		int logoX = (ssX/2)-(1292/8);
		int logoW = (1292/4);
		int logoH = (641/4);
		int logoY = 20;
		g.drawImage(ImageIO.read(new File("assets/title/violetMonopolyLogo.png")), logoX, logoY, logoW, logoH, null);
		
		blinkRate = blinkRate + 1;
		if (blinkRate > 10) {
			blinkRate = 0;
		}
		if (blinkRate > 5) {
			g.setFont(new Font("Arial", Font.BOLD, 40));
			g.drawString("Click to Start",(ssX/2)-110, 250);
		}
		
		return display;
	}
	
}
