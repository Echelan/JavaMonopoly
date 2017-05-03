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
package monopolyviolet.view;

import java.awt.Canvas;
import java.awt.Graphics;
import monopolyviolet.control.MouseHandler;
import monopolyviolet.model.Handler;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	private String lastState;
	public Thread thisThread;
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay() {
		lastState = "";
		thisThread = new Thread(this);
		
		MouseHandler mh = new MouseHandler();
		addMouseListener(mh);
		addMouseMotionListener(mh);
		addMouseWheelListener(mh);
	}

	@Override
	public void paint(Graphics g) {
	}

	@Override
	public void run() {
		createBufferStrategy(3);
		while (true) {
			Graphics g = getBufferStrategy().getDrawGraphics();
			if (!Handler.gameState.isEmpty()){
				
				String newState = "";

				newState = Handler.gameState.last().getName();

				if (lastState.compareTo(newState) != 0) {
					g.clearRect(0, 0, this.getWidth(), this.getHeight());
					lastState = newState;
				}

				g.drawImage(DisplayParser.displayImage(), 0, 0, this.getWidth(), this.getHeight(), this);

				getBufferStrategy().show();

				try {
					Thread.sleep(20);
				} catch (InterruptedException ex) {
				}
			}
		}
	}

}
