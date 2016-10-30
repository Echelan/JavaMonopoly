/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package monopolyviolet.main;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 *
 * @author Andres
 */
public class GameDisplay extends Canvas implements Runnable {
	
	/**
	 * Create a GameDisplay custom canvas.
	 */
	public GameDisplay() {
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
			
		}
	}

}
