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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Player;
import static monopolyviolet.scenes.Scene.ssX;

/**
 *
 * @author Andres
 */
public class TurnAnnounce extends Scene {

	private Player player;
	
	public TurnAnnounce(Handler main, Player player) {
		super(main, "TURN", false);
		
		this.player = player;
	}

	@Override
	protected void clickEvent(int x, int y) {
		this.dispose();
		((Game) main.gameState.last()).moveToPlayer(player);
		main.gameState.add(new DieRoll(main,player));
	}

	@Override
	protected void moveEvent(int x, int y) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void dragEvent(int x, int y) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void pressEvent(int x, int y) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	protected void releaseEvent(int x, int y) {
//		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();

		int width;
		int height;
		int xPos;
		int yPos;
		
		height = 100;
		width = 300;
		xPos = (ssX/2)-(width/2);
		yPos = (ssY/2)-(height/2);
		
		
		g.fillRect(xPos,yPos,width,height);
		
		height = -30;
		width = 200;
		xPos = (ssX/2)-(width/2);
		yPos = (ssY/2)-(height/2);
		
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.setColor(Color.black);
		g.drawString("Player "+player.getId()+"'s turn!", xPos, yPos);
		
		width = 150;
		xPos = (ssX/2)-(width/2);
		g.setFont(new Font("Arial",Font.PLAIN,20));
		g.drawString("Click to continue",xPos,yPos+30);
		
		return display;
	}
	
}