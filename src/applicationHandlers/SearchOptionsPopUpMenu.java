package applicationHandlers;

import application.BTreeMain;
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
import searchUIandHandlers.FindAgeRangeUI;
import searchUIandHandlers.FindAlInstancesUI;
import searchUIandHandlers.FindByFirstNameUI;
import searchUIandHandlers.LastNameLongerHandler;
import searchUIandHandlers.NamesLongerThanUI;

/**
 * Opens a GUI Interface for user to select different search options
 * 
 * 
 *
 */
public class SearchOptionsPopUpMenu implements EventHandler<ActionEvent>{

	BTreeMain main;
	
	public SearchOptionsPopUpMenu(BTreeMain main) {

		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		
		//https://www.geeksforgeeks.org/javafx-alert-with-examples/
		//https://www.programcreek.com/java-api-examples/?class=javafx.scene.control.Alert&method=setGraphic
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
		
		
		Stage searchOptions = new Stage();
				
		VBox controls = new VBox(10);
		controls.setAlignment(Pos.CENTER);
		controls.setPadding(new Insets(30, 30, 30, 30));
		
		int buttonWidth = 250;
		int buttonHeight = 30;
		
		Button nameLengthSearch = new Button("First Names Longer Than");
		nameLengthSearch.setPrefSize(buttonWidth, buttonHeight);
		nameLengthSearch.setOnAction(new NamesLongerThanUI(main,searchOptions));
		
		
		Button lastNameLongerThanFirst = new Button("Last name longer than First Name");
		lastNameLongerThanFirst.setPrefSize(buttonWidth, buttonHeight);
		lastNameLongerThanFirst.setOnAction(new LastNameLongerHandler(main, searchOptions));
		
		Button findByFirstName = new Button("Binary search - first name");
		findByFirstName.setPrefSize(buttonWidth, buttonHeight);
		findByFirstName.setOnAction(new FindByFirstNameUI(main, searchOptions));
		
		Button allInstancesFName = new Button("All instances of first name");
		allInstancesFName.setPrefSize(buttonWidth, buttonHeight);
		allInstancesFName.setOnAction(new FindAlInstancesUI(main, "fname", searchOptions));
		
		Button allInstancesLName = new Button("ALL instances of last name");
		allInstancesLName.setPrefSize(buttonWidth, buttonHeight);
		allInstancesLName.setOnAction(new FindAlInstancesUI(main, "lname", searchOptions));
		
		Button allInstancesAge = new Button("ALL instances of age");
		allInstancesAge.setPrefSize(buttonWidth, buttonHeight);
		allInstancesAge.setOnAction(new FindAgeRangeUI(main, searchOptions));
		
				
		controls.getChildren().addAll(nameLengthSearch, lastNameLongerThanFirst, findByFirstName, allInstancesFName, allInstancesLName, allInstancesAge);
		
		
		
		Scene scene = new Scene(controls, 500, 300);
		searchOptions.setScene(scene);
		
		searchOptions.sizeToScene();
		
		//https://stackoverflow.com/questions/55172310/how-to-position-the-window-stage-in-javafx
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		double x = (bounds.getMinX() + (bounds.getWidth() - 500) / 2);
		double y = (bounds.getMinY() + (bounds.getHeight() - 300) / 2);
		searchOptions.setX(x);
		searchOptions.setY(y);
				
		searchOptions.setTitle("Search Options");
		searchOptions.show();
		
	}

}
