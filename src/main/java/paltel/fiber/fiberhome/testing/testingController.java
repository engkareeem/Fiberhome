package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class testingController implements Initializable {

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ZoomIn stageZoom = new ZoomIn(ap);
        titleBar.setOpacity(0);
        stageZoom.setOnFinished((actionEvent) -> {
                new ZoomIn(titleBar).play();
            new FadeInDown(welcomebacklable).play();
            new FadeInDown(loginTitle).play();
            Animator.chainAnimator(new FadeInDown(emailUserNameLabel), new FadeInDown(emailInput));
            Animator.chainAnimator( new FadeInDown(passwordLabel) ,new FadeInDown(passwordInput) );
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
















