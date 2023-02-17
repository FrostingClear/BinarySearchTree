package searchUIandHandlers;

import application.BTreeMain;
import applicationHandlers.ChangeTreeAndOrOrdering;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.LastNameLongerSearch;

public class LastNameLongerHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	Stage stage;
	
	public LastNameLongerHandler(BTreeMain main, Stage stage) {
		
		this.main = main;
		this.stage = stage;
	}

	@Override
	public void handle(ActionEvent event) {
		
		//Runs this with the appropriate algorithm
		ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, main.getPeopleTreesManager().getActiveTree(), new LastNameLongerSearch(), "People with LAST names that are Longer than their FIRST names");
		todo.handle(event);
		
		stage.close();
	}

}
