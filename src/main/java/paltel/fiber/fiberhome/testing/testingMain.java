package paltel.fiber.fiberhome.testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import oracle.jdbc.OracleDriver;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class testingMain extends Application {
    static Stage primaryStage;
    static Connection dbConnection;
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(testingMain.class.getResource("loginPageScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("meow meow");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        new Thread(() -> {

            try {
                DriverManager.registerDriver(new
                        OracleDriver());
                dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@//nasrallahOracle:1521/orcl", "FiberHomeAdmin", "oracle");

                if (dbConnection == null) {
                    // todo: show failed to connect dialog
                }
                System.out.println("Connected to Database Successfully");
            } catch (SQLException e) {

                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).start();
        launch();

    }
}