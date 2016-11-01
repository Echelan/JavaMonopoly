/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author movillaf
 */
public class Game {
    
    private Card communityCard;
    private Card chanceCard;

    public Game() {
        this.communityCard = null;
        this.chanceCard = null;
    }

    public void setCommunityCard(Card communityCard) {
        communityCard.setLinkL(communityCard);
        communityCard.setLinkR(communityCard);
        this.communityCard = communityCard;
    }

    public void setChanceCard(Card chanceCard) {
        chanceCard.setLinkL(chanceCard);
        chanceCard.setLinkR(chanceCard);
        this.chanceCard = chanceCard;
    }
    
    public Card getCommunityCard() {
        return communityCard;
    }

    public Card getChanceCard() {
        return chanceCard;
    }
    
    public void nextChanceCard() {
        chanceCard = (Card) chanceCard.getLinkR();
    }
    
    public void nextCommunityCard() {
        communityCard = (Card) communityCard.getLinkR();
    }
    
}
