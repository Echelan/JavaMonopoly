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
	
	public String get(int index) {
		int counter = index;
		Node lookingGlass = this;
		while (counter > 0 && lookingGlass != null) {
			lookingGlass = lookingGlass.getLinkR();
			counter = counter - 1;
		}
		return ((DataNode) lookingGlass).getValue();
	}
	
	public int size() {
		int counter = 0;
		Node lookingGlass = this;
		while (lookingGlass != null) {
			counter = counter + 1;
			lookingGlass = lookingGlass.getLinkR();
		}
		return counter;
	}

	public String getValue() {
		return value;
	}
	
	
}
