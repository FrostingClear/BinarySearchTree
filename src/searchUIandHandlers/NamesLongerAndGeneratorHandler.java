package searchUIandHandlers;

import application.BTreeMain;
import applicationHandlers.ChangeTreeAndOrOrdering;
import dataClasses.PeopleTreeManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.NameLengthSearch;

/**
 * Dual purpose handler that can impose a max limit on an integer entered into a textfield
 * Used in conjunction with the find names longer and generate new sample methods.
 * 
 *
 */
public class NamesLongerAndGeneratorHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	TextField input;
	Text feedback;
	int max;
	String type;
	
	public NamesLongerAndGeneratorHandler(String type, BTreeMain main, Stage stage, TextField input, Text feedback, int max) {
	
		this.main = main;
		this.stage = stage;
		this.input = input;
		this.feedback = feedback;
		this.max = max;
		this.type = type;
	}

	@Override
	public void handle(ActionEvent event) {
		
		try {
			
			int num = Integer.parseInt(input.getText());
			
			if (num < 1) {
				
				input.clear();
				feedback.setText("Needs to be larger than 0, try again");
			}
			else if (num > max) {
				
				input.clear();
				feedback.setText("The limit is " + max + " try again");
			}
			else {
			
				if (type.equals("generate")) {
					feedback.setText("");
					main.setPeopleTrees(new PeopleTreeManager(num));
					main.reinitialiseDisplay();
					stage.close();
				}
				
				else if (type.equals("longerThan")){
					
					TextField textfield = (TextField) event.getSource();
					int parameter = Integer.parseInt(textfield.getText());
					
					feedback.setText("");
					ChangeTreeAndOrOrdering nextStep = new ChangeTreeAndOrOrdering(main, main.getPeopleTreesManager().getActiveTree(), new NameLengthSearch(parameter), "FIRST Names Greater Than " + parameter + " Characters Long");
					nextStep.handle(event);
					stage.close();
				}
			}
		}
		catch(NumberFormatException e) {
			
			input.clear();
			feedback.setText("Needs to be an integer, try again");
			return;
		}
		
	}

}
