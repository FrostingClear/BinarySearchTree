package applicationHandlers;


import application.BTreeMain;
import dataClasses.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import searchUIandHandlers.IntegerTextField;
import searchUIandHandlers.NameChangeTypeOne;
import traversalAndSearchAlgorithms.DepthFirstInOrder;

/**
 * GUI interface for allowing user to rename a person based on their ID
 * 
 *
 */
public class RenameTypeOnePopUpMenu implements EventHandler<ActionEvent> {

	
	BTreeMain main;
	
	public RenameTypeOnePopUpMenu(BTreeMain main) {
		
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
		
		ChangeTreeAndOrOrdering todo = new ChangeTreeAndOrOrdering(main, activeTree, new DepthFirstInOrder(), "Sorted by relevant order of the active tree");
		todo.handle(event);
		
		
		Stage stage = new Stage();
		
		VBox root = new VBox(2);
		root.setPadding(new Insets(5, 12, 5, 12));
		root.setAlignment(Pos.TOP_CENTER);
		
		final Text feedback = new Text("");
		
		Text prompt0 = new Text("Implementation approach #1, I'll do a second type, time-permitting");
		Text prompt0_1 = new Text("This is a slightly hacky approach in that it makes use of an");
		Text prompt0_2 = new Text("underlying arraylist to rebuild the tree after changing the name");
		Text prompt0_3 = new Text("BUT it does leave you with a balanced tree afterwards");
		
		Text prompt1 = new Text("Select a Person to rename via their ID (Refer to the list on the main window)");
		IntegerTextField IDselector = new IntegerTextField(Integer.toString(0), feedback);
		IDselector.setMaxWidth(150);
		IDselector.setMinWidth(150);
		
		Text space1 = new Text("");
		
		Text prompt2 = new Text("Enter a new first name");
		TextField fnameEntry = new TextField("Adam");
		fnameEntry.setMaxWidth(150);
		fnameEntry.setMaxWidth(150);
		
		Text space2 = new Text("");
		
		Text prompt3 = new Text("Enter a new last name");
		TextField lnameEntry = new TextField("Apple");
		lnameEntry.setMaxWidth(150);
		lnameEntry.setMinWidth(150);
		
		Button submit = new Button("Submit");
		submit.setPrefSize(100, 30);
		submit.setOnAction(todo);
		submit.setOnAction(new NameChangeTypeOne(main, IDselector, fnameEntry, lnameEntry, stage, feedback));
		
		
		
		root.getChildren().addAll(prompt0, prompt0_1, prompt0_2, prompt0_3, new Text(""), prompt1, IDselector, space1, prompt2, fnameEntry, space2, prompt3, lnameEntry, submit, feedback);
		
		Scene scene = new Scene(root, 500, 350);
		stage.setScene(scene);
		
		
		//https://stackoverflow.com/questions/55172310/how-to-position-the-window-stage-in-javafx
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = (bounds.getMinX() + (bounds.getWidth() - 500) / 2);
		double y = (bounds.getMinY() + (bounds.getHeight() - 350) / 2);
		stage.setX(x);
		stage.setY(y);
				
		stage.setTitle("Rename Implementation #1");
		stage.show();
		
	}

}
