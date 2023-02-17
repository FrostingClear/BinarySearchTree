package applicationHandlers;

import application.BTreeMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class autoBalanceHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public autoBalanceHandler(BTreeMain main) {
		
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		if (!main.getTreeSelected()) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Whoops!");
			alert.setHeaderText("You forgot to select a tree so we've chosen one for you!");
			alert.setContentText("We've defaulted you to the first name tree");
			alert.setGraphic(new ImageView(new Image("tree.jpeg")));


			main.getPeopleTreesManager().setActiveTree(main.getPeopleTreesManager().getFirstNameTree());
			main.setTreeSelected(true);
			
			alert.showAndWait();

		}
		
		

		
		if (main.getPeopleTreesManager().getActiveTree().isAutoBalancingOn()) {
			
			Alert noNeed = new Alert(AlertType.INFORMATION);
			noNeed.setHeaderText("Auto Balancing is already On");
			noNeed.show();
			return;
		}
		
		main.getPeopleTreesManager().activateAutoBalance();

		
		Alert info = new Alert(AlertType.INFORMATION); 
		info.setTitle("AutoBalance ON");
		info.setHeaderText("Auto Balance On! Try adding more people and trees should be fully rebalanced within a few clicks and stay that way");
		info.setContentText("Eclipse Console will provide further insight. This usually selectively reconstructs parts of the tree unless a full rebuild is unavoidable");
		
		info.show();

	}

}
