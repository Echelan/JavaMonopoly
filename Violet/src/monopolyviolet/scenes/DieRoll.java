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
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Player;
import static monopolyviolet.scenes.Scene.ssX;

/**
 *
 * @author Andres
 */
public class DieRoll extends Scene {

	private int id;
	private int roll;
	private int stage;
	
	public DieRoll(Handler main, int id) {
		super(main, "DICE", false);
		
		this.id = id;
		this.stage = 0;
	}

	@Override
	protected void clickEvent(int x, int y) {
		if (stage == 0) {
			stage = 1;
		} else {
			this.dispose();
			((Game) main.gameState.last()).setRoll(id,roll);
		}
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
		width = 100;
		height = 50;
		xPos = (ssX/2) - (width/2);
		yPos = (ssY/2) - (height/2);
		
		if (stage == 0) {
			g.setColor(Color.white);
			g.setFont(new Font("Arial",Font.BOLD,20));
			g.fillRect(xPos-5,yPos-70,width+30,height-10);
			g.setColor(Color.BLACK);
			g.drawString("Click to roll!",xPos,yPos-50);
			this.roll = Player.roll(0,1,6);
		}
		
		width = 202/2;
		height = 202/2;
		xPos = (ssX/2) - (width/2);
		yPos = (ssY/2) - (height/2);
		
		String path = "assets/players/"+id+"/"+this.roll+".png";
		g.drawImage(ImageIO.read(new File(path)), xPos, yPos, width, height, null);
		
		
		return display;
	}
	
}
