package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class DepthFirstInOrder implements TraversalAlgorithm {

	public DepthFirstInOrder() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		
		if (root.getStoredData() != null) {
			
			
			if (root.getBinaryLeftNode() != null) {
				
				traverse(root.getBinaryLeftNode(), list);
			}
			
			list.add(root.getStoredData().toString());
			
			
			if (root.getBinaryRightNode() != null) {
				
				traverse(root.getBinaryRightNode(), list);
			}
		}
	}
	
	
	
	
	


}
