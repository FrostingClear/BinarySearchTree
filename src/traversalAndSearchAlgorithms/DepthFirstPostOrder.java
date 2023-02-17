package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class DepthFirstPostOrder implements TraversalAlgorithm {

	public DepthFirstPostOrder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		
		if (root.getStoredData() != null) {
			
			if (root.getBinaryLeftNode() != null) {
				
				traverse(root.getBinaryLeftNode(), list);
			}
			
			
			if (root.getBinaryRightNode() != null) {
				
				traverse(root.getBinaryRightNode(), list);
			}
			
			list.add(root.getStoredData().toString());
		}

	}

}
