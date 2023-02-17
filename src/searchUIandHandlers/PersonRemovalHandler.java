package searchUIandHandlers;

import java.util.ArrayList;

import application.BTreeMain;
import applicationHandlers.ChangeTreeAndOrOrdering;
import dataClasses.BinaryTree;
import dataClasses.Node;
import dataClasses.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import personTreeAlgorithms.PersonTrees;
import traversalAndSearchAlgorithms.DepthFirstInOrder;

public class PersonRemovalHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	Text feedback;
	Stage stage;

	public PersonRemovalHandler(BTreeMain main, Text feedback, Stage stage) {

		this.main = main;
		this.feedback = feedback;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {

		TextField IDselector = (TextField) event.getSource();
		int id = Integer.parseInt(IDselector.getText());

		String name = null;
		
		for (BinaryTree<Person> tree : main.getPeopleTreesManager().getAllTrees()) {

			Node<Person> NodeToDelete = PersonTrees.getNodeByID(tree, id);

			if (NodeToDelete == null) {

				feedback.setText("That ID is not in the list");
				return;
			}
			
			//Unfortunately the companion array list is going to be a bit of a hindrance
			//I'll need to remove the person from all the lists...
			Person personToDelete = NodeToDelete.getData();
			
			if (name == null) {
				
				name = personToDelete.toString();
			}
			
			main.getPeopleTreesManager().getPeopleListUnsorted().remove(personToDelete);
			
			for (Person person : tree.getDataList()) {
				
				if (person.getUniqueID() == id) {
					
					tree.getDataList().remove(person);
					break;
				}
				
			}
			
			//Ok now we can actually go about removing the person from the actual trees
			PersonTrees.removeFromTree(tree, NodeToDelete);
			
			Node<Person> root = tree.getRoot();
			
			if (root != null) {
				
					tree.assignBalanceHeights(root);
				}
			}
		
			String message = String.format("%s, has been removed from ALL trees", name);
			
			ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, main.getPeopleTreesManager().getActiveTree(), new DepthFirstInOrder(), message);
			
			feedback.setText("");
			
			todo.handle(event);
			
		}
		

	

}
