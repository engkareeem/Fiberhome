package paltel.fiber.fiberhome.testing.homecontroller;

import animatefx.animation.*;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
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
    Button dashboardNavButton,employeesNavButton,controlPanelNavButton;
    @FXML
    MFXPaginatedTableView<Employee> employeesTable;

    Stage stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playOpenAnimation();
        saveLastLogin();
        stage = Navigator.primaryStage;
        Functions.move(stage,titleBar);
        optimizeImageView(backgroundImageView);
        setupTable();
    }
    private void setupTable() {
        initializeTableView(employeesTable);
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
    @FXML
    public void dashboardNavButtonClicked() {
        dashboardPane.setVisible(true);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(false);
        switchNavButton(dashboardNavButton);

    }
    @FXML
    public void employeesNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(true);
        controlPanelPane.setVisible(false);
        switchNavButton(employeesNavButton);

    }


    @FXML
    public void controlPanelNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(true);
        switchNavButton(controlPanelNavButton);
    }

    /*              Employees table view stuff                      */

    @FXML
    public void employeeDisplayClicked() {
        IMultipleSelectionModel<Employee> selectionModel = employeesTable.getSelectionModel();
        Collection<Employee> selected = selectionModel.getSelection().values();
        Employee emp = (Employee) selected.toArray()[0];
        // TODO: Selected employee in employees tableview
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
        if(button.equals(dashboardNavButton)) {
            dashboardNavButton.getStyleClass().add("selected-nav-button");
            employeesNavButton.getStyleClass().removeAll("selected-nav-button");
            controlPanelNavButton.getStyleClass().removeAll("selected-nav-button");
        } else if(button.equals(employeesNavButton)) {
            dashboardNavButton.getStyleClass().removeAll("selected-nav-button");
            employeesNavButton.getStyleClass().add("selected-nav-button");
            controlPanelNavButton.getStyleClass().removeAll("selected-nav-button");
        } else if(button.equals(controlPanelNavButton)) {
            dashboardNavButton.getStyleClass().removeAll("selected-nav-button");
            employeesNavButton.getStyleClass().removeAll("selected-nav-button");
            controlPanelNavButton.getStyleClass().add("selected-nav-button");
        }
    }

}

