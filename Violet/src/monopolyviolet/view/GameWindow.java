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

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
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
	 */
	public GameWindow() {
		setLayout(null);
		setSize(Handler.SCREEN_SIZE_X + 8, Handler.SCREEN_SIZE_Y + 31);
		setTitle("Monopoly Violet");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		try {
			setIconImage(ImageIO.read(new File("assets/icon.png")));
		} catch (IOException ex) {
		}
		
		screen = new GameDisplay();
		screen.setBounds(1, 1, Handler.SCREEN_SIZE_X, Handler.SCREEN_SIZE_Y);
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
