package applicationHandlers;

import application.BTreeMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;

public class addPeopleHandler implements EventHandler<ActionEvent> {

	BTreeMain main;
	
	public addPeopleHandler(BTreeMain main) {
		
		this.main = main;
	}

	@Override
	public void handle(ActionEvent event) {
		
		main.getList().clear(); 
		TextArea message = main.getUserMessages();
		main.getPeopleTreesManager().addRandomPerson(main, main.getList(), message);
		
	}

}
