package paltel.fiber.fiberhome;

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
import java.sql.*;
import java.util.ResourceBundle;

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
        Functions.employeeNumberValidatorListener(employeeNumberInput,employeeNumberInputValidatorLabel);
        Functions.passwordValidatorListener(passwordInput,passwordInputValidatorLabel);
        Functions.nickNameValidatorListener(nicknameInput,nicknameInputValidatorLabel);


        stage = Navigator.primaryStage;
        Functions.move(stage,titleBar);
        stage = Navigator.primaryStage;
        playOpeningAnimation();
        Functions.move(stage,ap);
        Functions.optimizeImageView(backgroundImageView);

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

    /*                         TESTING AREA! :3                                   */
    private void signUp(){
        if(Main.dbConnection == null){
            return;
        }
        try {
            Statement statement = Main.dbConnection.createStatement();
            ResultSet res = statement.executeQuery("select EMPLOYEE_ID from EMPLOYEE where EMPLOYEE_ID = " + employeeNumberInput.getText());
            if(res.next()){ // checks if user exist in employee table
                ResultSet resultSet = statement.executeQuery("select  EID, ROLE from EMPLOYEE_ACCOUNT where EID = " + employeeNumberInput.getText());
                if(resultSet.next()){ // checks if account is already exist
                    if(resultSet.getString("Role").equals("Pending")){
                        Functions.displayValidatingError(employeeNumberInput,employeeNumberInputValidatorLabel,"This Account is in the review section. Please wait for your approval");
                    }else{
                        Functions.displayValidatingError(employeeNumberInput,employeeNumberInputValidatorLabel,"This employee already has an account");
                    }

                }else{
                    PreparedStatement newUser = Main.dbConnection.prepareStatement("INSERT INTO EMPLOYEE_ACCOUNT VALUES(?, ?, ?, ?, ?)");
                    newUser.setString(1, employeeNumberInput.getText());
                    newUser.setString(2, passwordInput.getText());
                    newUser.setString(3, "Pending");
                    newUser.setNull(4, Types.DATE);
                    newUser.setString(5, nicknameInput.getText());
                    newUser.executeUpdate();
                }
            }else{
                Functions.displayValidatingError(employeeNumberInput,employeeNumberInputValidatorLabel,"There is no employee with this Employee number(EID)");
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
        boolean isValid = true;
        if(Main.dbConnection == null) {
            Functions.showDialog("You are not connected to server","Reconnect","Cancel", Functions.Errors.CONNECTION_ERROR);
            return;
        }
        if(!Functions.validateNickname(nicknameInput, nicknameInputValidatorLabel)) {
            isValid=false;
            Functions.displayValidatingError(nicknameInput,nicknameInputValidatorLabel,"Nickname must be 2 or more characters");
        }
        if(!Functions.validateEmployeeNumber(employeeNumberInput, employeeNumberInputValidatorLabel)) {
            isValid=false;
            Functions.displayValidatingError(employeeNumberInput,employeeNumberInputValidatorLabel,"Employee number must be 4 digits");
        }
        if(!Functions.validatePassword(passwordInput,passwordInputValidatorLabel)) {
            isValid = false;
            Functions.displayValidatingError(passwordInput,passwordInputValidatorLabel,"Password must be between 6 and 10 Characters");
        }

        if(isValid){ // Sign up:
            signupButton.setDisable(true);
            signUp();
            signupButton.setDisable(false);
        }
    }

}
