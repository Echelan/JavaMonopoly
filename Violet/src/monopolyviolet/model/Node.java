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
package monopolyviolet.model;


/**
 *
 * @author movillaf
 */
public abstract class Node {
    
    private Node linkR;
    private Node linkL;
	
	public Node get(int index) {
		Node lookingGlass = null;
		int counter = index;
		
		if (counter < this.size() && counter >= 0) {
			lookingGlass = this;
			while (counter > 0) {
				lookingGlass = lookingGlass.linkR;
				counter = counter - 1;
			}
		}
		
		return lookingGlass;
	}
	
	public Node add(Node element) {
		Node value;
		
		if (this == null) {
			value = element;
		} else {
			this.last().insertAfter(element);
			value = this;
		}
		
		return value;
	}
	
	public Node next() {
		return this.linkR;
	}
	
	public Node prev() {
		return this.linkR;
	}
	
	public boolean isEmpty() {
		boolean value = false;
		if (this == null) {
			value = true;
		}
		
		return value;
	}
	
	public int size() {
		int counter = 0;
		Node lookingGlass = this;
		
		while (lookingGlass != null) {
			counter = counter + 1;
			lookingGlass = lookingGlass.linkR;
			if (lookingGlass == this) {
				lookingGlass = null;
			}
		}
		
		return counter;
	}
    
	public void disconnect() {
		if (this.linkL != null) {
			this.linkL.linkR = this.linkR;
		}
		if (this.linkR != null) {
			this.linkR.linkL = this.linkL;
		}
		this.linkL = null;
		this.linkR = null;
	}
	
	public Node last() {
		return this.get(this.size() - 1);
	}
	
	public Node remove(int index) {
		Node value;
		
		Node removedNode = this.get(index);
		Node preRemovedNode = null;
		
		if (index != 0) {
			preRemovedNode = this.get(index-1);
		} else {
			preRemovedNode = this.get(this.size() - 1);
		}
		
		if (preRemovedNode != null) {
			if (preRemovedNode.linkR == removedNode) {
				preRemovedNode.linkR = removedNode.linkR;
			}
		}
		
		if (removedNode == this) {
			value = removedNode.linkR;
		} else {
			value = this;
		}
		
		removedNode.disconnect();
		
		return value;
	}

	public void setCircular(boolean value) {
		if (value) {
			this.get(this.size()-1).linkR = this;
		} else {
			this.get(this.size()-1).linkR = null;
		}
	}

	public void setDoubleLink(boolean value) {
		Node lookingGlass = this;
		while (lookingGlass != null) {
			if (value) {
				if (lookingGlass.linkR != null) {
					lookingGlass.linkR.linkL = lookingGlass;
				}
			} else {
				lookingGlass.linkL = null;
			}
			lookingGlass = lookingGlass.linkR;
			if (lookingGlass == this) {
				lookingGlass = null;
			}
		}
	}
	
	public void insertAfter(Node element) {
		element.disconnect();
		if (this.linkL != null) { // if list is circular
			element.linkL = this;
		}
		element.linkR = this.linkR;
		if (this.linkR != null) { // if not at the end of list
			if (this.linkR.linkL != null) { // if list is circular
				this.linkR.linkL = element;
			}
		}
		this.linkR = element;
	}
}
