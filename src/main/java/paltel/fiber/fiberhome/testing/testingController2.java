package paltel.fiber.fiberhome.testing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class testingController2 implements Initializable {

    @FXML
    Label wakeupLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       wakeupLabel.setText(Navigator.getValue("name") + " " +wakeupLabel.getText());
    }
    public void prevPage() {
        Navigator.pop();
    }
}
