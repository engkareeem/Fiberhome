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

    static Connection dbConnection;
    public static int connectionStatus = 0; // 0 connecting,1 connected, -1 failed
    public static int retrying=0;
    static Stage stage;
    @Override
    public void start(Stage _stage) throws IOException {
        Navigator.primaryStage  = stage = _stage;
        FXMLLoader fxmlLoader = new FXMLLoader(testingMain.class.getResource("loginPageScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        Navigator.setupStages();

        _stage.setTitle("FiberHome");
        _stage.setScene(scene);
        _stage.show();
    }

    public static void main(String[] args) {
        connectToDatabase();
        launch();

    }
    public static void connectToDatabase(){
        new Thread(() -> {
            for(int retryTimes = 1; retryTimes <= 3; retryTimes++){
                try {
                    //todo: show trying to connect icon and text Connecting
                    Functions.displayStatus(stage.getScene(),0);
                    DriverManager.registerDriver(new
                            OracleDriver());
                    dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@//nasrallahOracle:1521/orcl", "FiberHomeAdmin", "oracle");
                } catch (Exception e) {
                    Functions.displayStatus(stage.getScene(),0);

                    //todo: show reconnecting+retryTimes e.g: reconnecting1 reconnecting2 etc..
                } finally {
                    if (dbConnection != null) {
                        System.out.println("Connected to Database Successfully");
                        //todo: show connected icon and text
                        retryTimes = 999;


                    }
                }
            }
            if(dbConnection == null){
                System.out.println("CONNECTION FAILED");
                // todo: show failed to connect icon and text
            }
        }).start();
    }
}