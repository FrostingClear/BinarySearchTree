package searchUIandHandlers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * A textfield that guarantees that the user will enter an integer.
 * 
 * Intended to be paired with feedback text to guide the user accordingly.
 * 
 * 
 *
 */
public class IntegerTextField extends TextField {

	String value;
	Text feedback;
	
	public IntegerTextField(String value, Text feedback) {
		
		super(value);
		this.value = value;
		this.feedback = feedback;
		this.setPrefSize(150, 30);
		this.setMaxWidth(150);
		this.setAlignment(Pos.CENTER);
		integersOnly();
		
	}
	
	//Implementation of text property listener taken from Stuart Marshall's Social Network Analyser code
	public void integersOnly() {
		
		this.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		            
		        	feedback.setText("You tried to enter a non integer, reverted to default value");

		    		setText(value);
		        	
		        }
		    }
		});
		
	}
	
}
