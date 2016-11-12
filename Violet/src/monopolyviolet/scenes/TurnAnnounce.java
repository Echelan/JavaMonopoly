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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Player;

/**
 *
 * @author Andres
 */
public class TurnAnnounce extends Scene {

	private Player player;
	
	public TurnAnnounce(Handler main, Player player) {
		super(main, "TURN", false);
		
		this.player = player;
		main.getGame().centerOn(player);
	}

	@Override
	protected void clickEvent(int x, int y) {
		this.dispose();
		main.gameState.add(new DiceRoll(main,player));
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

		int width;
		int height;
		int xPos;
		int yPos;
		
		height = 80;
		width = 400;
		xPos = (ssX/2)-(width/2);
		yPos = (ssY/2)-(height/2) - 200;
		
		g.setColor(player.getColor());
		g.fillRect(xPos-5,yPos-5,width+10,height+10);
		
		g.setColor(Color.white);
		g.fillRect(xPos,yPos,width,height);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,30));
		
		String line = player.getName()+"'s turn!";
		
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontX = xPos+(width - metrics.stringWidth(line)) / 2;
		int fontY = yPos+((height - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.drawString(line, fontX, fontY);
		
		
		g.setFont(new Font("Arial",Font.PLAIN,20));
		
		line = "Click to continue";
		
		metrics = g.getFontMetrics(g.getFont());
		fontX = xPos+(width - metrics.stringWidth(line)) / 2;
		fontY = yPos+((height - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.drawString(line,fontX,fontY+30);
		
		return display;
	}
	
}
