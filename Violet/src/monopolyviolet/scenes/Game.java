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
	private Node<Property> specialList;
	
	private BufferedImage mapImage;
	
	private int xDisplace = 0;
	private int yDisplace = 0;
	
	private int xDisplaceLast = 0;
	private int yDisplaceLast = 0;
	
	private int xBegin;
	private int yBegin;
	
	private int dragStrength = 3;
	
	private int cooldown;
	private int maxCD = 20;
	
	private boolean waiting;
	
	public Game(Handler main, Node<Player> players) {
		super(main, "GAME", true);
		
		communityCard = new Node();
		chanceCard = new Node();
		map = new Node();
		propertyList = new Node();
		specialList = new Node();
		this.players = players;
		
		this.cooldown = maxCD;
		
		map.setCircular(true);
		map.setDoubleLink(true);
		
		communityCard.setCircular(true);
		communityCard.setDoubleLink(true);

		chanceCard.setCircular(true);
		chanceCard.setDoubleLink(true);
		
		createDecks();
		createProperties();
		createSpecials();
		buildMap();
		
		try {
			this.mapImage = this.buildMapDisplay();
		} catch(IOException e) {
			
		}
		for (int i = 0; i < players.size(); i++) {
			this.map.get(0).getPlayersHere().add(players.get(i).getId());
		}
		
		waiting = false;
	}
	
	public void moveToPlayer(int id) {
		int x = 0;
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).getPlayersHere().size(); j++) {
				boolean foundHim = map.get(i).getPlayersHere().get(j) == id;
				if (foundHim) {
					x = i;
				}
			}
		}
		
		
		int xPos = map.get(x).getX();
		int yPos = map.get(x).getY();
		
		xDisplace = xPos*-1;
		yDisplace = yPos*-1;
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
			propertyList.add(new Property(i,0));
		}
	}
	
	private void createSpecials() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_SPECIALS.size(); i++) {
			specialList.add(new Property(i,1));
		}
	}

	private void buildMap() {
		int maxSpace = 40;
		int counter = 0;
		int spCounter = 0;
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
					map.last().setType(Place.GO_TYPE);
				} else if (i/10 == 1) {
					map.last().setType(Place.JAIL_TYPE);
				} else if (i/10 == 2) {
					map.last().setType(Place.FREE_TYPE);
				} else if (i/10 == 3) {
					map.last().setType(Place.GOJAIL_TYPE);
				}
			} else {
				if (i == 2 || i == 17 || i == 33) {
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setType(Place.COMMUNITY_TYPE);
				} else if (i == 7 || i == 22 || i == 36) {
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setType(Place.CHANCE_TYPE);
				} else if (i == 4 || i == 38) {
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setType(Place.TAX_TYPE);
				} else if (i%10 == 5) {
					name = specialList.get(spCounter).getName();
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setProperty(specialList.get(spCounter));
					map.last().setType(Place.RAILROAD_TYPE);
					spCounter = spCounter + 1;
				} else if (i == 12) {
					name = specialList.get(spCounter).getName();
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setProperty(specialList.get(spCounter));
					map.last().setType(Place.ELECTRIC_TYPE);
					spCounter = spCounter + 1;
				} else if (i == 28) {
					name = specialList.get(spCounter).getName();
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setProperty(specialList.get(spCounter));
					map.last().setType(Place.WATER_TYPE);
					spCounter = spCounter + 1;
				} else {
					name = propertyList.get(counter).getName();
					map.add(new Place(isCorner,name,x,y,quad));
					map.last().setProperty(propertyList.get(counter));
					map.last().setType(Place.PROPERTY_TYPE);
					counter = counter + 1;
				}
			}
		}
	}
	
	public void setRoll(int id, int roll) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getId() == id) {
				players.get(i).setRoll(roll);
			}
		}
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
	
	protected void mapAction(int pID, int mID) {
		waiting = true;
		System.out.println(map.get(mID).getName());
		if (map.get(mID).getType() == Place.PROPERTY_TYPE) {
			if (map.get(mID).getProperty().getOwner() != -1) {
				
			}
		} else if (map.get(mID).getType() == Place.TAX_TYPE) {
			main.gameState.add(new PayAmount(main,200,pID,-1));
		}
	}
	
	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}
	
	protected void sendJail(int pID) {
		
		int j = 0;
		int k = 0;
		boolean found = false;
		while (j < map.size()) {
			k = 0;
			while (k < map.get(j).getPlayersHere().size() && !found) {
				if (pID == map.get(j).getPlayersHere().get(k)) {
					map.get(j).getPlayersHere().remove(k);
					found = true;
				}
				k = k + 1;
			}
			if (map.get(j).getType() == Place.JAIL_TYPE) {
				map.get(j).getPlayersHere().add(pID);
			}
			j = j + 1;
		}
	}
	
	@Override
	protected void clickEvent(int x, int y) {
		
	}

	@Override
	protected void moveEvent(int x, int y) {

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
		
		g.drawImage(ImageIO.read(new File("assets/title/violetMonopolyLogo.png")), xDisplace + (max/2)-(1292/6), yDisplace + (max/2)-(641/6), 1292/3, 641/3, null);
		
		for (int i = 0; i < map.size(); i++) {
			
			int xPos = map.get(i).getX();
			int yPos = map.get(i).getY();
			
			g.drawImage(map.get(i).getDisplay(), xPos, yPos, null);
		}
		
		return display;
	}
    
	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/background.png")), 0, 0, null);
		
		g.drawImage(this.mapImage,xDisplace,yDisplace, null);
		
		this.cooldown = this.cooldown - 1;
		
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).getPlayersHere().size(); j++) {
				int xPos = xDisplace + map.get(i).getX()+(j*8);
				int yPos = yDisplace + map.get(i).getY();
				for (int k = 0; k < players.size(); k++) {
					
					if (players.get(k).getId() == map.get(i).getPlayersHere().get(j)) {
						g.drawImage(players.get(k).getPiece(), xPos + 10, yPos + 10, null);
					}
				}
				
			}
		}
		
		
		if (this.cooldown < 0) {
			int playerID = -1;
			int mapID = -1;
			for (int i = 0; i < players.size(); i++) {
				int roll = players.get(i).getRoll();
				playerID = i;
				if (roll > 0) {
					int triggeringID = players.get(i).getId();
					this.cooldown = this.maxCD;

					int j = 0;
					int k = 0;
					boolean found = false;
					while (j < map.size() && !found) {
						k = 0;
						while (k < map.get(j).getPlayersHere().size() && !found) {
							if (triggeringID == map.get(j).getPlayersHere().get(k)) {
								map.get(j).getPlayersHere().remove(k);
								map.get(j+1).getPlayersHere().add(triggeringID);
								if (map.get(j+1).getType() == Place.GO_TYPE) {
									players.get(i).addFunds(200);
								}
								mapID = j+1;
								players.get(i).setRoll(roll-1);
								found = true;
							}
							k = k + 1;
						}
						j = j + 1;
					}
					if (roll == 1) {
						mapAction(playerID,mapID);
					}
				}
				
			}
		}
		
		
		return display;
	}
	
}
