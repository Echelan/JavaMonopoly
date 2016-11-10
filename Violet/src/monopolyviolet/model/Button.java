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
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andres
 */
public class Button {
	
	private int x;
	private int y;
	private String text;
	private int width;
	private int height;
	private Color fillColor;
	private Color textColor;
	private Color strokeColor;
	private Color selectionFillColor;
	private Color selectionTextColor;
	private boolean enabled;
	private Font font;
	private boolean rounded;
	private boolean hovered;
	private String internalName;
			
	public Button(int x, int y, int width, int height) {
		this.enabled = true;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = "";
		this.rounded = false;
		this.hovered = false;
		this.font = new Font("Arial",Font.PLAIN,20);
		this.fillColor = Color.white;
		this.textColor = Color.black;
		this.strokeColor = Color.black;
		this.selectionFillColor = Color.gray;
		this.selectionTextColor = Color.white;
		this.internalName = "button";
	}
	
	public BufferedImage getDisplay() {
		BufferedImage display = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		if (this.enabled) {
			g.setColor(strokeColor);
			if (this.rounded) {
				g.fillRoundRect(0, 0, width, height, width/20, height/20);
			} else {
				g.fillRect(0, 0, width, height);
			}
		}
		
		if (this.hovered) {
			g.setColor(selectionFillColor);
		} else {
			g.setColor(fillColor);
		}
		
		int shorten = 3;
		if (this.rounded) {
			g.fillRoundRect(shorten, shorten, width-(shorten*2), height-(shorten*2), width/20, height/20);
		} else {
			g.fillRect(shorten, shorten, width-(shorten*2), height-(shorten*2));
		}
		
		if (this.hovered) {
			g.setColor(selectionTextColor);
		} else {
			g.setColor(textColor);
		}
		
		FontMetrics metrics = g.getFontMetrics(font);
		int fontX = (this.width - metrics.stringWidth(text)) / 2;
		int fontY = ((this.height - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.setFont(font);
		g.drawString(text, fontX, fontY);
		
		return display;
	}
	
	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	
	public boolean isContained(int x, int y) {
		boolean value = false;
		if (this.x < x && x < this.x+this.width) {
			if (this.y < y && y < this.y+this.height) {
				value = true;
			}
		}
		return value;
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
	 * @param fillColor the fillColor to set
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * @param textColor the textColor to set
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	/**
	 * @param font the font to set
	 */
	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * @param rounded the rounded to set
	 */
	public void setRounded(boolean rounded) {
		this.rounded = rounded;
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

	/**
	 * @param strokeColor the strokeColor to set
	 */
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	/**
	 * @param selectionFillColor the selectionFillColor to set
	 */
	public void setSelectionFillColor(Color selectionFillColor) {
		this.selectionFillColor = selectionFillColor;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param selectionTextColor the selectionTextColor to set
	 */
	public void setSelectionTextColor(Color selectionTextColor) {
		this.selectionTextColor = selectionTextColor;
	}
	
	//</editor-fold>
	
}
