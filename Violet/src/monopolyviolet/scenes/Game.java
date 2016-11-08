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
package monopolyviolet.scenes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopolyviolet.model.Card;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
import monopolyviolet.model.Place;
import monopolyviolet.model.Player;
import monopolyviolet.model.Property;


/**
 *
 * @author movillaf
 */
public class Game extends Scene{
    
    private Node<Card> communityCard;
    private Node<Card> chanceCard;
	private Node<Place> map;
	private Node<Player> players;
	private Node<Property> propertyList;
	
	private BufferedImage mapImage;
	
	private int xDisplace = 0;
	private int yDisplace = 0;
	
	private int xDisplaceLast = 0;
	private int yDisplaceLast = 0;
	
	private int xBegin;
	private int yBegin;
	
	private int dragStrength = 3;
	
	public Game(Handler main, Node<Player> players) {
		super(main, "GAME", true);
		
		communityCard = new Node();
		chanceCard = new Node();
		map = new Node();
		propertyList = new Node();
		this.players = players;
		
		map.setCircular(true);
		map.setDoubleLink(true);
		
		communityCard.setCircular(true);
		communityCard.setDoubleLink(true);

		chanceCard.setCircular(true);
		chanceCard.setDoubleLink(true);
		
		createDecks();
		createProperties();
		buildMap();
		
		try {
			this.mapImage = this.buildMapDisplay();
		} catch(IOException e) {
			
		}
	}
	
	private void createDecks() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_CARDS.size(); i++) {
			Card tempCard = new Card(i);
			if (tempCard.getCardType() == Card.COMMUNITY_CHEST_ID) {
				communityCard.add(tempCard);
			} else if (tempCard.getCardType() == Card.CHANCE_ID) {
				chanceCard.add(tempCard);
			}
		}
	}
	
	private void createProperties() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_PROPERTIES.size(); i++) {
			propertyList.add(new Property(i));
		}
	}

	private void buildMap() {
		int maxSpace = 40;
		int counter = 0;
		
		int longS = monopolyviolet.model.Place.LONG_SIDE + 5;
		int shortS = monopolyviolet.model.Place.SHORT_SIDE + 5;
		
		for (int i = 0; i < maxSpace; i++) {
			int quad = 0;
			if (-1 < i && i < 10) {
				quad = 1;
			} else if (9 < i && i < 20) {
				quad = 2;
			} else if (19 < i && i < 30) {
				quad = 3;
			} else if (29 < i && i < 40) {
				quad = 4;
			}
			
			int x = 0;
			int y = 0;
			
			int min = 10;
			int max = longS + (shortS*9);
			
			int constant = min;
			int variable = 0;
			if (quad == 1 || quad == 2) {
				int a = i;
				a = a%10;
				
				if (a > 0) {
					variable = variable + longS;
					if (a > 1) {
						variable = variable + (((i%10)-1)*shortS);
					}
				}

				if (quad == 1) {
					y = constant;
					x = variable + min;
				} else if (quad == 2) {
					x = constant + max;
					y = variable + min;
				}
			} else {
				int a = i;
				a = a%10;
				
				if (a > 0) {
					variable = variable + shortS;
					if (a > 1) {
						variable = variable + (((a%10)-1)*(shortS));
					}
				}
				
				if (quad == 3) {
					y = constant + max;
					x = max - variable + min;
				} else if (quad == 4) {
					x = constant;
					y = max - variable + min;
				}
			}
			
			String name = "Space";
			boolean isCorner = (i%10 == 0);
			if (isCorner || counter >= propertyList.size()) {
				map.add(new Place(isCorner,name,x,y,quad));
				if (i/10 == 0) {
					map.last().setType("GO");
				} else if (i/10 == 1) {
					map.last().setType("JAIL");
				} else if (i/10 == 2) {
					map.last().setType("FREE");
				} else if (i/10 == 3) {
					map.last().setType("GOJAIL");
				}
			} else {
				name = propertyList.get(counter).getName();
				map.add(new Place(isCorner,name,x,y,quad));
				if (i == 2 || i == 17 || i == 33) {
					map.last().setType("COMMUNITY");
				} else if (i == 7 || i == 22 || i == 36) {
					map.last().setType("COMMUNITY");
				} else if (i == 4 || i == 38) {
					map.last().setType("TAX");
				} else if (i%10 == 5) {
					map.last().setType("RAILROAD");
				} else if (i == 12) {
					map.last().setType("ELECTRIC");
				} else if (i == 28) {
					map.last().setType("WATER");
				} else {
					map.last().setProperty(propertyList.get(counter));
					counter = counter + 1;
				}
			}
		}
	}

	@Override
	protected void clickEvent(int x, int y) {
		
	}

	@Override
	protected void moveEvent(int x, int y) {

	}

	private void checkDisplace() {
		int min = -400;
		int max = 20;
		
		if (xDisplace < min)  {
			xDisplace = min;
		}
		if (yDisplace < min)  {
			yDisplace = min;
		}
		
		if (xDisplace > max) {
			xDisplace = max;
		}
		if (yDisplace > max) {
			yDisplace = max;
		}
		
	}
	
	@Override
	protected void dragEvent(int x, int y) {
		xDisplace = xDisplaceLast + ((x-xBegin)*dragStrength);
		yDisplace = yDisplaceLast + ((y-yBegin)*dragStrength);
		checkDisplace();
	}

	@Override
	protected void pressEvent(int x, int y) {
		xDisplaceLast = xDisplace;
		yDisplaceLast = yDisplace;
		xBegin = x;
		yBegin = y;
	}

	@Override
	protected void releaseEvent(int x, int y) {
		xDisplace = xDisplaceLast + ((x-xBegin)*dragStrength);
		yDisplace = yDisplaceLast + ((y-yBegin)*dragStrength);
		checkDisplace();
		xBegin = 0;
		yBegin = 0;
	}
	
	private BufferedImage buildMapDisplay() throws IOException {
		int max = 865 + monopolyviolet.model.Place.LONG_SIDE + 10;
		
		BufferedImage display = new BufferedImage(max, max, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
//		g.drawImage(ImageIO.read(new File("assets/background.png")), 0, 0, null);
		
		
		g.drawImage(ImageIO.read(new File("assets/title/violetMonopolyLogo.png")), xDisplace + (max/2)-(1292/6), yDisplace + (max/2)-(641/6), 1292/3, 641/3, null);
		
		for (int i = 0; i < map.size(); i++) {
			
			int xPos = map.get(i).getX();
			int yPos = map.get(i).getY();
			
			g.drawImage(map.get(i).getDisplay(), xPos, yPos, null);
			
			for (int j = 0; j < map.get(i).getPlayersHere().size(); j++) {
				g.drawImage(players.get(map.get(i).getPlayersHere().get(i)).getPiece(), xPos + 10, yPos + 10, null);
			}
		}
		
		
		return display;
	}
    
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/background.png")), 0, 0, null);
		
		int max = 865 + monopolyviolet.model.Place.LONG_SIDE + 10;
		
		
		g.drawImage(this.mapImage,xDisplace,yDisplace, null);
//		g.drawImage(ImageIO.read(new File("assets/title/violetMonopolyLogo.png")), xDisplace + (max/2)-(1292/6), yDisplace + (max/2)-(641/6), 1292/3, 641/3, null);
//		
//		for (int i = 0; i < map.size(); i++) {
//			
//			int xPos = xDisplace + map.get(i).getX();
//			int yPos = yDisplace + map.get(i).getY();
//			
//			g.drawImage(map.get(i).getDisplay(), xPos, yPos, null);
//			
//			for (int j = 0; j < map.get(i).getPlayersHere().size(); j++) {
//				g.drawImage(players.get(map.get(i).getPlayersHere().get(i)).getPiece(), xPos + 10, yPos + 10, null);
//			}
//		}
		
		
		return display;
	}
	
}
