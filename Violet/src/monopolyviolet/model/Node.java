/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
		int counter = index;
		Node lookingGlass = this;
		while (counter > 0 && lookingGlass != null) {
			lookingGlass = lookingGlass.getLinkR();
			counter = counter - 1;
		}
		return lookingGlass;
	}
	
	public boolean isEmpty() {
		boolean value = true;
		if (this != null) {
			value = false;
		}
		return value;
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

	public Node getLinkR() {
		return linkR;
	}

	public void setLinkR(Node linkR) {
		this.linkR = linkR;
	}

	public Node getLinkL() {
		return linkL;
	}

	public void setLinkL(Node linkL) {
		this.linkL = linkL;
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
	
	private Node getNode(int index) {
		int counter = index;
		Node lookingGlass = this;
		while (counter > 0 && lookingGlass != null) {
			lookingGlass = lookingGlass.getLinkR();
			counter = counter - 1;
		}
		return lookingGlass;
	}
	
	public void remove(int index) {
		Node removedNode = this.getNode(index);
		Node preRemovedNode = this.getNode(index-1);
		
		preRemovedNode.setLinkR(removedNode.getLinkR());
		
		removedNode.disconnect();
	}

	public void insertAfter(Node element) {
		element.disconnect();
		element.linkL = this;
		element.linkR = this.linkR;
		if (this.linkR != null) {
			this.linkR.linkL = element;
		}
		this.linkR = element;
	}

	public void insertBefore(Node element) {
		element.disconnect();
		element.linkR = this;
		element.linkL = this.linkL;
		if (this.linkL != null) {
			this.linkL.linkR = element;
		}
		this.linkL = element;
	}
}
