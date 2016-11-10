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
import monopolyviolet.model.Player;
import monopolyviolet.model.Property;

/**
 *
 * @author Andres
 */
public class PayAmount extends Scene {

	private final int debt;
	private final Player payingPlayer;
	private final Player paidPlayer;
	private Node<Button> buttons;
	private Property propertyToSell;
	private int selected;
	
	public PayAmount(Handler main, int debt, Player payingPlayer, Player paidPlayer) {
		super(main, "PAY", false);
		
		this.buttons = new Node();
		
		this.debt = debt;
		this.paidPlayer = paidPlayer;
		this.payingPlayer = payingPlayer;
		this.selected = -1;
		
		Button newButton = new Button((ssX-240)/2,(ssY-140),240,80);
		newButton.setText("Pay $"+debt);
		newButton.setInternalName("PAY");
		buttons.add(newButton);
		
		if (this.payingPlayer.getFunds() < debt) {
			propertyToSell = ((Game) main.gameState.get(main.gameState.size()-2)).findSellableProperty(debt, this.payingPlayer.getId());
			if (propertyToSell != null) {
				newButton.setInternalName("SELL");
				newButton.setInternalName("Sell "+propertyToSell.getName());
			}
		}
		
		
	}

	@Override
	protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				String internalName = buttons.get(selected).getInternalName();
				
				if (internalName.compareTo("PAY") == 0) {
					doPayment();
				} else if (internalName.compareTo("SELL") == 0) {
					sellProperty();
				}
				
			}
		}
	}
	
	private void sellProperty() {
		propertyToSell.resetOwner();
		payingPlayer.addFunds(propertyToSell.getBuyPrice()/2);
		propertyToSell = null;
		try {
			((Game) main.gameState.last()).buildMapDisplay();
		} catch (IOException ex) {
		}
		buttons.get(0).setText("Pay $"+debt);
		buttons.get(0).setInternalName("PAY");
	}
	
	private void doPayment() {
		payingPlayer.removeFunds(debt);
		if (paidPlayer != null) {
			paidPlayer.addFunds(debt);
		}
		
		this.dispose();
		
		if (payingPlayer.getFunds() < 0) {
			payingPlayer.setBankrupt(true);
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
		
		int counter = 0;
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			counter = counter + 1;
		}
		
		return display;
	}
	
}
