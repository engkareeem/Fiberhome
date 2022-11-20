package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
        playOpenAnimation();
        saveLastLogin();
        stage = testingMain.primaryStage;
        optimizeImageView(backgroundImageView);

    }
    private void saveLastLogin(){

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(Calendar.getInstance().getTime());
        new Thread(()->{
//            while(Navigator.getValue("eid") == "") {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
            try {

                Statement statement = testingMain.dbConnection.createStatement();
                statement.executeUpdate("update EMPLOYEE_ACCOUNT set LAST_LOGIN = TO_DATE('" + timeStamp + "', 'yyyy-mm-dd HH24:mi:ss') where EID = " + Navigator.getValue("eid"));
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }).start();
    }
    private void playOpenAnimation(){
        ZoomIn openingAnimation = new ZoomIn(ap);
//        for(Node node: ap.getChildren()) {
//            node.setOpacity(0);
//        }
        openingAnimation.setOnFinished(event -> {});
        openingAnimation.play();


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
