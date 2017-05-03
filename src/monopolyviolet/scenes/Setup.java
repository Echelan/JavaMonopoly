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

import java.awt.Font;
import java.awt.FontMetrics;
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

	private int numPlayers;
	private final int maxPlayers;
	private int selected;
	private Node<Button> buttons;
	
	public Setup(Handler main) {
		super(main, "SETUP", true);
		
		buttons = new Node();
		
		selected = -1;
		maxPlayers = 6;
		numPlayers = 0;
		
		Button newButton = new Button(ssX-210, ssY-110, 180, 80);
		newButton.setText("Roll dice");
		newButton.setInternalName("ROLLS");
		newButton.setEnabled(false);
		buttons.add(newButton);
		
		newButton = new Button(50, 100, 200, 60);
		newButton.setText("Add Player 1");
		newButton.setInternalName("ADD;1");
		buttons.add(newButton);
	}

	private void addPlayers() {
		
		main.getPlayers().add(new Player(numPlayers+1));
			
		numPlayers = numPlayers + 1;
		
		buttons.last().setText(main.getPlayers().last().getName());
		buttons.last().setInternalName("PLAYER;"+numPlayers);
		
		if (numPlayers < maxPlayers) {
			Button newButton = new Button(50, 100 + (80*numPlayers), 200, 60);
			newButton.setText("Add Player "+(numPlayers+1));
			newButton.setInternalName("ADD;"+(numPlayers+1));
			buttons.add(newButton);
		}
		
		if (numPlayers > 1) {
			buttons.get(0).setEnabled(true);
		}
	}

	private void removePlayers() {
		if (buttons.get(0).getInternalName().compareTo("ROLLS") == 0) {
			main.getPlayers().remove(main.getPlayers().size()-1);

			numPlayers = numPlayers - 1;

			if (buttons.last().getInternalName().split(";")[0].compareTo("ADD") == 0) {
				buttons.remove(buttons.size()-1);
			}

			buttons.last().setText("Add Player "+(numPlayers+1));
			buttons.last().setInternalName("ADD;"+(numPlayers+1));

			if (numPlayers < 2) {
				buttons.get(0).setEnabled(false);
			}
		}
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
	protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				String internalName = buttons.get(selected).getInternalName();
				
				if (internalName.compareTo("ROLLS") == 0) {
					doRolls();
				} else if (internalName.compareTo("START") == 0) {
					start();
				} else if (internalName.split(";")[0].compareTo("PLAYER") == 0) {
					removePlayers();
				} else if (internalName.split(";")[0].compareTo("ADD") == 0) {
					addPlayers();
				}
				
			}
		}
	}

	private void doRolls() {
		if (numPlayers > 1) {
			buttons.get(0).setInternalName("START");
			buttons.get(0).setText("Start!");
			if (numPlayers < maxPlayers) {
				buttons.remove(buttons.size() - 1);
			}
			arrange();
		}
	}
	
	private void arrange() {
		int highestRoll = 0;
		int highestID = 0;

		for (int i = 0; i < main.getPlayers().size(); i++) {
			if (highestRoll < main.getPlayers().get(i).getRoll()) {
				highestRoll = main.getPlayers().get(i).getRoll();
				highestID = i;
			}

		}

		main.getPlayers().rotate(highestID);
		
		int pCount = 0;
		int bCount = 0;
		while (bCount < buttons.size()) {
			Button thisButton = buttons.get(bCount);
			if (thisButton.getInternalName().split(";")[0].compareTo("PLAYER") == 0) {
				Player thisPlayer = main.getPlayers().get(pCount);
				thisButton.setInternalName("PLAYER;"+thisPlayer.getId());
				thisButton.setText(thisPlayer.getName());
				pCount = pCount + 1;
			}
			bCount = bCount+1;
			
		}
	}
	
	private void start() {
		this.dispose();
		for (int i = 0; i < main.getPlayers().size(); i++) {
			main.getPlayers().get(i).setRoll(0);
		}
		main.gameState.add(new Game(main));
		main.gameState.add(new TurnAnnounce(main,main.getPlayers().get(0)));
	}
	
	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, ssX, ssY, null);
		
		g.setFont(new Font("Arial",Font.BOLD,30));
		
		String line = "Player List";
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontX = (ssX - metrics.stringWidth(line)) / 2;
		int fontY = ((100 - metrics.getHeight()) / 2) + metrics.getAscent();
			
		g.drawString(line,fontX,fontY);
		
		int counter = 0;
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			counter = counter + 1;
		}
		
		for (int i = 0; i < numPlayers; i++) {
			Player thisPlayer = main.getPlayers().get(i);
			
			if (buttons.get(0).getInternalName().compareTo("START") != 0) {
				thisPlayer.setRoll(Player.roll(0, 1, 6));
			}
			
			int x = 270;
			int y = 100 + (80*i) + 5;
			g.drawImage(thisPlayer.getDieImage(), x, y, 202/4, 202/4, null);
			g.drawImage(thisPlayer.getPiece(), 30, y, null);
		}
		
		return display;
	}
	
}
