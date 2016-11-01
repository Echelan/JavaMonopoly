/*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package monopolyviolet.main;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author movillaf
 */
public class GameWindow extends JFrame implements WindowListener, ActionListener {

	// <editor-fold defaultstate="collapsed" desc="Attributes">
		/**
		 * Canvas.
		 */
//		private final GameDisplay screen;
	// </editor-fold>

    
        JLabel name;
        JLabel description;
        JLabel category;
        JLabel cardtype;
        JButton nextCard;
        Handler main;
	/**
	 * Create a new GameWindow.
	 * @param sizeX size of window in X dimension.
	 * @param sizeY size of window in Y dimension.
	 */
	public GameWindow(int sizeX, int sizeY, Handler param) {
		setLayout(null);
		setSize(sizeX + 8, sizeY + 31);
		setTitle("Monopoly Violet");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                
                main = param;
                
                
//		screen = new GameDisplay();
//		screen.setBounds(1, 1, sizeX, sizeY);
//		screen.setBackground(Color.green);
//		screen.setFocusable(false);
//		add(screen);

                name = new JLabel("name");
                name.setBackground(Color.yellow);
                name.setBounds(50, 50, 100, 50);
                add(name);

                description = new JLabel("description");
                description.setBackground(Color.yellow);
                description.setBounds(200, 50, 150, 150);
                add(description);

                category = new JLabel("category");
                category.setBackground(Color.yellow);
                category.setBounds(50, 150, 100, 50);
                add(category);

                cardtype = new JLabel("cardtype");
                cardtype.setBackground(Color.yellow);
                cardtype.setBounds(100, 150, 100, 50);
                add(cardtype);
                
                nextCard = new JButton("next");
                nextCard.setBounds(200,200,100,100);
                nextCard.addActionListener(this);
                add(nextCard);

//		addKeyListener(new KeyHandler());

		setVisible(true);
	}
	
//	public void startCanvasThread(){
//		screen.thisThread.start();
//	}
	
        public void update() {
            Card cardInfo = main.getCard();
            name.setText(cardInfo.getTitle());
            cardtype.setText(""+cardInfo.getCardType());
            category.setText(""+cardInfo.getCategory());
            description.setText(cardInfo.getDescription());
        }
        
	//<editor-fold defaultstate="collapsed" desc="Overriden JFrame Methods">
		@Override
		public void actionPerformed(ActionEvent e) {
                   update();
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
