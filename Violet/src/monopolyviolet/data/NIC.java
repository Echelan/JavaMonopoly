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

/**
 *
 * @author Andres
 */
public abstract class NIC {

	//<editor-fold defaultstate="collapsed" desc="Information Lists">
		/**
		 * Main data for Properties.
		 */
		public static DataNode INFO_PROPERTIES;
		/**
		 * Main data for Cards.
		 */
		public static DataNode INFO_CARDS;
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="Static Images">
	//</editor-fold>
		
	public static void loadAllData() {
		
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
			String value = Files.readAllLines(archivo.toPath()).get(i);
			if (INFO_CARDS == null) {
				INFO_CARDS = new DataNode(value);
			} else {
				INFO_CARDS.add(new DataNode(value));
			}
		}
		
		archivo = new File("db/listProperties.txt");
		for (int i = 0; i < Files.readAllLines(archivo.toPath()).size(); i++) {
			String value = Files.readAllLines(archivo.toPath()).get(i);
			if (INFO_PROPERTIES == null) {
				INFO_PROPERTIES = new DataNode(value);
			} else {
				INFO_PROPERTIES.add(new DataNode(value));
			}
		}
	}
	
}
