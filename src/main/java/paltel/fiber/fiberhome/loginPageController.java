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
import paltel.fiber.fiberhome.utils.Animator;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class loginPageController implements Initializable {

    Stage stage;
    @FXML
    AnchorPane ap;
    @FXML
    Pane disablePane;
    @FXML
    Button loginButton;
    @FXML
    Pane titleBar;

    @FXML
    Pane loginPopup;
    @FXML
    ImageView backgroundImageView;

    @FXML
    Label loginTitle;
    @FXML
    Label welcomeBackLabel;

    @FXML
    TextField employeeNumberInput;
    @FXML
    Label employeeNumberLabel;
    @FXML
    Label employeeNumberInputValidatorLabel;
    @FXML
    Label passwordInputValidatorLabel;

    @FXML
    Label passwordLabel;
    @FXML
    TextField passwordInput;
    @FXML
    Label forgetPasswordLabel;

    @FXML
    Label notRegisterLabel;

    @FXML
    Label registerLabel;

    @FXML
    MFXProgressSpinner loadingSpinner;
    @FXML
    ImageView connectedStatusLabel,connectingStatusLabel,notConnectedStatusLabel;

    private boolean usedMinimize = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Functions.employeeNumberValidatorListener(employeeNumberInput,employeeNumberInputValidatorLabel);
        Functions.passwordValidatorListener(passwordInput,passwordInputValidatorLabel);
        Functions.optimizeImageView(backgroundImageView);
        stage = Navigator.primaryStage;
        Functions.move(stage,titleBar);


        playOpenAnimation();

        stage.iconifiedProperty().addListener((observableValue, aBoolean, iconified) -> {
            if(iconified && !usedMinimize) {

                new ZoomOutDown(ap).setSpeed(2).play();
                usedMinimize = false;

            }else{
                new ZoomInUp(ap).setSpeed(2).play();

            }
        });

    }

    private void playOpenAnimation(){
        ZoomIn openingAnimation = new ZoomIn(ap);
        titleBar.setOpacity(0);
        for(Node node: loginPopup.getChildren()) {
            node.setOpacity(0);
        }

        employeeNumberInputValidatorLabel.setOpacity(1);
        passwordInputValidatorLabel.setOpacity(1);
        loadingSpinner.setOpacity(1);

        openingAnimation.setOnFinished((actionEvent) -> {
            new ZoomIn(titleBar).play();
            new FadeInDown(welcomeBackLabel).play();
            new FadeInDown(loginTitle).play();
            Animator.chainAnimator( new FadeInDown(passwordInput) ,new FadeInDown(passwordLabel));
            Animator.chainAnimator(new FadeInDown(employeeNumberInput), new FadeInDown(employeeNumberLabel));
            Animator.chainAnimator(new FadeInDown(loginButton), new FadeInDown(forgetPasswordLabel));
            Animator.chainAnimator(new FadeInDown(notRegisterLabel), new FadeInDown(registerLabel));
        });
        openingAnimation.play();
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




    void login(){

        try {
            String loginText = loginButton.getText();
            loadingSpinner.setVisible(true);
            loginButton.setText("");
            Statement statement = Main.dbConnection.createStatement();
            String eid = employeeNumberInput.getText();
            String password = passwordInput.getText();
            if(eid.isEmpty()) eid = "0002";
            if(password.isEmpty()) password = "admin123";
            ResultSet res = statement.executeQuery("select * from employee_account where eid = " + eid);

            if(res.next()){
               if(res.getString("password").equals(password)){
                   ZoomOut switchAnimation = new ZoomOut(ap);
                   String finalEid = eid;
                   switchAnimation.setOnFinished(event -> {
                       Navigator.pushNamedReplacementWithArgs("homePageScene", "eid", finalEid);
                   });
                   switchAnimation.play();
                   System.out.println("logged in as " + res.getString("role"));
                   loadingSpinner.setVisible(false);
                   loginButton.setText(loginText);
               }else{
                   Functions.displayValidatingError(passwordInput,passwordInputValidatorLabel,"Wrong Password Entered");
                   loadingSpinner.setVisible(false);
                   loginButton.setText(loginText);
               }
            } else {
                Functions.displayValidatingError(employeeNumberInput,employeeNumberInputValidatorLabel,"User was not Found");

                loadingSpinner.setVisible(false);
                loginButton.setText(loginText);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loginButtonClicked() {
        boolean isValid = true;
        if(Main.dbConnection == null) {
            Functions.showDialog("You are not connected to server","Reconnect","Cancel", Functions.Errors.CONNECTION_ERROR);
            return;
        }
        if(!Functions.validateEmployeeNumber(employeeNumberInput, employeeNumberInputValidatorLabel)) {
            isValid=false;
            Functions.displayValidatingError(employeeNumberInput,employeeNumberInputValidatorLabel,"Employee number must be 4 digits");
        }
        if(!Functions.validatePassword(passwordInput,passwordInputValidatorLabel)) {
            isValid = false;
            Functions.displayValidatingError(passwordInput,passwordInputValidatorLabel,"Password must be between 6 and 10 Characters");
        }

        isValid = true;

        if(isValid){ // log In
            loginButton.setDisable(true);
            login();
            loginButton.setDisable(false);
        }

    }
    @FXML
    public void register() {
        Navigator.pushNamed("signupPageScene");
    }
//    public void maximize(){
//
//        int standardW = 900;
//        int standardH = 600;
////
////        if(stage.isMaximized()){
////            AnimationFX maximizeOffAnimation = new ZoomOut(ap);
////
////            maximizeOffAnimation.setOnFinished(event -> {
////                stage.setMaximized(false);
////            });
////            maximizeOffAnimation.play();
////        }else{
////
////            Screen screen = Screen.getPrimary();
////            Rectangle2D bounds = screen.getVisualBounds();
////
////            stage.setX(bounds.getMinX());
////            stage.setY(bounds.getMinY());
////            stage.setWidth(bounds.getWidth());
////            stage.setHeight(bounds.getHeight());
////            for(Node node: ap.getChildren()) {
////                Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
////                System.out.println(boundsInScene.getMinX());
////                node.setLayoutX(bounds.getWidth()/standardW * boundsInScene.getMinX());
////                node.setLayoutY((boundsInScene.getMinY() / (double)standardH)*bounds.getMaxY());
////                boundsInScene = node.localToScene(node.getBoundsInLocal());
////                System.out.println(boundsInScene.getMinX());
////            }
////        }
//
//    }

}
















