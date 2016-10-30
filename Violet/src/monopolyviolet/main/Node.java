/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monopolyviolet.main;

/**
 *
 * @author movillaf
 */
public abstract class Node {
    
    private Node linkR;
    private Node linkL;

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
    
	
}
