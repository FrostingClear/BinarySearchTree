package traversalAndSearchAlgorithms;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class IndentRelationship implements TraversalAlgorithm {

	public IndentRelationship() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {

		String indent = "";
		int level = - 1;
		indentTraversal(root, list, indent, level);

	}
	
	private void indentTraversal(Node<Person> root, ObservableList<String> list, String indent, int level) {
		
		if (root.getStoredData() != null) {

			level++;
			
			list.add(indent + String.valueOf(level) + " | " + root.getStoredData().toString() + " |");

			indent+= "                         ";

			if (root.getBinaryLeftNode() != null) {

				indentTraversal(root.getBinaryLeftNode(), list, indent, level);
			}


			if (root.getBinaryRightNode() != null) {

				indentTraversal(root.getBinaryRightNode(), list, indent, level);
			}
		}
		
	}

}
