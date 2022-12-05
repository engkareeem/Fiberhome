package paltel.fiber.fiberhome.testing.homecontroller;

import animatefx.animation.*;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import paltel.fiber.fiberhome.testing.Functions;
import paltel.fiber.fiberhome.testing.Navigator;
import paltel.fiber.fiberhome.testing.model.Contractor;
import paltel.fiber.fiberhome.testing.model.Employee;
import paltel.fiber.fiberhome.testing.model.Project;
import paltel.fiber.fiberhome.testing.model.User;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;
import static paltel.fiber.fiberhome.testing.DBapi.*;

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

    @FXML
    Pane employeeInfoPane;

    Pane currentPane;

    private boolean usedMinimize = false;



    /*                  Employee info         */
    @FXML
    Pane profileCard;
    @FXML
    Label employeeInfoEmpName,employeeInfoEmpId,employeeInfoEmpBirthdate,
            employeeInfoEmpAge,employeeInfoEmpJobPos,employeeInfoEmpDistrict,employeeInfoLastLogin;
    @FXML
    Label employeeInfoCurrentProjectName,employeeInfoCurrentProjectId,employeeInfoCurrentProjectContractor,
            employeeInfoCurrentProjectType,employeeInfoCurrentProjectStartDate,employeeInfoCurrentProjectDueDate
            ,employeeInfoCurrentProjectStreet,employeeInfoCurrentProjectCity;


    Stage stage;

    @FXML
    MFXScrollPane contractorListScrollPane;
    @FXML
    MFXScrollPane lastProjectsScrollPane;
    @FXML
    VBox contractorListScrollPaneVbox;
    @FXML
    VBox lastProjectsScrollPaneVbox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        playOpenAnimation();
        saveLastLogin();
        stage = Navigator.primaryStage;

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                stage.centerOnScreen();
            }
        });

        switchNavButton(navButton2);
        Functions.move(stage,titleBar);
        Functions.optimizeImageView(backgroundImageView);
        setupTable();

        stage.iconifiedProperty().addListener((observableValue, aBoolean, iconified) -> {
            if(iconified && !usedMinimize) {

                new ZoomOutDown(ap).setSpeed(2).play();
                usedMinimize = false;

            }else{
                new ZoomInUp(ap).setSpeed(2).play();

            }
        });

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
        currentPane = dashboardPane;
    }
    @FXML
    public void employeesNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(true);
        controlPanelPane.setVisible(false);
        switchNavButton(navButton2);
        currentPane = employeesPane;
    }


    @FXML
    public void controlPanelNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(true);
        switchNavButton(navButton3);
        currentPane = controlPanelPane;
    }

    @FXML
    public void signOutClicked() {
        AnimationFX closeAnimation = new ZoomOutUp(ap);
        closeAnimation.setOnFinished((event) -> {
            Platform.exit();
            System.exit(0);
        });
        closeAnimation.play();

    }
    /*               display Employee stuff                     */

    /*

    @FXML
    Label employeeInfoEmpName,employeeInfoEmpId,employeeInfoBirthdate,
            employeeInfoEmpAge,employeeInfoJobPos,employeeInfoEmpDistrict,employeeInfoLastLogin;
    */
    int getAge(Date firstDate, Date secondDate) {
        Calendar firstYear = getCalendar(firstDate);
        Calendar secondYear = getCalendar(secondDate);
        int numOfYears = secondYear.get(YEAR) - firstYear.get(YEAR);
        if (firstYear.get(MONTH) > secondYear.get(MONTH) ||
                (firstYear.get(MONTH) == secondYear.get(MONTH) && firstYear.get(DATE) > secondYear.get(DATE))) {
            numOfYears--;
        }
        return numOfYears;
    }
    Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        return calendar;
    }


    @FXML
    public void tableAddEmployeeClicked() {
        Functions.showAddEmployeePopup();
    }

    @FXML
    public void employeeInfoClose() {
        employeeInfoPane.setVisible(false);
        employeesPane.setVisible(true);
    }
    @FXML
    public void employeeInfoEditButtonClicked() {
        switchToEdit();
    }
    @FXML
    public void employeeDisplayClicked() {

        Employee employee = employeesTableViewFunctions.employeeDisplayClicked();
        if(employee == null) return;
        SimpleDateFormat birthdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat projectDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat lastLoginFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm aa");

        new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    User user = getUserInfo(employee.getEid());
                    employeeInfoLastLogin.setText(lastLoginFormat.format(user.getLastLogin()));
                }
            });

        }).start();

        new Thread(() -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Project project = getCurrentProject(employee.getEid());
                    if(project.getProjectId().isEmpty()){
                        employeeInfoCurrentProjectName.setText("");
                        employeeInfoCurrentProjectCity.setText("");
                        employeeInfoCurrentProjectType.setText("");
                        employeeInfoCurrentProjectId.setText("");
                        employeeInfoCurrentProjectStartDate.setText("");
                        employeeInfoCurrentProjectStreet.setText("");
                        employeeInfoCurrentProjectDueDate.setText("");
                        employeeInfoCurrentProjectContractor.setText("");

                    }else{
                        Contractor contractor = getContractorInfo(project.getContractorId());
                        employeeInfoCurrentProjectName.setText(project.getCity() + " " + project.getProjType());
                        employeeInfoCurrentProjectCity.setText("");
                        employeeInfoCurrentProjectType.setText("");
                        employeeInfoCurrentProjectId.setText(project.getProjectId());
                        employeeInfoCurrentProjectStartDate.setText(projectDateFormat.format(project.getStartDate()));
                        employeeInfoCurrentProjectDueDate.setText(projectDateFormat.format(project.getDueDate()));
                        employeeInfoCurrentProjectStreet.setText(project.getCity() + " - " + (project.getStreet().isEmpty() ? "" : project.getStreet()));
                        employeeInfoCurrentProjectContractor.setText("contractor: " + contractor.getFname() + " " + contractor.getLname());
                    }


                }
            });

        }).start();
        /*
        @FXML
        Label employeeInfoCurrentProjectName,employeeInfoCurrentProjectId,employeeInfoCurrentProjectContractor,
                employeeInfoCurrentProjectType,employeeInfoCurrentProjectStartDate,employeeInfoCurrentProjectDueDate
                ,employeeInfoCurrentProjectStreet,employeeInfoCurrentProjectCity;

     */

        employeeInfoEmpName.setText(employee.getFname() + " " + employee.getMname() + " " + employee.getLname());
        employeeInfoEmpId.setText(employee.getEid());
        employeeInfoEmpBirthdate.setText(birthdateFormat.format(employee.getBirthdate()));
        employeeInfoEmpJobPos.setText(employee.getJobPos());
        employeeInfoEmpAge.setText(getAge(employee.getBirthdate(), new Date()) + " yo");
        employeeInfoEmpDistrict.setText(employee.getDistrict());


        employeesPane.setVisible(false);
        employeeInfoPane.setVisible(true);



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
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
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
        });

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





    public void addRow() {
        // this function for a test button I deleted it :#


        // contractor example
        // 40,150,75 is the labels size respectively (ID,NAME,TYPE)
//        enhancedScrollPane.addRow(contractorListScrollPaneVbox,"9395","Amjad fauore","Electricity",40,150,75);

        // employee projects example
        enhancedScrollPane.addRow(lastProjectsScrollPaneVbox,"1919","Bidya Fiber","Bidya",35,220,72);

    }

    public void switchToEdit() {
        employeeInfoEmpName.setVisible(false);
        Label []labels = {employeeInfoEmpName,employeeInfoEmpJobPos,employeeInfoEmpDistrict};
        for(Label label:labels) {
            TextField textField = new TextField();
            profileCard.getChildren().add(textField);
            textField.setLayoutX(label.getLayoutX());
            textField.setLayoutY(label.getLayoutY());
            textField.setMaxWidth(label.getWidth());
            textField.getStyleClass().add("info-field");
            label.setVisible(false);
        }
    }

}

