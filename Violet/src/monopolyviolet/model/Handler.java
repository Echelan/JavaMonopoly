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
	private GameWindow gw;
	
	public Handler() {
		gw = new GameWindow();
		
		gameState = new monopolyviolet.scenes.BaseScene(this);
		gameState = (Scene) gameState.add(new monopolyviolet.scenes.Title(this));
		
		gw.startCanvasThread();
	}
	
}
