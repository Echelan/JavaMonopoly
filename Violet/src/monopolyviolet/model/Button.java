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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public class Button extends Node{
	
	private int x;
	private int y;
	private String text;
	private int width;
	private int height;
	private Color colorBack;
	private Color colorFore;
	private Color colorSel;
	private Font font;
	private boolean rounded;
	private boolean hovered;
	private String internalName;
			
	public Button(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = "";
		this.rounded = false;
		this.hovered = false;
		this.font = new Font("Arial",Font.PLAIN,20);
		this.colorBack = Color.white;
		this.colorFore = Color.black;
		this.colorSel = Color.black;
		this.internalName = "button";
	}
	
	public int search(String internalName) {
		int counter = 0;
		Node lookingGlass = this;
		while (((Button) lookingGlass).getInternalName().compareTo(internalName) != 0) {
			lookingGlass = lookingGlass.next();
			counter = counter + 1;
		}
		
		return counter;
	}
	
	public boolean isContained(int x, int y) {
		boolean value = false;
		if (this.x < x && x < this.x+this.width) {
			if (this.y < y && y < this.y+this.height) {
				value = true;
			}
		}
		return value;
	}
	
	public BufferedImage getDisplay() {
		BufferedImage display = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.setColor(colorBack);
		if (this.rounded) {
			g.fillRoundRect(0, 0, width, height, width/20, height/20);
		} else {
			g.fillRect(0, 0, width, height);
		}
		
		g.setColor(colorFore);
		g.setFont(font);
		int fontX = (this.width / 2) - ((font.getSize()/2) * (text.length() / 2));
		int fontY = (this.height / 2) + (font.getSize() / 2);
		g.drawString(text, fontX, fontY);
		
		if (this.hovered) {
			int shorten = 4;
			g.setColor(colorSel);
			if (this.rounded) {
				g.drawRoundRect(shorten, shorten, width-(shorten*2), height-(shorten*2), width/20, height/20);
			} else {
				g.drawRect(shorten, shorten, width-(shorten*2), height-(shorten*2));
			}
		}
		
		return display;
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the colorBack
	 */
	public Color getColorBack() {
		return colorBack;
	}

	/**
	 * @param colorBack the colorBack to set
	 */
	public void setColorBack(Color colorBack) {
		this.colorBack = colorBack;
	}

	/**
	 * @return the colorFore
	 */
	public Color getColorFore() {
		return colorFore;
	}

	/**
	 * @param colorFore the colorFore to set
	 */
	public void setColorFore(Color colorFore) {
		this.colorFore = colorFore;
	}

	/**
	 * @return the font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @return the rounded
	 */
	public boolean isRounded() {
		return rounded;
	}

	/**
	 * @param rounded the rounded to set
	 */
	public void setRounded(boolean rounded) {
		this.rounded = rounded;
	}

	/**
	 * @return the hovered
	 */
	public boolean isHovered() {
		return hovered;
	}

	/**
	 * @param hovered the hovered to set
	 */
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}

	/**
	 * @return the internalName
	 */
	public String getInternalName() {
		return internalName;
	}

	/**
	 * @param internalName the internalName to set
	 */
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	
	
	
	
}
