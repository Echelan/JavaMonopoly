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
	
	private final boolean corner;
	private final String name;
	private Property property;
	private final int x;
	private final int y;
	private final int side;
	private Node<Integer> playersHere;
	private String type;
	
	public static final float RESIZE = 0.5f;
	public static final int SHORT_SIDE = (int) (154 * RESIZE);
	public static final int LONG_SIDE = (int) (225 * RESIZE);

	public Place(boolean corner, String name, int x, int y, int side) {
		this.corner = corner;
		this.name = name;
		this.x = x;
		this.y = y;
		this.side = side;
		playersHere = new Node();
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
		this.setType("PROPERTY");
		this.property = property;
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
		} else {
			g.setColor(Color.BLACK);
			g.drawString(this.type,x,y+50);
			if (this.corner) {
				g.drawImage(ImageIO.read(new File("assets/cornerBlank.png")), x, y, LONG_SIDE, LONG_SIDE, null);
			} else {
				g.drawImage(ImageIO.read(new File("assets/blankMap.png")), x, y, SHORT_SIDE, LONG_SIDE, null);
			}
		}
		
		return display;
	}

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
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
