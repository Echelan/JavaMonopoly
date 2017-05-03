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
package monopolyviolet.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import monopolyviolet.model.Node;

/**
 *
 * @author Andres
 */
public abstract class NIC {

	//<editor-fold defaultstate="collapsed" desc="Information Lists">
		/**
		 * Main data for Properties.
		 */
		public static Node<String> INFO_PROPERTIES;
		/**
		 * Main data for Cards.
		 */
		public static Node<String> INFO_CARDS;
		/**
		 * Main data for Special Properties.
		 */
		public static Node<String> INFO_SPECIALS;
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Static Images">
	//</editor-fold>
		
	public static void loadAllData() {
		
		INFO_PROPERTIES = new Node();
		INFO_CARDS = new Node();
		INFO_SPECIALS = new Node();
		
		try {
			loadImages();
		} catch (IOException ex) {
			
		}
		
		try {
			loadInfo();
		} catch (IOException ex) {
			
		}
		
	}
	
	private static void loadImages() throws IOException {
		
	}
	
	private static void loadInfo() throws IOException {

		File archivo;
		
		archivo = new File("db/listCards.txt");
		for (int i = 0; i < Files.readAllLines(archivo.toPath()).size(); i++) {
			INFO_CARDS.add(Files.readAllLines(archivo.toPath()).get(i));
		}
		
		archivo = new File("db/listProperties.txt");
		for (int i = 0; i < Files.readAllLines(archivo.toPath()).size(); i++) {
			INFO_PROPERTIES.add(Files.readAllLines(archivo.toPath()).get(i));
		}
		
		archivo = new File("db/listSpecials.txt");
		for (int i = 0; i < Files.readAllLines(archivo.toPath()).size(); i++) {
			INFO_SPECIALS.add(Files.readAllLines(archivo.toPath()).get(i));
		}
	}
	
	public static void clearAllData() {
		
		INFO_PROPERTIES = null;
		INFO_CARDS = null;
		INFO_SPECIALS = null;
		
	}
	
}
