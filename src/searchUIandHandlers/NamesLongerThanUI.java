package searchUIandHandlers;

import application.BTreeMain;
import dataClasses.Node;
import dataClasses.Person;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.TraversalAlgorithm;

public class NamesLongerThanUI implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	
	public NamesLongerThanUI(BTreeMain main, Stage stage) {
		
		this.main = main;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		VBox root = new VBox(8);
		root.setAlignment(Pos.CENTER);
		
		Text prompt1 = new Text("Find people with FIRST names longer than __ characters long");
		Text prompt2 = new Text("Enter an integer and press enter/return on keyboard");
		TextField input = new TextField("7");
		input.setMaxWidth(200);		
		Text feedback = new Text("");
		
		//User can enter a max char length of 20
		input.setOnAction(new NamesLongerAndGeneratorHandler("longerThan", main, stage, input, feedback, 20));
		
		root.getChildren().addAll(prompt1, prompt2, input, feedback);
		Scene interactionScene = new Scene(root, 500, 300);
		stage.setScene(interactionScene);
		
		
		
	}

	
	
	

}
