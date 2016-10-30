/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

/**
 *
 * @author movillaf
 */
public class Handler {
	public static final int SIZEX = 900;
	public static final int SIZEY = 800;
	
	public Handler() {
		new GameWindow(SIZEX,SIZEY);
		
		new Card(1);
	}
    
	
	private void createProperties() {
		
	}
}
