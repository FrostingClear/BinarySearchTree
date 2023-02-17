package searchUIandHandlers;

import java.util.ArrayList;

import application.BTreeMain;
import dataClasses.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * UI Interface for finding all instances of last/first name.
 * 
 * Provides a random but valid name for the user so they don't think of one themselves.
 * 
 * 
 *
 */
public class FindAlInstancesUI implements EventHandler<ActionEvent>{

	BTreeMain main;
	String key;
	Stage stage;
	
	public FindAlInstancesUI(BTreeMain main, String key, Stage stage) {
		// TODO Auto-generated constructor stub
		this.main = main;
		this.key = key;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		VBox root = new VBox(5);
		root.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(10, 5, 5, 10));
		
		if (key.equals("lname")) {
			
			Text prompt1 = new Text("Type in a last name");
			Text prompt2 = new Text("This will display all occurances of that last name");
			Text space = new Text("");
			Text prompt3 = new Text("If you're stuck on ideas I've randomly selected a valid last name for you");
			Text prompt4 = new Text("If you happen to be using the test list, then 'K' is my suggested demo case");
			Text space2 = new Text("");
			
			//Grab a random last name from the list as a suggestion for the user
			ArrayList<Person> list = main.getPeopleTreesManager().getActiveTree().getDataList();
			int max = list.size() -1;
			int random = (int) Math.floor(Math.random()*(max - 0 + 1) + 0);
			
			TextField input = new TextField(list.get(random).getLastName());
			input.setMaxWidth(250);		
			
			input.setOnAction(new FindAllInstancesHandler(main, stage, "lname"));
			
			root.getChildren().addAll(prompt1, prompt2, space, prompt3, prompt4, space2, input);
			Scene interactionScene = new Scene(root, 500, 300);
			stage.setScene(interactionScene);
		}
		
		if (key.equals("fname")) {
			
			Text prompt1 = new Text("Type in a first name");
			Text prompt2 = new Text("This will display all occurances of that first name");
			Text space = new Text("");
			Text prompt3 = new Text("If you're stuck on ideas I've randomly selected a valid first name for you");
			Text prompt4 = new Text("If you happen to be using the test list, then 'D' is my suggested demo case");
			Text space2 = new Text("");
			
			ArrayList<Person> list = main.getPeopleTreesManager().getActiveTree().getDataList();
			int max = list.size() -1;
			int random = (int) Math.floor(Math.random()*(max - 0 + 1) + 0);
			
			TextField input = new TextField(list.get(random).getFirstName());
			input.setMaxWidth(250);		
			
			input.setOnAction(new FindAllInstancesHandler(main, stage, "fname"));
			
			root.getChildren().addAll(prompt1, prompt2, space, prompt3, prompt4, space2, input);
			Scene interactionScene = new Scene(root, 500, 300);
			stage.setScene(interactionScene);
			
		}
		
	}
	
	

}
