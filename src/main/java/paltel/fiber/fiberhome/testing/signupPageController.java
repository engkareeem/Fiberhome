package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    @FXML
    Pane signupPopup;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeNumberValidatorListener(employeeNumberInput,employeeNumberInputValidatorLabel);
        passwordValidatorListener(passwordInput,passwordInputValidatorLabel);
        nickNameValidatorListener(nicknameInput,nicknameInputValidatorLabel);


        stage = testingMain.primaryStage;
        playOpeningAnimation();
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

    private void playOpeningAnimation(){
        //FadeIn openingAnimation = new FadeIn(ap);
        for(Node node: signupPopup.getChildren()){
            node.setOpacity(0);
        }
        for(Node node: signupPopup.getChildren()){
            new FadeIn(node).play();
        }
        //openingAnimation.play();
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
    private void signUp(){
        try {
            Statement statement = testingMain.dbConnection.createStatement();
            ResultSet res = statement.executeQuery("select EMPLOYEE_ID from EMPLOYEE where EMPLOYEE_ID = " + employeeNumberInput.getText());
            if(res.next()){ // checks if user exist in employee table
                ResultSet resultSet = statement.executeQuery("select  EID from EMPLOYEE_ACCOUNT where EID = " + employeeNumberInput.getText());
                if(resultSet.next()){ // checks if account is already exist
                    // todo: show popup that this employee already has an account
                    new Shake(employeeNumberInput).play();
                }else{
                    //statement.executeUpdate("INSERT INTO EMPLOYEE_ACCOUNT VALUES(" + employeeNumberInput.getText() + "," +  + "," + ",");
                }
            }else{
                // todo: show popup that there is no employee with this eid and show red info that this eid doesn't exist
                new Shake(employeeNumberInput).play();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
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
