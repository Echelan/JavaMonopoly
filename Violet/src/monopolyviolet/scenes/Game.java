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
    private static final int MAX_CD = 3;

    private Node<Card> communityCard;
    private Node<Card> chanceCard;
    private Node<Place> map;
    private Node<Player> players;
    private Node<Property> propertyList;
    private Node<Property> specialList;

    private BufferedImage mapImage;

    private int selected;

    private boolean waitingEnd;
	private boolean updatable;

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
		this.updatable = false;

		createDecks();
		createProperties();
		createSpecials();
		buildMap();

		Button newButton = new Button(ssX-205, ssY-45, 200, 40);
		newButton.setText("End Turn");
		newButton.setInternalName("NEXT");
		buttons.add(newButton);

		newButton = new Button(120, ssY-45, 260, 40);
		newButton.setText("");
		newButton.setInternalName("CENTER");
		buttons.add(newButton);

		newButton = new Button(ssX-205, ssY-90, 200, 40);
		newButton.setText("View Properties");
		newButton.setInternalName("LIST");
		buttons.add(newButton);

		try {
			this.buildMapDisplay();
		} catch(IOException e) {
		}

		for (int i = 0; i < players.size(); i++) {
			this.map.get(0).getPlayersHere().add(players.get(i).getId());
		}

    }

    public void centerOn(Player search) {
		int xPos = (int) ((findPlaceWithPlayerWithID(search.getId()).getX()+(search.getId()*8)) * Handler.ZOOM);
		int yPos = (int) (findPlaceWithPlayerWithID(search.getId()).getY() * Handler.ZOOM);

		xPos = xPos * -1;
		yPos = yPos * -1;
		
		xPos = xPos + (ssX/2) - 35;
		yPos = yPos + (ssY/2) - 120;
		
		boolean longCondition = findPlaceWithPlayerWithID(search.getId()).hasProperty();
		longCondition = longCondition && search == players.get(0);
		longCondition = longCondition && search.getRoll() == 0;
		if ( longCondition ) {
			if ( findPlaceWithPlayerWithID(search.getId()).getProperty().getOwner() == -1 ) {
				buttons.get(1).setInternalName("REOPEN");
			}
		}
		
		xDisplace = xPos;
		yDisplace = yPos;
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
			getMap().add(new Place(isCorner,x,y,quad));
			if (isCorner || counter >= propertyList.size()) {
				if (i/10 == 0) {
					getMap().last().setName("Go!");
					getMap().last().setType(Place.GO_TYPE);
				} else if (i/10 == 1) {
					getMap().last().setName("Jail");
					getMap().last().setType(Place.JAIL_TYPE);
				} else if (i/10 == 2) {
					getMap().last().setName("Free");
					getMap().last().setType(Place.FREE_TYPE);
				} else if (i/10 == 3) {
					getMap().last().setName("Go to jail");
					getMap().last().setType(Place.GOJAIL_TYPE);
				}
			} else {
				if (i == 2 || i == 17 || i == 33) {
					getMap().last().setName("Community Card");
					getMap().last().setType(Place.COMMUNITY_TYPE);
				} else if (i == 7 || i == 22 || i == 36) {
					getMap().last().setName("Chance Card");
					getMap().last().setType(Place.CHANCE_TYPE);
				} else if (i == 4 || i == 38) {
					getMap().last().setName("Income Tax");
					getMap().last().setType(Place.TAX_TYPE);
				} else if (i%10 == 5) {
					getMap().last().setName(specialList.get(spCounter).getName());
					getMap().last().setProperty(specialList.get(spCounter));
					getMap().last().setType(Place.RAILROAD_TYPE);
					spCounter = spCounter + 1;
				} else if (i == 12 || i == 28) {
					getMap().last().setName(specialList.get(spCounter).getName());
					getMap().last().setProperty(specialList.get(spCounter));
					getMap().last().setType(Place.UTILITY_TYPE);
					spCounter = spCounter + 1;
				} else {
					getMap().last().setName(propertyList.get(counter).getName());
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
				if (search.getProperty().getColor() == place.getProperty().getColor()) {
					if (place.getProperty().getOwner() != search.getProperty().getOwner()) {
						forProps = false;
					}
				}
			} else if (search.getType() == Place.RAILROAD_TYPE && place.getType() == Place.RAILROAD_TYPE) {
				if (place.getProperty().getOwner() == search.getProperty().getOwner()) {
					if (place != search) {
						multiple = multiple * 2;
					}
				}
			} else if (search.getType() == Place.UTILITY_TYPE && place.getType() == Place.UTILITY_TYPE) {
				if (place.getProperty().getOwner() == search.getProperty().getOwner()) {
					if (multiple != 4) {
						multiple = 2;
					} else {
						multiple = 5;
					}
				}
			}
		}
		
		if (search.getType() == Place.PROPERTY_TYPE && forProps == true) {
			multiple = 2;
		}
		
		return multiple;
	}

    public int isMonopoly(Property search, int playerID) {
		return isMonopoly(findPlaceWithProperty(search), playerID);
	}
	
    public Place findPropertyOwnedByPlayer(int ownerID, int count) {
		Place result = null;
		
		Node<Integer> nodes = new Node();
		for (int i = 0; i < this.map.size(); i++) {
			if (this.map.get(i).hasProperty()) {
				if (this.map.get(i).getProperty().getOwner() == ownerID) {
					nodes.add(i);
				}
			}
		}
		
		if (count < nodes.size()) {
			result = this.map.get(nodes.get(count));
		}
		
		return result;
    }
    
    public int findPropertyIndexOwnedByPlayer(int ownerID, int count) {
		
		Node<Integer> nodes = new Node();
		for (int i = 0; i < this.map.size(); i++) {
			if (this.map.get(i).hasProperty()) {
				if (this.map.get(i).getProperty().getOwner() == ownerID) {
					nodes.add(i);
				}
			}
		}
		
		int result = 0;
		
		if (count < 0) {
			result = nodes.size()-1;
		} else if (count >= nodes.size()) {
			result = 0;
		} else {
			result = count;
		}
		
		return result;
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

    public Place findPlaceWithProperty(Property search) {
        Place result = null;
        int j = 0;
        while (j < getMap().size() && result == null) {
			Place thisPlace = getMap().get(j);
			if (thisPlace.hasProperty()) {
				if (thisPlace.getProperty()== search) {
					result = thisPlace;
				}
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
				if (this.specialList.get(i).getOwner() == ownerID && this.specialList.get(i).getBuyPrice()/2 >= minimum) {
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
		if (place.hasProperty()) {
			main.gameState.add(new PropertyStatus(main,place,player));
		} else if (place.getType() == Place.TAX_TYPE) {
			main.gameState.add(new PayAmount(main, 200, player, null));
		} else if (place.getType() == Place.GOJAIL_TYPE) {
			sendJail(player.getId());
		} else if (place.getType() == Place.CHANCE_TYPE || place.getType() == Place.COMMUNITY_TYPE) {
//			main.gameState.add(new DrawCard(main));
		}
		centerOn(player);
		waitingEnd = true;
	}
	
	protected void sendJail(int pID) {
		findPlayerWithID(pID).setJailed(true);
		findPlayerWithID(pID).setRoll(0);
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
				} else if (buttons.get(selected).getInternalName().compareTo("CENTER") == 0){
					centerOn(players.get(0));
				} else if (buttons.get(selected).getInternalName().compareTo("LIST") == 0){
					main.gameState.add(new PropertyList(main, players.get(0)));
				} else if (buttons.get(selected).getInternalName().compareTo("REOPEN") == 0){
					buttons.get(1).setInternalName("CENTER");
					mapAction(findPlaceWithPlayerWithID(players.get(0).getId()), players.get(0));
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
		buttons.get(1).setInternalName("CENTER");
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
		
		updatable = main.gameState.last() == this;
		
		this.cooldown = this.cooldown - 1;
		
		if (this.cooldown < 0 && updatable) {
			
			int i = 0;
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
				centerOn(players.get(i));
			}

			if (this.cooldown < 0 && !waitingEnd) {
				this.cooldown = this.MAX_CD;
				mapAction(findPlaceWithPlayerWithID(playerID),players.get(i));
			}
		}
		
		int counter = 0;
		boolean cond2 = this.cooldown < 0;
		if (!updatable) {
			selected = -1;
		}
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			if (counter == 0 || counter == 2) {
				thisButton.setEnabled(updatable && cond2);
			} else if (counter == 1) {
				thisButton.setText(findPlaceWithPlayerWithID(players.get(0).getId()).getName());
			}
			
			if (!updatable) {
				thisButton.setHovered(false);
			}
			counter = counter + 1;
		}
		
	}
	
	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/background.png")), 0, 0, null);
		
		int xMap = xDisplace + (int) ((ssX-this.mapImage.getWidth()+380)/2*Handler.ZOOM);
		int yMap = yDisplace + (int) ((ssY-this.mapImage.getHeight()+380)/2*Handler.ZOOM);
		int mapWidth = (int) (this.mapImage.getWidth()*Handler.ZOOM);
		int mapHeight = (int) (this.mapImage.getHeight()*Handler.ZOOM);
		
		g.drawImage(this.mapImage, xMap, yMap, mapWidth, mapHeight, null);
		
//		for (int i = players.size()-1; i > -1; i--) {
		for (int i = 0; i < players.size(); i++) {
			int playerID = players.get(i).getId();
			int placeID = findPlaceIndexWithPlayerWithID(playerID);
			int xPos = xDisplace + (int) (getMap().get(placeID).getX()*Handler.ZOOM);
			int yPos = yDisplace + (int) (getMap().get(placeID).getY()*Handler.ZOOM);
			
			int xRes = (i%2);
			int yRes = (int) Math.floor(i/2);
			
			if (xRes == 0) {
				xRes = -1;
			} else {
				xRes = 4;
			}
			
			if (yRes == 0) {
				yRes = -4;
			} else if (yRes == 1) {
				yRes = -1;
			} else {
				yRes = 1;
			}
			
			int xPlayer = (int) (xRes * 8 * Handler.ZOOM);
			int yPlayer = (int) (yRes * 8 * Handler.ZOOM);
			
			xPos = xPos + xPlayer;
			yPos = yPos + yPlayer;
			
			g.drawImage(players.get(i).getPiece(), xPos + 10, yPos + 10, (int) (players.get(i).getPiece().getWidth()*Handler.ZOOM), (int)(players.get(i).getPiece().getHeight()*Handler.ZOOM),null);
		}
		
		if (updatable) {
		
			g.drawImage(genTextRect("$"+players.get(0).getFunds(), 110, 40, 2, new Font("Arial",Font.BOLD,20), players.get(0).getColor(), Color.white, Color.black),5,ssY-45,null);
		
			if (players.get(0).getRoll() > 0) {
				g.drawImage(genTextRect("Spaces: "+players.get(0).getRoll(), 150, 60, 2, new Font("Arial",Font.BOLD,25), players.get(0).getColor(), Color.white, Color.black),ssX-155,5,null);
			}

			int counter = 0;
			while (counter < buttons.size()) {
				Button thisButton = buttons.get(counter);
				g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
				counter = counter + 1;
			}
			
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
