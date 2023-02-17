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

public class FindByFirstNameUI implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	
	public FindByFirstNameUI(BTreeMain main, Stage stage) {
		
		this.main = main;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		VBox root = new VBox(5);
		root.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(10, 5, 5, 10));
		
		Text prompt1 = new Text("*****THIS WILL SWITCH YOU OVER TO THE FIRST NAME TREE****");
		Text space = new Text("");
		Text noteToMarker = new Text("This is a binary search logic demo, the last name and age search");
		Text noteToMarker2 = new Text("the 'ALL' buttons will demonstrate cases of multiple matches");
		Text space2 = new Text("");
		Text prompt2 = new Text("Type in a FIRST name and BINARY search will find the FIRST Occurance");
		Text prompt3 = new Text("of a person with that FIRST name and the path it took to get there");
		Text prompt4 = new Text("(If you're stuck on name ideas, I've already randomly selected a valid name for you)");
		Text space3 = new Text("");
		
		ArrayList<Person> list = main.getPeopleTreesManager().getActiveTree().getDataList();
		int max = list.size() -1;
		int random = (int) Math.floor(Math.random()*(max - 0 + 1) + 0);

		TextField input = new TextField(list.get(random).getFirstName());
		input.setMaxWidth(250);		
		
		input.setOnAction(new FNameTreeSearchHandler(main, stage));
		
		root.getChildren().addAll(prompt1, space, noteToMarker, noteToMarker2, space2, prompt2, prompt3, prompt4, space3, input);
		Scene interactionScene = new Scene(root, 500, 300);
		stage.setScene(interactionScene);
		
	}

}
