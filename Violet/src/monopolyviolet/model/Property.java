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
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author movillaf
 */
public class Property {
	
	public static Color PROPERTY_PINK = Color.getHSBColor(335/360f,71/100f,92/100f);
	public static Color PROPERTY_BLUE = Color.getHSBColor(186/360f,98/100f,82/100f);
	public static Color PROPERTY_RED = Color.getHSBColor(356/360f,80/100f,89/100f);
	public static Color PROPERTY_YELLOW = Color.getHSBColor(43/360f,90/100f,95/100f);
	public static Color PROPERTY_GREEN = Color.getHSBColor(124/360f,57/100f,67/100f);
	public static Color PROPERTY_PURPLE = Color.getHSBColor(261/360f,62/100f,94/100f);
	public static Color PROPERTY_LIME = Color.getHSBColor(65/360f,74/100f,79/100f);
	public static Color PROPERTY_ORANGE = Color.getHSBColor(35.1f/360f,100/100f,100/100f);
	
	private final int id;
	private String name;
	private int buyPrice;
	private int rent;
	private int buildingCost;
	private String rentChanges;
	private int numHouses;
	private int owner;
	private Color ownerColor;
	private Color color;
	private String ownerName;
	private boolean mortgaged;
	private int monopolyBonus;
		
	public Property(int id, boolean special) {
		this.monopolyBonus = 1;
		this.mortgaged = false;
		this.id = id;
		this.numHouses = 0;
		this.owner = -1;
		this.ownerColor = Color.gray;
		this.ownerName = "";
		readInfo(this.id,special);
	}
	
	private void readInfo(int id, boolean special) {
		String value;
		if (special) {
			value = monopolyviolet.data.NIC.INFO_SPECIALS.get(id);
		} else {
			value = monopolyviolet.data.NIC.INFO_PROPERTIES.get(id);
		}
        for (int i = 0; i < value.split(";").length; i++) {
            if (value.split(";")[i].split("=")[0].compareTo("name")==0) {
				String wrap =value.split(";")[i].split("=")[1];
				wrap = wrap.substring(1,  wrap.length()-1);
				this.name = wrap;
            }else if (value.split(";")[i].split("=")[0].compareTo("price")==0) {
				this.buyPrice = Integer.parseInt(value.split(";")[i].split("=")[1]);
            }else if (value.split(";")[i].split("=")[0].compareTo("rent")==0) {
				this.rent = Integer.parseInt(value.split(";")[i].split("=")[1]);
            }else if (value.split(";")[i].split("=")[0].compareTo("buildingCost")==0) {
				this.buildingCost = Integer.parseInt(value.split(";")[i].split("=")[1]);
            }else if (value.split(";")[i].split("=")[0].compareTo("rentChange")==0) {
				this.rentChanges = value.split(";")[i].split("=")[1];
            }else if (value.split(";")[i].split("=")[0].compareTo("color")==0) {
				String wrap =value.split(";")[i].split("=")[1];
				wrap = wrap.substring(1,  wrap.length()-1);
				switch (wrap) {
					case "PINK":
						this.color = PROPERTY_PINK;
						break;
					case "BLUE":
						this.color = PROPERTY_BLUE;
						break;
					case "RED":
						this.color = PROPERTY_RED;
						break;
					case "YELLOW":
						this.color = PROPERTY_YELLOW;
						break;
					case "GREEN":
						this.color = PROPERTY_GREEN;
						break;
					case "PURPLE":
						this.color = PROPERTY_PURPLE;
						break;
					case "LIME":
						this.color = PROPERTY_LIME;
						break;
					case "ORANGE":
						this.color = PROPERTY_ORANGE;
						break;
					default:
						this.color = Color.white;
						break;
				}
            }
        }
	}
	
	public void resetOwner() {
		this.owner = -1;
		this.ownerColor = Color.gray;
		this.ownerName = "";
	}
	
	public BufferedImage getPropertyMap() throws IOException {
		BufferedImage display = new BufferedImage(154,225, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		int maxX = 154;
		int maxY = 225;
		
		g.fillRect(0, 0, maxX, maxY);
		g.setColor(this.color);
		g.fillRect(0, 0, maxX, 60);
		g.drawImage(ImageIO.read(new File("assets/propertyMap.png")), 0, 0, maxX, maxY, null);
		
		int diam = 100;
		
		g.setColor(this.ownerColor);
		g.fillOval((154/2)-(diam/2), (225/2)-(diam/2)+20, diam, diam);
		
		if (this.numHouses == 5) {
			int width = 50;
			int height = 50;
			g.drawImage(ImageIO.read(new File("assets/icons/hotel.png")), (maxX-width)/2, 4, width, height, null);
		} else {
			int width = 50;
			int height = 50;
			
			for (int i = 0; i < numHouses; i++) {
				g.drawImage(ImageIO.read(new File("assets/icons/house.png")), 11+(i*(width*4/6)), 4, width, height, null);	
			}
		}
		
		return display;
	}
	
	public BufferedImage getPropertyCard() throws IOException {
		BufferedImage display = new BufferedImage(246, 360, BufferedImage.TYPE_INT_ARGB);
		Graphics g = (Graphics2D) display.getGraphics();
		
		int maxX = 246;
		int maxY = 360;
		
		g.fillRect(0, 0, maxX, maxY);
		g.setColor(this.color);
		g.fillRect(0, 0, maxX, 75);
		g.drawImage(ImageIO.read(new File("assets/propertyCard.png")), 0, 0, maxX, maxY, null);
		
		if (this.isMonopoly()) {
			int dim = 193/3;
			g.drawImage(ImageIO.read(new File("assets/icons/monopoly.png")), (maxX-dim)/2, 8, dim, dim, null);
		}

		g.setFont(new Font("Arial",Font.BOLD,20));
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int fontX = (246 - metrics.stringWidth(this.name)) / 2;
		int fontY = ((75 - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.setColor(Color.BLACK);
		g.drawString(this.name, fontX,fontY);
		
		if (this.mortgaged) {
			int dim = (int) (193/1.5f);
			g.drawImage(ImageIO.read(new File("assets/icons/mortgage.png")), (maxX-dim)/2, ((maxY-dim)/2)+70, dim, dim, null);
		}
		
		if (this.numHouses == 5) {
			int width = 50;
			int height = 50;
			g.drawImage(ImageIO.read(new File("assets/icons/hotel.png")), (maxX-width)/2, maxY-height-10, width, height, null);
		} else {
			int width = 50;
			int height = 50;
			
			for (int i = 0; i < numHouses; i++) {
				g.drawImage(ImageIO.read(new File("assets/icons/house.png")), (int) ((maxX/2) + ((i-2.5)*(width*4/6))), maxY-height-10, width, height, null);	
			}
		}
		
		g.setColor(Color.black);
		
		int x = (int) (maxX*0.08f);
		if (this.owner == -1) {
			g.setFont(new Font("Arial",Font.BOLD,15));
		} else {
			g.setFont(new Font("Arial",Font.PLAIN,15));
		}
		g.drawString("Buy Price: $"+this.buyPrice, x, 115);
		
		g.setFont(new Font("Arial",Font.PLAIN,15));
		if (this.buildingCost != 0) {
			g.drawString("Building Cost: $"+this.buildingCost, x, 140);

			int yStart = 180;
			for (int i = 0; i < 6; i++) {
				String description = "Rent with "+(i)+" houses: $";
				if (i == 0) {
					description = "Rent: $";
				} else if (i == 5) {
					description = "Rent with a hotel: $";
				}
				if (i == numHouses) {
					g.setFont(new Font("Arial",Font.BOLD,15));
				} else {
					g.setFont(new Font("Arial",Font.PLAIN,15));
				}
				g.drawString(description+this.getRentValue(i), x, yStart+(i*25));
			}
		} else {
			
			int yStart = 180;
			int i = 0;
			String description = "Rent: $";
			
			g.drawString(description+this.getRentValue(i), x, yStart+(i*25));
			
		}
		
		g.setFont(new Font("Arial",Font.PLAIN,15));
		String description = "No owner";
		if (this.owner != -1) {
			g.setColor(this.ownerColor);
			description = "Current owner: "+this.ownerName;
		}
		g.drawString(description, x, 330);
		
		return display;
	}

	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	
	/**
	 * @return the monopoly
	 */
	public boolean isMonopoly() {
		return getMonopolyBonus() != 1;
	}

	/**
	 * @return the monopolyBonus
	 */
	public int getMonopolyBonus() {
		return monopolyBonus;
	}

	/**
	 * @param monopolyBonus the monopolyBonus to set
	 */
	public void setMonopolyBonus(int monopolyBonus) {
		this.monopolyBonus = monopolyBonus;
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param ownerColor the ownerColor to set
	 */
	public void setOwnerColor(Color ownerColor) {
		this.ownerColor = ownerColor;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName the ownerName to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	
	/**
	 * @return the mortgage
	 */
	public int getMortgage() {
		return this.buyPrice/2;
	}
	
	/**
	 * @return the mortgage interest
	 */
	public int getMortgageInterest() {
		return (int) (getMortgage()*1.1f);
	}

	/**
	 * 
	 * @param houses
	 * @return the rent
	 */
	public int getRentValue(int houses) {
		float value = this.rent;
		float change = 1;
		if (houses>0) {
			change = Float.parseFloat(this.rentChanges.split(",")[houses-1]);
		}
		switch (houses) {
			case 1:
				value = value * change;
			break;
			case 2:
				value = getRentValue(1) * change;
			break;
			case 3:
				value = getRentValue(2) * change;
			break;
			case 4:
				value = (getRentValue(3)-getRentValue(1)) * change;
			break;
			case 5:
				value = (getRentValue(3)+getRentValue(4)) * change;
			break;
		}
		return (int) Math.floor(value);
	}
	
	/**
	 * @return the mortgaged
	 */
	public boolean isMortgaged() {
		return mortgaged;
	}

	/**
	 * @param mortgaged the mortgaged to set
	 */
	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
	}
	
	//</editor-fold>


}