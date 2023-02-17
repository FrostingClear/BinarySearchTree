package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class NameLengthSearch implements TraversalAlgorithm {

	private int min;
	
	public NameLengthSearch(int min) {
		
		this.min = min;
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		// TODO Auto-generated method stub
		if (root.getStoredData() != null) {
			
			
			if (root.getBinaryLeftNode() != null) {
				
				traverse(root.getBinaryLeftNode(), list);
			}
			
			if (root.getStoredData().getFirstName().length() > min) {
				
				list.add(root.getStoredData().toString());
			}
			
			
			if (root.getBinaryRightNode() != null) {
				
				traverse(root.getBinaryRightNode(), list);
			}
		}
		
	}

}
