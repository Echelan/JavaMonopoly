/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.control.MouseHandler;
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
					communityCard.setLinkL(communityCard);
					communityCard.setLinkR(communityCard);
				}
			} else if (tempCard.getCardType() == Card.CHANCE_ID) {
				if (chanceCard != null) {
					chanceCard.add(tempCard);
				} else {
					chanceCard = tempCard;
					chanceCard.setLinkL(chanceCard);
					chanceCard.setLinkR(chanceCard);
				}
			}
		}
	}

	private void buildMap() {
		
	}
    
	public BufferedImage getDisplay() throws IOException{
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		
		
		return display;
	}

	@Override
	public void receiveAction(int action, int x, int y) {
		if (action == MouseHandler.EVENT_CLICK) {
			
		}
	}
	
}
