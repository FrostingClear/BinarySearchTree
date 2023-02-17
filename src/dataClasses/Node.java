package dataClasses;

import java.util.Comparator;

import javafx.collections.ObservableList;

/*
 * A "Generic" Compatible Node For a Binary Tree
 */
public class Node<T> {

	private Node<T> parentNode;
	private T storedData;
	private Node<T> binaryLeftNode;
	private Node<T> binaryRightNode;
	
	private int balancingHeight;
		
	//New node declaration
	public Node(T thisPerson) {
		
		parentNode = null;
		this.storedData = thisPerson;
		binaryLeftNode = null;
		binaryRightNode = null;
		balancingHeight = 0;
	}
	

	//Assigns child <-> parent relationship
	public void linkageToLeft(Node<T> thisNode) {
		
		binaryLeftNode = thisNode;
		thisNode.setParentNode(this);
	}
	
	public void linkageToRight(Node<T> thisNode) {
		
		binaryRightNode = thisNode;
		thisNode.setParentNode(this);
	}
	
	
	/**
	 * String representation of node, including the data that it contains as well
	 * as it's parent, left and right node if any linkages
	 */
	public String toString() {
		
		String parent = "Nothing";
		if (parentNode != null) {
			
			parent = parentNode.getData().toString();
		}
		
		String thePerson = storedData.toString();
		
		String left = "Nothing";
		if (binaryLeftNode != null) {
			
			left = binaryLeftNode.getData().toString();
		}
		
		String right = "Nothing";
		if (binaryRightNode != null) {
			
			right = binaryRightNode.getData().toString();
		}
		
		return String.format("Parent Node: %s\nPerson: %s\nLeft: %s\nRight: %s\nHeight: %d\n\n", parent, thePerson, left, right, this.balancingHeight);
	}
	
	
	public boolean isBalanced() {
		
		int skew = this.balanceSkew();
		
		//Using a differential of 1 as our definition of skew
		if (skew <= 1) {
			
			return true;
		}
		else {
			
			return false;
		}
	}
	
	public int balanceSkew() {
		
		/*
		 * Recursive method that checks for skews
		 * 
		 * I'm starting to think this could be very inefficient.
		 * 
		 * I should've made skew a field in the node, not enough time to adjust code
		 */
		
		//No children by definition balanced, diff 0
		if (this.getBinaryLeftNode() == null && this.getBinaryRightNode() == null) {
			
			return 0;
		}
		
		//Both nodes occupied
		else if (this.getBinaryLeftNode() != null && this.getBinaryRightNode() != null) {
			
			return Math.abs(this.getBinaryLeftNode().getBalancingHeight() - this.getBinaryRightNode().getBalancingHeight());
			
			
			
		}
		
		//Only left child then would be left child's height - 0 = left child's height 
		else if (this.getBinaryLeftNode() != null && this.getBinaryRightNode() == null) {
			
			return this.getBinaryLeftNode().getBalancingHeight();
		}
		
		else {
			
			return this.getBinaryRightNode().getBalancingHeight();
		}
	}
	
	
	
	
	public boolean isLeaf() {
		
		if (this.binaryLeftNode == null && this.binaryRightNode == null) {
			
			return true;
		}
		
		return false;
	}


	public void incrementBalanceHeight() {
		
		this.balancingHeight++;
	}


	public int numOfLinkedChildren() {
		
		if (this.getBinaryLeftNode() != null && this.getBinaryRightNode() != null) {
			
			return 2;
		}
		
		if (this.getBinaryLeftNode() == null && this.getBinaryRightNode() == null) {
			
			return 0;
		}
		
		else {
			
			return 1;
		}
		
	}
	
	public String whichSideAmIOfParent() {
		
		if (parentNode == null) {
			
			return "iamroot";
		}
		
		if (parentNode.getBinaryLeftNode() == this) {
			
			return "left";
		}
		else {
			
			return "right";
		}
		
	}
	
	public Node<T> findLeftMostNode(Node<T> node){
		
		if (node.getBinaryLeftNode() == null) {
			
			return node;
		}
		
		else {
			
			return findLeftMostNode(node.getBinaryLeftNode());
		}
		
	}
	

	
	
	//Breaks both parent <-> child relationships
	public void unlinkFromParent() {
		
		/*
		 * Figure out which side of the parent node the node
		 * is attached to then unlink the parent 
		 * 
		 * Then unlink yourself from the parent
		 * 
		 */
		
		
		Node<T> parent = parentNode;
		
		
		if (parentNode.getBinaryLeftNode() != null) {
		
			if (this == parentNode.getBinaryLeftNode()) {
				
				parentNode.setBinaryLeftNode(null);
			}
		}
		
		if (parentNode.getBinaryRightNode() != null) {
			
			if (this == parentNode.getBinaryRightNode()) {
			
				parentNode.setBinaryRightNode(null);
			}
		}
		
		this.setParentNode(null);
		
	}
	
	public boolean isRoot() {
		
		if (this.parentNode == null) {
			return true;
		}
		
		return false;
	}

	/**
	 * Deals with cases where the node to be removed from the tree has TWO children
	 * @param relevantTree
	 * @param replacement
	 */
	public void replaceWithToRemove(BinaryTree<T> relevantTree, Node<T> replacement) {
		
		
		Node<T> originalParent = this.parentNode;
		Node<T> directRight = this.getBinaryRightNode();
		Node<T> directLeft = this.getBinaryLeftNode();
		
		String posRelToParent = this.whichSideAmIOfParent();
		
		Node<T> replacementsRightBranch = replacement.getBinaryRightNode();
		
		//If the node to remove is an actively working root node we need to know		
		if (this == relevantTree.getRoot()) {
			
			//Break the relevant linkages to "delete" the root
			directLeft.unlinkFromParent();
			directRight.unlinkFromParent();
			
			//Break the parent linkage from replacement, then slot it in where the root was
			if (replacement != directRight){
				replacement.unlinkFromParent();	
			}
			replacement.linkageToLeft(directLeft);
			
			if (replacement != directRight) {
				replacement.linkageToRight(directRight);
			}
			//The replacement is now the new root
			relevantTree.setRoot(replacement);
			
			
			//If the replacement node had a right branch then add it back into the tree
			//Noting that we have updated the root of the tree!
			if (replacementsRightBranch != null) {
				
				relevantTree.addNode(replacementsRightBranch);
			}
			
			return;
		}
		
		
		//If not trying to remove a root node
		
		//Break all of the node's relevant bonds to "delete" it
		this.unlinkFromParent();
		directLeft.unlinkFromParent();
		directRight.unlinkFromParent();
		
		//Break the replacement's relevant bonds
		
		//If the replacement node was the same as the direct right, there's no point
		//trying to unlink it again
		if (replacement != directRight) {
			
			replacement.unlinkFromParent();
		}
		
		if (replacementsRightBranch != null) {
			
			replacementsRightBranch.unlinkFromParent();
		}
		
		
		//Swap in the replacement by "restoring" the three original bonds
		if (posRelToParent.equals("right")){
			
			originalParent.linkageToRight(replacement);
		}
		else if (posRelToParent.equals("left")) {
			
			originalParent.linkageToLeft(replacement);
		}
		else {
			
			System.out.println("Something went wrong here I didn't deal with the root properly");
		}
		
		
		replacement.linkageToLeft(directLeft);
		
		//Make sure you don't loop the replacement onto itself as a parent
		if (replacement != directRight) {
			
			replacement.linkageToRight(directRight);
		}
		
		//Once again, if we managed to orphan the pre-existing right branch of the replacement node
		if (replacementsRightBranch != null) {
			
			//Since the root was unchanged shouldn't be any issues here
			relevantTree.addNode(replacementsRightBranch);
		}
		
		
	}
	

	
	
	///////setter and getters live down here//////
	

	public T getStoredData() {
		return storedData;
	}

	public T getData() {
		return storedData;
	}


	public Node<T> getBinaryLeftNode() {
		return binaryLeftNode;
	}

	public Node<T> getBinaryRightNode() {
		return binaryRightNode;
	}

	public Node<T> getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node<T> parentNode) {
		this.parentNode = parentNode;
	}

	public int getBalancingHeight() {
		return balancingHeight;
	}

	public void setBalancingHeight(int balancingHeight) {
		this.balancingHeight = balancingHeight;
	}


	public void setBinaryLeftNode(Node<T> binaryLeftNode) {
		this.binaryLeftNode = binaryLeftNode;
	}


	public void setBinaryRightNode(Node<T> binaryRightNode) {
		this.binaryRightNode = binaryRightNode;
	}





	
	
}
