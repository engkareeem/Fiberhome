package paltel.fiber.fiberhome.testing;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class testingController implements Initializable {

    @FXML
    AnchorPane ap;
    double xOffset,yOffset;
    @FXML
    Pane titleBar;
    @FXML
    ImageView imageView;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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