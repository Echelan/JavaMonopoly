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
