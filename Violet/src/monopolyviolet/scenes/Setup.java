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
import monopolyviolet.model.Player;

/**
 *
 * @author Andres
 */
public class Setup extends Scene{

	private Player playerList;
	private int numPlayers;
	private int maxPlayers;
	private int setUpPhase;
			
	public Setup(Handler main) {
		super(main, "SETUP", true);
		
		setUpPhase = 0;
		maxPlayers = 4;
		numPlayers = 0;
	}

	private void addPlayer() {
		if (playerList == null) {
			Player newPlayer = new Player(numPlayers+1);
			playerList = newPlayer;
			playerList.setCircular(true);
		} else {
			Player newPlayer = new Player(numPlayers+1);
			playerList.add(newPlayer);
		}
		numPlayers = numPlayers + 1;
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
	protected void clickEvent(int x, int y) {
		if (50 < x && x < 250){
			if (numPlayers < maxPlayers) {
				int minY = 100 + (80*numPlayers);
				int maxY = 100 + (80*numPlayers) + 40;
				if (minY < y && y < maxY) {
					addPlayer();
				}
			}
		}
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, ssX, ssY, null);
		
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("Player List",(ssX/4),50);
		
		for (int i = 0; i < numPlayers + 1; i++) {
			int x = 50;
			int y = 100 + (80*i);
			
			if (i < maxPlayers) {
				g.setColor(Color.white);
				g.fillRect(x, y, 200, 40);

				if (i < numPlayers) {
					g.setColor(Color.black);
					g.drawString("Player "+(i+1),x+10,y+30);

					g.drawImage(((Player) playerList.get(i)).getDieImage(), x+210,y-5, null);
				} else if (setUpPhase == 0) {
					g.setColor(Color.gray);
					g.drawString("Add Player "+(i+1),x+10,y+30);
				}
			}
		}
		
		return display;
	}
	
}
