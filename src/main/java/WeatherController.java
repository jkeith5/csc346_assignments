import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;

public class WeatherController {
    @FXML
    private TextField stateField;
    @FXML
    private TextField cityField;
    @FXML
    private Button sumbitButton;

    @FXML
    protected void init(){
        System.out.println("Program running!");
    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event){
        Window owner= sumbitButton.getScene().getWindow();
        if (stateField.getText().isEmpty()){
            System.out.println("State Field is empty.");
        }else if (cityField.getText().isEmpty()){
            System.out.println("City Field is empty.");
        }else {
            System.out.printf("The State is %s and city is %s.",stateField.getText(),cityField.getText());
        }
    }

}
