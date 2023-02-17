package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class LastNameLongerSearch implements TraversalAlgorithm {

	public LastNameLongerSearch() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		
		if (root.getBinaryLeftNode() != null) {
			
			traverse(root.getBinaryLeftNode(), list);
		}
		
		if (root.getStoredData().getFirstName().length() < root.getStoredData().getLastName().length()) {
			
			list.add(root.getStoredData().toString());
		}
		
		
		if (root.getBinaryRightNode() != null) {
			
			traverse(root.getBinaryRightNode(), list);
		}
	}
		
	

}
