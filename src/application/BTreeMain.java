package application;
	

import java.util.ArrayList;

import applicationHandlers.*;
import dataClasses.PeopleTreeManager;
import dataClasses.Person;
import dataSetSwappers.BackToStandardDataset;
import dataSetSwappers.GenerateNewSample;
import dataSetSwappers.TestingDataset;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.BalanceReportAlgorithm;
import traversalAndSearchAlgorithms.DepthFirstInOrder;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

//References
//https://stackoverflow.com/questions/27712213/how-do-i-make-a-simple-solid-border-around-a-flowpane-in-javafx (border colors)
//https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html
//https://stackoverflow.com/questions/22007595/borderpane-with-color-gradient


public class BTreeMain extends Application {
	
	HBox overallLayout;
	VBox controls;
	VBox displayBox;
	final TextArea userMessages = setupUserMessage();
	
	//Tried to use these as a basis for closing pop up stages when pressing buttons
	//Didn't manage to implement appropriately
	Stage auxiliaryStage = null;
	Boolean auxiliaryStageOpen = false;
	
	/*
	 * All GUI Information Display Operates through this listview associated observable list
	 */
	ObservableList<String> list;
	ListView<String> listview;
	
	PeopleTreeManager peopleTrees;
	Boolean treeSelected = false;
	Boolean usingTestDataList = false;
	
	public void replacePopUpStage(Stage replacement) {
		
		if (auxiliaryStageOpen) {
			
			auxiliaryStage.close();
			auxiliaryStage = replacement;
			auxiliaryStageOpen = true;
		}
		
		else {
			
			return;
		}
		
	}
	
	public void closedPopUpStage() {
		
		auxiliaryStage = null;
		auxiliaryStageOpen = false;
		
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			
			//By default loads up the standard name list
			peopleTrees = new PeopleTreeManager("standard");
			
			buildUI();
								
			Scene scene = new Scene(overallLayout, 1280, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setTitle("Binary Search Tree");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(event->{
				
				//https://stackoverflow.com/questions/12194967/how-to-close-all-stages-when-the-primary-stage-is-closed
				Platform.exit();
				
			});
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private void buildUI() {
		
		controls = buildControls();
		displayBox = buildDisplay();
		
		overallLayout = buildOverallLayout();
		
	}
	
	private VBox buildDisplay() {
		
		VBox displayBox = new VBox();		
		HBox.setHgrow(displayBox, Priority.ALWAYS);

		
		list = FXCollections.observableArrayList();
		listview = new ListView<String>(list);
		
		
		listview.setOnMouseClicked(new EventHandler<MouseEvent>() {

	        @Override
	        public void handle(MouseEvent event) {
	        	
	        	if (list.size() == 0) {
	        		return;
	        	}
	        	
	            System.out.println("clicked on " + listview.getSelectionModel().getSelectedItem());
	        }
	    });
		
		VBox.setVgrow(listview, Priority.ALWAYS);
		
		/*
		 * Initial view is a "raw" display of the people (likely will show up in ID order due to my Person class auto assigning ID incrementally)
		 * OF COURSE FUTURE GUI VIEWS WILL BE OPERATING FROM BINARY TREES as the data structure, NOT arraylists!!!
		 */
		ArrayList<Person> peopleList = peopleTrees.getPeopleListUnsorted();
		
		
		for (Person person : peopleList) {
			
			list.add(person.toString());
		}

		
		displayBox.getChildren().addAll(userMessages, listview);
		
		return displayBox;
	}
	
	/**
	 * When the user changes up the data set makes the necessary adjustments
	 * to load up a "Fresh" version of the display.
	 */
	public void reinitialiseDisplay() {
		
		list.clear();
		treeSelected = false;
		
		userMessages.setText("Based on order of generation, here are the people loaded into the trees\n\n<----SELECT A TREE TO GET STARTED");
	
		//Once again it is ONLY in the context of INITIAL DISPLAY that I use
		//an arraylist structure to feed data into the the observable list
		//All other methods should refer to data directly in the trees.
		ArrayList<Person> peopleList = peopleTrees.getPeopleListUnsorted();
		
		for (Person person : peopleList) {
			
			list.add(person.toString());
		}
		
		controls.getChildren().clear();
		
		addButtonSet(controls);
	
	
	}

	private HBox buildOverallLayout() {
		
		final HBox overallLayout = new HBox();
		
		overallLayout.getChildren().addAll(controls, displayBox);
		
		return overallLayout;
	}
	
	private VBox buildControls() {
		
		VBox controls = new VBox(10);
		
		controls.setPadding(new Insets(20, 10, 10, 20));
		
		controls.setMinWidth(100);
		controls.setMaxWidth(200);
		controls.setPrefWidth(200);
		controls.setStyle("-fx-border-color:#185373; -fx-background-color: linear-gradient(from 25% 25% to 100% 100%, #84bddb, #327fa8)");
		
		addButtonSet(controls);
		
		return controls;
	}
	
	/* Everytime we change datasets we actually need to replace the buttons
	 * Because the handlers need to be associated with the new trees that are made when "switching" datasets
	 */
	private void addButtonSet(VBox controls) {
		
		int width = 150;
		int height = 30;
		
		Button orderByFirstName = new Button("First Name Tree");
		orderByFirstName.setPrefSize(width, height);
		orderByFirstName.setOnAction(new ChangeTreeAndOrOrdering(this, peopleTrees.getFirstNameTree(), new DepthFirstInOrder(), "Sorted by First Name Priority"));
		
		Button orderByLastName = new Button("Last Name Tree");
		orderByLastName.setPrefSize(width, height);
		orderByLastName.setOnAction(new ChangeTreeAndOrOrdering(this, peopleTrees.getLastNameTree(), new DepthFirstInOrder(), "Sorted by Last Name Priority"));
		
		Button orderByAge = new Button("Age Tree");
		orderByAge.setPrefSize(width, height);
		orderByAge.setOnAction(new ChangeTreeAndOrOrdering(this, peopleTrees.getAgeTree(), new DepthFirstInOrder(), "Sorted by Age Priority"));
		
		Text space = new Text("");
		
		Button changeTraversal = new Button("Views / Traversals");
		changeTraversal.setPrefSize(width, height);
		changeTraversal.setOnAction(new TraversalOptionPopUpMenu(this));
		
		Button search = new Button("Search");
		search.setPrefSize(width, height);
		search.setOnAction(new SearchOptionsPopUpMenu(this));
		
		Text space2 = new Text("");
		
		Button changeDataset = new Button("Remake Dataset");
		changeDataset.setPrefSize(width, height);
		changeDataset.setOnAction(new GenerateNewSample(this));
		
		
		Button useTestDataset = new Button("Load Test Dataset");
		useTestDataset.setPrefSize(width, height);
		useTestDataset.setOnAction(new TestingDataset(this));
		
		Button backToStandard = new Button("Non-Test Dataset");
		backToStandard.setPrefSize(width, height);
		backToStandard.setOnAction(new BackToStandardDataset(this));
		
		Text space3 = new Text("");
		
		Button reportBalance = new Button("Report Balance");
		reportBalance.setPrefSize(width, height);
		reportBalance.setOnAction(new BalanceReportHandler(this));
		
		Button bruteRebalance = new Button("Balanced Rebuild");
		bruteRebalance.setPrefSize(width, height);
		bruteRebalance.setOnAction(new BruteForceRebalanceHandler(this));
		
		Button autoBalanceOn = new Button("Use auto-balance");
		autoBalanceOn.setPrefSize(width, height);
		autoBalanceOn.setOnAction(new autoBalanceHandler(this));
		
		Button stopAutoBalance = new Button("Stop auto-balance");
		stopAutoBalance.setPrefSize(width, height);
		stopAutoBalance.setOnAction(new stopAutoBalance(this));
		
		
		Text space4 = new Text("");
		
		Button addRandomPeople = new Button("Add Random Person");
		addRandomPeople.setPrefSize(width, height);
		addRandomPeople.setOnAction(new addPeopleHandler(this));
		
		Button renameAPerson = new Button("Rename a Person v1");
		renameAPerson.setPrefSize(width, height);
		renameAPerson.setOnAction(new RenameTypeOnePopUpMenu(this));
		
		Button renameAPerson2 = new Button("Rename a Person v2");
		renameAPerson2.setPrefSize(width, height);
		renameAPerson2.setOnAction(new RenameTypeTwoPopUpMenu(this));
		
		Button deleteAPerson = new Button("Remove a Person");
		deleteAPerson.setPrefSize(width, height);
		deleteAPerson.setOnAction(new RemovePopUpMenu(this));
		
		
		controls.getChildren().addAll(orderByFirstName, orderByLastName, orderByAge, space,  changeTraversal, search, space2, changeDataset, useTestDataset, backToStandard, space3,
				reportBalance, bruteRebalance, autoBalanceOn, stopAutoBalance, space4, addRandomPeople, renameAPerson, renameAPerson2, deleteAPerson);
		
	}


	private TextArea setupUserMessage() {
		
		TextArea text = new TextArea("Based on order of generation, here are the people loaded into the trees\n\n<----SELECT A TREE TO GET STARTED");
		text.setPrefHeight(120);
		
		//https://examples.javacodegeeks.com/desktop-java/swing/jtextfield/create-read-only-non-editable-jtextfield/
		text.setEditable(false);
		
		return text;
		
	}

	public HBox getOverallLayout() {
		return overallLayout;
	}


	public VBox getControls() {
		return controls;
	}


	public Pane getDisplayBox() {
		return displayBox;
	}


	public ObservableList<String> getList() {
		return list;
	}


	public ListView<String> getListview() {
		return listview;
	}


	public PeopleTreeManager getPeopleTreesManager() {
		return peopleTrees;
	}
	
	


	public TextArea getUserMessages() {
		return userMessages;
	}
	
	


	public Boolean getTreeSelected() {
		return treeSelected;
	}


	public void setTreeSelected(Boolean treeSelected) {
		this.treeSelected = treeSelected;
	}


	public Boolean getUsingTestData() {
		return usingTestDataList;
	}


	public void setUsingTestData(Boolean usingTestData) {
		this.usingTestDataList = usingTestData;
	}


	public void setPeopleTrees(PeopleTreeManager peopleTrees) {
		this.peopleTrees = peopleTrees;
	}

	
	

	public void setAuxiliaryStage(Stage auxiliaryStage) {
		this.auxiliaryStage = auxiliaryStage;
		this.auxiliaryStageOpen = true;
	}

	public Boolean getAuxiliaryStageOpen() {
		return auxiliaryStageOpen;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
