/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package monopolyviolet.view;

import java.awt.Canvas;
import java.awt.Graphics;
import monopolyviolet.control.MouseHandler;
import monopolyviolet.model.Handler;
import monopolyviolet.scenes.Scene;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	private String lastState;
	public Thread thisThread;
	private Handler main;
	
	/**
	 * Create a GameDisplay custom canvas.
	 * @param main main game handler
	 */
	public GameDisplay(Handler main) {
		lastState = "";
		thisThread = new Thread(this);
		
		addMouseListener(new MouseHandler());

		this.main = main;
	}

	@Override
	public void paint(Graphics g) {
		try {
			getBufferStrategy().show();
		} catch (Exception ex) {
		}
	}

	@Override
	public void run() {
		createBufferStrategy(3);
		while (true) {
			if (!Handler.gameState.isEmpty()) {
				Graphics g = getBufferStrategy().getDrawGraphics();

				if (lastState.compareTo(((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).getName()) != 0) {
					g.clearRect(0, 0, this.getWidth(), this.getHeight());
					lastState = ((Scene) Handler.gameState.get(Handler.gameState.size() - 1)).getName();
				}

				g.drawImage(DisplayParser.displayImage(), 0, 0, this.getWidth(), this.getHeight(), this);

				repaint();
			}

			try {
				Thread.sleep(40);
			} catch (InterruptedException ex) {
			}
		}
	}

}
