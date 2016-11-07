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
package monopolyviolet.control;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import monopolyviolet.model.Handler;
import monopolyviolet.scenes.Scene;


/**
 *
 * @author Andres
 */
public class MouseHandler extends MouseAdapter {
	
	public static int EVENT_MOVE = 0;
	public static int EVENT_DRAG = 1;
	public static int EVENT_PRESS = 2;
	public static int EVENT_RELEASE = 3;
	public static int EVENT_CLICK = 4;
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int action = EVENT_MOVE;
		int x = e.getX();
		int y = e.getY();
		
		wrapper(action,x,y);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int action = EVENT_DRAG;
		int x = e.getX();
		int y = e.getY();
		
		wrapper(action,x,y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int action = EVENT_RELEASE;
		int x = e.getX();
		int y = e.getY();
		
		wrapper(action,x,y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int action = EVENT_PRESS;
		int x = e.getX();
		int y = e.getY();
		
		wrapper(action,x,y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int action = EVENT_CLICK;
		int x = e.getX();
		int y = e.getY();
		
		wrapper(action,x,y);
	}
	
	private void wrapper(int action, int x, int y) {
		if (Handler.gameState.last() != null) {
			((Scene) Handler.gameState.last()).receiveAction(action,x,y);
		}
	}
	
}
