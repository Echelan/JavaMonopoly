/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
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
		((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).receiveAction(action,x,y);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int action = EVENT_DRAG;
		int x = e.getX();
		int y = e.getY();
		((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).receiveAction(action,x,y);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int action = EVENT_RELEASE;
		int x = e.getX();
		int y = e.getY();
		((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).receiveAction(action,x,y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int action = EVENT_PRESS;
		int x = e.getX();
		int y = e.getY();
		((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).receiveAction(action,x,y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int action = EVENT_CLICK;
		int x = e.getX();
		int y = e.getY();
		((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).receiveAction(action,x,y);
	}
	
}
