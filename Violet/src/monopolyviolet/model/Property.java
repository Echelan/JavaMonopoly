/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.model;

import java.awt.Color;
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
public class Property extends Node{
	
	public static Color propertyPink = Color.getHSBColor(335/360f,71/100f,92/100f);
	public static Color propertyBlue = Color.getHSBColor(186/360f,98/100f,82/100f);
	public static Color propertyRed = Color.getHSBColor(356/360f,80/100f,89/100f);
	public static Color propertyYellow = Color.getHSBColor(43/360f,90/100f,95/100f);
	public static Color propertyGreen = Color.getHSBColor(124/360f,57/100f,67/100f);
	public static Color propertyPurple = Color.getHSBColor(261/360f,62/100f,94/100f);
	public static Color propertyLime = Color.getHSBColor(65/360f,74/100f,79/100f);
	
	private int id;
	private String name;
	private int buyPrice;
	private int rent;
	private int buildingCost;
	private String rentChanges;
	private int numHouses;
	private int owner;
		
	public Property(int id) {
		this.id = id;
		this.numHouses = 0;
		this.owner = -1;
		readInfo(this.id);
	}
	
	private void readInfo(int id) {
        String[] propertyInfo = ((DataNode) monopolyviolet.data.NIC.INFO_PROPERTIES.get(id-1)).getValue().split(";");
        for (int i = 0; i < propertyInfo.length; i++) {
            String[] partes = propertyInfo[i].split("=");
            if (partes[0].compareTo("name")==0) {
				partes[1] = partes[1].substring(1,  partes[1].length()-1);
				this.name = partes[1];
            }else if (partes[0].compareTo("price")==0) {
				this.buyPrice = Integer.parseInt(partes[1]);
            }else if (partes[0].compareTo("rent")==0) {
				this.rent = Integer.parseInt(partes[1]);
            }else if (partes[0].compareTo("buildingCost")==0) {
				this.buildingCost = Integer.parseInt(partes[1]);
            }else if (partes[0].compareTo("rentChange")==0) {
				this.rentChanges = partes[1];
            }
        }
	}
	
	/**
	 * 
	 * @param houses
	 * @return the rent
	 */
	public int getRentValue(int houses) {
		int value = this.rent;
		int change = 1;
		switch (houses) {
			case 1:
				change = Integer.parseInt(this.rentChanges.split(",")[0]);
				value = value * change;
			break;
			case 2:
				change = Integer.parseInt(this.rentChanges.split(",")[1]);
				value = getRentValue(1) * change;
			break;
			case 3:
				change = Integer.parseInt(this.rentChanges.split(",")[2]);
				value = getRentValue(2) * change;
			break;
			case 4:
				change = Integer.parseInt(this.rentChanges.split(",")[3]);
				value = (getRentValue(3)-getRentValue(1)) * change;
			break;
			case 5:
				change = Integer.parseInt(this.rentChanges.split(",")[4]);
				value = (getRentValue(3)+getRentValue(4)) * change;
			break;
		}
		return value;
	}
	
	/**
	 * @return the mortgage
	 */
	public int getMortgage() {
		return this.rent/2;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the buyPrice
	 */
	public int getBuyPrice() {
		return buyPrice;
	}

	/**
	 * @return the buildingCost
	 */
	public int getBuildingCost() {
		return buildingCost;
	}

	/**
	 * @return the numHouses
	 */
	public int getNumHouses() {
		return numHouses;
	}

	/**
	 * @param numHouses the numHouses to set
	 */
	public void setNumHouses(int numHouses) {
		this.numHouses = numHouses;
	}

	/**
	 * @return the rent
	 */
	public int getRent() {
		return getRentValue(this.numHouses);
	}

	/**
	 * @return the owner
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	
	public BufferedImage getPropertyMap(int rotation) throws IOException{
		BufferedImage tempStitched = new BufferedImage(154,225, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		int maxX = 154;
		int maxY = 225;
		
		g.fillRect(0, 0, maxX, maxY);
		g.drawImage(ImageIO.read(new File("assets/propertyMap.png")), maxX, maxY, 0, 0, null);
		g.drawString(this.name,maxX/2,20 );
		
		return tempStitched;
	}
	
	
	public BufferedImage getPropertyCard(int rotation) throws IOException{
		BufferedImage tempStitched = new BufferedImage(246, 360, BufferedImage.TYPE_INT_ARGB);
		Graphics g = tempStitched.getGraphics();
		
		int maxX = 246;
		int maxY = 360;
		
		g.fillRect(0, 0, maxX, maxY);
		g.drawImage(ImageIO.read(new File("assets/propertyCard.png")), maxX, maxY, 0, 0, null);
		g.drawString(this.name,maxX/2,20);
		
		return tempStitched;
	}
}