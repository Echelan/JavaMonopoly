/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopolyviolet.data.DataNode;

/**
 *
 * @author movillaf
 */
public class Card extends Node{
    
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
    private int category;
	
    private String value;
    
    public Card(int id) {
        readInfo(id);
    }
    
    private void readInfo(int id) {
        String[] cardInfo = ((DataNode) monopolyviolet.data.NIC.INFO_CARDS.get(id-1)).getValue().split(";");
        for (int i = 0; i < cardInfo.length; i++) {
            String[] partes = cardInfo[i].split("=");
            if (partes[0].compareTo("title")==0) {
                partes[1] = partes[1].substring(1,  partes[1].length()-1);
                this.title = partes[1];
            }else if (partes[0].compareTo("description")==0) {
                partes[1] = partes[1].substring(1,  partes[1].length()-1);
                this.description = partes[1];
            }else if (partes[0].compareTo("id")==0) {
                partes[1] = partes[1].substring(1,  partes[1].length()-1);
                if (partes[1].compareTo("COMMUNITY")==0) {
                    this.cardType = COMMUNITY_CHEST_ID;
                } else if (partes[1].compareTo("CHANCE")==0) {
                    this.cardType = CHANCE_ID;
                }
            }else if (partes[0].compareTo("flags")==0) {
                partes[1] = partes[1].substring(1,  partes[1].length()-1);
                String flags = partes[1];
                if (flags.contains("at")) {
                    this.category = ADVANCE_TO_ID;
                } else if (flags.contains("ai")) {
                     this.category = ADVANCE_TO_ID;
                } else if (flags.contains("a")) {
                     this.category = ADVANCE_ID;
                } else if (flags.contains("cp")) {
                     this.category = COLLECT_ID+PER_PLAYER_ID;
                } else if (flags.contains("c")) {
                     this.category = COLLECT_ID;
                } else if (flags.contains("pi")) {
                     this.category = PAY_ID+PER_ITEM_ID;
                } else if (flags.contains("pp")) {
                     this.category = PAY_ID+PER_PLAYER_ID;
                } else if (flags.contains("p")) {
                     this.category = PAY_ID;
                } else if (flags.contains("fj")) {
                     this.category = FREE_JAIL_ID;
                }
            }else if (partes[0].compareTo("value")==0) {
                this.value = partes[1];
            }
        }
    }

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
     * @return the cardType
     */
    public int getCardType() {
        return cardType;
    }

    /**
     * @return the category
     */
    public int getCategory() {
        return category;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }
    
	public BufferedImage getPropertyMap(int rotation) throws IOException{
		BufferedImage tempStitched = new BufferedImage(507,310, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		int maxX = 507;
		int maxY = 310;
		
		g.fillRect(0, 0, maxX, maxY);
		g.drawImage(ImageIO.read(new File("assets/card.png")), maxX, maxY, 0, 0, null);
		g.drawString(this.title,maxX/2,20);
		
		return tempStitched;
	}
}
