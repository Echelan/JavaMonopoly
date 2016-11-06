/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package monopolyviolet.view;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
import monopolyviolet.model.Handler;

/**
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {

	// <editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Canvas.
		 */
		private final GameDisplay screen;
	// </editor-fold>
	
	/**
	 * Create a new GameWindow.
	 * @param main main game handler
	 */
	public GameWindow(Handler main) {
		setLayout(null);
		setSize(main.SCREEN_SIZE_X + 8, main.SCREEN_SIZE_Y + 31);
		setTitle("Monopoly Violet");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		screen = new GameDisplay(main);
		screen.setBounds(1, 1, main.SCREEN_SIZE_X, main.SCREEN_SIZE_Y);
		screen.setFocusable(false);
		add(screen);

		setVisible(true);
	}
	
	public void startCanvasThread(){
		screen.thisThread.start();
	}
	
        
	//<editor-fold defaultstate="collapsed" desc="Overriden JFrame Methods">
		@Override
		public void actionPerformed(ActionEvent e) {
                   
		}

		@Override
		public void windowClosing(WindowEvent e) {
			dispose();
			System.exit(0);
		}

		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}

		@Override
		public void windowClosed(WindowEvent e) {
	}
	//</editor-fold>
}
