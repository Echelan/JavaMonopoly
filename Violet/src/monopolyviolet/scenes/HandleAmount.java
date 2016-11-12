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
import monopolyviolet.model.Button;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
import monopolyviolet.model.Player;

/**
 *
 * @author Andres
 */
public class HandleAmount extends Scene {

	private final int amount;
	private final boolean isPayment;
	private final Player subject1;
	private final Player subject2;
	private Node<Button> buttons;
	private int selected;
	private boolean creationPending;
	
	public HandleAmount(Handler main, int amount, Player subject1, Player subject2, boolean isPayment) {
		super(main, "PAY/COLLECT", false);
		
		this.buttons = new Node();
		
		this.isPayment = isPayment;
		this.amount = amount;
		this.subject2 = subject2;
		this.subject1 = subject1;
		this.selected = -1;
		this.creationPending = false;
		
		Button newButton = new Button((ssX-240)/2,(ssY-180),240,80);
		if (isPayment) {
			newButton.setText("Pay $"+amount);
			newButton.setInternalName("OK");
			if (this.subject1.getFunds() < amount) {
				if (main.getPlayerWorth(this.subject1) >= amount) {
					this.creationPending = true;
				} else {
					this.subject1.setBankrupt(true);
					this.dispose();
					main.sellAll(this.subject1);
				}
			}
		} else {
			newButton.setText("Collect $"+amount);
			newButton.setInternalName("OK");
		}
		buttons.add(newButton);
		
	}

	@Override
	protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				String internalName = buttons.get(selected).getInternalName();
				
				if (internalName.compareTo("OK") == 0) {
					this.dispose();
					doPayment();
				}
				
			}
		}
	}
	
	private void doPayment() {
		if (this.isPayment) {
			subject1.removeFunds(amount);
			if (subject2 != null) {
				subject2.addFunds(amount);
			}
		} else {
			subject1.addFunds(amount);
			if (subject2 != null) {
				subject2.removeFunds(amount);
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
		
		if (this.creationPending) {
			this.creationPending = false;
			main.gameState.add(new PropertyList(main, subject1, amount));
		}
		
		if (main.gameState.last() == this) {
			int counter = 0;
			while (counter < buttons.size()) {
				Button thisButton = buttons.get(counter);
				g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
				counter = counter + 1;
			}
			
			String text = subject1.getName();
			int width = 300;
			int height = 40;
			int strokeWidth = 2;
			Font font = new Font("Arial",Font.BOLD,20);
			Color strokeColor = subject1.getColor();
			Color fillColor = Color.white;
			Color textColor = Color.black;
			int x = (ssX-width)/2;
			int y = 50;
			g.drawImage(genTextRect(text, width, height, strokeWidth, font, strokeColor, fillColor, textColor), x, y, null);
			String description;
			if (!this.isPayment) {
				description = "Collecting from ";
			} else {
				description = "Paying ";
			}
			if (subject2 != null) {
				text = description + subject2.getName();
				strokeColor = subject2.getColor();
			} else {
				text = description+"the bank";
				strokeColor = Color.black;
			}
			y = y + 50;
			g.drawImage(genTextRect(text, width, height, strokeWidth, font, strokeColor, fillColor, textColor), x, y, null);
		}
		
		return display;
	}
	
}
