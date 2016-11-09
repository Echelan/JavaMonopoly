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
package monopolyviolet.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 *
 * @author movillaf
 */
public class Player extends Node{
    
    private int funds;
    private Node<Integer> deeds;
    private Node<Card> hand;
	private int roll;
	private final int id;

	public Player(int id) {
		this.funds = 1500;
		this.roll = 0;
		this.id = id;
		
		this.deeds = new Node();
		this.hand = new Node();
		
		hand.setCircular(true);
		hand.setDoubleLink(true);
		
		deeds.setCircular(true);
		deeds.setDoubleLink(true);
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
	public void addProperty(int deed) {
		this.deeds.add(deed);
	}

	/**
	 * Rolls -numDice- dice with -numSides- sides and adds a base -value-.
	 * @param value Base value to add.
	 * @param numDice Number of dice to roll.
	 * @param numSides Number of sides per die.
	 * @return Result of dice roll.
	 */
	public static int roll(int value, int numDice, int numSides) {
		Random rnd = new Random();

		for (int i = 0; i < numDice; i++) {
			value = value + (rnd.nextInt(numSides) + 1);
		}

		return value;
	}
	
	public BufferedImage getPiece() throws IOException {
		BufferedImage display = new BufferedImage(306/8, 507/8, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		String path = "assets/players/"+this.getId()+"/piece.png";
		g.drawImage(ImageIO.read(new File(path)), 0, 0, 306/8, 507/8, null);
		
		return display;
	}
	
	public BufferedImage getDieImage() throws IOException {
		BufferedImage display = new BufferedImage(202/2, 202/2, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		String path = "assets/players/"+this.getId()+"/"+this.getRoll()+".png";
		g.drawImage(ImageIO.read(new File(path)), 0, 0, 202/2, 202/2, null);
		
		return display;
	}
	
	public void throwDie() {
		this.setRoll(roll(0,1,6));
	}
	
	/**
	 * @param card the card to add
	 */
	public void addCard(Card card) {
		this.hand.add(card);
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
	public Node<Integer> getDeeds() {
		return deeds;
	}

	/**
	 * @return the hand
	 */
	public Node<Card> getHand() {
		return hand;
	}

	/**
	 * @return the roll
	 */
	public int getRoll() {
		return roll;
	}

	/**
	 * @param roll the roll to set
	 */
	public void setRoll(int roll) {
		this.roll = roll;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
    
	
}
