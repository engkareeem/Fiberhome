package paltel.fiber.fiberhome.testing;

import animatefx.animation.Shake;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

import static paltel.fiber.fiberhome.testing.Functions.DialogType.*;

public class Functions {

    private static boolean dialogOpend = false;
    enum DialogType{
        ERROR_DIALOG,
        WARNING_DIALOG,
        SUCCESSFUL_DIALOG,
        INFORMATION_DIALOG,
    }
    enum Errors{
        // شملت كلشي مع الايرورز لانه عقلي مش قادر يعطيني اسم يشملهم كلهم، مشيها
        // اذا طلع معك اسم منيح سوي للاينم ريفاكتور
        // DEFAULT ERRORS OR TYPES
        SUCCESSFUL(SUCCESSFUL_DIALOG),
        WARNING(WARNING_DIALOG),
        INFORMATION(INFORMATION_DIALOG),
        ERROR(ERROR_DIALOG),

        // Custom Types

        CONNECTION_ERROR(ERROR_DIALOG),
        NONE(null);

        private final DialogType dialogType;

        Errors(DialogType dialogType) {
            this.dialogType = dialogType;
        }
        DialogType getType() {
            return dialogType;
        }
    }

/*                            Data base connection Stuff                                                  */

    public static void displayStatus(Scene scene,int status,int tryNum) {
        Platform.runLater(new Runnable() {
            public void run() {
                if(scene == null || scene.lookup("#connectionStatusLabel") == null) return;

                if(status == 0) {
                    scene.lookup("#connectingStatusLabel").setVisible(true);
                    scene.lookup("#connectedStatusLabel").setVisible(false);
                    scene.lookup("#notConnectedStatusLabel").setVisible(false);
                    Label label= (Label) scene.lookup("#connectionStatusLabel");
                    if(tryNum <= 1) label.setText("Connecting ...");
                    else {
                        label.setText("Reconnecting " + tryNum + " ...");
                    }
                } else if(status == 1) {
                    scene.lookup("#connectingStatusLabel").setVisible(false);
                    scene.lookup("#connectedStatusLabel").setVisible(true);
                    scene.lookup("#notConnectedStatusLabel").setVisible(false);
                    Label label= (Label) scene.lookup("#connectionStatusLabel");
                    label.setText("Connected successfully");
                } else if(status == -1) {
                    scene.lookup("#connectingStatusLabel").setVisible(false);
                    scene.lookup("#connectedStatusLabel").setVisible(false);
                    scene.lookup("#notConnectedStatusLabel").setVisible(true);
                    Label label= (Label) scene.lookup("#connectionStatusLabel");
                    label.setText("Failed to connect");
                }
            }
        });
        // 0 connecting,1 connected, -1 failed


    }
/*                           Optimize design                                                             */

    public static void optimizeImageView(ImageView imageView) {

        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(29);
        clip.setArcHeight(29);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setImage(image);
    }

/*                                                   Design functions                                                      */
public static void move(Stage stage, Node pane) {
    AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
    AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
    pane.setOnMousePressed(event -> {
        xOffset.set(stage.getX() - event.getScreenX());
        yOffset.set(stage.getY() - event.getScreenY());
    });
    pane.setOnMouseDragged(event -> {
        stage.setX(event.getScreenX() + xOffset.get());
        stage.setY(event.getScreenY() + yOffset.get());
    });
}


    /*                                                      Navigator stuff                                              */
    public static void showDialog(String description) {
        showDialog(description,null,null,Errors.INFORMATION);
    }
    public static void showDialog(String description,Errors type) {
        showDialog(description,null,null,type);
    }

    public static void showDialog(String description,String buttonText1,String buttonText2,Errors type) {
        Navigator.showPopup("dialogScene","description",description,"button1",buttonText1,"button2",buttonText2,"type",type);
        dialogOpend = true;
    }
    public static void closeDialog() {
        Navigator.closePopup();
    }

    public static void showAddEmployeePopup() {
        Navigator.showPopup("addEmployeeScene");
    }
    public static void closeAddEmployeePopup() {
        Navigator.closePopup();
    }

/*                                                      Validators                                                   */

    public static void displayValidatingError(TextField tf,Label validator,String validateText) {
        validator.setText(validateText);
        tf.getStyleClass().removeAll("informationTextFields");
        tf.getStyleClass().add("informationTextFieldsError");
        new Shake(tf).play();
    }

    public static void employeeNumberValidatorListener(TextField inputField,Label validatorLabel) {
        inputField.textProperty().addListener((obs, oldText, newText) -> {
            validateEmployeeNumber(inputField,validatorLabel);
        });
    }
    public static void passwordValidatorListener(TextField inputField,Label validatorLabel) {
        inputField.textProperty().addListener((obs, oldText, newText) -> {
            validatePassword(inputField,validatorLabel);
        });
    }
    public static void nickNameValidatorListener(TextField inputField,Label validatorLabel) {
        inputField.textProperty().addListener((obs, oldText, newText) -> {
            validateNickname(inputField,validatorLabel);
        });
    }

    public static boolean validateNickname(TextField inputField,Label validatorLabel) {
        String text = inputField.getText();
        if(text.length() >=2) {
            validatorLabel.setText("");
            inputField.getStyleClass().removeAll("informationTextFieldsError");
            inputField.getStyleClass().add("informationTextFields");
            return true;
        }
        else {
            validatorLabel.setText("Nickname must be 2 or more characters");
            inputField.getStyleClass().removeAll("informationTextFields");
            inputField.getStyleClass().add("informationTextFieldsError");
            return false;
        }
    }
    public static boolean validateEmployeeNumber(TextField inputField,Label validatorLabel) {
        String text = inputField.getText();
        if(text.length() == 4 && isNum(text)) {
            validatorLabel.setText("");
            inputField.getStyleClass().removeAll("informationTextFieldsError");
            inputField.getStyleClass().add("informationTextFields");
            return true;
        }
        else {
            validatorLabel.setText("Employee number must be 4 digits");
            inputField.getStyleClass().removeAll("informationTextFields");
            inputField.getStyleClass().add("informationTextFieldsError");
            return false;
        }
    }

    public static boolean validatePassword(TextField inputField,Label validatorLabel) {
        String text = inputField.getText();

        if(text.length() <= 10 && text.length() >= 6) {
            validatorLabel.setText("");
            inputField.getStyleClass().removeAll("informationTextFieldsError");
            inputField.getStyleClass().add("informationTextFields");
            return true;
        } else {
            validatorLabel.setText("Password must be between 6 and 10 Characters");
            inputField.getStyleClass().removeAll("informationTextFields");
            inputField.getStyleClass().add("informationTextFieldsError");

            return false;
        }
    }
    /*                                                  CHECKERS                                                   */
    public static boolean isNum(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
