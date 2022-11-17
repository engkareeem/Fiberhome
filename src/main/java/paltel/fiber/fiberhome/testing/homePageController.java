package paltel.fiber.fiberhome.testing;

import animatefx.animation.AnimationFX;
import animatefx.animation.ZoomOutDown;
import animatefx.animation.ZoomOutUp;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static paltel.fiber.fiberhome.testing.Functions.*;

public class homePageController implements Initializable {

    @FXML
    AnchorPane ap;
    @FXML
    ImageView backgroundImageView;
    private boolean usedMinimize = false;
    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = testingMain.primaryStage;
        optimizeImageView(backgroundImageView);
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
}
