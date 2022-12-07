package paltel.fiber.fiberhome.testing;

import animatefx.animation.ZoomIn;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class addEmployeeController implements Initializable {
    Stage stage;
    @FXML
    Pane employeePane;
    @FXML
    AnchorPane ap;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = Navigator.popupStage;
        Functions.move(stage,employeePane);
    }
    @FXML
    public void addEmployeeButtonClicked() {
        Functions.closeAddEmployeePopup();
    }
}
