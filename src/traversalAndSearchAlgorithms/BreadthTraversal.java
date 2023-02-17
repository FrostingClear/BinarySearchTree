package traversalAndSearchAlgorithms;

import java.util.ArrayDeque;
import java.util.Queue;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;

public class BreadthTraversal implements TraversalAlgorithm {

	public BreadthTraversal() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {
		
		
		Queue<Node<Person>> queue = new ArrayDeque<Node<Person>>();
		queue.offer(root);
		
		while(!queue.isEmpty()) {
			
			Node<Person> qNode = queue.poll();
			list.add(qNode.getStoredData().toString());
			
			if (qNode.getBinaryLeftNode() != null) {
				
				queue.offer(qNode.getBinaryLeftNode());
			}
			
			if (qNode.getBinaryRightNode() != null) {
				
				queue.offer(qNode.getBinaryRightNode());
			}
			
		}
		
	}
	
	

}
