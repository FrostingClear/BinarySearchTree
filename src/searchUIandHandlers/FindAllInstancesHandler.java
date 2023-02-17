package searchUIandHandlers;

import application.BTreeMain;
import dataClasses.Node;
import dataClasses.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personTreeAlgorithms.PersonTrees;
import traversalAndSearchAlgorithms.*;

public class FindAllInstancesHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	String key;
	
	public FindAllInstancesHandler(BTreeMain main, Stage stage, String key) {
		
		this.main = main;
		this.stage = stage;
		this.key = key;
	}

	@Override
	public void handle(ActionEvent event) {
		
		if (key.equals("lname")) {
			
			TextField textfield = (TextField) event.getSource();
			String toFind = textfield.getText().strip().toUpperCase();
			
			Node<Person> root = main.getPeopleTreesManager().getActiveTree().getRoot();

			main.getList().clear();

			String message = "Searching for ALL instances of the last name, " + textfield.getText() + ":"; 
			
			String fullMessage = String.format("%s\n\nThe current tree is: %s", message, main.getPeopleTreesManager().getActiveTree().getName());

			main.getUserMessages().setText(fullMessage);

			PersonTrees.allInstanceNameSeach("lname", root, toFind, main.getList());
			
			stage.close();
		}
		
		else if (key.equals("fname")) {
			
			TextField textfield = (TextField) event.getSource();
			String toFind = textfield.getText().strip().toLowerCase();
			
			Node<Person> root = main.getPeopleTreesManager().getActiveTree().getRoot();

			main.getList().clear();

			String message = "Searching for ALL instances of the first name, " + textfield.getText() + ":"; 
			
			String fullMessage = String.format("%s\n\nThe current tree is: %s", message, main.getPeopleTreesManager().getActiveTree().getName());

			main.getUserMessages().setText(fullMessage);

			PersonTrees.allInstanceNameSeach("fname", root, toFind, main.getList());   
			
				
			stage.close();
		}
		

	}

}
