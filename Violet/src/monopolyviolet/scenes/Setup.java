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
import monopolyviolet.model.Button;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
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
	private String selecting;
	private Button buttons;
	
	public Setup(Handler main) {
		super(main, "SETUP", true);
		
		selecting = "";
		setUpPhase = 0;
		maxPlayers = 6;
		numPlayers = 0;
		
		Button newButton = new Button(400, 100, 150, 40);
		newButton.setColorFore(Color.gray);
		newButton.setText("Roll dice");
		newButton.setInternalName("ROLLS");
		buttons = newButton;
		
		newButton = new Button(400, 200, 150, 40);
		newButton.setColorFore(Color.gray);
		newButton.setText("Arrange");
		newButton.setInternalName("ARRANGE");
		buttons.add(newButton);
		
		newButton = new Button(400, 300, 150, 40);
		newButton.setColorFore(Color.gray);
		newButton.setText("Start!");
		newButton.setInternalName("START");
		buttons.add(newButton);
		
		newButton = new Button(50, 100, 200, 40);
		newButton.setColorFore(Color.gray);
		newButton.setText("Add Player 1");
		newButton.setInternalName("PLAYER;1");
		buttons.add(newButton);
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
		
		((Button) buttons.last()).setColorFore(Color.black);
		((Button) buttons.last()).setText("Player "+numPlayers);
		
		if (numPlayers < maxPlayers) {
			Button newButton = new Button(50, 100 + (80*numPlayers), 200, 40);
			newButton.setColorFore(Color.gray);
			newButton.setText("Add Player "+(numPlayers+1));
			newButton.setInternalName("PLAYER;"+(numPlayers+1));
			buttons.add(newButton);
		}
		
		if (numPlayers > 1) {
			((Button) buttons.get(buttons.search("ROLLS"))).setColorFore(Color.black);
		}
	}
	

	@Override
	protected void moveEvent(int x, int y) {
		String placement = "";
		Node lookingGlass = buttons;
		while (lookingGlass != null) {
			if (((Button) lookingGlass).isContained(x, y)) {
				placement = ((Button) lookingGlass).getInternalName();
				((Button) lookingGlass).setHovered(true);
			} else {
				((Button) lookingGlass).setHovered(false);
			}
			lookingGlass = lookingGlass.next();
		}
		selecting = placement;
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
		if (setUpPhase == 0) {
			if (selecting.split(";")[0].compareTo("PLAYER") == 0) {
				if (Integer.parseInt(selecting.split(";")[1]) == numPlayers+1) {
					addPlayer();
				}
			} else if (selecting.compareTo("ROLLS") == 0){
				doRolls();
			}
		} else if (setUpPhase == 1) {
			
		} else if (setUpPhase == 2) {
			
		}
	}

	private void doRolls() {
		if (numPlayers > 1) {
			setUpPhase = 1;
			for (int i = 0; i < numPlayers; i++) {
				((Player) playerList.get(i)).throwDie();
			}
			((Button) buttons.get(buttons.search("ROLLS"))).setColorFore(Color.gray);
			((Button) buttons.get(buttons.search("ARRANGE"))).setColorFore(Color.black);
			if (Integer.parseInt(((Button) buttons.last()).getInternalName().split(";")[1]) == numPlayers+1) {
				buttons.remove(buttons.size() - 1);
			}
		}
	}
	
	private void arrange() {
		
	}
	
	private void start() {
		
	}
	
	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, ssX, ssY, null);
		
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("Player List",300,50);
		
		Node lookingGlass = buttons;
		while (lookingGlass != null) {
			Button thisButton = (Button) lookingGlass;
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			
			lookingGlass = lookingGlass.next();
		}
		
		for (int i = 0; i < numPlayers; i++) {
			Player thisPlayer = (Player) playerList.get(i);
			int x = 270;
			int y = 100 + (80*i) - 5;
			g.drawImage(thisPlayer.getDieImage(), x, y, null);
		}
		
		return display;
	}
	
}
