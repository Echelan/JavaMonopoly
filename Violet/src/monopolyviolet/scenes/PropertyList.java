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
import java.awt.Font;
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
 * @author movillaf
 */
public class PropertyList extends Scene {

    private Place currentProperty;
    private int currentIndex;
	private int selected;
    private Node<Button> buttons;
    
    private Player player;
    
    
    public PropertyList(Handler main, Player player) {
        super(main, "LIST", false);
        
        this.player = player;
        this.selected = -1;
		
		buttons = new Node();
		
        findCurrent();
		
		createButtons();
    }
    
    private void findCurrent() {
        this.currentIndex = ((Game) main.gameState.get(main.gameState.size()-2)).findPropertyIndexOwnedByPlayer(player.getId(), this.currentIndex);
        this.currentProperty = ((Game) main.gameState.get(main.gameState.size()-2)).findPropertyOwnedByPlayer(player.getId(), this.currentIndex);
    }

    @Override
    protected void clickEvent(int x, int y) {
		
		if (selected != -1) {
			if (buttons.get(selected).isEnabled()) {
				String internalName = buttons.get(selected).getInternalName();
				
				if (internalName.compareTo("BACK") == 0) {
					this.dispose();
				} else if (internalName.compareTo("SELLP") == 0) {
					sellProperty();
				} else if (internalName.compareTo("MORTGAGE") == 0) {
					doMortgage();
				} else if (internalName.compareTo("BUYH") == 0) {
					buyHouse();
				} else if (internalName.compareTo("SELLH") == 0) {
					sellHouse();
				} else if (internalName.compareTo("NEXT") == 0) {
					nextProperty();
				} else if (internalName.compareTo("PREV") == 0) {
					prevProperty();
				}
				
			}
		}
    }
	
	private void sellHouse() {
		player.addFunds(currentProperty.getProperty().getBuildingCost()/2);
		currentProperty.getProperty().setNumHouses(currentProperty.getProperty().getNumHouses()-1);
		
		updateButtons();
	}
	
	private void buyHouse() {
		player.removeFunds(currentProperty.getProperty().getBuildingCost());
		currentProperty.getProperty().setNumHouses(currentProperty.getProperty().getNumHouses()+1);
		
		updateButtons();
	}
	
	private void doMortgage() {
		if (currentProperty.getProperty().isMortgaged()) {
			player.removeFunds(currentProperty.getProperty().getMortgageInterest());
			currentProperty.getProperty().setMortgaged(false);
		} else {
			player.addFunds(currentProperty.getProperty().getMortgage());
			currentProperty.getProperty().setMortgaged(true);
		}
		
		updateButtons();
	}
	
	private void updateButtons() {
		
		if (currentProperty == null) {
			removeButtons();
		} else {
			int bIndex = 1;
			boolean canSell = currentProperty.getProperty().getNumHouses() == 0;
			buttons.get(bIndex).setEnabled(canSell);

			bIndex = bIndex+1;
			boolean mortgaged = true;
			String state = "Get ";
			if (currentProperty.getProperty().isMortgaged()) {
				mortgaged = player.getFunds() >= currentProperty.getProperty().getMortgageInterest();
				state = "Pay ";
			}
			buttons.get(bIndex).setEnabled(mortgaged);
			buttons.get(bIndex).setText(state+"Mortgage");

			bIndex = bIndex+1;
			boolean canBuy = currentProperty.getProperty().getNumHouses()<5;
			canBuy = canBuy && currentProperty.getProperty().getBuildingCost() <= player.getFunds();
			canBuy = canBuy && currentProperty.getProperty().getBuildingCost() > 0;
			canBuy = canBuy && currentProperty.getProperty().isMonopoly();
			buttons.get(bIndex).setEnabled(canBuy);

			bIndex = bIndex+1;
			buttons.get(bIndex).setEnabled(currentProperty.getProperty().getNumHouses()>0);
		}
	}
	
	private void removeButtons() {
		int numButtons = buttons.size();
		for (int i = 1; i < numButtons; i++) {
			buttons.remove(1);
		}
	}
	
	private void createButtons() {
		
		int width = 145;
		int height = 40;
		
		int firstX = 20;
		int secondX = firstX + width + 5;
		
		int firstY = 250;
		int secondY = firstY + height + 20;
		int lastY = ssY - height - 40;
		
		
		Button newButton;
		
		newButton = new Button(firstX, lastY, width, height+20);
		newButton.setText("Back to Map");	
		newButton.setInternalName("BACK");
		buttons.add(newButton);
		
		if (currentProperty != null) {
			newButton = new Button(secondX, firstY, width, height);
			boolean canSell = currentProperty.getProperty().getNumHouses() == 0;
			newButton.setEnabled(canSell);
			newButton.setText("Sell Property");
			newButton.setInternalName("SELLP");
			buttons.add(newButton);

			newButton = new Button(firstX, firstY, width, height);
			boolean mortgaged = true;
			String state = "Get ";
			if (currentProperty.getProperty().isMortgaged()) {
				mortgaged = player.getFunds() >= currentProperty.getProperty().getMortgageInterest();
				state = "Pay ";
			}
			newButton.setEnabled(mortgaged);
			newButton.setText(state+"Mortgage");
			newButton.setInternalName("MORTGAGE");
			buttons.add(newButton);

			newButton = new Button(secondX, secondY, width, height);
			boolean canBuy = currentProperty.getProperty().getNumHouses()<5;
			canBuy = canBuy && currentProperty.getProperty().getBuildingCost() >= player.getFunds();
			canBuy = canBuy && currentProperty.getProperty().isMonopoly();
			newButton.setEnabled(canBuy);
			newButton.setText("Buy House");
			newButton.setInternalName("BUYH");
			buttons.add(newButton);

			newButton = new Button(firstX, secondY, width, height);
			newButton.setEnabled(currentProperty.getProperty().getNumHouses()>0);
			newButton.setText("Sell House");
			newButton.setInternalName("SELLH");
			buttons.add(newButton);

			newButton = new Button(secondX + (width/2) + width + 5, lastY, width, height);
			newButton.setText("Next");
			newButton.setInternalName("NEXT");
			buttons.add(newButton);

			newButton = new Button(secondX + (width/2), lastY, width, height);
			newButton.setText("Previous");
			newButton.setInternalName("PREV");
			buttons.add(newButton);
		}
		
	}
	
	private void sellProperty() {
		player.removeFunds(currentProperty.getProperty().getBuyPrice()/2);
		currentProperty.getProperty().resetOwner();
		nextProperty();
		try {
			((Game) main.gameState.get(main.gameState.size()-2)).buildMapDisplay();
		} catch (IOException ex) {
		}
		
		updateButtons();
	}
	
	private void nextProperty() {
		this.currentIndex = this.currentIndex + 1;
		findCurrent();
		
		updateButtons();
	}
	
	private void prevProperty() {
		this.currentIndex = this.currentIndex - 1;
		findCurrent();
		
		updateButtons();
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

    }

    @Override
    protected void pressEvent(int x, int y) {

    }

    @Override
    protected void releaseEvent(int x, int y) {

    }

    @Override
    public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		if (currentProperty != null) {
			BufferedImage propertyCard = currentProperty.getProperty().getPropertyCard();
			g.drawImage(propertyCard, ssX-10-propertyCard.getWidth(), (ssY-propertyCard.getHeight())/2, null);
		} else {
			String text = "You own no properties.";
			int width = 300;
			int height = 70;
			int strokeWidth = 4;
			Font font = new Font("Arial",Font.BOLD,20);
			Color strokeColor = player.getColor();
			Color fillColor = Color.white;
			Color textColor = Color.black;
			int x = (ssX-width)/2;
			int y = (ssY-height)/2;
			g.drawImage(genTextRect(text, width, height, strokeWidth, font, strokeColor, fillColor, textColor), x, y, null);
		}
		
		int counter = 0;
		while (counter < buttons.size()) {
			Button thisButton = buttons.get(counter);
			g.drawImage(thisButton.getDisplay(),thisButton.getX(),thisButton.getY(), null);
			counter = counter + 1;
		}
		
		return display;
    }
    
}
