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

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Card;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;


/**
 *
 * @author movillaf
 */
public class Game extends Scene{
    
    private Card communityCard;
    private Card chanceCard;
	private Node map;
	
	public Game(Handler main) {
		super(main, "GAME", true);
		
		this.communityCard = null;
		this.chanceCard = null;
		this.map = null;
		
		createDecks();
	}
	
    
	private void createDecks() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_CARDS.size(); i++) {
			Card tempCard = new Card(i);
			if (tempCard.getCardType() == Card.COMMUNITY_CHEST_ID) {
				if (communityCard != null) {
					communityCard.add(tempCard);
				} else {
					communityCard = tempCard;
					communityCard.setCircular(true);
					communityCard.setDoubleLink(true);
				}
			} else if (tempCard.getCardType() == Card.CHANCE_ID) {
				if (chanceCard != null) {
					chanceCard.add(tempCard);
				} else {
					chanceCard = tempCard;
					communityCard.setCircular(true);
					communityCard.setDoubleLink(true);
				}
			}
		}
	}

	private void buildMap() {
		
	}

	@Override
	protected void clickEvent(int x, int y) {
		
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
    
	public BufferedImage getDisplay() throws IOException{
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		
		
		return display;
	}
	
}
