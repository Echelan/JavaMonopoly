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

/**
 *
 * @author Andres
 */
public class PayAmount extends Scene {

	private int debt;
	private int payingPlayer;
	private int paidPlayer;
	private Node<Button> buttons;
	String selecting;
	
	public PayAmount(Handler main, int debt, int payer, int paid) {
		super(main, "PAY", false);
		
		this.debt = debt;
		this.paidPlayer = paid;
		this.payingPlayer = payer;
		this.selecting = "";
		
		this.buttons = new Node();
		
		Button newButton = new Button(150,180,150,50);
		newButton.setText("Pay "+debt);
		newButton.setInternalName("PAY");
		buttons.add(newButton);
	}

	@Override
	protected void clickEvent(int x, int y) {
		if (selecting.compareTo("ROLLS") == 0){
			doPayment();
		}
	}
	
	private void doPayment() {
		
	}

	@Override
	protected void moveEvent(int x, int y) {
		String placement = "";
		int counter = 0;
		
		while (counter < buttons.size()) {
			if (buttons.get(counter).isContained(x, y)) {
				placement = buttons.get(counter).getInternalName();
				buttons.get(counter).setHovered(true);
			} else {
				buttons.get(counter).setHovered(false);
			}
			counter = counter + 1;
		}
		
		selecting = placement;
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
		}
		
		return display;
	}
	
}
