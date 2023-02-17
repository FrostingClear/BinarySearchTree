package applicationHandlers;

import java.util.ArrayList;

import application.BTreeMain;
import dataClasses.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BruteForceRebalanceHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public BruteForceRebalanceHandler(BTreeMain main) {
		
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		PeopleTreeManager manager = main.getPeopleTreesManager();
		
		
		ArrayList<BinaryTree<Person>> allTrees = manager.getAllTrees();
		
		
		
		for (BinaryTree<Person> tree : allTrees) {
			
			tree.bruteRebuild();
		}
		
		BalanceReportHandler todo = new BalanceReportHandler(main);
		
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("All Trees rebuilt in a balanced manner, we'll redirect you to "
				+ "the usual balance report");
		
		alert.setContentText("If I managed to get auto tree balancing working then"
				+ " this button is probably a bit redundant - it absolutely does produce desired functional result though, check my code");
		
		alert.showAndWait();
		
		
		todo.handle(event);
		

	}

}
