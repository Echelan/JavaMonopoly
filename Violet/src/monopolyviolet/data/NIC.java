 /*
 *  Pokemon Violet - A University Project by Andres Movilla
 *  Pokemon COPYRIGHT 2002-2016 Pokemon.
 *  COPYRIGHT 1995-2016 Nintendo/Creatures Inc./GAME FREAK inc. TRADEMARK, REGISTERED TRADEMARK
 *  and Pokemon character names are trademarks of Nintendo.
 *  No copyright or trademark infringement is intended in using Pokemon content on Pokemon Violet.
 */
package monopolyviolet.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

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
//				INFO_CARDS.setLinkR(INFO_CARDS);
			} else {
				INFO_CARDS.insertAfter(new DataNode(value));
			}
		}
		
		archivo = new File("db/listProperties.txt");
		for (int i = 0; i < Files.readAllLines(archivo.toPath()).size(); i++) {
			String value = Files.readAllLines(archivo.toPath()).get(i);
			if (INFO_PROPERTIES == null) {
				INFO_PROPERTIES = new DataNode(value);
//				INFO_PROPERTIES.setLinkR(INFO_PROPERTIES);
			} else {
				INFO_PROPERTIES.insertAfter(new DataNode(value));
			}
		}
	}
	
}
