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

import monopolyviolet.scenes.Game;
import monopolyviolet.scenes.Scene;
import monopolyviolet.scenes.TurnAnnounce;
import monopolyviolet.view.GameWindow;

/**
 *
 * @author movillaf
 */
public class Handler {
	public static final int SCREEN_SIZE_X = 600;
	public static final int SCREEN_SIZE_Y = 600;
	public static float ZOOM = 1.0f;
	public static Node<Scene> gameState;

    private Node<Card> communityCard;
    private Node<Card> chanceCard;
    private Node<Place> map;
    private Node<Player> players;
    private Node<Property> propertyList;
    private Node<Property> specialList;
	
	private final GameWindow gw;
	
	public Handler() {
		gw = new GameWindow();
		
		communityCard = new Node();
		chanceCard = new Node();
		map = new Node();
		propertyList = new Node();
		specialList = new Node();
		gameState = new Node();
		players = new Node();

		players.setCircular(true);
		
		map.setCircular(true);
		map.setDoubleLink(true);

		communityCard.setCircular(true);
		communityCard.setDoubleLink(true);

		chanceCard.setCircular(true);
		chanceCard.setDoubleLink(true);
		
		gameState.add(new monopolyviolet.scenes.Title(this));
		
		gw.startCanvasThread();

		createDecks();
		createProperties();
		createSpecials();
		buildMap();
	}

	public static void modZoom(float aZoom) {
		float change = aZoom / -50;
		ZOOM = ZOOM + change;
		if (ZOOM > 2.5f) {
			ZOOM = 2.5f;
		} else if (ZOOM < 0.5) {
			ZOOM = 0.5f;
		}
	}
	
    private void createDecks() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_CARDS.size(); i++) {
			Card tempCard = new Card(i);
			if (tempCard.getCardType()== Card.COMMUNITY_CHEST_ID) {
				communityCard.add(tempCard);
			} else if (tempCard.getCardType()== Card.CHANCE_ID) {
				chanceCard.add(tempCard);
			}
		}
    }

    private void createProperties() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_PROPERTIES.size(); i++) {
			propertyList.add(new Property(i,false));
		}
    }

    private void createSpecials() {
		for (int i = 0; i < monopolyviolet.data.NIC.INFO_SPECIALS.size(); i++) {
			specialList.add(new Property(i,true));
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

    public void nextTurn() {
		boolean playerWon = false;
		refreshMonopolies();
		if (!players.get(0).isRolledDoubles()) {
			int curPlayerID = players.get(0).getId();
			do {
				players.rotate(1);
			} while (players.get(0).isBankrupt());
			playerWon = curPlayerID == players.get(0).getId();
		} else {
			players.get(0).setRolledDoubles(false);
		}
		if (!playerWon) {
			gameState.add(new TurnAnnounce(this,players.get(0)));
		} else {
			System.out.println(players.get(0).getName()+" won!");
		}
    }
	
	public void sendJail(int pID) {
		findPlayerWithID(pID).setJailed(true);
		findPlayerWithID(pID).setRoll(0);
		findPlayerWithID(pID).setRolledDoubles(false);
		findPlayerWithID(pID).setDoubleCount(0);
		removePlayerFromPlace(findPlaceWithPlayerWithID(pID), pID);
		findPlaceWithType(Place.JAIL_TYPE).getPlayersHere().add(pID);
	}
	
	private int isMonopoly(Place search) {
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
					if (place != search) {
						multiple = 2;
					}
				}
			}
		}

		if (search.getType() == Place.PROPERTY_TYPE && forProps == true) {
			multiple = 2;
		}
		
		if (search.getProperty().getOwner() == -1) {
			multiple = 1;
		}
		

		return multiple;
	}

	private void refreshMonopolies() {
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).hasProperty()) {
				map.get(i).getProperty().setMonopolyBonus(isMonopoly(map.get(i)));
			}
		}
	}
	
	public int getMinimumHouses(Place search) {
		int result = search.getProperty().getNumHouses();
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).hasProperty()) {
				if (map.get(i).getProperty().getColor() == search.getProperty().getColor()) {
					if (map.get(i).getProperty().getNumHouses() < result) {
						result = map.get(i).getProperty().getNumHouses();
					}
				}
			}
		}
		
		return result;
	}
	
	public int getNumHotelsFromPlayer(Player search) {
		int result = 0;
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).hasProperty()) {
				if (map.get(i).getProperty().getOwner() == search.getId()) {
					result = result + (int) Math.floor(map.get(i).getProperty().getNumHouses()/5);
				}
			}
		}
		
		return result;
	}
	
	public int getDistanceFrom(Player player, int search) {
		int i = 0;
		int start = -1;
		int distance = -1;
		
		while (i < map.size()*2 && distance == -1) {
			int corrected = i%map.size();
			if (map.get(corrected).hasPlayer(player.getId())) {
				start = corrected;
			}
			if (map.get(corrected).getType() == search && start != -1) {
				distance = i-start;
			}
			i = i + 1;
		}
		
		return distance;
	}
	
	public int getDistanceFrom(Player player, Place search) {
		int i = 0;
		int start = -1;
		int distance = -1;
		
		while (i < map.size()*2 && distance == -1) {
			int corrected = i%map.size();
			if (map.get(corrected).hasPlayer(player.getId())) {
				start = corrected;
			}
			if (map.get(corrected) == search && start != -1) {
				distance = i-start;
			}
			i = i + 1;
		}
		
		return distance;
	}
	
	public int getNumHousesFromPlayer(Player search) {
		int result = 0;
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).hasProperty()) {
				if (map.get(i).getProperty().getOwner() == search.getId()) {
					result = result +(map.get(i).getProperty().getNumHouses()%5);
				}
			}
		}
		
		return result;
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
    
	public int getPlayerWorth(Player player) {
		int value = 0;
		
		value = value + player.getFunds();
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).hasProperty()) {
				Property thisProperty = map.get(i).getProperty();
				if (thisProperty.getOwner() == player.getId()) {
					
					for (int j = 0; j < thisProperty.getNumHouses(); j++) {
						value = value + thisProperty.getBuildingCost()/2;
					}
					value = value + thisProperty.getBuildingCost()/2;
					
				}
			}
		}
		
		return value;
	}
	
	public void sellAll(Player player) {
		
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i).hasProperty()) {
				Property thisProperty = map.get(i).getProperty();
				if (thisProperty.getOwner() == player.getId()) {
					thisProperty.setNumHouses(0);
					thisProperty.resetOwner();
				}
			}
		}
		
	}
	
	//<editor-fold defaultstate="collapsed" desc="Getters & Setters">
	public Game getGame() {
		Game result = null;
		for (int i = 0; i < gameState.size(); i++) {
			if (gameState.get(i) instanceof Game) {
				result = (Game) gameState.get(i);
			}
		}
		return result;
	}

	/**
	 * @return the communityCard
	 */
	public Node<Card> getCommunityCard() {
		return communityCard;
	}

	/**
	 * @return the chanceCard
	 */
	public Node<Card> getChanceCard() {
		return chanceCard;
	}

	/**
	 * @return the map
	 */
	public Node<Place> getMap() {
		return map;
	}

	/**
	 * @return the players
	 */
	public Node<Player> getPlayers() {
		return players;
	}

	/**
	 * @return the propertyList
	 */
	public Node<Property> getPropertyList() {
		return propertyList;
	}

	/**
	 * @return the specialList
	 */
	public Node<Property> getSpecialList() {
		return specialList;
	}
	//</editor-fold>
	
}
