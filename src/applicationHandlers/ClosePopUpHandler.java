package applicationHandlers;

import application.BTreeMain;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClosePopUpHandler implements EventHandler<WindowEvent> {

	BTreeMain main;
	
	public ClosePopUpHandler(BTreeMain main) {
		
		this.main = main;
	}




	@Override
	public void handle(WindowEvent arg0) {
		
		main.closedPopUpStage();
		
	}

}
