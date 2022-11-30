package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

import static paltel.fiber.fiberhome.testing.Functions.DialogType.*;

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
    @FXML
    FontIcon dialogIcon;
    Stage stage;
    private Functions.Errors error;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = Navigator.popupStage;
        ZoomIn stageZoom = new ZoomIn(ap);
        stageZoom.setSpeed(2);
        stageZoom.play();
        Functions.move(stage,dialogPane);

        descriptionLabel.setText((String)Navigator.getPopupValue("description"));

        String buttonText1 = (String)Navigator.getPopupValue("button1");
        String buttonText2 = (String)Navigator.getPopupValue("button2");
        confirmButton.setText(buttonText1 != null ? buttonText1:"Confirm");
        cancelButton.setText(buttonText2 != null ? buttonText2:"Cancel");

        error = (Functions.Errors)Navigator.getPopupValue("type");
        assert error != null;
        if(error.getType() == ERROR_DIALOG) {
            dialogIcon.setIconLiteral("fltral-dismiss-circle-24");
            dialogIcon.setIconColor(Color.web("#ff2525"));
        } else if(error.getType() == WARNING_DIALOG) {
            dialogIcon.setIconLiteral("fltrmz-warning-24");
            dialogIcon.setIconColor(Color.web("#ffcc00"));
        } else if(error.getType() == SUCCESSFUL_DIALOG) {
            dialogIcon.setIconLiteral("fltral-checkmark-circle-24");
            dialogIcon.setIconColor(Color.web("#5cb85c"));
        } else if(error.getType() == INFORMATION_DIALOG) {
            dialogIcon.setIconLiteral("fltral-info-24");
            dialogIcon.setIconColor(Color.web("#499ab2"));

        }
    }
    @FXML
    public void confirmButtonClicked(){
        if(error == Functions.Errors.CONNECTION_ERROR) {
            Functions.displayStatus(Navigator.primaryStage.getScene(),0,0);
            testingMain.connectToDatabase();
        }
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
