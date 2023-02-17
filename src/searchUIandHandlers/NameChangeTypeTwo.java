package searchUIandHandlers;

import application.BTreeMain;
import applicationHandlers.ChangeTreeAndOrOrdering;
import dataClasses.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import personTreeAlgorithms.PersonTrees;
import traversalAndSearchAlgorithms.DepthFirstInOrder;

//More Binary Tree oriented approach using binary tree deletion and insertion
//As of this moment incomplete
public class NameChangeTypeTwo implements EventHandler<ActionEvent> {

	BTreeMain main;
	IntegerTextField IDselector;
	TextField fnameEntry;
	TextField lnameEntry;
	Stage stage;
	Text feedback;
	
	public NameChangeTypeTwo(BTreeMain main, IntegerTextField IDselector, TextField fnameEntry, TextField lnameEntry,
			Stage stage, Text feedback) {
		
		this.main = main;
		this.IDselector = IDselector;
		this.fnameEntry = fnameEntry;
		this.lnameEntry = lnameEntry;
		this.stage = stage;
		this.feedback = feedback;
		
	}
		

	@Override
	public void handle(ActionEvent event) {
		
		String fname = fnameEntry.getText();
		fname = fname.replaceAll(" ", "");
		fname = fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length());
		
		String lname = lnameEntry.getText();
		lname = lname.replaceAll(" ", "");
		lname = lname.toUpperCase();
		
		Person personToChange = null;
		String originalFName = null;
		String originalLName = null;
		
		
		int id = Integer.parseInt(IDselector.getText());
		
		for (BinaryTree<Person> tree : main.getPeopleTreesManager().getAllTrees()) {
			
			Node<Person> NodeToChange = PersonTrees.getNodeByID(tree, id);
			
			if (NodeToChange == null) {
				
				feedback.setText("That ID is not in the list");
				return;
			}
			
			feedback.setText("");
			
			personToChange = NodeToChange.getData();
			
			originalFName = personToChange.getFirstName();
			originalLName = personToChange.getLastName();
			
			PersonTrees.removeFromTree(tree, NodeToChange);
			personToChange.setFirstName(fname);
			personToChange.setLastName(lname);
			
			tree.addNode(NodeToChange);
			
		}
		
		String message = String.format("Person ID: %d, has been renamed to %s %s in ALL trees", personToChange.getUniqueID(), fname, lname);
		
		ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, main.getPeopleTreesManager().getActiveTree(), new DepthFirstInOrder(), message);
		
		feedback.setText("");
		
		todo.handle(event);
	}

}

