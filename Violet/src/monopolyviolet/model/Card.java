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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author movillaf
 */
public class Card {
    
	public static Color COMMUNITY_CHEST_COLOR = Color.getHSBColor(210/360f, 39.6f/100f, 85.1f/100f);
	public static Color CHANCE_COLOR = Color.getHSBColor(0/360f, 39.6f/100f, 85.1f/100f);
	
    public static int COMMUNITY_CHEST_ID = 1;
    public static int CHANCE_ID = 2;
    
    public static int OTHER_ID = 0;
    public static int FREE_JAIL_ID = 1;
    public static int ADVANCE_ID = 2;
    public static int ADVANCE_TO_ID = 3;
    public static int PAY_ID = 4;
    public static int COLLECT_ID = 7;
	
    public static int STRAIGHT = 0;
    public static int PER_ITEM_ID = 1;
    public static int PER_PLAYER_ID = 2;
    
    private String title;
    private String description;
    private int cardType;
    private int actionCode;
	private Color color;
    private String value;
    
    public Card(int id) {
        readInfo(id);
    }
    
    private void readInfo(int id) {
		String cardInfo = monopolyviolet.data.NIC.INFO_CARDS.get(id);
        for (int i = 0; i <  cardInfo.split(";").length; i++) {
            if (cardInfo.split(";")[i].split("=")[0].compareTo("title")==0) {
				String wrap =cardInfo.split(";")[i].split("=")[1];
				wrap = wrap.substring(1,  wrap.length()-1);
                this.title = wrap;
            }else if (cardInfo.split(";")[i].split("=")[0].compareTo("description")==0) {
				String wrap =cardInfo.split(";")[i].split("=")[1];
				wrap = wrap.substring(1,  wrap.length()-1);
                this.description = wrap;
            }else if (cardInfo.split(";")[i].split("=")[0].compareTo("id")==0) {
				String wrap =cardInfo.split(";")[i].split("=")[1];
				wrap = wrap.substring(1,  wrap.length()-1);
                if (wrap.compareTo("COMMUNITY")==0) {
                    this.cardType = COMMUNITY_CHEST_ID;
					this.color = COMMUNITY_CHEST_COLOR;
                } else if (wrap.compareTo("CHANCE")==0) {
                    this.cardType = CHANCE_ID;
					this.color = CHANCE_COLOR;
                }
            }else if (cardInfo.split(";")[i].split("=")[0].compareTo("flags")==0) {
				String flags =cardInfo.split(";")[i].split("=")[1];
				flags = flags.substring(1,  flags.length()-1);
                if (flags.contains("at")) {
                    this.actionCode = ADVANCE_TO_ID;
                } else if (flags.contains("ai")) {
                     this.actionCode = ADVANCE_TO_ID;
                } else if (flags.contains("a")) {
                     this.actionCode = ADVANCE_ID;
                } else if (flags.contains("cp")) {
                     this.actionCode = COLLECT_ID+PER_PLAYER_ID;
                } else if (flags.contains("c")) {
                     this.actionCode = COLLECT_ID;
                } else if (flags.contains("pi")) {
                     this.actionCode = PAY_ID+PER_ITEM_ID;
                } else if (flags.contains("pp")) {
                     this.actionCode = PAY_ID+PER_PLAYER_ID;
                } else if (flags.contains("p")) {
                     this.actionCode = PAY_ID;
                } else if (flags.contains("fj")) {
                     this.actionCode = FREE_JAIL_ID;
                }
            }else if (cardInfo.split(";")[i].split("=")[0].compareTo("value")==0) {
				String value = cardInfo.split(";")[i].split("=")[1];
                this.value = value.substring(1,  value.length()-1);
            }
        }
    }

	public BufferedImage getCardDisplay() throws IOException {
		BufferedImage tempStitched = new BufferedImage(507,310, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		int maxX = 507;
		int maxY = 310;
		
		g.setColor(color);
		g.fillRect(0, 0, maxX, maxY);
		
		g.fillRect(0, 0, maxX, maxY);
		g.drawImage(ImageIO.read(new File("assets/card.png")), 0, 0, null);
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,25));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		
		int fontX;
		int fontY;
		
		if (metrics.stringWidth(title) > 400) {
			
			int max = (int) Math.ceil(metrics.stringWidth(title)/400);
			int startIndex = 0;
			int endIndex = 0;
			
			for (int i = 0; i < max+1; i++) {
				String line = title.substring(startIndex,endIndex);
				while (metrics.stringWidth(line) < 400 && endIndex < title.length()) {
					endIndex = endIndex+1;
					line = title.substring(startIndex, endIndex);
				}
				line = title.substring(startIndex, endIndex);
				
				fontX = (507 - metrics.stringWidth(line)) / 2;
				fontY = ((80 - metrics.getHeight()) / 2) + metrics.getAscent();

				g.drawString(line, fontX, fontY+(40*i));
				startIndex = endIndex;
			}
			
		} else {
			
			fontX = (507 - metrics.stringWidth(title)) / 2;
			fontY = ((80 - metrics.getHeight()) / 2) + metrics.getAscent();
			g.drawString(title, fontX, fontY);
			
		}
		
		g.setFont(new Font("Arial",Font.PLAIN,20));
		metrics = g.getFontMetrics(g.getFont());
		
		if (metrics.stringWidth(description) > 400) {
			
			int max = (int) Math.ceil(metrics.stringWidth(description)/400);
			int startIndex = 0;
			int endIndex = 0;
			
			for (int i = 0; i < max+1; i++) {
				String line = description.substring(startIndex,endIndex);
				while (metrics.stringWidth(line) < 400 && endIndex < description.length()) {
					endIndex = endIndex+1;
					line = description.substring(startIndex, endIndex);
				}
				line = description.substring(startIndex, endIndex);
				
				fontX = (507 - metrics.stringWidth(line)) / 2;
				fontY = ((75 - metrics.getHeight()) / 2) + metrics.getAscent();

				g.drawString(line, fontX, fontY+110+(30*(i+1)));
				startIndex = endIndex;
			}
			
		} else {
			
			fontX = (507 - metrics.stringWidth(description)) / 2;
			fontY = ((75 - metrics.getHeight()) / 2) + metrics.getAscent();

			g.drawString(description, fontX, fontY+110);
			
		}
		
		return tempStitched;
	}
	
	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	
	/**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the actionCode - action codes
     */
    public int getActionCode() {
        return actionCode;
    }
	
	/**
	 * @return the cardType - Community / Chance
	 */
	public int getCardType() {
		return cardType;
	}

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	//</editor-fold>

}
