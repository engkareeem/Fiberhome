package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import  java.sql.*;

import eu.iamgio.animated.AnimatedSwitcher;

import eu.iamgio.animated.Animation;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static paltel.fiber.fiberhome.testing.Functions.*;

public class loginPageController implements Initializable {

    Stage stage;
    @FXML
    AnchorPane ap;


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

    private boolean usedMinimize = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeNumberValidatorListener(employeeNumberInput,employeeNumberInputValidatorLabel);
        passwordValidatorListener(passwordInput,passwordInputValidatorLabel);
        stage = testingMain.primaryStage;
        move(stage);



        playOpenAnimation();


        Rectangle clip = new Rectangle(
                backgroundImageView.getFitWidth(), backgroundImageView.getFitHeight()
        );
        clip.setArcWidth(29);
        clip.setArcHeight(29);
        backgroundImageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = backgroundImageView.snapshot(parameters, null);
        backgroundImageView.setClip(null);
        backgroundImageView.setImage(image);

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

/*                                   Testing Area :3                                */

    void login(){
        try {
            String loginText = loginButton.getText();
            loadingSpinner.setVisible(true);
            loginButton.setText("");
            Statement statement = testingMain.dbConnection.createStatement();
            ResultSet res = statement.executeQuery("select * from employee_account where eid = " + employeeNumberInput.getText());

            if(res.next()){
               if(res.getString("password").equals(passwordInput.getText())){
                   ZoomOut switchAnimation = new ZoomOut(ap);
                   switchAnimation.setOnFinished(event -> {
                       Navigator.pushNamedReplacementWithArgs("homePageScene", "eid", employeeNumberInput.getText());
                   });
                   switchAnimation.play();
                   System.out.println("logged in as " + res.getString("role"));
                   loadingSpinner.setVisible(false);
                   loginButton.setText(loginText);
               }else{
                   System.out.println("Wrong Password Entered"); // todo: show wrong password dialog
                   loadingSpinner.setVisible(false);
                   loginButton.setText(loginText);
               }
            } else {
                System.out.println("User was not Found"); // todo: show user was not found dialog
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
        if(!validateEmployeeNumber(employeeNumberInput, employeeNumberInputValidatorLabel)){ isValid = false; new Shake(employeeNumberInput).play();}
        if(!validatePassword(passwordInput,passwordInputValidatorLabel)) {isValid = false; new Shake(passwordInput).play();}
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
















