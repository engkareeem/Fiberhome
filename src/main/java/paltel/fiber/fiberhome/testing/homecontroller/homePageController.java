package paltel.fiber.fiberhome.testing.homecontroller;

import animatefx.animation.*;
import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import paltel.fiber.fiberhome.testing.DBapi;
import paltel.fiber.fiberhome.testing.Functions;
import paltel.fiber.fiberhome.testing.Navigator;
import paltel.fiber.fiberhome.testing.model.Employee;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.ResourceBundle;

import static paltel.fiber.fiberhome.testing.Functions.*;
import static paltel.fiber.fiberhome.testing.homecontroller.employeesTableViewFunctions.initializeTableView;

public class homePageController implements Initializable {

    @FXML
    AnchorPane ap;
    @FXML
    ImageView backgroundImageView;
    @FXML
    Pane titleBar;
    @FXML
    Pane dashboardPane,employeesPane,controlPanelPane;
    @FXML
    Button navButton1,navButton2,navButton3;
    @FXML
    MFXPaginatedTableView<Employee> employeesTable;
    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        playOpenAnimation();

        saveLastLogin();
        stage = Navigator.primaryStage;
//        switchNavButton(navButton2);
        Functions.move(stage,titleBar);
        Functions.optimizeImageView(backgroundImageView);
        setupTable();

        //        bindingUsersListViewFunctions.initializeListView(bindingUsersListView);
    }
    private void setupTable() {
        employeesTableViewFunctions.initializeTableView(employeesTable);
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
//            try {
//
//                Statement statement = testingMain.dbConnection.createStatement();
//                statement.executeUpdate("update EMPLOYEE_ACCOUNT set LAST_LOGIN = TO_DATE('" + timeStamp + "', 'yyyy-mm-dd HH24:mi:ss') where EID = " + Navigator.getValue("eid"));
//            } catch (SQLException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
        }).start();
    }
    private void playOpenAnimation(){
        ZoomIn openingAnimation = new ZoomIn(ap);
        openingAnimation.setOnFinished(event -> {});
        openingAnimation.play();
    }

    /*                   Nav bar stuff                    */
    @FXML
    public void dashboardNavButtonClicked() {
        dashboardPane.setVisible(true);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(false);
        switchNavButton(navButton1);
    }
    @FXML
    public void employeesNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(true);
        controlPanelPane.setVisible(false);
        switchNavButton(navButton2);
    }


    @FXML
    public void controlPanelNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(true);
        switchNavButton(navButton3);
    }

    @FXML
    public void signOutClicked() {
        // TODO: sign out button clicked
    }
    /*               display Employee stuff                     */
    @FXML
    public void employeeDisplayClicked() {
        employeesTableViewFunctions.employeeDisplayClicked();
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
        AnimationFX minimizeAnimation = new ZoomOutDown(ap);
        minimizeAnimation.setOnFinished((event) -> {
            stage.setIconified(true);
        });

        minimizeAnimation.setSpeed(2).play();
    }

    private void switchNavButton(Button button) {
        int buttonIndex = Integer.parseInt(String.valueOf(button.getId().charAt(9))); // assume we have 9 buttons maximum;

        resetNavBarButtons();

        Pane higherPane = (Pane) stage.getScene().lookup("#radiusPane" + (buttonIndex-1) + "1");
        Pane lowerPane = (Pane) stage.getScene().lookup("#radiusPane" + (buttonIndex+1) + "1");
        Pane pHigherPane = (Pane) stage.getScene().lookup("#radiusPane" + (buttonIndex-1) + "0");
        Pane pLowerPane = (Pane) stage.getScene().lookup("#radiusPane" + (buttonIndex+1) + "0");
        higherPane.getStyleClass().add("higher-radius-activated");
        lowerPane.getStyleClass().add("lower-radius-activated");
        button.getStyleClass().add("selected-nav-button");

        higherPane.setVisible(true);
        lowerPane.setVisible(true);
        pHigherPane.setVisible(true);
        pLowerPane.setVisible(true);
    }
    private void resetNavBarButtons() {
        int index=0;
        Pane pane = (Pane) stage.getScene().lookup("#radiusPane" + (index) + "1");
        while(pane !=null) {
            pane.getStyleClass().removeAll("higher-radius-activated","lower-radius-activated");
            pane.setVisible(false);
            pane = (Pane) stage.getScene().lookup("#radiusPane" + (index) + "0");
            pane.setVisible(false);

            index++;
            pane = (Pane) stage.getScene().lookup("#radiusPane" + (index) + "1");
        }
        index = 1;
        Button button = (Button) stage.getScene().lookup("#navButton" + (index++));
        while(button != null) {
            button.getStyleClass().removeAll("selected-nav-button");
            button = (Button) stage.getScene().lookup("#navButton" + (index++));
        }
    }

}

