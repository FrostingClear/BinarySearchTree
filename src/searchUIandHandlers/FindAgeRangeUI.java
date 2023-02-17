package searchUIandHandlers;

import application.BTreeMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FindAgeRangeUI implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	
	public FindAgeRangeUI(BTreeMain main, Stage stage) {
		
		this.main = main;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		VBox root = new VBox(8);
		root.setAlignment(Pos.TOP_CENTER);
		
		final Text feedback = new Text("");
		
		Text prompt1 = new Text("Select an age range to search");
		Text space1 = new Text("");
		
		Text min = new Text("Enter minimum age");
		final IntegerTextField minInput = new IntegerTextField(Integer.toString(0), feedback);
		Text space2 = new Text("");
		
		Text max = new Text("Enter maximum age");
		final IntegerTextField maxInput = new IntegerTextField(Integer.toString(100), feedback);
		Text space3 = new Text("");
		
		Button search = new Button("Search");
		search.setOnAction(new AgeRangeFinderHandler(main, stage, minInput, maxInput, feedback));
		
		
		
		root.getChildren().addAll(prompt1, space1, min, minInput, space2, max, maxInput, space3, search, feedback);

		
		Scene interactionScene = new Scene(root, 500, 300);
		stage.setScene(interactionScene);
	}



}
