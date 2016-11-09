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
    private Place place;
    private Node<Button> buttons;
    private String selecting;
        
    public PropertyStatus(Handler main, Place place, Player player) {
        super(main, "PROPERTY", false);
        this.place = place;
        this.player = player;
        this.selecting = "";
        buttons = new Node();
        
        if (place.getProperty().getOwner() == -1) {
		Button newButton = new Button(50, 100, 200, 40);
                if (place.getProperty().getBuyPrice() > player.getFunds()) {
                    newButton.setColorFore(Color.gray);
                }
		newButton.setText("Buy property");
		newButton.setInternalName("BUY");
		buttons.add(newButton);
                
                newButton = new Button(50, 200, 200, 40);
		newButton.setText("Back");
		newButton.setInternalName("BACK");
		buttons.add(newButton);
        }
    }

    @Override
    protected void clickEvent(int x, int y) {
        if (selecting.compareTo("BACK") == 0){
                resolved();
        } else if (selecting.compareTo("BUY") == 0){
            
                if (place.getProperty().getBuyPrice() > player.getFunds()) {
                    place.getProperty().setOwner(player.getId());
                    resolved();
                }
        }
        
    }

    private void resolved() {
        this.dispose();
        ((Game) main.gameState.last()).nextTurn();
    }
    
    @Override
    protected void moveEvent(int x, int y) {

        String placement = "";
        int counter = 0;

        while (counter < buttons.size()) {
                if (buttons.get(counter).isContained(x, y)) {
                        placement = buttons.get(counter).getInternalName();
                        buttons.get(counter).setHovered(true);
                } else {
                        buttons.get(counter).setHovered(false);
                }
                counter = counter + 1;
        }

        selecting = placement;
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
                
        g.drawImage(place.getProperty().getPropertyCard(), ssX-300, (ssY/2)-150, null);
        
        
        return display;
    }
	
}
