package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class DepthFirstPreOrder implements TraversalAlgorithm {

	public DepthFirstPreOrder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		
		/*
		 * Print yourself
		 * Print everything before yourself
		 * Print everything after yourself
		 */
				
		if (root.getStoredData() != null) {
			
			list.add(root.getStoredData().toString());

			
			if (root.getBinaryLeftNode() != null) {
				
				traverse(root.getBinaryLeftNode(), list);
			}
			
			
			if (root.getBinaryRightNode() != null) {
				
				traverse(root.getBinaryRightNode(), list);
			}
		}

	}

}
