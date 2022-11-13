package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class testingController implements Initializable {

    Stage stage;
    @FXML
    AnchorPane ap;
    double xOffset,yOffset;

    @FXML
    Button loginButton;
    @FXML
    Pane titleBar;

    @FXML
    Pane loginPopup;
    @FXML
    ImageView imageView;

    @FXML
    Label loginTitle;
    @FXML
    Label welcomebacklable;

    @FXML
    TextField emailInput;

    @FXML
    Label passwordLabel;
    @FXML
    TextField passwordInput;

    @FXML
    Label forgetPasswordLabel;
    @FXML
    Label emailUserNameLabel;

    @FXML
    Label notRegisterLabel;

    @FXML
    Label registerLabel;

    private boolean usedMinimize = false;

    public testingController(Stage stage) {
        this.stage = stage;
        // this.move(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoomIn stageZoom = new ZoomIn(ap);
        titleBar.setOpacity(0);
        stageZoom.setOnFinished((actionEvent) -> {
                new ZoomIn(titleBar).play();
            new FadeInDown(welcomebacklable).play();
            new FadeInDown(loginTitle).play();
            Animator.chainAnimator( new FadeInDown(passwordInput) ,new FadeInDown(passwordLabel));
            Animator.chainAnimator(new FadeInDown(emailInput), new FadeInDown(emailUserNameLabel));
            new FadeInDown(forgetPasswordLabel).play();
            new FadeInDown(loginButton).play();
            Animator.chainAnimator(new FadeInDown(notRegisterLabel), new FadeInDown(registerLabel));
        });


        stageZoom.play();
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

    public void maximize(MouseEvent e){
        if(stage.isMaximized()){
            AnimationFX maximizeOffAnimation = new ZoomOut(ap);

            maximizeOffAnimation.setOnFinished(event -> {
                stage.setMaximized(false);
            });
            maximizeOffAnimation.play();
        }else{
            ap.setVisible(false);
            stage.setFullScreen(true);
            stage.setMaximized(true);
            AnimationFX maximizeOnAnimation = new ZoomIn(ap);

            maximizeOnAnimation.setOnFinished(event -> {
                 stage.setMaximized(true);
            });
            stage.toFront();
            ap.setVisible(true);
            maximizeOnAnimation.play();
        }

    }

    public void move(Stage stage) {

        titleBar.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        titleBar.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
    }

}
















