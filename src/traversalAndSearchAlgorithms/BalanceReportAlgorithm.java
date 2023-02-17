package traversalAndSearchAlgorithms;

import java.util.ArrayDeque;
import java.util.Queue;

import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;
import personTreeAlgorithms.PersonTrees;

public class BalanceReportAlgorithm implements TraversalAlgorithm {

	@Override
	public void traverse(Node<Person> root, ObservableList<String> list) {

		Queue<Node<Person>> queue = new ArrayDeque<Node<Person>>();
		queue.offer(root);
		
		while(!queue.isEmpty()) {
			
			Node<Person> qNode = queue.poll();			
			
			list.add(PersonTrees.PersonBalanceString(qNode));
			
			if (qNode.getBinaryLeftNode() != null) {
				
				queue.offer(qNode.getBinaryLeftNode());
			}
			
			if (qNode.getBinaryRightNode() != null) {
				
				queue.offer(qNode.getBinaryRightNode());
			}
			
		}
		
		
	}

}
