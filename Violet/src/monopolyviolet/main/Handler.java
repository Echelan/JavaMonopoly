/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

/**
 *
 * @author movillaf
 */
public class Handler {
	public static final int SIZEX = 900;
	public static final int SIZEY = 800;
        
        private Game gameInstance;
        
	public Handler() {
            new GameWindow(SIZEX,SIZEY,this);

            gameInstance = new Game();

            for (int i = 0; i < monopolyviolet.data.NIC.INFO_CARDS.size(); i++) {
                Card tempCard = new Card(i);
                if (tempCard.getCardType() == Card.COMMUNITY_CHEST_ID) {
                    if (gameInstance.getCommunityCard() != null) {
                        gameInstance.getCommunityCard().insertAfter(tempCard);
                    } else {
                        gameInstance.setCommunityCard(tempCard);
                    }
                } else if (tempCard.getCardType() == Card.CHANCE_ID) {
                    if (gameInstance.getChanceCard() != null) {
                        gameInstance.getChanceCard().insertAfter(tempCard);
                    } else {
                        gameInstance.setChanceCard(tempCard);
                    }
                }
                
             }
	}
    
	public Card getCard() {
            gameInstance.nextChanceCard();
            return gameInstance.getChanceCard();
        }
        
        
	private void createProperties() {
		
	}
}
