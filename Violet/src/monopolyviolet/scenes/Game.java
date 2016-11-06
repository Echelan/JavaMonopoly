/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
					communityCard.insertAfter(tempCard);
				} else {
					communityCard = tempCard;
					communityCard.setLinkL(communityCard);
					communityCard.setLinkR(communityCard);
				}
			} else if (tempCard.getCardType() == Card.CHANCE_ID) {
				if (chanceCard != null) {
					chanceCard.insertAfter(tempCard);
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
		BufferedImage tempStitched = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		
		
		return tempStitched;
	}

	@Override
	public void receiveAction(String action, String state) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
