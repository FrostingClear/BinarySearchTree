package searchUIandHandlers;

import application.BTreeMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personTreeAlgorithms.PersonTrees;
import dataClasses.*;


public class FNameTreeSearchHandler implements EventHandler<ActionEvent>{

	BTreeMain main;
	Stage stage;
	
	public FNameTreeSearchHandler(BTreeMain main, Stage stage) {
		
		this.main = main;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		TextField textfield = (TextField) event.getSource();
		String toFind = textfield.getText().strip().toLowerCase();
		
		main.getPeopleTreesManager().setActiveTree(main.getPeopleTreesManager().getFirstNameTree());
		
		Node<Person> root = main.getPeopleTreesManager().getActiveTree().getRoot();
		
		main.getList().clear();
		
		String message = "Searching for the first instance of " + textfield.getText() + ":";
		
		String fullMessage = String.format("%s\n\nThe current tree is: %s", message, main.getPeopleTreesManager().getActiveTree().getName());
		
		main.getUserMessages().setText(fullMessage);
		
		//Find the name, updating the list as it moves through the search returning true if found
		if (!PersonTrees.binarySearchPathDisplay(root, root, toFind, main.getList(), 0)) {
			
			main.getList().add(String.format("Sorry nobody in the tree is named %s", toFind));
		}
		
		stage.close();
	}

}
