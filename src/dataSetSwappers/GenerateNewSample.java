package dataSetSwappers;

import application.BTreeMain;
import dataClasses.PeopleTreeManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import searchUIandHandlers.NamesLongerAndGeneratorHandler;

//https://stackoverflow.com/questions/55172310/how-to-position-the-window-stage-in-javafx

/**
 * Provides the interface and logic for allowing a user to create a new random cohort of people for
 * the trees
 * 
 *
 */
public class GenerateNewSample implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public GenerateNewSample(BTreeMain main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		Stage inputStage = new Stage();
		inputStage.setTitle("Generate New Sample");
		
		VBox box = new VBox(15);
		box.setAlignment(Pos.CENTER);
		
		final Text prompt = new Text("Select a sample size");
		final Text prompt2 = new Text("Press enter/return on keyboard when ready");
		final Text feedback = new Text("");
		
		
		TextField input = new TextField("50");
		input.setMaxWidth(200);
		
		input.setOnAction(new NamesLongerAndGeneratorHandler("generate", main, inputStage, input, feedback, 5000));
		
		box.getChildren().addAll(prompt, prompt2, input, feedback);
		
		Scene root = new Scene(box, 500, 300);
		
		
		inputStage.setScene(root);
		inputStage.sizeToScene();
		
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = (bounds.getMinX() + (bounds.getWidth() - 500) / 2);
		double y = (bounds.getMinY() + (bounds.getHeight() - 300) / 2);
		inputStage.setX(x);
		inputStage.setY(y);
		
		inputStage.show();
		
		
		
	}

}
