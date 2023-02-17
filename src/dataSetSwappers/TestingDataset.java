package dataSetSwappers;

import application.BTreeMain;
import dataClasses.PeopleTreeManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * Loads the testing dataset
 * 
 * 
 *
 */
public class TestingDataset implements EventHandler<ActionEvent>{

	BTreeMain main;

	
	public TestingDataset(BTreeMain main) {
		// TODO Auto-generated constructor stub
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		if (!main.getUsingTestData()) {
			
			main.setPeopleTrees(new PeopleTreeManager("test"));
			main.reinitialiseDisplay();
			main.setUsingTestData(true);
		}
		else {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setContentText("Already using the test dataset");
			alert.setHeaderText("No need for that");
			alert.show();
		}
		
	}

}
