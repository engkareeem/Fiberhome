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

    public static Connection dbConnection;
    public static int connectionStatus = 0; // 0 connecting,1 connected, -1 failed
    public static int retrying=0;
    static Stage stage;
    private static boolean whileThread = false;
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
        try {
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        launch();

    }
    public static void connectToDatabase(){
        System.out.println("PING");
        if(whileThread) return;
        Thread connectionThread = new Thread(() -> {
            System.out.println("PONG");
            whileThread = true;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(int retryTimes = 1; retryTimes <= 3; retryTimes++){
                try {
                    Functions.displayStatus(stage.getScene(),0,retryTimes);
                    connectionStatus = 0;
                    retrying = retryTimes;
                    dbConnection = DriverManager.getConnection("jdbc:oracle:thin:@//nasrallahOracle:1521/orcl", "FiberHomeAdmin", "oracle");
                } catch (Exception e) {
                    Functions.displayStatus(stage.getScene(),0,retryTimes);
                    System.out.println(retryTimes);
                } finally {
                    if (dbConnection != null) {
                        System.out.println("Connected to Database Successfully");
                        Functions.displayStatus(stage.getScene(),1,0);
                        connectionStatus = 1;
                        retryTimes = 999;
                    }
                }
            }
            if(dbConnection == null){
                Functions.displayStatus(stage.getScene(),-1,0);
                connectionStatus = -1;
                System.out.println("CONNECTION FAILED");
                whileThread = false;
            }
        });

        connectionThread.start();
    }
}