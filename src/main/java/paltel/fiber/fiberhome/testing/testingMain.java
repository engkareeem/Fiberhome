package paltel.fiber.fiberhome.testing;

import animatefx.animation.Bounce;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class testingMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(testingMain.class.getResource("testScene.fxml"));
        fxmlLoader.setController(new testingController(stage));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);




        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("meow meow");
        stage.setScene(scene);
        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}