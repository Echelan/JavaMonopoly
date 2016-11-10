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

import java.awt.Color;
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
public class Player {
    
	private static final String NAME_1 = "Captain Crimson";
	private static final String NAME_2 = "Assistant Azure";
	private static final String NAME_3 = "Ornate Orange";
	private static final String NAME_4 = "Major Magenta";
	private static final String NAME_5 = "General Green";
	private static final String NAME_6 = "Colonel Cyan";
	
	private static final Color COLOR_1 = Color.getHSBColor(0/360f, 100/100f, 74.5f/100f);
	private static final Color COLOR_2 = Color.getHSBColor(210/360f, 100/100f, 74.5f/100f);
	private static final Color COLOR_3 = Color.getHSBColor(30/360f, 100/100f, 74.5f/100f);
	private static final Color COLOR_4 = Color.getHSBColor(300/360f, 100/100f, 74.5f/100f);
	private static final Color COLOR_5 = Color.getHSBColor(150/360f, 100/100f, 74.5f/100f);
	private static final Color COLOR_6 = Color.getHSBColor(180/360f, 100/100f, 74.5f/100f);
	
    private int funds;
    private Node<Card> hand;
    private int doubleCount;
    private boolean rolledDoubles;
    private int jailed;
	private int roll;
	private int lastRoll;
	private final int id;
	private final String name;
	private final Color color;
	private boolean bankrupt;

	public Player(int id) {
		this.lastRoll = 0;
		this.bankrupt = false;
		this.funds = 1500;
		this.roll = 0;
		this.id = id;
		this.doubleCount = 0;
		this.rolledDoubles = false;
		this.jailed = 0;
		this.hand = new Node();
		
		hand.setCircular(true);
		hand.setDoubleLink(true);
		
		switch(this.id) {
			case 1:
				this.color = COLOR_1;
				this.name = NAME_1;
				break;
			case 2:
				this.color = COLOR_2;
				this.name = NAME_2;
				break;
			case 3:
				this.color = COLOR_3;
				this.name = NAME_3;
				break;
			case 4:
				this.color = COLOR_4;
				this.name = NAME_4;
				break;
			case 5:
				this.color = COLOR_5;
				this.name = NAME_5;
				break;
			case 6:
				this.color = COLOR_6;
				this.name = NAME_6;
				break;
			default:
				this.name = "Bob";
				this.color = Color.gray;
				break;
		}
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
	
	/**
	 * @param card the card to add
	 */
	public void addCard(Card card) {
		this.hand.add(card);
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
	
    public void reduceSentence() {
		this.jailed = this.jailed - 1;
    }

	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	
	/**
	 * @return the funds
	 */
	public int getFunds() {
		return funds;
	}

	/**
	 * @param funds the funds to set
	 */
	public void setFunds(int funds) {
		this.funds = funds;
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

    /**
     * @return the doubleCount
     */
    public int getDoubleCount() {
        return doubleCount;
    }

    /**
     * @param doubleCount the doubleCount to set
     */
    public void setDoubleCount(int doubleCount) {
        this.doubleCount = doubleCount;
    }

    /**
     * @return the rolledDoubles
     */
    public boolean isRolledDoubles() {
        return rolledDoubles;
    }

    /**
     * @param rolledDoubles the rolledDoubles to set
     */
    public void setRolledDoubles(boolean rolledDoubles) {
        this.rolledDoubles = rolledDoubles;
    }

    /**
     * @return the jailed
     */
    public boolean isJailed() {
        return jailed!=0;
    }

    /**
     * @param jailed the jailed to set
     */
    public void setJailed(boolean jailed) {
		if (jailed) {
			this.jailed = 3;
		} else {
			this.jailed = 0;
		}
    }
    
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the bankrupt
	 */
	public boolean isBankrupt() {
		return bankrupt;
	}

	/**
	 * @param bankrupt the bankrupt to set
	 */
	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}
    
	//</editor-fold>

	/**
	 * @return the lastRoll
	 */
	public int getLastRoll() {
		return lastRoll;
	}

	/**
	 * @param lastRoll the lastRoll to set
	 */
	public void setLastRoll(int lastRoll) {
		this.lastRoll = lastRoll;
	}
}
