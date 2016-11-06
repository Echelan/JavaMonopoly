/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.model;

/**
 *
 * @author movillaf
 */
public class Player {
    
    private int funds;
    private Property deeds;
    private Card hand;

	public Player() {
		this.funds = 1500;
	}

	/**
	 * @return the funds
	 */
	public int getFunds() {
		return funds;
	}

	/**
	 * @param deed the deed to add
	 */
	public void addProperty(Property deed) {
		if (deeds == null) {
			this.deeds = deed;
			this.deeds.setLinkL(this.deeds);
			this.deeds.setLinkR(this.deeds);
		} else {
			this.deeds.insertAfter(deed);
		}
	}

	/**
	 * @param card the card to add
	 */
	public void addCard(Card card) {
		if (deeds == null) {
			this.hand = card;
			this.hand.setLinkL(this.hand);
			this.hand.setLinkR(this.hand);
		} else {
			this.hand.insertAfter(card);
		}
	}

	/**
	 * @param funds the funds to set
	 */
	public void setFunds(int funds) {
		this.funds = funds;
	}

	/**
	 * @param funds the funds to add
	 */
	public void addFunds(int funds) {
		this.funds = this.funds + funds;
	}

	/**
	 * @param funds the funds to remove
	 */
	public void removeFunds(int funds) {
		this.funds = this.funds - funds;
	}

	/**
	 * @return the deeds
	 */
	public Property getDeeds() {
		return deeds;
	}

	/**
	 * @return the hand
	 */
	public Card getHand() {
		return hand;
	}
    
	
}
