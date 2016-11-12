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
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
import monopolyviolet.model.Place;
import monopolyviolet.model.Player;


/**
 *
 * @author movillaf
 */
public class Game extends Scene{
	
    private static final int DRAG_STRENGTH = 3;
    private static final int MAX_CD = 3;

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

    public Game(Handler main) {
		super(main, "GAME", true);

		buttons = new Node();

		this.cooldown = MAX_CD;
		this.selected = -1;
		this.yDisplaceLast = 0;
		this.xDisplaceLast = 0;
		this.yDisplace = 0;
		this.xDisplace = 0;
		this.waitingEnd = false;
		this.updatable = false;

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

		for (int i = 0; i < main.getPlayers().size(); i++) {
			main.getMap().get(0).getPlayersHere().add(main.getPlayers().get(i).getId());
		}

    }

    public void centerOn(Player search) {
		int xPos = (int) ((main.findPlaceWithPlayerWithID(search.getId()).getX()+(search.getId()*8)) * Handler.ZOOM);
		int yPos = (int) (main.findPlaceWithPlayerWithID(search.getId()).getY() * Handler.ZOOM);

		xPos = xPos * -1;
		yPos = yPos * -1;
		
		xPos = xPos + (ssX/2) - 35;
		yPos = yPos + (ssY/2) - 120;
		
		boolean longCondition = main.findPlaceWithPlayerWithID(search.getId()).hasProperty();
		longCondition = longCondition && search == main.getPlayers().get(0);
		longCondition = longCondition && search.getRoll() == 0;
		if ( longCondition ) {
			buttons.get(1).setInternalName("REOPEN");
		}
		
		xDisplace = xPos;
		yDisplace = yPos;
    }

	protected void mapAction(Place place, Player player) {
		if (place.hasProperty()) {
			main.gameState.add(new PropertyStatus(main,place,player));
		} else if (place.getType() == Place.TAX_TYPE) {
			main.gameState.add(new HandleAmount(main, 200, player, null, true));
		} else if (place.getType() == Place.GOJAIL_TYPE) {
			main.sendJail(player.getId());
		} else if (place.getType() == Place.CHANCE_TYPE) {
			main.getChanceCard().rotate(1);
			main.gameState.add(new DrawCard(main,player,main.getChanceCard().get(0)));
		} else if (place.getType() == Place.COMMUNITY_TYPE) {
			main.getCommunityCard().rotate(1);
			main.gameState.add(new DrawCard(main,player,main.getCommunityCard().get(0)));
		}
		centerOn(player);
		this.waitingEnd = true;
	}
	
	@Override
	protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				if (buttons.get(selected).getInternalName().compareTo("NEXT") == 0){
					main.nextTurn();
				} else if (buttons.get(selected).getInternalName().compareTo("CENTER") == 0){
					centerOn(main.getPlayers().get(0));
				} else if (buttons.get(selected).getInternalName().compareTo("LIST") == 0){
					main.gameState.add(new PropertyList(main, main.getPlayers().get(0), 0));
				} else if (buttons.get(selected).getInternalName().compareTo("REOPEN") == 0){
					boolean canReopen = main.findPlaceWithPlayerWithID(main.getPlayers().get(0).getId()).hasProperty();
					if (canReopen) {
						canReopen = main.findPlaceWithPlayerWithID(main.getPlayers().get(0).getId()).getProperty().getOwner() == -1;
					}
					if ( canReopen ) {
						mapAction(main.findPlaceWithPlayerWithID(main.getPlayers().get(0).getId()), main.getPlayers().get(0));
					} else {
						buttons.get(1).setInternalName("CENTER");
						centerOn(main.getPlayers().get(0));
					}
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
		
		for (int i = 0; i < main.getMap().size(); i++) {
			
			int xPos = main.getMap().get(i).getX();
			int yPos = main.getMap().get(i).getY();
			
			g.drawImage(main.getMap().get(i).getDisplay(), xPos, yPos, null);
		}
		
		this.mapImage = display;
	}
    
	private void refresh() {
		
		updatable = main.gameState.last() == this;
		
		this.cooldown = this.cooldown - 1;
		
		if (this.cooldown < 0 && updatable) {
			
			int i = 0;
			int roll = main.getPlayers().get(i).getRoll();
			int playerID = main.getPlayers().get(i).getId();
			if (roll > 0) {
				this.cooldown = this.MAX_CD;
				this.waitingEnd = false;
				

				int currentID = main.findPlaceIndexWithPlayerWithID(playerID);
				int mapID =  currentID+1;
				if (mapID == main.getMap().size()) {
					mapID = 0;
				}
				
				main.removePlayerFromPlace(currentID, playerID);
				main.getMap().get(mapID).getPlayersHere().add(playerID);
				if (main.getMap().get(mapID).getType() == Place.GO_TYPE) {
					main.getPlayers().get(i).addFunds(200);
				}
				main.getPlayers().get(i).setRoll(roll-1);
				centerOn(main.getPlayers().get(i));
			} else if (roll < 0) {
				this.cooldown = this.MAX_CD;
				this.waitingEnd = false;

				int currentID = main.findPlaceIndexWithPlayerWithID(playerID);
				int mapID =  currentID-1;
				if (mapID == -1) {
					mapID = main.getMap().size()-1;
				}

				main.removePlayerFromPlace(currentID, playerID);
				main.getMap().get(mapID).getPlayersHere().add(playerID);
				main.getPlayers().get(i).setRoll(roll+1);
				centerOn(main.getPlayers().get(i));
			}

			if (this.cooldown < 0 && !waitingEnd) {
				this.cooldown = this.MAX_CD;
				mapAction(main.findPlaceWithPlayerWithID(playerID),main.getPlayers().get(i));
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
				thisButton.setText(main.findPlaceWithPlayerWithID(main.getPlayers().get(0).getId()).getName());
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
		
		for (int i = 0; i < main.getPlayers().size(); i++) {
			int playerID = main.getPlayers().get(i).getId();
			int placeID = main.findPlaceIndexWithPlayerWithID(playerID);
			int xPos = xDisplace + (int) (main.getMap().get(placeID).getX()*Handler.ZOOM);
			int yPos = yDisplace + (int) (main.getMap().get(placeID).getY()*Handler.ZOOM);
			
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
			
			g.drawImage(main.getPlayers().get(i).getPiece(), xPos + 10, yPos + 10, (int) (main.getPlayers().get(i).getPiece().getWidth()*Handler.ZOOM), (int)(main.getPlayers().get(i).getPiece().getHeight()*Handler.ZOOM),null);
		}
		
		g.drawImage(genTextRect("$"+main.getPlayers().get(0).getFunds(), 110, 40, 2, new Font("Arial",Font.BOLD,20), main.getPlayers().get(0).getColor(), Color.white, Color.black),5,ssY-45,null);
		
		if (updatable) {
			if (main.getPlayers().get(0).getRoll() > 0) {
				g.drawImage(genTextRect("Spaces: "+main.getPlayers().get(0).getRoll(), 150, 60, 2, new Font("Arial",Font.BOLD,25), main.getPlayers().get(0).getColor(), Color.white, Color.black),ssX-155,5,null);
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
	
}
