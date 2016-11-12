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
import monopolyviolet.model.Card;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
import monopolyviolet.model.Place;
import monopolyviolet.model.Player;

/**
 *
 * @author Andres
 */
public class DrawCard extends Scene {

	private Node<Button> buttons;
	private int selected;
	private Card card;
	private Player player;
	
	public DrawCard(Handler main,Player player, Card card) {
		super(main, "CARD", false);
		
		selected = -1;
		buttons = new Node();
		
		this.card = card;
		this.player = player;
		
		
		Button newButton = new Button((ssX-240)/2, ssY-140,240,80);
		newButton.setText("Accept");
		newButton.setInternalName("OK");
		buttons.add(newButton);
	}

	@Override
	protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				String internalName = buttons.get(selected).getInternalName();
				
				if (internalName.compareTo("OK") == 0) {
					this.dispose();
					nextStep();
				}
				
			}
		}
	}
	
	private void nextStep() {
		
		if (card.getActionCode() == Card.ADVANCE_ID) {
			
			player.setRoll(Integer.parseInt(card.getValue()));
			
		} else if (card.getActionCode() == Card.ADVANCE_TO_ID) {
			
			if (card.getValue().compareTo("nr") == 0) {
				int distance = main.getDistanceFrom(player, Place.RAILROAD_TYPE);
				player.setRoll(distance);
			} else if (card.getValue().compareTo("nu") == 0) {
				int distance = main.getDistanceFrom(player, Place.UTILITY_TYPE);
				player.setRoll(distance);
			} else if (card.getValue().compareTo("j") == 0) {
				main.sendJail(player.getId());
			} else {
				int id = Integer.parseInt(card.getValue());
				int distance = main.getDistanceFrom(player, main.getMap().get(id));
				player.setRoll(distance);
			}
			
		} else if (card.getActionCode() == Card.FREE_JAIL_ID) {
			
			player.getHand().add(card);
			if (card.getCardType() == Card.CHANCE_ID) {
				main.getChanceCard().remove(0);
			} else if (card.getCardType() == Card.COMMUNITY_CHEST_ID) {
				main.getCommunityCard().remove(0);
			}
			
		} else if (card.getActionCode() == Card.PAY_ID) {
			
			int amount = Integer.parseInt(card.getValue());
			main.gameState.add(new HandleAmount(main, amount, player, null, true));
			
		} else if (card.getActionCode() == Card.PAY_ID+Card.PER_ITEM_ID) {
			
			int amountHouses = main.getNumHousesFromPlayer(player);
			int amountHotels = main.getNumHotelsFromPlayer(player);
			amountHouses = amountHouses * Integer.parseInt(card.getValue().split(",")[0]);
			amountHotels = amountHotels * Integer.parseInt(card.getValue().split(",")[1]);
			main.gameState.add(new HandleAmount(main, amountHotels+amountHouses, player, null, true));
			
		} else if (card.getActionCode() == Card.PAY_ID+Card.PER_PLAYER_ID) {
			
			int amount = Integer.parseInt(card.getValue());
			for (int i = 1; i < main.getPlayers().size(); i++) {
				main.gameState.add(new HandleAmount(main, amount, player, main.getPlayers().get(i), true));
			}
			
		} else if (card.getActionCode() == Card.COLLECT_ID) {
			
			int amount = Integer.parseInt(card.getValue());
			main.gameState.add(new HandleAmount(main, amount, player, null, false));
			
		} else if (card.getActionCode() == Card.COLLECT_ID+Card.PER_ITEM_ID) {
			
			int amountHouses = main.getNumHousesFromPlayer(player);
			int amountHotels = main.getNumHotelsFromPlayer(player);
			amountHouses = amountHouses * Integer.parseInt(card.getValue().split(",")[0]);
			amountHotels = amountHotels * Integer.parseInt(card.getValue().split(",")[1]);
			main.gameState.add(new HandleAmount(main, amountHotels+amountHouses, player, null, false));
			
		} else if (card.getActionCode() == Card.COLLECT_ID+Card.PER_PLAYER_ID) {
			
			int amount = Integer.parseInt(card.getValue());
			for (int i = 1; i < main.getPlayers().size(); i++) {
				main.gameState.add(new HandleAmount(main, amount, player, main.getPlayers().get(i), false));
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
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(card.getCardDisplay(),(ssX/2)-(507/2),50, null);
		
		int counter = 0;
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			counter = counter + 1;
		}
			
		return display;
	}
	
}
