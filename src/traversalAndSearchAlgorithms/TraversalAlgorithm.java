package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public interface TraversalAlgorithm {

	void traverse(Node<Person> root, ObservableList<String> list);
	
	
}
