package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class dialogController implements Initializable {
    @FXML
    AnchorPane ap;
    @FXML
    Pane dialogPane;
    @FXML
    Button confirmButton;
    @FXML
    Button cancelButton;
    @FXML
    Label titleLabel;
    @FXML
    Label descriptionLabel;

    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = Navigator.popupStage;
        ZoomIn stageZoom = new ZoomIn(ap);
        stageZoom.setSpeed(2);
        stageZoom.play();
        Functions.move(stage,dialogPane);

        titleLabel.setText((String)Navigator.getPopupValue("title"));
        descriptionLabel.setText((String)Navigator.getPopupValue("description"));

    }
    @FXML
    public void confirmButtonClicked(){
        closeDialog();
    }
    @FXML
    public void cancelButtonClicked() {
        closeDialog();
    }
    private void closeDialog() {
        ZoomOut closeAnimation = new ZoomOut(ap);
        closeAnimation.setSpeed(3);
        closeAnimation.setOnFinished((event) -> {
            Functions.closeDialog();
        });
        closeAnimation.play();
    }
}
