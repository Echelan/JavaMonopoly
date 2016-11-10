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
    private Place nextProperty;
    private Place prevProperty;
    private int currentIndex;
    private Node<Button> buttons;
    
    private Player player;
    
    
    public PropertyList(Handler main, Player player) {
        super(main, "LIST", false);
        
        this.currentIndex = 0;
        this.player = player;
        
        findCurrent();
        findPrev();
        findNext();
    }
    
    private void findCurrent() {
        this.currentIndex = ((Game) main.gameState.get(main.gameState.size()-2)).findPropertyIndexOwnedByPlayer(player.getId(), this.currentIndex);
        this.currentProperty = ((Game) main.gameState.get(main.gameState.size()-2)).findPropertyOwnedByPlayer(player.getId(), this.currentIndex);
    }
    
    private void findNext() {
        this.nextProperty = ((Game) main.gameState.get(main.gameState.size()-2)).findPropertyOwnedByPlayer(player.getId(), this.currentIndex+1);
    }
    
    private void findPrev() {
        this.prevProperty = ((Game) main.gameState.get(main.gameState.size()-2)).findPropertyOwnedByPlayer(player.getId(), this.currentIndex-1);
    }

    @Override
    protected void clickEvent(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void moveEvent(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void dragEvent(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void pressEvent(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void releaseEvent(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BufferedImage getDisplay() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
