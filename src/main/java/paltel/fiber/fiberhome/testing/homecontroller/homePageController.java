package paltel.fiber.fiberhome.testing.homecontroller;

import animatefx.animation.*;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    VBox homeNavBarVBox;
    @FXML
    ImageView backgroundImageView;
    @FXML
    Pane titleBar;
    @FXML
    Pane dashboardPane,employeesPane,controlPanelPane,projectsPane;
    @FXML
    Button navButton1,navButton2,navButton3,navButton4;
    @FXML
    MFXPaginatedTableView<Employee> employeesTable;

    @FXML
    Pane employeeInfoPane;

    Pane currentPane;

    private boolean usedMinimize = false;


    /*                    Employees page statics         */

    @FXML
    Label totalEmployeesCountLabel,totalContractorCountLabel,totalWorkingEmployeesCountLabel,totalUsersCountLabel;

    /*                  Employee info         */
    @FXML
    Pane profileCard;
    @FXML
    Button employeeInfoEditButton;
    boolean employeeInfoOnEdit = false;
    @FXML
    Label employeeInfoEmpName,employeeInfoEmpId,employeeInfoEmpBirthdate,
            employeeInfoEmpAge,employeeInfoEmpJobPos,employeeInfoEmpDistrict,employeeInfoLastLogin;
    @FXML
    Label employeeInfoCurrentProjectName,employeeInfoCurrentProjectId,employeeInfoCurrentProjectContractor,
            employeeInfoCurrentProjectType,employeeInfoCurrentProjectStartDate,employeeInfoCurrentProjectDueDate
            ,employeeInfoCurrentProjectStreet,employeeInfoCurrentProjectCity,employeeInfoLastProjectsLabel;

    /*               User info              */

    @FXML
    Label userInfoEmpName,userInfoEmpId,userInfoNickName,userInfoRole,userInfoChangePassword;
    @FXML
    Button userInfoEditButton,employeeInfoAssignToProjectButton;
    @FXML
    Pane userInfoPane;
    @FXML
    Pane userProfileCard;

    /*                 Contractor info           */
    @FXML
    Label contractorInfoContName,contractorInfoContId,contractorInfoContType,contractorInfoContBirthdate,contractorInfoContAge;
    @FXML
    Label contractorInfoProjectName,contractorInfoProjectId,contractorInfoProjectContractor,contractorInfoProjectType
            ,contractorInfoProjectStartDate,contractorInfoProjectDueDate,contractorInfoProjectStreet,contractorInfoProjectCity
            ,contractorInfoCurrentProjectsLabel;
    @FXML
    MFXScrollPane currentProjectsScrollPane;
    @FXML
    VBox currentProjectsScrollPaneVbox;
    @FXML
    Pane contractorProfileCard;
    @FXML
    Button contractorInfoEditButton;

    @FXML
    Pane contractorInfoPane;
    boolean userInfoOnEdit = false;



    /*                  Projects Page                */
    @FXML
    MFXPaginatedTableView<Project> projectsTable;
    @FXML
    MFXScrollPane warehouseListScrollPane;
    @FXML
    VBox warehouseListScrollPaneVbox;
    Stage stage;
    /*                 Project info            */
    @FXML
    Label projectInfoProjId,projectInfoProjType,projectInfoProjAmount,projectInfoStartDate,projectInfoDueDate,projectInfoStreet,projectInfoCity;
    @FXML
    Button projectInfoEditButton;
    @FXML
    Label projectInfoContName,projectInfoContId,projectInfoContType,projectInfoContBirthdate,projectInfoContAge;
    @FXML
    Label projectInfoPartsUsedLabel;
    @FXML
    Pane projectProfileCard,projectContCard,partsUsedCard,projectInfoPane;
    @FXML
    MFXScrollPane partsUsedScrollPane;
    @FXML
    VBox partsUsedScrollPaneVbox;

    @FXML
    MFXScrollPane contractorListScrollPane;
    @FXML
    MFXScrollPane lastProjectsScrollPane;
    @FXML
    VBox contractorListScrollPaneVbox;
    @FXML
    VBox lastProjectsScrollPaneVbox;

    @FXML
    Pane currentProjectCard;

    @FXML
    Pane lastProjectsCard;

    @FXML
    PieChart pieChart;

    boolean contractorInfoOnEdit = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        pieChart.setData(pieChartData);
        pieChart.setLabelsVisible(false);

        ap.setOpacity(0);
        Platform.runLater(this::playOpenAnimation);

        currentPane = employeesPane;
        updateLastLoginTime((String) Navigator.getValue("eid"));
        stage = Navigator.primaryStage;

        Platform.runLater(() -> stage.centerOnScreen());

        switchNavButton(navButton2);
        Functions.move(stage,titleBar);
//        Functions.optimizeImageView(backgroundImageView);
        employeesTableViewFunctions.initializeTableView(employeesTable);
        projectsTableViewFunctions.initializeTableView(projectsTable);
        setupContractorsTable();
        setUpStatisticsBlocks();


        stage.iconifiedProperty().addListener((observableValue, aBoolean, iconified) -> {
            if(iconified && !usedMinimize) {

                new ZoomOutDown(ap).setSpeed(2).play();
                usedMinimize = false;

            }else{
                new ZoomInUp(ap).setSpeed(2).play();

            }
        });

    }


    private void setupContractorsTable(){
        enhancedScrollPane.resetRows(contractorListScrollPaneVbox);
        ArrayList<Contractor> contractors = getAllContractors();
        contractors.forEach(contractor -> {
            enhancedScrollPane.addRow(contractorListScrollPaneVbox, contractor.getContractorId(), contractor.getFname() + " " + contractor.getMname() + " " + contractor.getLname(),
                    contractor.getContractorType(), 35, 150 ,85, Functions.ListType.CONT_LIST,currentPane,homeNavBarVBox,contractorInfoPane);

        });
    }

    private void setUpStatisticsBlocks(){
        new Thread(() -> {
            Platform.runLater(() -> totalEmployeesCountLabel.setText(getEmployeesCount().toString()));
        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalUsersCountLabel.setText(getUsersCount().toString()));
        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalContractorCountLabel.setText(getContractorCount().toString()));
        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalWorkingEmployeesCountLabel.setText(getWorkingEmployeesCount().toString()));
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
        projectsPane.setVisible(false);
        switchNavButton(navButton1);
        currentPane = dashboardPane;
    }
    @FXML
    public void employeesNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(true);
        controlPanelPane.setVisible(false);
        projectsPane.setVisible(false);
        switchNavButton(navButton2);
        currentPane = employeesPane;
    }


    @FXML
    public void controlPanelNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(true);
        projectsPane.setVisible(false);
        switchNavButton(navButton4);
        currentPane = controlPanelPane;
    }
    
    @FXML
    public void projectsNavButtonClicked() {
        dashboardPane.setVisible(false);
        employeesPane.setVisible(false);
        controlPanelPane.setVisible(false);
        projectsPane.setVisible(true);
        switchNavButton(navButton3);
        currentPane = projectsPane;
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
    public void tableRemoveEmployeeClicked() {
        if(employeesTableViewFunctions.getSelectedRow() == null) return;
        Employee selectedEmployee = employeesTableViewFunctions.getSelectedRow();
        Functions.showDialog("Are you sure you want to delete  \"" + selectedEmployee.getFname() + " " + selectedEmployee.getLname() +"\" records?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                // TODO: [remove Employee] clicked with confirm
            }

        }).start();
    }
    @FXML
    public void employeeInfoClose() {
        employeeInfoPane.setVisible(false);
        employeesPane.setVisible(true);
        homeNavBarVBox.setDisable(false);
        enhancedScrollPane.resetRows(lastProjectsScrollPaneVbox);
        switchFromEdit();
    }

    @FXML
    public void employeeInfoEditButtonClicked() {
        if(!employeeInfoOnEdit) {
            switchToEdit();
        } else {
            // TODO: [Edit Employee] update employee information here
            // i will add the validator if الله قدرني

            switchFromEdit();
        }
    }




    @FXML
    public void employeeDisplayClicked() {

        Employee employee = employeesTableViewFunctions.getSelectedRow();
        enhancedScrollPane.resetRows(lastProjectsScrollPaneVbox);
        if (employee == null) return;
        SimpleDateFormat birthdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat projectDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat lastLoginFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm aa");

        new Thread(() -> {
            Platform.runLater(() -> {
                User user = getUserInfo(employee.getEid());
                if (user == null || user.getLastLogin() == null) {
                    employeeInfoLastLogin.setText("Never");
                } else {
                    employeeInfoLastLogin.setText(lastLoginFormat.format(user.getLastLogin()));
                }
            });

        }).start();

        if (employee.getJobPos().equals("Technician") || employee.getJobPos().equals("Project Monitor") || employee.getJobPos().equals("Project Manager")) {
            new Thread(() -> {
                Platform.runLater(() -> {
                    lastProjectsCard.setVisible(true);
                    Project project = getCurrentProject(employee.getEid());
                    if (project == null) {
                        currentProjectCard.setVisible(false);
                        employeeInfoAssignToProjectButton.setVisible(true);


                    } else {
                        employeeInfoAssignToProjectButton.setVisible(false);
                        currentProjectCard.setVisible(true);
                        Contractor contractor = getContractorInfo(project.getContractorId());
                        employeeInfoCurrentProjectName.setText(project.getCity() + " " + project.getProjType());
                        employeeInfoCurrentProjectCity.setText("");
                        employeeInfoCurrentProjectType.setText("");
                        employeeInfoCurrentProjectId.setText(project.getProjectId());
                        employeeInfoCurrentProjectStartDate.setText(projectDateFormat.format(project.getStartDate()));
                        employeeInfoCurrentProjectDueDate.setText(projectDateFormat.format(project.getDueDate()));
                        employeeInfoCurrentProjectStreet.setText(project.getCity() + " - " + (project.getStreet() == null ? "" : project.getStreet()));
                        if (contractor == null) {
                            employeeInfoCurrentProjectContractor.setText("No contractor.");
                        } else {
                            employeeInfoCurrentProjectContractor.setText("contractor: " + contractor.getFname() + " " + contractor.getLname());

                        }
                    }


            });

            }).start();

            new Thread(() -> {
                Platform.runLater(() -> {
                    ArrayList<Project> recentFinishedProjects = getRecentFinishedProjects(employee.getEid());
                    recentFinishedProjects.forEach(project -> {
                        enhancedScrollPane.addRow(lastProjectsScrollPaneVbox, project.getProjectId(), project.getCity() + " " + project.getProjType(), project.getCity() + (project.getStreet() == null ? "" : " - " + project.getStreet()), 32, 165, 125, Functions.ListType.LAST_PROJECTS_LIST);

                    });

                });
            }).start();


        }else { // this employee doesn't work on projects
            employeeInfoLastProjectsLabel.setVisible(false);
            employeeInfoAssignToProjectButton.setVisible(false);
            currentProjectCard.setVisible(false);
            lastProjectsCard.setVisible(false);
        }
        employeeInfoEmpName.setText(employee.getFname() + " " + employee.getMname() + " " + employee.getLname());
        employeeInfoEmpId.setText(employee.getEid());
        employeeInfoEmpBirthdate.setText(birthdateFormat.format(employee.getBirthdate()));
        employeeInfoEmpJobPos.setText(employee.getJobPos());
        employeeInfoEmpAge.setText(getAge(employee.getBirthdate(), new Date()) + " yo");
        employeeInfoEmpDistrict.setText(employee.getDistrict());


        employeesPane.setVisible(false);
        employeeInfoPane.setVisible(true);

        homeNavBarVBox.setDisable(true);

    }

    /*               Contractor info          */

    @FXML
    public void contractorInfoEditButtonClicked() {
        // TODO: contractor info edit button clicked
        if(!contractorInfoOnEdit) {
            contractorSwitchToEdit();
        } else {

            // TODO: update contractor info here

            contractorSwitchFromEdit();
        }
    }
    @FXML
    public void contractorInfoDeleteButtonClicked() {
        Functions.showDialog("Are you sure you want to delete  \"" + contractorInfoContName.getText() +"\" records?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                // TODO: [remove Contractor] clicked with confirm
            }

        }).start();
    }
    @FXML
    public void contractorInfoClose() {
        contractorInfoPane.setVisible(false);
        currentPane.setVisible(true);
        homeNavBarVBox.setDisable(false);
        contractorSwitchFromEdit();
    }

    /*             User info                  */
    @FXML
    public void userInfoClicked() {
        currentPane.setVisible(false);
        userInfoPane.setVisible(true);
        homeNavBarVBox.setDisable(true);
    }
    @FXML
    public void userInfoClose() {
        userInfoPane.setVisible(false);
        currentPane.setVisible(true);
        homeNavBarVBox.setDisable(false);
        userSwitchFromEdit();
    }
    @FXML
    public void userInfoEditButtonClicked() {
        if(!userInfoOnEdit) {
            userSwitchToEdit();
        } else {

            // TODO: [Edit User] Update user information here :3
            // check if there is change password text field
            // by lookup == null

            userSwitchFromEdit();
        }
    }
    @FXML
    public void employeeInfoAssignToProjectButtonClicked() {
        // TODO: assign to project button clicked
    }
    @FXML
    public void userChangePasswordClicked() {
        TextField textField = new TextField();
        profileCard.getChildren().add(textField);
        textField.setLayoutX(userInfoChangePassword.getLayoutX());
        textField.setLayoutY(userInfoChangePassword.getLayoutY());
        textField.setMaxWidth(userInfoChangePassword.getWidth());
        textField.getStyleClass().add("info-field");
        textField.setId("editTextField" + userInfoChangePassword.getId());
        textField.setPromptText("New Password");
        userProfileCard.getChildren().add(textField);
        userInfoChangePassword.setVisible(false);
    }

    /*                           Projects Page                 */

    @FXML
    public void tableRemoveProjectClicked() {
        if(projectsTableViewFunctions.getSelectedRow() == null) return;
        Project selectedProject = projectsTableViewFunctions.getSelectedRow();
        Functions.showDialog("Are you sure you want to delete  \"" + selectedProject.getProjectId() +"\" records from database?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                // TODO: [remove Project] clicked with confirm
            }

        }).start();
    }
    @FXML
    public void tableAddProjectClicked() {


    }
    @FXML
    public void tableDisplayProjectClicked() {
        projectsPane.setVisible(false);
        homeNavBarVBox.setDisable(true);
        projectInfoPane.setVisible(true);

        // TODO: Project info initialize
    }


    /*                  Project Info                */
    @FXML
    public void projectInfoEditButtonClicked() {

    }
    @FXML
    public void projectInfoClose() {
        projectsPane.setVisible(true);
        homeNavBarVBox.setDisable(false);
        projectInfoPane.setVisible(false);
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
        Platform.runLater(() -> {
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
        enhancedScrollPane.addRow(lastProjectsScrollPaneVbox,"1919","Bidya Fiber","Bidya",35,220,72, Functions.ListType.LAST_PROJECTS_LIST);

    }

    public void switchToEdit() {
        Label []labels = {employeeInfoEmpDistrict};
        TextField textField;
        textField = new TextField();
        textField.setLayoutX(employeeInfoEmpName.getLayoutX());
        textField.setLayoutY(employeeInfoEmpName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("First");
        textField.setId("editTextField" + "FirstName");
        profileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(employeeInfoEmpName.getLayoutX() + 85);
        textField.setLayoutY(employeeInfoEmpName.getLayoutY());
        textField.setMaxWidth(60);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Middle");
        textField.setId("editTextField" + "MiddleName");
        profileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(employeeInfoEmpName.getLayoutX() + 155);
        textField.setLayoutY(employeeInfoEmpName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Last");
        textField.setId("editTextField" + "LastName");
        profileCard.getChildren().add(textField);

        employeeInfoEmpName.setVisible(false);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLayoutX(employeeInfoEmpJobPos.getLayoutX());
        comboBox.setLayoutY(employeeInfoEmpJobPos.getLayoutY());
        comboBox.setPrefWidth(employeeInfoEmpJobPos.getWidth());
        comboBox.setPromptText(employeeInfoEmpJobPos.getText());
        comboBox.setId("editTextField" + employeeInfoEmpJobPos.getId());
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList(getJobPositions());
        comboBox.setItems(comboBoxItems);

        profileCard.getChildren().add(comboBox);
        employeeInfoEmpJobPos.setVisible(false);


        for(Label label:labels) {
            textField = new TextField();
            textField.setLayoutX(label.getLayoutX());
            textField.setLayoutY(label.getLayoutY());
            textField.setMaxWidth(label.getWidth());
            textField.getStyleClass().add("info-field");
            textField.setText(label.getText());
            textField.setId("editTextField" + label.getId());
            profileCard.getChildren().add(textField);
            label.setVisible(false);
        }
        employeeInfoEditButton.setText("Submit");
        employeeInfoOnEdit=true;
    }
    public void switchFromEdit() {
        if(!employeeInfoOnEdit) return;
        Label []labels = {employeeInfoEmpName,employeeInfoEmpJobPos,employeeInfoEmpDistrict};
        profileCard.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("editTextField"));
        for(Label label: labels) {
            label.setVisible(true);
        }
        // TODO: Refresh the labels :3

        employeeInfoEditButton.setText("Edit");
        employeeInfoOnEdit=false;

    }


    public void userSwitchToEdit() {
        TextField textField = new TextField();
        profileCard.getChildren().add(textField);
        textField.setLayoutX(userInfoNickName.getLayoutX());
        textField.setLayoutY(userInfoNickName.getLayoutY());
        textField.setMaxWidth(userInfoNickName.getWidth());
        textField.getStyleClass().add("info-field");
        textField.setText(userInfoNickName.getText());
        textField.setId("editTextField" + userInfoNickName.getId());
        userProfileCard.getChildren().add(textField);
        userInfoNickName.setVisible(false);

        userInfoChangePassword.setVisible(true);
        userInfoEditButton.setText("Submit");
        userInfoOnEdit=true;

    }
    public void userSwitchFromEdit() {
        if(!userInfoOnEdit) return;
        userInfoOnEdit = false;
        userProfileCard.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("editTextField"));
        userInfoNickName.setVisible(true);

        userInfoEditButton.setText("Edit");

        userInfoChangePassword.setVisible(false);

    }

    public void contractorSwitchToEdit() {
        TextField textField;
        textField = new TextField();
        textField.setLayoutX(contractorInfoContName.getLayoutX());
        textField.setLayoutY(contractorInfoContName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("First");
        textField.setId("editTextField" + "FirstName");
        contractorProfileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(contractorInfoContName.getLayoutX() + 85);
        textField.setLayoutY(contractorInfoContName.getLayoutY());
        textField.setMaxWidth(60);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Middle");
        textField.setId("editTextField" + "MiddleName");
        contractorProfileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(contractorInfoContName.getLayoutX() + 155);
        textField.setLayoutY(contractorInfoContName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Last");
        textField.setId("editTextField" + "LastName");
        contractorProfileCard.getChildren().add(textField);

        contractorInfoContName.setVisible(false);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLayoutX(contractorInfoContType.getLayoutX());
        comboBox.setLayoutY(contractorInfoContType.getLayoutY());
        comboBox.setPrefWidth(contractorInfoContType.getWidth());
        comboBox.setPromptText(contractorInfoContType.getText());
        comboBox.setId("editTextField" + contractorInfoContType.getId());
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList(getContractorTypes());
        comboBox.setItems(comboBoxItems);

        contractorProfileCard.getChildren().add(comboBox);
        contractorInfoContType.setVisible(false);

        contractorInfoEditButton.setText("Submit");
        contractorInfoOnEdit=true;
    }
    public void contractorSwitchFromEdit() {
        if(!contractorInfoOnEdit) return;
        Label []labels = {contractorInfoContName,contractorInfoContType};
        contractorProfileCard.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("editTextField"));
        for(Label label: labels) {
            label.setVisible(true);
        }
        // TODO: Refresh the labels :3

        contractorInfoEditButton.setText("Edit");
        contractorInfoOnEdit=false;

    }


}

