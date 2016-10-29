/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asd;

/**
 *
 * @author movillaf
 */
public class Node {
    
    private Object value;
    private Node linkR;
    private Node linkL;
    
    public Node(Object value) {
        this.value = value;
    }
    
    public Node(Object value, Node linkR) {
        this.value = value;
        this.linkR = linkR;
    }
    
    public Node(Object value, Node linkR, Node linkL) {
        this.value = value;
        this.linkR = linkR;
        this.linkL = linkL;
    }

    public Node getLinkL() {
        return linkL;
    }

    public void setLinkL(Node linkL) {
        this.linkL = linkL;
    }

    public Node getLinkR() {
        return linkR;
    }

    public void setLinkR(Node linkR) {
        this.linkR = linkR;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    
    
}
