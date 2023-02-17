package applicationHandlers;

import java.util.ArrayList;

import application.BTreeMain;
import dataClasses.BinaryTree;
import dataClasses.PeopleTreeManager;
import dataClasses.Person;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import traversalAndSearchAlgorithms.TraversalAlgorithm;

/**
 * EventHandler that handles almost all of the GUI information display changes
 * 
 *
 */
public class ChangeTreeAndOrOrdering implements EventHandler<ActionEvent> {

	BTreeMain main;
	BinaryTree<Person> relevantTree;
	String message;
	TraversalAlgorithm traversal;
	Stage popUpStage = null;

	public ChangeTreeAndOrOrdering(BTreeMain main, BinaryTree<Person> relevantTree, TraversalAlgorithm traversal, String message) {
		// TODO Auto-generated constructor stub
		this.main = main;
		this.relevantTree = relevantTree;
		this.message = message;
		this.traversal = traversal;
	}
	
	//When using in conjunction with pop-up stages, so we can close the associated stage when we're done with it
	public ChangeTreeAndOrOrdering(BTreeMain main, BinaryTree<Person> relevantTree, TraversalAlgorithm traversal, String message, Stage stage) {
		// TODO Auto-generated constructor stub
		this.main = main;
		this.relevantTree = relevantTree;
		this.message = message;
		this.traversal = traversal;
		this.popUpStage = stage;
	}

	@Override
	public void handle(ActionEvent event) {

		/*
		 * Clear off the existing observable list data, traverse through the binary tree and add
		 * to update the observable list data appropriately.
		 */
		
		ObservableList<String> list = main.getList();

		list.clear();
		
		main.getPeopleTreesManager().setActiveTree(relevantTree);
		
		String fullMessage = String.format("%s\n\nThe current tree is: %s", message, main.getPeopleTreesManager().getActiveTree().getName());
		
		main.getUserMessages().setText(fullMessage);

		if (relevantTree.getRoot() != null) {
		
			//Traverse tree in the relevant manner to update the observable list
			traversal.traverse(relevantTree.getRoot(), list);
		
		}
		//Enable functional buttons to do their job
		main.setTreeSelected(true);
		

	}



}
