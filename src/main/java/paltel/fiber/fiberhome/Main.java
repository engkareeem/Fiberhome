package paltel.fiber.fiberhome;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import paltel.fiber.fiberhome.testing.testingController2;
import paltel.fiber.fiberhome.testing.testingMain;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(testingMain.class.getResource("testScene2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        stage.show();



    }

    public static void main(String[] args) {
        launch();
    }
}
