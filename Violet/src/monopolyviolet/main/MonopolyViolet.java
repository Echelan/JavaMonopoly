/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JWindow;

/**
 *
 * @author Andres
 */
public class MonopolyViolet {

	private static Handler h;
	
	public static void main(String[] args) throws InterruptedException {

		SplashWindow s = new SplashWindow();

		monopolyviolet.data.NIC.loadAllData();

		h = new Handler();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
		}
		
		s.dispose();
//		h.canContinue();
//		new Thread(h).start();
		
	}	
	
	private static class SplashWindow extends JWindow {

		BufferedImage image;
		MediaTracker media;

		/**
		 * Create a splash window.
		 */
		public SplashWindow() {
			media = new MediaTracker(this);

			try {
				image = ImageIO.read(new File("assets/splashImage.png"));
				media.addImage(image, 0);
				media.waitForID(0);
			} catch (Exception ex) {
			}

			setSize(image.getWidth(), image.getHeight());
			setLocationRelativeTo(null);
			setVisible(true);
		}

		@Override
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, this);
		}
	}
}
