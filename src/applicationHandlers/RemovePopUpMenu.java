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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import searchUIandHandlers.IntegerTextField;
import searchUIandHandlers.PersonRemovalHandler;
import traversalAndSearchAlgorithms.DepthFirstInOrder;

public class RemovePopUpMenu implements EventHandler<ActionEvent> {

	BTreeMain main;

	public RemovePopUpMenu(BTreeMain main) {

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

		BinaryTree<Person> activeTree = main.getPeopleTreesManager().getActiveTree();
		ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, activeTree, new DepthFirstInOrder(), "Sorted by First Name Priority");
		todo.handle(event);
		
		Stage stage = new Stage();
		
		VBox root = new VBox(8);
		root.setPadding(new Insets(5, 12, 5, 12));
		root.setAlignment(Pos.CENTER);
		
		final Text feedback = new Text("");

		Text prompt0 = new Text("This will delete a person/node and rearrange tree appropriately");
		Text space1 = new Text("");
		
		Text prompt1 = new Text("Select a Person to remove via their ID (Refer to the list on the main window)");
		Text prompt1_1 = new Text("If you like go ahead and try removing everyone!");
		Text prompt1_2 = new Text("Then press the add people button on an empty tree!");
		Text prompt2 = new Text("Press enter/return on keyboard when ready");
		IntegerTextField IDselector = new IntegerTextField(Integer.toString(0), feedback);
		IDselector.setMaxWidth(150);
		IDselector.setMinWidth(150);
		IDselector.setOnAction(new PersonRemovalHandler(main, feedback, stage));
		
		root.getChildren().addAll(prompt0, space1, prompt1, prompt1_1, prompt1_2, prompt2, IDselector, feedback);
		
		Scene scene = new Scene(root, 500, 300);
		stage.setScene(scene);
		
		
		//https://stackoverflow.com/questions/55172310/how-to-position-the-window-stage-in-javafx
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = (bounds.getMinX() + (bounds.getWidth() - 500) / 2);
		double y = (bounds.getMinY() + (bounds.getHeight() - 300) / 2);
		stage.setX(x);
		stage.setY(y);
				
		stage.setTitle("Person Removal");
		stage.show();
	}






}
