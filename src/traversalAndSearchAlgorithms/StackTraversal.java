package traversalAndSearchAlgorithms;

import java.util.ArrayDeque;
import java.util.Queue;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class StackTraversal implements TraversalAlgorithm {

	public StackTraversal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		
		ArrayDeque<Node<Person>> stack = new ArrayDeque<Node<Person>>();
		
		stack.push(root);
		
		while(!stack.isEmpty()) {
			
			Node<Person> sPerson = stack.pop();
			list.add(sPerson.getStoredData().toString());
			
			if (sPerson.getBinaryRightNode() != null) {
				
				stack.push(sPerson.getBinaryRightNode());
			}
			
			if (sPerson.getBinaryLeftNode() !=  null) {
				
				stack.push(sPerson.getBinaryLeftNode());
			}
			
		}

	}

}
