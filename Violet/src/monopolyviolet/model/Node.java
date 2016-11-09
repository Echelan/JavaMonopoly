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
public class Node<E> {
    
    private Node<E> linkR;
    private Node<E> linkL;
	private E value;
	private int size;

	public Node(E value) {
		this.value = value;
		this.size = 1;
	}

	public Node() {
		this.value = null;
		this.size = 0;
	}
	
	public void set(int index, E element) {
		this.getNode(index).value = element;
	}
	
	public E get(int index) {
		return this.getNode(index).value;
	}
	
	public Node<E> getNode(int index) {
		Node<E> lookingGlass = null;
		int counter = index;
		
		if (counter < this.size()) {
			lookingGlass = this;
			while (counter > 0) {
				lookingGlass = lookingGlass.linkR;
				counter = counter - 1;
			}
		}
		
		return lookingGlass;
	}
	
	public void add(E element) {
		if (this.value == null) {
			this.value = element;
		} else {
			this.lastNode().insertAfter(new Node(element));
		}
		size = size + 1;
	}
	
	public Node<E> next() {
		return this.linkR;
	}
	
	public Node<E> prev() {
		return this.linkR;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public int size() {
		return size;
	}
	
	public Node<E> lastNode() {
		return this.getNode(this.size() - 1);
	}
	
	public E last() {
		return this.lastNode().value;
	}
	
	public void remove(int index) {
		if (index < size) {
			
			for (int i = index; i < this.size-1; i++) {
				this.getNode(i).value = this.getNode(i+1).value;
			}
			
			index = this.size()-1;
			
			Node<E> toRemove = this.getNode(index);
			
			toRemove.value = null;
			
			if (index != 0) {
				this.getNode(index-1).linkR = this.getNode(index).linkR;
			} else {
				if (this.lastNode().linkR != null) {
					this.lastNode().linkR = this.getNode(index).linkR;
				}
			}
			
			if (index+1 < size) {
				this.getNode(index+1).linkL = this.getNode(index).linkL;
			} else {
				if (this.linkL != null) {
					this.linkL = this.getNode(index).linkL;
				}
			}
			
			toRemove.linkL = null;
			toRemove.linkR = null;

			size = size - 1;
		}
	}

	public void setCircular(boolean value) {
		if (value) {
			this.lastNode().linkR = this;
		} else {
			this.lastNode().linkR = null;
		}
	}

	public void setDoubleLink(boolean value) {
		Node<E> lookingGlass = this;
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
	
	public void insertAfter(Node<E> element) {
		element.linkL = null;
		element.linkR = null;
		
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
