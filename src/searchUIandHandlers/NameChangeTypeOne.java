package searchUIandHandlers;

import java.util.ArrayList;

import application.BTreeMain;
import applicationHandlers.ChangeTreeAndOrOrdering;
import dataClasses.BinaryTree;
import dataClasses.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.DepthFirstInOrder;

//Crude implementation involving rebuilding the entire binary tree
//With any luck I'd love to do one with binary tree removal and re-insertion
public class NameChangeTypeOne implements EventHandler<ActionEvent> {

	BTreeMain main;
	IntegerTextField IDselector;
	TextField fnameEntry;
	TextField lnameEntry;
	Stage stage;
	Text feedback;
	
	public NameChangeTypeOne(BTreeMain main, IntegerTextField IDselector, TextField fnameEntry, TextField lnameEntry,
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
		fname = fname.substring(0 , 1).toUpperCase() + fname.substring(1, fname.length());
		
		String lname = lnameEntry.getText();
		lname = lname.replaceAll(" ", "");
		lname = lname.toUpperCase();
		
		int id = Integer.parseInt(IDselector.getText());
		
		
		Person personToChange = null;
		String originalFName = null;
		String originalLName = null;
		
		for (BinaryTree<Person> tree : main.getPeopleTreesManager().getAllTrees()) {
			
			ArrayList<Person> personsList = tree.getDataList();
			
			for (Person person : personsList) {
				
				if (person.getUniqueID() == id) {
					
					personToChange = person;
				}
			}
			
			if (personToChange == null) {
				
				feedback.setText("Sorry that ID isn't in the list try again");
				return;
			}
			
			else {
				
				feedback.setText("");
				originalFName = personToChange.getFirstName();
				originalLName = personToChange.getLastName();
				
				personToChange.setFirstName(fname);
				personToChange.setLastName(lname);
				tree.bruteRebuild();
			}
		}
		
		String message = String.format("Person ID: %d, has been renamed to %s %s in ALL trees, and all trees have rebuilt to be balanced", personToChange.getUniqueID(), fname, lname);
		
		ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, main.getPeopleTreesManager().getActiveTree(), new DepthFirstInOrder(), message);
		
		feedback.setText("");
		
		todo.handle(event);

		
	}

}
