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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopolyviolet.model.Button;
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
	
	private static final int DRAG_STRENGTH = 3;
	private static final int MAX_CD = 5;
    
    private Node<Card> communityCard;
    private Node<Card> chanceCard;
	private Node<Place> map;
	private Node<Player> players;
	private Node<Property> propertyList;
	private Node<Property> specialList;
	
	private BufferedImage mapImage;
	
	private int selected;
	
	private boolean waitingEnd;
	
	private Node<Button> buttons;
	
	private int xDisplace;
	private int yDisplace;
	private int xDisplaceLast;
	private int yDisplaceLast;
	private int xBegin;
	private int yBegin;
	
	private int cooldown;
	
	public Game(Handler main, Node<Player> players) {
		super(main, "GAME", true);
		
		this.players = players;
		
		buttons = new Node();
		communityCard = new Node();
		chanceCard = new Node();
		map = new Node();
		propertyList = new Node();
		specialList = new Node();
		
		map.setCircular(true);
		map.setDoubleLink(true);
		
		communityCard.setCircular(true);
		communityCard.setDoubleLink(true);

		chanceCard.setCircular(true);
		chanceCard.setDoubleLink(true);
		
		this.cooldown = MAX_CD;
		this.selected = -1;
		this.yDisplaceLast = 0;
		this.xDisplaceLast = 0;
		this.yDisplace = 0;
		this.xDisplace = 0;
		this.waitingEnd = false;
		
		createDecks();
		createProperties();
		createSpecials();
		buildMap();
		
		Button newButton = new Button(ssX-205, ssY-45, 200, 40);
		newButton.setText("End Turn");
		newButton.setInternalName("NEXT");
		buttons.add(newButton);
		
//		newButton = new Button(50, 100, 200, 40);
//		newButton.setTextColor(Color.gray);
//		newButton.setText("Add Player 1");
//		newButton.setInternalName("PLAYER;1");
//		buttons.add(newButton);
		
		try {
			this.buildMapDisplay();
		} catch(IOException e) {
		}
		
		for (int i = 0; i < players.size(); i++) {
			this.map.get(0).getPlayersHere().add(players.get(i).getId());
		}
		
	}
	
	public void moveToPlayer(Player search) {
		int xPos = findPlaceWithPlayerWithID(search.getId()).getX();
		int yPos = findPlaceWithPlayerWithID(search.getId()).getY();
		
		xDisplace = xPos*-1;
		yDisplace = yPos*-1;
		
		xDisplace = xDisplace + (ssX/2) - 50;
		yDisplace = yDisplace + (ssY/2) - 50;
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
				getMap().add(new Place(isCorner,name,x,y,quad));
				if (i/10 == 0) {
					getMap().last().setType(Place.GO_TYPE);
				} else if (i/10 == 1) {
					getMap().last().setType(Place.JAIL_TYPE);
				} else if (i/10 == 2) {
					getMap().last().setType(Place.FREE_TYPE);
				} else if (i/10 == 3) {
					getMap().last().setType(Place.GOJAIL_TYPE);
				}
			} else {
				if (i == 2 || i == 17 || i == 33) {
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setType(Place.COMMUNITY_TYPE);
				} else if (i == 7 || i == 22 || i == 36) {
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setType(Place.CHANCE_TYPE);
				} else if (i == 4 || i == 38) {
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setType(Place.TAX_TYPE);
				} else if (i%10 == 5) {
					name = specialList.get(spCounter).getName();
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setProperty(specialList.get(spCounter));
					getMap().last().setType(Place.RAILROAD_TYPE);
					spCounter = spCounter + 1;
				} else if (i == 12) {
					name = specialList.get(spCounter).getName();
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setProperty(specialList.get(spCounter));
					getMap().last().setType(Place.ELECTRIC_TYPE);
					spCounter = spCounter + 1;
				} else if (i == 28) {
					name = specialList.get(spCounter).getName();
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setProperty(specialList.get(spCounter));
					getMap().last().setType(Place.WATER_TYPE);
					spCounter = spCounter + 1;
				} else {
					name = propertyList.get(counter).getName();
					getMap().add(new Place(isCorner,name,x,y,quad));
					getMap().last().setProperty(propertyList.get(counter));
					getMap().last().setType(Place.PROPERTY_TYPE);
					counter = counter + 1;
				}
			}
		}
	}
	
	public int isMonopoly(Place search, int playerID) {
		int multiple = 1;
		boolean forProps = true;
		for (int i = 0; i < map.size(); i++) {
			Place place = map.get(i);
			
			if (search.getType() == Place.PROPERTY_TYPE && place.getType() == Place.PROPERTY_TYPE) {
				if (place.getProperty().getOwner() != search.getProperty().getOwner()) {
					System.out.println(place.getProperty().getOwner() +" - "+ search.getProperty().getOwner());
					forProps = false;
				}
			} else if (search.getType() == Place.RAILROAD_TYPE && place.getType() == Place.RAILROAD_TYPE) {
				if (place.getProperty().getOwner() == search.getProperty().getOwner()) {
					if (place != search) {
						multiple = multiple * 2;
					}
				}
			} else if (search.getType() == Place.ELECTRIC_TYPE || search.getType() == Place.WATER_TYPE) {
				multiple = players.get(0).getLastRoll();
				if (place.getType() == Place.ELECTRIC_TYPE || place.getType() == Place.WATER_TYPE) {
					if (place.getType() != search.getType()) {
						if (place.getProperty().getOwner() == search.getProperty().getOwner()) {
							multiple = (int) (multiple * 2.5f);
						}
					}
				}
			}
			
		}
		if (search.getType() == Place.PROPERTY_TYPE && forProps == true) {
			multiple = 2;
		}
		return multiple;
	}
	
    public Player findPlayerWithID(int id) {
        Player result = null;
        int i = 0;
        while (i < players.size() && result == null) {
            if (players.get(i).getId() == id) {
                result = players.get(i);
            }
            i = i + 1;
        }
        return result;
    }
    
    public Place findPlaceWithPlayerWithID(int id) {
        Place result = null;
        int j = 0, k = 0;
        while (j < getMap().size() && result == null) {
            k = 0;
            while (k < getMap().get(j).getPlayersHere().size() && result == null) {
                if (id == getMap().get(j).getPlayersHere().get(k)) {
                    result = getMap().get(j);
                }
                k = k + 1;
            }
            j = j + 1;
        }
        return result;
    }
    
    public void removePlayerFromPlace(Place place, int playerID) {
        int k = 0;
        boolean found = false;
        while (k < place.getPlayersHere().size() && !found) {
            if (place.getPlayersHere().get(k) == playerID) {
                place.getPlayersHere().remove(k);
                found = true;
            }
			k = k + 1;
        }
    }
    
    public void removePlayerFromPlace(int placeID, int playerID) {
        Place triggeringPlace = findPlaceWithPlayerWithID(playerID);
        removePlayerFromPlace(triggeringPlace, playerID);
    }
    
    public void removePlayerFromPlace(Place place, Player player) {
        int id = player.getId();
        removePlayerFromPlace(place, id);
    }
    
    public void removePlayerFromPlace(int placeID, Player player) {
        int id = player.getId();
        removePlayerFromPlace(placeID, id);
    }
    
    public int findPlaceIndexWithPlayerWithID(int id) {
        int result = -1;
        int j = 0, k = 0;
        while (j < getMap().size() && result == -1) {
            k = 0;
            while (k < getMap().get(j).getPlayersHere().size() && result == -1) {
                if (id == getMap().get(j).getPlayersHere().get(k)) {
                    result = j;
                }
                k = k + 1;
            }
            j = j + 1;
        }
        return result;
    }
    
    public int findPlaceIndexWithType(int type) {
        int result = -1;
        int j = 0;
        while (j < getMap().size() && result == -1) {
            if (getMap().get(j).getType() == type) {
                result = j;
            }
            j = j + 1;
        }
        return result;
    }
    
    public Place findPlaceWithType(int type) {
        Place result = null;
        int j = 0;
        while (j < getMap().size() && result == null) {
            if (getMap().get(j).getType() == type) {
                result = getMap().get(j);
            }
            j = j + 1;
        }
        return result;
    }

	public Property findSellableProperty(int minimum, int ownerID) {
		Property result = null;
		
		int i = 0;
		while (i < this.propertyList.size() && result != null) {
			if (this.propertyList.get(i).getOwner() == ownerID && this.propertyList.get(i).getBuyPrice()/2 >= minimum) {
				result = this.propertyList.get(i);
			}
			i = i + 1;
		}
		
		if (result == null) {
			i = 0;
			while (i < this.specialList.size() && result != null) {
				if (this.propertyList.get(i).getOwner() == ownerID && this.specialList.get(i).getBuyPrice()/2 >= minimum) {
					result = this.specialList.get(i);
				}
				i = i + 1;
			}
		}
		
		return result;
	}
	
    private void checkDisplace() {
        int min = -900;
        int max = 500;
        
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
	
    public void nextTurn() {
		boolean playerWon = false;
		if (!players.get(0).isRolledDoubles()) {
			int curPlayerID = players.get(0).getId();
			do {
				players.rotate(1);
			} while (players.get(0).isBankrupt());
			playerWon = curPlayerID == players.get(0).getId();
		} else {
			players.get(0).setRolledDoubles(false);
		}
		waitingEnd = false;
		if (!playerWon) {
			main.gameState.add(new TurnAnnounce(main,players.get(0)));
		}
    }
    
	protected void mapAction(Place place, Player player) {
		System.out.println(place.getName());
		if (place.getType() == Place.PROPERTY_TYPE || place.getType() == Place.ELECTRIC_TYPE || place.getType() == Place.WATER_TYPE || place.getType() == Place.RAILROAD_TYPE) {
			main.gameState.add(new PropertyStatus(main,place,player));
		} else if (place.getType() == Place.TAX_TYPE) {
			main.gameState.add(new PayAmount(main, 200, player, null));
		} else if (place.getType() == Place.GOJAIL_TYPE) {
			sendJail(player.getId());
		} else if (place.getType() == Place.CHANCE_TYPE || place.getType() == Place.COMMUNITY_TYPE) {
//			main.gameState.add(new DrawCard(main));
		}
		waitingEnd = true;
	}
	
	protected void sendJail(int pID) {
		findPlayerWithID(pID).setJailed(true);
		findPlayerWithID(pID).setRolledDoubles(false);
		findPlayerWithID(pID).setDoubleCount(0);
		removePlayerFromPlace(findPlaceWithPlayerWithID(pID), pID);
		findPlaceWithType(Place.JAIL_TYPE).getPlayersHere().add(pID);
	}
	
	@Override
	protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				if (buttons.get(selected).getInternalName().compareTo("NEXT") == 0){
					nextTurn();
				}
			}
		}
	}

	@Override
	protected void moveEvent(int x, int y) {
		int placement = -1;
		int counter = 0;
		
		while (counter < buttons.size()) {
			if (buttons.get(counter).isContained(x, y)) {
				placement = counter;
				buttons.get(counter).setHovered(true);
			} else {
				buttons.get(counter).setHovered(false);
			}
			counter = counter + 1;
		}
		
		selected = placement;
	}

	@Override
	protected void dragEvent(int x, int y) {
		xDisplace = xDisplaceLast + ((x-xBegin)*DRAG_STRENGTH);
		yDisplace = yDisplaceLast + ((y-yBegin)*DRAG_STRENGTH);
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
		xDisplace = xDisplaceLast + ((x-xBegin)*DRAG_STRENGTH);
		yDisplace = yDisplaceLast + ((y-yBegin)*DRAG_STRENGTH);
		checkDisplace();
		xBegin = 0;
		yBegin = 0;
	}
	
	public void buildMapDisplay() throws IOException {
		int max = 865 + monopolyviolet.model.Place.LONG_SIDE + 10;
		
		BufferedImage display = new BufferedImage(max, max, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/title/violetMonopolyLogo.png")), (max/2)-(1292/6), (max/2)-(641/6), 1292/3, 641/3, null);
		
		for (int i = 0; i < getMap().size(); i++) {
			
			int xPos = getMap().get(i).getX();
			int yPos = getMap().get(i).getY();
			
			g.drawImage(getMap().get(i).getDisplay(), xPos, yPos, null);
		}
		
		this.mapImage = display;
	}
    
	private void refresh() {
		
		boolean cond1 = main.gameState.last() == this;
		
		this.cooldown = this.cooldown - 1;
		
		if (this.cooldown < 0) {
			
			int i = 0;
			if (players.get(i).getDoubleCount() > 2) {
				sendJail(players.get(i).getId());
			} else {
				int roll = players.get(i).getRoll();
				int playerID = players.get(i).getId();
				if (roll > 0) {
					this.cooldown = this.MAX_CD;

					int currentID = findPlaceIndexWithPlayerWithID(playerID);
					int mapID =  currentID+1;
					if (mapID == getMap().size()) {
						mapID = 0;
					}

					removePlayerFromPlace(currentID, playerID);
					getMap().get(mapID).getPlayersHere().add(playerID);
					if (getMap().get(mapID).getType() == Place.GO_TYPE) {
						players.get(i).addFunds(200);
					}
					players.get(i).setRoll(roll-1);

				}

				if (this.cooldown < 0 && cond1 && !waitingEnd) {
					this.cooldown = this.MAX_CD;
					mapAction(findPlaceWithPlayerWithID(playerID),players.get(i));
				}
			}
		}
		
		int counter = 0;
		boolean cond2 = this.cooldown < 0;
		if (!cond1) {
			selected = -1;
		}
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			if (thisButton.getInternalName().compareTo("NEXT") == 0) {
				thisButton.setEnabled(cond1 && cond2);
				
				if (!cond1) {
					thisButton.setHovered(false);
				}
			}
			counter = counter + 1;
		}
		
	}
	
	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/background.png")), 0, 0, null);
		
		g.drawImage(this.mapImage,xDisplace,yDisplace, null);
		
		for (int i = 0; i < players.size(); i++) {
			int playerID = players.get(i).getId();
			int placeID = findPlaceIndexWithPlayerWithID(playerID);
			int xPos = xDisplace + getMap().get(placeID).getX()+(playerID*8);
			int yPos = yDisplace + getMap().get(placeID).getY();
			g.drawImage(players.get(i).getPiece(), xPos + 10, yPos + 10, null);
		}
        
		g.setColor(players.get(0).getColor());
		g.fillRect(5, ssY-45, 110, 40);
		g.setColor(Color.white);
		g.fillRect(10, ssY-40, 100, 30);
		g.setColor(Color.black);
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.drawString("$"+players.get(0).getFunds(),15,ssY-15);
		
		
		int counter = 0;
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			counter = counter + 1;
		}
		
		refresh();
		
		return display;
	}

    /**
     * @return the map
     */
    public Node<Place> getMap() {
        return map;
    }
	
}
