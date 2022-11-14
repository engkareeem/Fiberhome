package paltel.fiber.fiberhome.testing;

import animatefx.animation.AnimationFX;
import animatefx.animation.ZoomInUp;
import animatefx.animation.ZoomOutDown;
import animatefx.animation.ZoomOutUp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class signupPageController implements Initializable {
    private boolean usedMinimize = false;
    Stage stage;
    @FXML
    AnchorPane ap;
    @FXML
    ImageView backgroundImageView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = testingMain.primaryStage;
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
    @FXML
    public void comeBack() {
        Navigator.pop();
    }

}
