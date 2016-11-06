/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import monopolyviolet.model.Handler;
import static monopolyviolet.scenes.Scene.ssX;

/**
 *
 * @author Andres
 */
public class Title extends Scene {

	public Title(Handler main) {
		super(main, "TITLE", true);
	}

	@Override
	public void receiveAction(int action, int x, int y) {
		
	}

	@Override
	public BufferedImage getDisplay() throws IOException {
		BufferedImage display = new BufferedImage(ssX, ssY, BufferedImage.TYPE_INT_ARGB);
		Graphics g = display.getGraphics();
		
		g.drawImage(ImageIO.read(new File("assets/title/background.png")), 0, 0, ssX, ssY, null);
		
		g.setColor(Color.black);
		g.fillRect(0, ssY - 100, ssX, 100);
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Andrés Movilla", 10, ssY - 25);
		
		int logoX = (ssX/2)-(1292/8);
		int logoW = (1292/4);
		int logoH = (641/4);
		int logoY = 20;
		g.drawImage(ImageIO.read(new File("assets/title/violetMonopolyLogo.png")), logoX, logoY, logoW, logoH, null);
		
		
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("Click to Start",(ssX/2)-110, 250);
		
		return display;
	}
	
}
