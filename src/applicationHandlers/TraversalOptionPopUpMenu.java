package applicationHandlers;

import application.BTreeMain;
import dataClasses.BinaryTree;
import dataClasses.Person;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.*;

/**
 * Pops open a GUI to allow user to select different traversal views
 * 
 * 
 *
 */
public class TraversalOptionPopUpMenu implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public TraversalOptionPopUpMenu(BTreeMain main) {
		
		this.main = main;
	}


	@Override
	public void handle(ActionEvent event) {
		
		//https://www.geeksforgeeks.org/javafx-alert-with-examples/
		//https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Alert&method=setGraphic
		if (!main.getTreeSelected()) {
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Whoops!");
			alert.setHeaderText("\"You forgot to select a tree so we've chosen one for you!\"");
			alert.setContentText("We've defaulted you to the first name tree");
			alert.setGraphic(new ImageView(new Image("tree.jpeg")));
			
			main.getPeopleTreesManager().setActiveTree(main.getPeopleTreesManager().getFirstNameTree());
			main.setTreeSelected(true);
			
			alert.showAndWait();
			

		}
		
		Stage traversalOptions = new Stage();
		
		//main.setAuxiliaryStage(traversalOptions);
		
		
		VBox controls = new VBox(8);
		controls.setAlignment(Pos.CENTER);
		controls.setPadding(new Insets(30, 30, 30, 30));
		
		int buttonWidth = 200;
		int buttonHeight = 30;
		
		BinaryTree<Person> activeTree = main.getPeopleTreesManager().getActiveTree();
		
		Button depthFirstInOrder = new Button("Depth-First, In-Order");
		depthFirstInOrder.setPrefSize(buttonWidth, buttonHeight);
		depthFirstInOrder.setOnAction(new ChangeTreeAndOrOrdering(main, activeTree, new DepthFirstInOrder() ,"Depth First, In Order (ie. based on the order imposed by the comparator)", traversalOptions));
		
		Button depthFirstPreOrder = new Button("Depth-First, Pre-Order");
		depthFirstPreOrder.setPrefSize(buttonWidth, buttonHeight);
		depthFirstPreOrder.setOnAction(new ChangeTreeAndOrOrdering(main, activeTree, new DepthFirstPreOrder(), "Depth First Pre-Order", traversalOptions));
		
		Button depthFirstPostOrder = new Button("Depth-First, Post-Order");
		depthFirstPostOrder.setPrefSize(buttonWidth, buttonHeight);
		depthFirstPostOrder.setOnAction(new ChangeTreeAndOrOrdering(main, activeTree, new DepthFirstPostOrder(), "Depth First Post-Order", traversalOptions));
		
		Button indentTraversal = new Button("Indentation Relationship");
		indentTraversal.setPrefSize(buttonWidth, buttonHeight);
		indentTraversal.setOnAction(new ChangeTreeAndOrOrdering(main, activeTree, new IndentRelationship(), "Node levels (depths) visualised through indentation", traversalOptions));
		
		Button breadthTraversal = new Button("Breadth");
		breadthTraversal.setPrefSize(buttonWidth, buttonHeight);
		breadthTraversal.setOnAction(new ChangeTreeAndOrOrdering(main, activeTree, new BreadthTraversal(), "Listings are ordered by depth (Root level -> 1 level below root -> etc.)", traversalOptions));
		
		Button stackTraversal = new Button("Stack Traversal");
		stackTraversal.setPrefSize(buttonWidth, buttonHeight);
		stackTraversal.setOnAction(new ChangeTreeAndOrOrdering(main, activeTree, new StackTraversal(), "Honestly I'm not sure what this is, seems to produce the same result as Depth First, Pre-Order Algorithm but with a stack oriented implementation rather than recursion", traversalOptions));
				
		controls.getChildren().addAll(depthFirstInOrder, depthFirstPreOrder, depthFirstPostOrder, indentTraversal, breadthTraversal, stackTraversal);
		

		Scene scene = new Scene(controls, 500, 300);
		traversalOptions.setScene(scene);
		
		traversalOptions.sizeToScene();
		
		//https://stackoverflow.com/questions/55172310/how-to-position-the-window-stage-in-javafx
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = (bounds.getMinX() + (bounds.getWidth() - 500) / 2);
		double y = (bounds.getMinY() + (bounds.getHeight() - 300) / 2);
		traversalOptions.setX(x);
		traversalOptions.setY(y);
				
		traversalOptions.setTitle("Select the arrangement you want to view");
		traversalOptions.show();
		
		//traversalOptions.setOnCloseRequest(new ClosePopUpHandler(main));
		
	}

}
