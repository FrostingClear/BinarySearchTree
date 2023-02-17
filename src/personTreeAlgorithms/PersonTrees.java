package personTreeAlgorithms;

import application.BTreeMain;
import dataClasses.BinaryTree;
import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

/**
 * A collection of relevant methods for this program.
 * 
 * Since the nodes themselves are generic, I needed external methods
 * to better work with Person nodes and/or Trees
 * 
 * 
 *
 */
public class PersonTrees<T> {

	/**
	 * Tells whether the given tree is entirely balanced
	 * 
	 * @param activeTree
	 * @return
	 */
	public static boolean wholeTreeBalanced(BinaryTree<Person> activeTree) {

		Node<Person> root = activeTree.getRoot();
		
		boolean result = true;
		
		if (root != null) {

			result =  recursiveBalanceSearch(root);
		}
		
		return result;
	}


	private static boolean recursiveBalanceSearch(Node<Person> root) {

		
		int skew = root.balanceSkew();
		
		if (skew > 1) {

			return false;
		}

		//If we haven't yet proved that the tree is NOT balanced then we'll keep looking
		else if (root.getBinaryLeftNode() != null) {

			return recursiveBalanceSearch(root.getBinaryLeftNode());
		}

		else if (root.getBinaryRightNode() != null) {

			return recursiveBalanceSearch(root.getBinaryRightNode());
		}	
		
		else {
			
			return true;
		}
	}

	/**
	 * Update list based on with ALL last names or first names.
	 * 
	 * @param key
	 * @param root
	 * @param toFind
	 * @param list
	 */
	public static void allInstanceNameSeach(String key, Node<Person> root, String toFind, ObservableList<String> list) {

		if (key.equals("lname")) {
			toFind = toFind.toUpperCase();

			if (root.getBinaryLeftNode() != null) {

				allInstanceNameSeach(key, root.getBinaryLeftNode(), toFind, list);
			}

			//**
			if (root.getStoredData().getLastName().toUpperCase().equals(toFind)) {

				list.add(root.getStoredData().toString());
			}


			if (root.getBinaryRightNode() != null) {

				allInstanceNameSeach(key, root.getBinaryRightNode(), toFind, list);
			}
		}

		else if (key.equals("fname")) {

			toFind = toFind.toLowerCase();


			if (root.getBinaryLeftNode() != null) {

				allInstanceNameSeach(key, root.getBinaryLeftNode(), toFind, list);
			}

			//**
			if (root.getStoredData().getFirstName().toLowerCase().equals(toFind)) {

				list.add(root.getStoredData().toString());
			}


			if (root.getBinaryRightNode() != null) {

				allInstanceNameSeach(key, root.getBinaryRightNode(), toFind, list);
			}


		}

	}



	/**
	 * String format to describe a node's balance state
	 * 
	 * @param node
	 * @return
	 */
	public static String PersonBalanceString(Node<Person> node) {

		Person person = node.getStoredData();

		String balance = "is BALANCED";

		if (!node.isBalanced()) {

			balance = "is NOT balanced";

		}

		return String.format("%s's (ID: %d) node %s   -   balance differential:  %d", person.getFullName()
				, person.getUniqueID(), balance, node.balanceSkew());

	}


	/**
	 * Updates the list with the path of nodes/people that it crosses during a binary search
	 * 
	 * @param root
	 * @param trueroot
	 * @param toFind
	 * @param list
	 * @param step
	 * @return
	 */
	public static boolean binarySearchPathDisplay(Node<Person> root, Node<Person> trueroot, String toFind, ObservableList<String> list, int step) {

		step++;


		String rootFirstName = root.getStoredData().getFirstName().toLowerCase();

		list.add(String.format("Step %d: %s", step, root.getStoredData().toString()));


		if (rootFirstName.equals(toFind)) {

			if (step == 1) {

				list.add(String.format("Our first instance of %s was found at the root", root.getStoredData().getFirstName()));

				return true;
			}

			list.add(String.format("^ The first %s was found after %d step(s)", root.getStoredData().getFirstName(), step));
			return true;
		}

		else if (toFind.compareTo(rootFirstName) < 0 && root.getBinaryLeftNode() != null) {

			return binarySearchPathDisplay(root.getBinaryLeftNode(), trueroot, toFind, list, step);
		}

		else if (root.getBinaryRightNode() != null) {

			return binarySearchPathDisplay(root.getBinaryRightNode(), trueroot, toFind, list, step);
		}


		return false;
	}

	/**
	 * Provides a suitable format for representing a node in the balance report
	 * @return
	 */
	public static String personNodeAddedString(Node<Person> node) {
		
		Node<Person> parentNode = node.getParentNode();
		
		boolean right = true;
		
		
		if (parentNode == null) {
			
			return String.format("%s has been added as the root of the tree", node.getStoredData().toString());
		}
		
		
		if (parentNode.getBinaryLeftNode() == node) {
			right = false;
		}
		
		String direction = "set as the right node of";
		if (!right) {
			direction = "set as the left node of";
		}
		
		
		
		return String.format("%s has been %s %s\n", node.getStoredData().toString(), direction, parentNode.getStoredData().toString());
		
		
	}
	
	static Node<Person> foundByIDSearch = null;
	
	/**
	 * Searches the entire tree for the Person with the stated ID.
	 * 
	 * 
	 * @param tree
	 * @param id - Must be unique to the person, must not be duplicate ID's in the tree
	 * @return
	 */
	public static Node<Person> getNodeByID(BinaryTree<Person> tree, int id) {
		
		Node<Person> root = tree.getRoot();
		
		foundByIDSearch = null;
		
		recursiveNodeByID(root, id);
		
		return foundByIDSearch;
		
	}


	private static void recursiveNodeByID(Node<Person> root, int id) {
		
		if (root.getStoredData().getUniqueID() == id) {
			
			foundByIDSearch = root;
			return;
		}
		
		if (root.getBinaryLeftNode() != null) {
			
			recursiveNodeByID(root.getBinaryLeftNode(), id);
		}
		
		if (root.getBinaryRightNode() != null) {
			
			recursiveNodeByID(root.getBinaryRightNode(), id);
		}
		
	}
	
	
	//Binary Search Trees (BSTs) - Insert and Remove Explained - by Colleen Lewis 
	//https://youtu.be/wcIRPqTR3Kc 
	//I followed along with the explanation from this video
	public static void removeFromTree(BinaryTree<Person> relevantTree, Node<Person> node) {
		
		
		
		//Easiest Case is if the node is a leaf node, just unlink it from parent
		if (node.isLeaf()) {
			
			//A node that is both leaf and root must be only node in the tree
			if (node.isRoot()) {
				
				relevantTree.setRoot(null);
				return;
			}
			
			node.unlinkFromParent();
			return;
		}
		
		
		//Second case is if node to remove has 1 child only
		if (node.numOfLinkedChildren() == 1) {
			

						
			//note down parent and child
			Node<Person> parent = node.getParentNode();
			Node<Person> child = null;
			
			//figure out which one is the child
			if (node.getBinaryLeftNode() == null) {
				
				child = node.getBinaryRightNode();
			}
			else {
				
				child = node.getBinaryLeftNode();
			}
			
			//Figure out which side you're on relative to your parent
			String side = node.whichSideAmIOfParent();
			
			
			
			//There's still the possibility of a two node tree, with one being the root
			//Meaning there wont be a parent to linke to afterwards
			if (node == relevantTree.getRoot()) {
				
				child.unlinkFromParent();
				relevantTree.setRoot(child);
				return;
			}
			
			
			
			child.unlinkFromParent();
			node.unlinkFromParent();
			
			//Link the child in accordingly to the original parent
			if (side == "left") {
				
				parent.linkageToLeft(child);
				return;
				
			}
			
			else if (side == "right") {
				
				parent.linkageToRight(child);
				return;
			}
			
		}
		
		///Third case, node has two children
		if (node.numOfLinkedChildren() == 2) {
			
			Node<Person> toTheRight = node.getBinaryRightNode();
			Node<Person> nextBiggest = toTheRight.findLeftMostNode(toTheRight);
			
			node.replaceWithToRemove(relevantTree, nextBiggest);
			
		}
		
		
	}
	

}
