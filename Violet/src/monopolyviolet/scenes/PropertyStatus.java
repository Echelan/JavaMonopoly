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
package monopolyviolet.scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import monopolyviolet.model.Button;
import monopolyviolet.model.Handler;
import monopolyviolet.model.Node;
import monopolyviolet.model.Place;
import monopolyviolet.model.Player;

/**
 *
 * @author Andres
 */
public class PropertyStatus extends Scene {

    private Player player;
	private Player owner;
    private Place place;
    private Node<Button> buttons;
    private int selected;
	private int amount;
        private int monopolyBonus;
        
    public PropertyStatus(Handler main, Place place, Player player) {
        super(main, "PROPERTY", false);
        this.place = place;
        this.player = player;
        this.selected = -1;
        buttons = new Node();
		
        if (place.getProperty().getOwner() == -1) {
			Button newButton;
			
			newButton = new Button(50, 210, 200, 40);
			if (place.getProperty().getBuyPrice() > player.getFunds()) {
				newButton.setTextColor(Color.gray);
			}
			newButton.setText("Buy property");
			newButton.setInternalName("BUY");
			newButton.setEnabled(player.getFunds() >= place.getProperty().getBuyPrice());
			buttons.add(newButton);

			newButton = new Button(50, 280, 200, 40);
			newButton.setText("Back");
			newButton.setInternalName("BACK");
			buttons.add(newButton);
			
        } else if (!place.getProperty().isMortgaged()) {
			Button newButton;
			
			newButton = new Button(50, 280, 200, 40);
			newButton.setText("Pay Rent");
			newButton.setInternalName("RENT");
			buttons.add(newButton);
			
			this.owner = ((Game) main.gameState.last()).findPlayerWithID(place.getProperty().getOwner());
			this.amount = place.getProperty().getRent();
                        if (place.getType() == Place.UTILITY_TYPE) {
                            this.amount = this.amount * player.getLastRoll();
                        }
                        this.monopolyBonus = ((Game) main.gameState.last()).isMonopoly(place, owner.getId());
			
			if (owner == player) {
				buttons.get(0).setText("Back");
				buttons.get(0).setInternalName("BACK");
			}
			
		} else {
			Button newButton;
			
			newButton = new Button(50, 280, 200, 40);
			newButton.setText("Back");
			newButton.setInternalName("BACK");
			buttons.add(newButton);
		}
    }

    @Override
    protected void clickEvent(int x, int y) {
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				String internalName = buttons.get(selected).getInternalName();
				
				if (internalName.compareTo("BACK") == 0) {
					this.dispose();
					
				} else if (internalName.compareTo("BUY") == 0) {
					this.dispose();
					buyProperty();
				} else if (internalName.compareTo("RENT") == 0) {
					this.dispose();
					payRent();
				}
			}
		}
    }
	
	private void buyProperty() {
		
		player.removeFunds(place.getProperty().getBuyPrice());
		place.getProperty().setOwnerName(player.getName());
		place.getProperty().setOwner(player.getId());
		place.getProperty().setOwnerColor(player.getColor());

		try {
			((Game) main.gameState.last()).buildMapDisplay();
		} catch (IOException ex) {
		}
	}
	
	private void payRent() {
		
		main.gameState.add(new PayAmount(main, amount*monopolyBonus, player, owner));

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
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void pressEvent(int x, int y) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void releaseEvent(int x, int y) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BufferedImage getDisplay() throws IOException {
        BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
        Graphics g = display.getGraphics();

		int counter = 0;
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			counter = counter + 1;
		}
        BufferedImage propertyCard = place.getProperty().getPropertyCard();
		
        if (this.monopolyBonus > 1) {
			g.setColor(Color.yellow);
			g.fillRect(ssX-305, (ssY/2)-205, propertyCard.getWidth()+10, propertyCard.getHeight()+10);
		}
		
        g.drawImage(propertyCard, ssX-300, (ssY/2)-200, null);
        
        return display;
    }
	
}
