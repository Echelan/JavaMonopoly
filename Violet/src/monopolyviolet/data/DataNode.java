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

import monopolyviolet.model.Node;

/**
 *
 * @author Andres
 */
public class DataNode extends Node{
	
	private String value;

	public DataNode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	
}
