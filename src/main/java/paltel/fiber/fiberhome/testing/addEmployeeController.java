package paltel.fiber.fiberhome.testing;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
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
        ap.setOpacity(0);
        Platform.runLater(() -> {
            ZoomIn stageZoom = new ZoomIn(ap);
            stageZoom.setSpeed(2);
            stageZoom.play();
        });
        Functions.move(stage,employeePane);
    }
    @FXML
    public void addEmployeeButtonClicked() {
        closePopup();
    }
    @FXML
    public void close() {
        closePopup();
    }
    @FXML
    private void closePopup() {
        ZoomOut closeAnimation = new ZoomOut(ap);
        closeAnimation.setSpeed(3);
        closeAnimation.setOnFinished((event) -> {
            Functions.closeAddEmployeePopup();
        });
        closeAnimation.play();
    }
}
