package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class findInAgeRange {

	
	public static void search(int min, int max, Node<Person> root, ObservableList<String> list){
		
		if (root.getBinaryLeftNode() != null) {
			
			search(min, max, root.getBinaryLeftNode(), list);
			
		}
		
		//**
		if (root.getStoredData().getAge() >= min && root.getStoredData().getAge() <= max) {
			
			list.add(root.getStoredData().toString());
		}
		
		
		if (root.getBinaryRightNode() != null) {
			
			search(min, max, root.getBinaryRightNode(), list);
			
		}
		
	}
}
