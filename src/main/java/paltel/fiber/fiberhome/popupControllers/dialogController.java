package paltel.fiber.fiberhome.popupControllers;

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
import paltel.fiber.fiberhome.Functions;
import paltel.fiber.fiberhome.Navigator;
import paltel.fiber.fiberhome.Main;

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
    @FXML
    FontIcon dialogIcon;
    Stage stage;
    private Functions.Errors error;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Functions.confirmFlag = null;
        stage = Navigator.popupStage;
        ZoomIn stageZoom = new ZoomIn(ap);
        stageZoom.setSpeed(2);
        stageZoom.play();
        Functions.move(stage,dialogPane);

        descriptionLabel.setText((String)Navigator.getPopupValue("description"));

        String buttonText1 = (String)Navigator.getPopupValue("button1");
        String buttonText2 = (String)Navigator.getPopupValue("button2");
        if(buttonText1 == null && buttonText2 == null) {
            confirmButton.setText("Confirm");
            cancelButton.setText("Cancel");
        } else if(buttonText2 == null) {
            confirmButton.setText(buttonText1);
            confirmButton.setLayoutX(106);
            confirmButton.setLayoutY(186);
            cancelButton.setVisible(false);
        } else if(buttonText1 == null){
            cancelButton.setText(buttonText2);
            cancelButton.setLayoutX(106);
            cancelButton.setLayoutY(186);
            confirmButton.setVisible(false);
        } else {
            confirmButton.setText(buttonText1);
            cancelButton.setText(buttonText2);
        }
//        confirmButton.setText(buttonText1 != null ? buttonText1:"Confirm");
//        cancelButton.setText(buttonText2 != null ? buttonText2:"Cancel");

        error = (Functions.Errors)Navigator.getPopupValue("type");
        assert error != null;
        if(error.getType() == Functions.DialogType.ERROR_DIALOG) {
            dialogIcon.setIconLiteral("fltral-dismiss-circle-24");
            dialogIcon.setIconColor(Color.web("#ff2525"));
        } else if(error.getType() == Functions.DialogType.WARNING_DIALOG) {
            dialogIcon.setIconLiteral("fltrmz-warning-24");
            dialogIcon.setIconColor(Color.web("#ffcc00"));
        } else if(error.getType() == Functions.DialogType.SUCCESSFUL_DIALOG) {
            dialogIcon.setIconLiteral("fltral-checkmark-circle-24");
            dialogIcon.setIconColor(Color.web("#5cb85c"));
        } else if(error.getType() == Functions.DialogType.INFORMATION_DIALOG) {
            dialogIcon.setIconLiteral("fltral-info-24");
            dialogIcon.setIconColor(Color.web("#499ab2"));
        }
    }
    @FXML
    public void confirmButtonClicked(){
        if(error == Functions.Errors.CONNECTION_ERROR) {
            Functions.displayStatus(Navigator.primaryStage.getScene(),0,0);
            Main.connectToDatabase();
        } else if(error == Functions.Errors.CONFIRM_DIALOG) {
            Functions.confirmFlag = true;
        } else if(error == Functions.Errors.SUCCESSFUL) {
            Functions.confirmFlag = true;
        }
        closeDialog();
    }
    @FXML
    public void cancelButtonClicked() {
        if(error == Functions.Errors.CONFIRM_DIALOG) {
            Functions.confirmFlag = false;
        }
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
