/*
 *  Monopoly Violet - A University Project by Andres Movilla
 *  MONOPOLY COPYRIGHT
 *  the distinctive design of the gameboard
 *  the four corner squares
 *  the Mr. Monopoly name and character
 *  and each of the distinctive elements of the board
 *  are trademarks of Hasbro, Inc.
 *  for its property trading game and game equipment.
 *  COPYRIGHT 1999 Hasbro, Inc. All Rights Reserved.
 *  No copyright or trademark infringement is intended in using Monopoly content on Monopoly Violet.
 */
package monopolyviolet.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Andres
 */
public class Place {
	
	public static final int PROPERTY_TYPE = -1;
	public static final int GO_TYPE = 0;
	public static final int JAIL_TYPE = 1;
	public static final int FREE_TYPE = 2;
	public static final int GOJAIL_TYPE = 3;
	public static final int COMMUNITY_TYPE = 4;
	public static final int CHANCE_TYPE = 5;
	public static final int TAX_TYPE = 6;
	public static final int RAILROAD_TYPE = 7;
	public static final int UTILITY_TYPE = 8;
	
	private final boolean corner;
	private String name;
	private Property property;
	private final int x;
	private final int y;
	private final int side;
	private Node<Integer> playersHere;
	private int type;
	
	public static final float RESIZE = 0.5f;
	public static final int SHORT_SIDE = (int) (154 * RESIZE);
	public static final int LONG_SIDE = (int) (225 * RESIZE);

	public Place(boolean corner, int x, int y, int side) {
		this.corner = corner;
		this.name = name;
		this.x = x;
		this.y = y;
		this.side = side;
		playersHere = new Node();
	}

	public BufferedImage getDisplay() throws IOException {
		int maxX;
		int maxY;
		if (this.corner) {
			maxX = LONG_SIDE;
			maxY = LONG_SIDE;
		} else {
			if (this.side == 1 || this.side == 3) {
				maxX = SHORT_SIDE;
				maxY = LONG_SIDE;
			} else {
				maxX = LONG_SIDE;
				maxY = SHORT_SIDE;
			}
		}
		
		BufferedImage display = new BufferedImage(maxX,maxY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) display.getGraphics();
		
		g.fillRect(0,0,maxX,maxY);
		
		int angle = (this.side-1)*90;
		
		g.rotate(Math.toRadians(angle));
		
		int x = 0;
		int y = 0;
		
		if (this.side == 2) {
			y = -maxX;
		} else if (this.side == 3) {
			x = -maxX;
			y = -maxY;
		} else if (this.side == 4) {
			x = -maxY;
		}
		
		if (this.property != null) {
			g.drawImage(this.property.getPropertyMap(), x, y, SHORT_SIDE, LONG_SIDE, null);
			
				int iconDim = 200/4;
				int iconX = x+(int) (iconDim/3.5);
				int iconY = y+(int) (iconDim/1.25);
//				iconDim = iconDim*3/4;
				if (this.type == PROPERTY_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/property.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == RAILROAD_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/railroad.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == UTILITY_TYPE) {
                                        if (this.side == 2) {
                                            g.drawImage(ImageIO.read(new File("assets/icons/electric.png")), iconX, iconY, iconDim, iconDim, null);
                                        } else {
                                            g.drawImage(ImageIO.read(new File("assets/icons/water.png")), iconX, iconY, iconDim, iconDim, null);
                                        }
				}
		} else {
			g.setColor(Color.BLACK);
			if (this.corner) {
				g.drawImage(ImageIO.read(new File("assets/cornerBlank.png")), x, y, LONG_SIDE, LONG_SIDE, null);
				int iconDim = 300/3;
				int iconX = x + 10;
				int iconY = y + 10;
				
				if (this.type == GO_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/go.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == GOJAIL_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/gojail.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == FREE_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/parking.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == JAIL_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/jail.png")), iconX, iconY, iconDim, iconDim, null);
				}
			} else {
				g.drawImage(ImageIO.read(new File("assets/blankMap.png")), x, y, SHORT_SIDE, LONG_SIDE, null);
				
				int iconDim = 200/3;
				int iconX = x+(iconDim/10);
				int iconY = y+(iconDim/3);
				
				if (this.type == CHANCE_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/chance.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == COMMUNITY_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/chest.png")), iconX, iconY, iconDim, iconDim, null);
				} else if (this.type == TAX_TYPE) {
					g.drawImage(ImageIO.read(new File("assets/icons/tax.png")), iconX, iconY, iconDim, iconDim, null);
				}
			}
		}
		
		return display;
	}

	public boolean hasProperty() {
		return this.type == PROPERTY_TYPE || this.type == RAILROAD_TYPE || this.type == UTILITY_TYPE;
	}
	
	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	
	/**
	 * @return the playersHere
	 */
	public Node<Integer> getPlayersHere() {
		return playersHere;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean isCorner() {
		return corner;
	}

	public String getName() {
		return name;
	}

	public Property getProperty() {
		return property;
	}

	public void setProperty(Property property) {
		this.property = property;
	}
	
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
	
	//</editor-fold>
	
}
