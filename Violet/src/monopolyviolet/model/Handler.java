/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.model;

import monopolyviolet.scenes.Scene;
import monopolyviolet.view.GameWindow;

/**
 *
 * @author movillaf
 */
public class Handler {
	public static final int SCREEN_SIZE_X = 600;
	public static final int SCREEN_SIZE_Y = 600;
	public static Scene gameState;
        
	public Handler() {
		new GameWindow(this);
		
		gameState = new monopolyviolet.scenes.Game(this);		
		
	}
}
