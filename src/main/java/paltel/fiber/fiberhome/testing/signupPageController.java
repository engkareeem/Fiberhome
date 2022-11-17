package paltel.fiber.fiberhome.testing;

import animatefx.animation.AnimationFX;
import animatefx.animation.ZoomInUp;
import animatefx.animation.ZoomOutDown;
import animatefx.animation.ZoomOutUp;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static paltel.fiber.fiberhome.testing.Functions.*;

public class signupPageController implements Initializable {
    private boolean usedMinimize = false;
    Stage stage;
    @FXML
    AnchorPane ap;
    @FXML
    ImageView backgroundImageView;
    @FXML
    Pane titleBar;


    @FXML
    TextField employeeNumberInput;
    @FXML
    Label employeeNumberLabel;
    @FXML
    Label employeeNumberInputValidatorLabel;

    @FXML
    TextField passwordInput;
    @FXML
    Label passwordInputValidatorLabel;

    @FXML
    TextField nicknameInput;
    @FXML
    Label nicknameInputValidatorLabel;

    @FXML
    Button signupButton;
    @FXML
    MFXProgressSpinner loadingSpinner;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeNumberValidatorListener(employeeNumberInput,employeeNumberInputValidatorLabel);
        passwordValidatorListener(passwordInput,passwordInputValidatorLabel);
        nickNameValidatorListener(nicknameInput,nicknameInputValidatorLabel);


        stage = testingMain.primaryStage;
        move(stage);
        optimizeImageView(backgroundImageView);

        stage.iconifiedProperty().addListener((observableValue, aBoolean, iconified) -> {
            if(iconified && !usedMinimize) {

                new ZoomOutDown(ap).setSpeed(2).play();
                usedMinimize = false;

            }else{
                new ZoomInUp(ap).setSpeed(2).play();

            }

        });
    }
    @FXML
    public void close(){
        AnimationFX closeAnimation = new ZoomOutUp(ap);
        closeAnimation.setOnFinished((event) -> {
            Platform.exit();
            System.exit(0);
        });
        closeAnimation.play();
    }

    @FXML
    public void minimize(){
        usedMinimize = true;
        AnimationFX minimizeAnimation = new ZoomOutDown(ap);
        minimizeAnimation.setOnFinished((event) -> stage.setIconified(true));

        minimizeAnimation.setSpeed(2).play();
    }

    public void move(Stage stage) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        titleBar.setOnMousePressed(event -> {
            xOffset.set(stage.getX() - event.getScreenX());
            yOffset.set(stage.getY() - event.getScreenY());
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset.get());
            stage.setY(event.getScreenY() + yOffset.get());
        });
    }

    /*                         TESTING AREA! :3                                   */
    @FXML
    public void comeBack() {
        Navigator.pop();
    }
    @FXML
    public void signupButtonClicked() {
        if(validateNickname(nicknameInput,nicknameInputValidatorLabel) && validateEmployeeNumber(employeeNumberInput,employeeNumberInputValidatorLabel)
        && validatePassword(passwordInput,passwordInputValidatorLabel)) { // make sure all fields are validated to signup // the validator just make sure for the input form..
            loadingSpinner.setVisible(true);
            signupButton.setText("");
        }
    }

}
