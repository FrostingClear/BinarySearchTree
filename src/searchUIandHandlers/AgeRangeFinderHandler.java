package searchUIandHandlers;

import application.BTreeMain;
import dataClasses.Node;
import dataClasses.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.findInAgeRange;

/**
 * Handles the logic for the age in range finder, with some protections against user error
 * 
 *
 */
public class AgeRangeFinderHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	IntegerTextField minInput;
	IntegerTextField maxInput;
	Text feedback;
	
	public AgeRangeFinderHandler(BTreeMain main, Stage stage, IntegerTextField minInput, IntegerTextField maxInput, Text feedback) {
		
		this.main = main;
		this.stage = stage;
		this.feedback = feedback;
		this.minInput = minInput;
		this.maxInput = maxInput;
		
	}

	@Override
	public void handle(ActionEvent event) {
		
		int min = Integer.parseInt(minInput.getText());
		int max = Integer.parseInt(maxInput.getText());
		
		if (min > max) {
			
			feedback.setText("Minimum Age Cannot Be Higher Than Maximum, Try Again");
			return;
		}
		
		
		Node<Person> root = main.getPeopleTreesManager().getActiveTree().getRoot();

		main.getList().clear();

		String message = "Searching for ALL people aged between " + min + " and " + max + " years old:"; 
		String fullMessage = String.format("%s\n\nThe current tree is: %s", message, main.getPeopleTreesManager().getActiveTree().getName());		
		main.getUserMessages().setText(fullMessage);

		
		findInAgeRange.search(min, max, root, main.getList());

		stage.close();
	}

}
