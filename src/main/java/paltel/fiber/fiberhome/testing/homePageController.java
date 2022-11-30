package paltel.fiber.fiberhome.testing;

import animatefx.animation.*;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import paltel.fiber.fiberhome.testing.objects.Employee;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.ResourceBundle;

import static paltel.fiber.fiberhome.testing.Functions.*;

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
        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********


        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<Employee> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(Employee::getEid));
        MFXTableColumn<Employee> FNameColumn = new MFXTableColumn<>("FName", false, Comparator.comparing(Employee::getFname));
        MFXTableColumn<Employee> MNameColumn = new MFXTableColumn<>("MName", false, Comparator.comparing(Employee::getMname));
        MFXTableColumn<Employee> LNameColumn = new MFXTableColumn<>("LName", false, Comparator.comparing(Employee::getLname));
        MFXTableColumn<Employee> districtColumn = new MFXTableColumn<>("District", false, Comparator.comparing(Employee::getDistrict));
        MFXTableColumn<Employee> jobPositionColumn = new MFXTableColumn<>("Job Position", false, Comparator.comparing(Employee::getJobPos));

        ObservableList<Employee> employees;

        // TODO: Get employees data here
        //                               v
        employees = FXCollections.observableArrayList(
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Ahmad","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin")
        );
        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getEid));
        FNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getFname));
        MNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getMname));
        LNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getLname));
        districtColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getDistrict));
        jobPositionColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getJobPos) {{
            setAlignment(Pos.CENTER_LEFT);
        }});
        employeesTable.getTableColumns().addAll(idColumn, FNameColumn, MNameColumn, LNameColumn, districtColumn,jobPositionColumn);
        employeesTable.getFilters().addAll(
                new StringFilter<>("ID", Employee::getEid),
                new StringFilter<>("FName", Employee::getFname),
                new StringFilter<>("MName", Employee::getMname),
                new StringFilter<>("LName", Employee::getLname),
                new StringFilter<>("District", Employee::getDistrict),
                new StringFilter<>("Job Position", Employee::getJobPos)
        );
        employeesTable.setItems(employees);
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
//        for(Node node: ap.getChildren()) {
//            node.setOpacity(0);
//        }
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
