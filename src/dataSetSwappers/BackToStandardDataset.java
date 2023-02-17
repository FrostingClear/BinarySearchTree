package dataSetSwappers;

import application.BTreeMain;
import dataClasses.PeopleTreeManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Reloads the trees with the standard dataset
 * 
 *
 */
public class BackToStandardDataset implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public BackToStandardDataset(BTreeMain main) {
		
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		main.setPeopleTrees(new PeopleTreeManager("standard"));
		main.reinitialiseDisplay();
		main.setUsingTestData(false);
	}

}
