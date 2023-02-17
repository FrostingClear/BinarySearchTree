package applicationHandlers;

import application.BTreeMain;
import dataClasses.BinaryTree;
import dataClasses.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import personTreeAlgorithms.PersonTrees;
import traversalAndSearchAlgorithms.BalanceReportAlgorithm;

public class BalanceReportHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public BalanceReportHandler(BTreeMain main) {
		
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		if (!main.getTreeSelected()) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Whoops!");
			alert.setHeaderText("You haven't selected a Tree for us to report on. So we've chosen one for you!");
			alert.setContentText("We've defaulted you onto the first name tree");
			alert.setGraphic(new ImageView(new Image("tree.jpeg")));
			
			alert.showAndWait();
			
			main.getPeopleTreesManager().setActiveTree(main.getPeopleTreesManager().getFirstNameTree());
			main.setTreeSelected(true);
		}
		
		BinaryTree<Person> relevantTree = main.getPeopleTreesManager().getActiveTree();
		
		ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, relevantTree, new BalanceReportAlgorithm(), "Breadth Traversal Report (Nodes considered imbalanced if absolute balance differential is greater than 1)");
		todo.handle(event);
		
		
		Alert overallBalance = new Alert(AlertType.INFORMATION);
		overallBalance.setTitle("Overall Result");
		
		String result = "At least one node in the tree is imbalanced, hence the tree is not balanced";
		
		boolean balanceStatus = PersonTrees.wholeTreeBalanced(relevantTree);
		
		if (balanceStatus) {
			
			result = "The entire tree is balanced (or this is an empty tree)";
		}
		
		overallBalance.setHeaderText(result);
		overallBalance.setContentText("The data on individual nodes is listed in the main window");
		
		overallBalance.show();
		
	}

}
