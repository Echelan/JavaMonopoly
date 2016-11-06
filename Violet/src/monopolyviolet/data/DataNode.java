/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
