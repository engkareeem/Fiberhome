package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import  java.sql.*;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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

    private boolean usedMinimize = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employeeNumberValidatorListener(employeeNumberInput,employeeNumberInputValidatorLabel);
        passwordValidatorListener(passwordInput,passwordInputValidatorLabel);
        stage = testingMain.primaryStage;
        move(stage);

        ZoomIn stageZoom = new ZoomIn(ap);
        titleBar.setOpacity(0);
        for(Node node: loginPopup.getChildren()) {
            node.setOpacity(0);
        }

        employeeNumberInputValidatorLabel.setOpacity(1);
        passwordInputValidatorLabel.setOpacity(1);

        stageZoom.setOnFinished((actionEvent) -> {
            new ZoomIn(titleBar).play();
            new FadeInDown(welcomeBackLabel).play();
            new FadeInDown(loginTitle).play();
            Animator.chainAnimator( new FadeInDown(passwordInput) ,new FadeInDown(passwordLabel));
            Animator.chainAnimator(new FadeInDown(employeeNumberInput), new FadeInDown(employeeNumberLabel));
            new FadeInDown(forgetPasswordLabel).play();
            AnimationFX loginButtonAnimation = new FadeInDown(loginButton);
            loginButtonAnimation.play();
            Animator.chainAnimator(new FadeInDown(notRegisterLabel), new FadeInDown(registerLabel));
        });


        stageZoom.play();
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



    @FXML
    public void close(MouseEvent e){
        AnimationFX closeAnimation = new ZoomOutUp(ap);
        closeAnimation.setOnFinished((event) -> {
            Platform.exit();
            System.exit(0);
        });
        closeAnimation.play();
    }

    @FXML
    public void minimize(MouseEvent e){
        usedMinimize = true;
        AnimationFX minimizeAnimation = new ZoomOutDown(ap);
        minimizeAnimation.setOnFinished((event) -> {
            stage.setIconified(true);
        });

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
            DriverManager.registerDriver(new
                    oracle.jdbc.driver.OracleDriver());
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//nasrallahOracle:1521/orcl", "fiber_test", "oracle");
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from employee_account where eid =" + employeeNumberInput.getText() + " and password = " + passwordInput.getText() );

            if(res.next()){
                System.out.println("SUCCESSFULL!!!");
            } else {
                System.out.println("YOU GAY");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void loginButtonClicked(ActionEvent e) {
        if(validateEmployeeNumber(employeeNumberInput,employeeNumberInputValidatorLabel) && validatePassword(passwordInput,passwordInputValidatorLabel)) {
            login();
        }
    }
    @FXML
    public void register() {
        Navigator.pushNamed("signupPageScene");
    }
    public void maximize(MouseEvent e){

        int standardW = 900;
        int standardH = 600;
//
//        if(stage.isMaximized()){
//            AnimationFX maximizeOffAnimation = new ZoomOut(ap);
//
//            maximizeOffAnimation.setOnFinished(event -> {
//                stage.setMaximized(false);
//            });
//            maximizeOffAnimation.play();
//        }else{
//
//            Screen screen = Screen.getPrimary();
//            Rectangle2D bounds = screen.getVisualBounds();
//
//            stage.setX(bounds.getMinX());
//            stage.setY(bounds.getMinY());
//            stage.setWidth(bounds.getWidth());
//            stage.setHeight(bounds.getHeight());
//            for(Node node: ap.getChildren()) {
//                Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
//                System.out.println(boundsInScene.getMinX());
//                node.setLayoutX(bounds.getWidth()/standardW * boundsInScene.getMinX());
//                node.setLayoutY((boundsInScene.getMinY() / (double)standardH)*bounds.getMaxY());
//                boundsInScene = node.localToScene(node.getBoundsInLocal());
//                System.out.println(boundsInScene.getMinX());
//            }
//        }

    }

}
















