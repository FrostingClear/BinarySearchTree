package dataClasses;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Generic Binary Tree, when given a pre-sorted list of data it will build it in a balanced fashion
 * As of 15th October 10:15pm, it is not capable of auto-balancing.
 * 
 * So starts off perfectly balanced, but will not stay that way if more nodes are added.
 * 
 * 
 *
 * @param <T>
 */
public class BinaryTree<T> {

	String name;
		
	ArrayList<T> dataList; //A "companion" list that supports some methods required for the ACTUAL binary tree
	Comparator<T> relevantComparator;
	Node<T> root;
	
	boolean autoBalancingOn = false;
	
	
	public BinaryTree(String name, ArrayList<T> data, Comparator<T> comparator) {
		
		this.name = name;
		this.dataList = data;
		relevantComparator = comparator;
		
		//MUST SORT BEOFRE BUILDING, otherwise the algorithm won't build a valid binary tree
		sortList();
		buildTree();
	}
	
	/**
	 * Builds up the binary tree. Assuming a pre-sorted array, the binary tree it builds will be balanced
	 */
	public void buildTree() {
		
		/*
		 * Identify the root node and assign it, then assign the left (identify median position of the left half) and 
		 * right node linkages to the root node and its children to build the binary tree using a recursive method
		 */
		
		int start = 0;
		int end = dataList.size();
		
		int rootPos = getMedianPos(dataList);
		Node<T> rootNode = new Node<T>(dataList.get(rootPos));
		root = rootNode;
		
		ArrayList<T> peopleBefore = subArray(dataList, start, rootPos);
		ArrayList<T> peopleAfter = subArray(dataList, rootPos + 1, end);
		
		recursiveBuild(rootNode, peopleBefore, peopleAfter);
		
		this.assignBalanceHeights(root);
		
		return;
	}
	
	/**
	 * "Balances" a tree by rebuilding it.
	 * 
	 * Admittedly not an elegant solution
	 * 
	 */
	public void bruteRebuild() {
		
		/*
		 * Dependent on the "companion" dataList being updated accordingly when nodes are added
		 * to the tree
		 */
		dataList.sort(relevantComparator);
		
		root = null;
		
		int start = 0;
		int end = dataList.size();
		
		//Empty tree situation, nothing to build
		if (dataList.size() == 0) {
			
			return;
		}
		
		int rootPos = getMedianPos(dataList);
		
		
		Node<T> rootNode = new Node<T>(dataList.get(rootPos));
		root = rootNode;
		
		ArrayList<T> peopleBefore = subArray(dataList, start, rootPos);
		ArrayList<T> peopleAfter = subArray(dataList, rootPos + 1, end);
		
		recursiveBuild(rootNode, peopleBefore, peopleAfter);
		
		this.assignBalanceHeights(root);
		
		return;
		
		
	}

	/**
	 * Adds a node to the tree in a valid manner
	 * 
	 * @param newNode
	 */
	public void addNode(Node<T> newNode) {

		//Traverses based on binary tree rules
		

		if (root == null) {
			
			root = newNode;
			return;
		}
		
		int compare = relevantComparator.compare( newNode.getData(), root.getData());

		
		if (compare < 0)  {
			
			recursivePlace("left", root, newNode);
		}
		
		else {
			
			recursivePlace("right", root, newNode);
		}
		
		this.assignBalanceHeights(this.getRoot());
		
		if (autoBalancingOn) {
			
			balanceFrom(newNode);
		}
		
	}
	
	
	/**
	 * This will only reconstruct the portion of the array which are imbalanced.
	 * Though in the worst case scenario it may still have to carry out a full rebuild
	 * If the root itself is skewed.
	 * 
	 * @param node
	 */
	private void balanceFrom(Node<T> node) {
		
		/*
		 * Take only the imbalanced portion of the tree (ie the node and all its descendants)
		 * then do the recursive build method I used to build the original tree but only
		 * on the elements required. To produce a mini balanced tree.
		 * 
		 * Link that back in with the main tree on the relevant side of the parent.
		 * 
		 * If it's the root unfortunately we'll need a full rebuild.
		 * 
		 * From my personal testing the full rebuild is relatively rare.
		 * 
		 */
		
		if (node == null) {
			
			return;
		}
		
		if (node.balanceSkew() > 1) {
			
			System.out.printf("Balance Will Be Carried Out on %s\n", this.getName());
			
			ArrayList<T> miniArray = miniBalanceArray(node);
			
			BinaryTree<T> miniTree = new BinaryTree<T>("mini tree", miniArray, relevantComparator);
			
			if (node.isRoot()) {
				
				this.bruteRebuild();
				System.out.println("Full Rebuild Was necessary");
				return;
			}
			
			
			miniTree.buildTree();
			
			Node<T> parent = node.getParentNode();
			String side = node.whichSideAmIOfParent();
			
			if (side.equals("left")) {
				
				parent.linkageToLeft(miniTree.getRoot());
			}
			else {
				
				parent.linkageToRight(miniTree.getRoot());
			}
			
			this.assignBalanceHeights(this.getRoot());
			
			balanceFrom(node.getParentNode());
		}
		
		else {
			
			balanceFrom(node.getParentNode());
		}
		
		
	}
	
	private ArrayList<T> miniBalanceArray(Node<T> trueRoot) {
		
		ArrayList<T> miniArray = new ArrayList<T>();
		
		populateMiniArray(trueRoot, miniArray);
		
		return miniArray;
	}
	
	private void populateMiniArray(Node<T> root, ArrayList<T> miniArray) {
		
		//Traverse through the node, depth first in order
		if (root.getStoredData() != null) {
			
			if (root.getBinaryLeftNode() != null) {
				
				populateMiniArray(root.getBinaryLeftNode(), miniArray);
			}
			
			miniArray.add(root.getData());
			
			
			if (root.getBinaryRightNode() != null) {
				
				populateMiniArray(root.getBinaryRightNode(), miniArray);
			}
		}
		
		//Since the data came from a binary tree the data shouldn't need sorting.
		
	}
	
	
	
	
	
	
	//Companion to addNode method
	private void recursivePlace(String direction, Node<T> root, Node<T> newNode) {
		
		
		if (direction.equals("left")) {
			
			if (root.getBinaryLeftNode() == null) {
				
				root.linkageToLeft(newNode);
				return;
			}
			else {
				
				Node<T> newRoot = root.getBinaryLeftNode();
				
				int compare = relevantComparator.compare( newNode.getData(), newRoot.getData());

				
				if (compare < 0) {
					
					recursivePlace("left", newRoot, newNode);
				}
				
				else {
					
					recursivePlace("right", newRoot, newNode);
				}
					
			}
		}
		
		
		if (direction.equals("right")) {
			
			if (root.getBinaryRightNode() == null) {
				
				root.linkageToRight(newNode);
				return;
			}
			
			else {
				

				
				Node<T> newRoot = root.getBinaryRightNode();
				
				int compare = relevantComparator.compare( newNode.getData(), newRoot.getData());

				
				if (compare < 0) {
					
					recursivePlace("left", newRoot, newNode);
				}
				
				else {
					
					recursivePlace("right", newRoot, newNode);
				}
				
			}
		}
	}
	
	
	private void sortList() {
		
		Comparator<T> comparator = relevantComparator;
		dataList.sort(comparator);
	}

	
	//Recursive method that works with buildTree
	private void recursiveBuild(Node<T> parentNode, ArrayList<T> peopleBefore, ArrayList<T> peopleAfter) {
		
		
		//Assign the left node
		if (peopleBefore.size() > 0) {
			
			//Identify and assign the appropriate left node linkage
			int leftNodePos = getMedianPos(peopleBefore);
			Node<T> leftNode = new Node<T>(peopleBefore.get(leftNodePos));
			
			parentNode.linkageToLeft(leftNode);
			
			//Recurse to assign the appropriate linkages to this node (ie leftNode)
			ArrayList<T> leftSidePeopleBefore = subArray(peopleBefore, 0, leftNodePos);
			ArrayList<T> leftSidePeopleAfter = subArray(peopleBefore, leftNodePos + 1, peopleBefore.size());
			
			recursiveBuild(leftNode, leftSidePeopleBefore, leftSidePeopleAfter);
		}
		
		//Assign the right node
		if (peopleAfter.size() > 0) {
			
			int rightNodePos = getMedianPos(peopleAfter);
			Node<T> rightNode = new Node<T>(peopleAfter.get(rightNodePos));
			
			parentNode.linkageToRight(rightNode);
			
			ArrayList<T> rightSidepeopleAfter = subArray(peopleAfter, 0, rightNodePos);
			ArrayList<T> rightSidePeopleAfter = subArray(peopleAfter, rightNodePos + 1, peopleAfter.size());
			
			recursiveBuild(rightNode, rightSidepeopleAfter, rightSidePeopleAfter);
		}
		
		
	}
	
	
	//Returns a sub-array of the input array
	private ArrayList<T> subArray(ArrayList<T> array, int start, int end){
		
		ArrayList<T> subArray = new ArrayList<T>();
		
		for (int i = start; i < end; i++) {
			
			subArray.add(array.get(i));
		}
		
		return subArray;
	}
	
	
	
	
	private int getMedianPos(ArrayList<T> array) {

		return ((array.size())) / 2;
	}

	/**
	 * Will assign heights to the root node and anything downstream of it
	 * 
	 * @param root - root should be the actual root of the tree.
	 */
	public void assignBalanceHeights(Node<T> root) {

		//Our true base case, any leaf node by definition has a height of 1
		 if (root.isLeaf()) {
			 
			 root.setBalancingHeight(1);
			 return;
		 }
		
		 //If no comparisons needed, height of this node is the height of the child node + 1
		 if (root.getBinaryLeftNode() != null && root.getBinaryRightNode() == null) {
			 
			 assignBalanceHeights(root.getBinaryLeftNode());
			 root.setBalancingHeight(root.getBinaryLeftNode().getBalancingHeight() + 1);
			 return;
		 }
		 
		 if (root.getBinaryLeftNode() == null && root.getBinaryRightNode() != null) {
			 
			 assignBalanceHeights(root.getBinaryRightNode());
			 root.setBalancingHeight(root.getBinaryRightNode().getBalancingHeight() + 1);
			 return;
		 }
		 
		 //If comparison between the two branches required
		 if (root.getBinaryLeftNode() != null && root.getBinaryRightNode() != null) {
			 
			 assignBalanceHeights(root.getBinaryLeftNode());
			 assignBalanceHeights(root.getBinaryRightNode());
			 
			 if (root.getBinaryLeftNode().getBalancingHeight() >= root.getBinaryRightNode().getBalancingHeight()) {
				 
				 root.setBalancingHeight(root.getBinaryLeftNode().getBalancingHeight() + 1);
			 }
			 else {
				 
				 root.setBalancingHeight(root.getBinaryRightNode().getBalancingHeight() + 1);
			 }
		 }
		 
	}
	

	

	public ArrayList<T> getDataList() {
		return dataList;
	}

	public Comparator<T> getRelevantComparator() {
		return relevantComparator;
	}

	public Node<T> getRoot() {
		return root;
	}

	public String getName() {
		return name;
	}

	public void setRoot(Node<T> root) {
		this.root = root;
	}

	public boolean isAutoBalancingOn() {
		return autoBalancingOn;
	}

	public void setAutoBalancingOn(boolean autoBalancingOn) {
		this.autoBalancingOn = autoBalancingOn;
	}

	
	
}
