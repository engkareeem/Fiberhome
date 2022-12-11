package paltel.fiber.fiberhome.testing.homecontroller;

import animatefx.animation.*;
import eu.hansolo.medusa.Gauge;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import paltel.fiber.fiberhome.testing.DBapi;
import paltel.fiber.fiberhome.testing.Functions;
import paltel.fiber.fiberhome.testing.Navigator;
import paltel.fiber.fiberhome.testing.model.*;
import paltel.fiber.fiberhome.testing.testingMain;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;
import static paltel.fiber.fiberhome.testing.DBapi.*;

public class homePageController implements Initializable {



    @FXML
    Label userNickNameLabel,userRoleLabel;

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

    Pane currentPane,prevCurrentPane = null;

    private boolean usedMinimize = false;


    /*                    Employees page statics         */

    @FXML
    Label totalEmployeesCountLabel,totalContractorCountLabel,totalWorkingEmployeesCountLabel,totalUsersCountLabel;

    /*                  Employee info         */
    @FXML
    Pane currentProjectCard,currentProjectCard1;
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

    public static MFXPaginatedTableView<Employee> employeesTableView;

    /*               User info              */

    @FXML
    Label userInfoEmpName,userInfoEmpId,userInfoNickName,userInfoRole,userInfoChangePassword,employeeInfoAssignProjectIdLabel;
    @FXML
    TextField employeeInfoAssignTextField;
    @FXML
    Button userInfoEditButton,employeeInfoAssignToProjectButton,employeeInfoAssignButton;
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
    Label contractorInfoAssignProjectIdLabel;
    @FXML
    TextField contractorInfoAssignTextField;
    @FXML
    MFXScrollPane currentProjectsScrollPane;
    @FXML
    VBox currentProjectsScrollPaneVbox;
    @FXML
    Pane contractorProfileCard;
    @FXML
    Button contractorInfoEditButton,contractorInfoAssignToProject,contractorInfoAssign;

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
    Label totalProjectsCountLabel,totalWarehouseCountLabel,totalRunningProjectsCountLabel,totalFinishedProjectsCountLabel;
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
    /*                Warehouse info                   */
    @FXML
    Label warehouseInfoWid, warehouseInfoWLocation,warehouseInfoWCapacityCount;
    @FXML
    Gauge warehouseInfoWCapacity;
    @FXML
    PieChart warehouseInfoWStorage;
    @FXML
    MFXScrollPane warehouseInfoPartsScrollPane;
    @FXML
    VBox warehouseInfoPartsScrollPaneVbox;
    @FXML
    Pane warehouseInfoPane;
    @FXML
    MFXScrollPane contractorListScrollPane;
    @FXML
    MFXScrollPane lastProjectsScrollPane;
    @FXML
    VBox contractorListScrollPaneVbox;
    @FXML
    VBox lastProjectsScrollPaneVbox;
    /*                              Control panel Page                         */
    @FXML
    MFXPaginatedTableView<User> controlPanelUsersTableView;
    @FXML
    MFXPaginatedTableView<Supplier> controlPanelSuppliersTableView;
    @FXML
    MFXPaginatedTableView<Warehouse> controlPanelWarehousesTableView;
    @FXML
    Label controlPanelTotalActiveUsers,controlPanelTotalEmployeeAccounts,controlPanelTotalAdminAccounts,controlPanelTotalPendingAccounts;
    @FXML
    Label controlPanelTotalSuppliers,controlPanelTotalProducts;
    @FXML
    Label controlPanelTotalWarehousesNumber,controlPanelTotalWarehousesBudget,controlPanelTotalStoredParts,controlPanelTotalFreeSpace;

    @FXML
    MFXScrollPane controlPanelPendingUsersScrollPane,controlPanelCheapestProductScrollPane,controlPanelWarehouseProjectsScrollPane;
    @FXML
    VBox controlPanelPendingUsersScrollPaneVbox,controlPanelCheapestProductScrollPaneVbox,controlPanelWarehouseProjectsScrollPaneVbox;

    @FXML
    Pane lastProjectsCard;
    /*                  Supplier Info                   */
    @FXML
    Pane supplierInfoPane;
    @FXML
    Pane supplierInfoProfileCard;
    @FXML
    MFXScrollPane offersProductsScrollPane;
    @FXML
    VBox offersProductsScrollPaneVbox;
    @FXML
    Label supplierInfoCompanyName,supplierInfoSupplierId;
    @FXML
    Button supplierInfoEditButton;

    Employee currentEmployeeProfilePage;

    boolean contractorInfoOnEdit = false;
    boolean supplierInfoOnEdit = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warehouseInfoWStorage.setLegendSide(Side.LEFT);
        warehouseInfoWStorage.setLabelsVisible(false);


        employeesTableView = employeesTable;
        currentPane = employeesPane;
        updateLastLoginTime((String) Navigator.getValue("eid"));
        stage = Navigator.primaryStage;
        ap.setOpacity(0);
        Platform.runLater(() -> {
            stage.centerOnScreen();
            playOpenAnimation();
        });

        switchNavButton(navButton2);
        Functions.move(stage,titleBar);
//        Functions.optimizeImageView(backgroundImageView);
        employeesTableViewFunctions.initializeTableView(employeesTable);
        projectsTableViewFunctions.initializeTableView(projectsTable);
        usersTableViewFunctions.initializeTableView(controlPanelUsersTableView);
        suppliersTableViewFunctions.initializeTableView(controlPanelSuppliersTableView);
        warehousesTableViewFunctions.initializeTableView(controlPanelWarehousesTableView);
        setupContractorsTable();
//        setUpStatisticsBlocks();
        Functions.optimizeImageView(backgroundImageView);


        new Thread(() -> {
            Platform.runLater(() -> {

                employeesTableViewFunctions.initializeTableView(employeesTable);
                setupContractorsTable();
                setUpEmployeeStatisticsBlocks();
            });
        }).start();

        new Thread(() -> {
            Platform.runLater(() -> {
                projectsTableViewFunctions.initializeTableView(projectsTable);
                setupWarehouseTable();
            });
        }).start();

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
            addContRow(contractor.getContractorId(), contractor.getFname() + " " + contractor.getMname() + " " + contractor.getLname(),contractor.getContractorType());
        });
    }

    private void setupWarehouseTable(){
        enhancedScrollPane.resetRows(warehouseListScrollPaneVbox);
        ArrayList<Warehouse> warehouses = getAllWarehouses();
        warehouses.forEach(warehouse -> {
            addWarehouseRow(warehouse.getWarehouseId(), warehouse.getCity(), String.valueOf(warehouse.getCapacity()));

        });
    }

    private void setUpEmployeeStatisticsBlocks(){
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                new Thread(() -> Platform.runLater(() -> {
                    deleteEmployee(selectedEmployee.getEid());
                    employeesTableViewFunctions.initializeTableView(employeesTable);
                })).start();
            }

        }).start();
    }
    @FXML
    public void employeeInfoClose() {
        employeeInfoPane.setVisible(false);
        employeesPane.setVisible(true);
        homeNavBarVBox.setDisable(false);
        employeeInfoAssignProjectIdLabel.setVisible(false);
        employeeInfoAssignTextField.setVisible(false);
        employeeInfoAssignButton.setVisible(false);
        enhancedScrollPane.resetRows(lastProjectsScrollPaneVbox);
        switchFromEdit();
    }
    @FXML
    public void employeeInfoRemoveFromProjectClicked() {
        Functions.showDialog("Are you sure you want to remove this employee from the project?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if(Functions.confirmFlag) {
                Platform.runLater(() -> {
                    removeEmployeeFromCurrentProject();

                });
            }

        }).start();
    }
    @FXML
    public void employeeInfoEditButtonClicked() {
        if(!employeeInfoOnEdit) {
            switchToEdit();
        } else {
            switchFromEdit();
        }
    }




    @FXML
    public void employeeDisplayClicked() {



        Employee employee = employeesTableViewFunctions.getSelectedRow();
        currentEmployeeProfilePage = employee;
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

            lastProjectsCard.setVisible(true);
            Project project = getCurrentProject(employee.getEid());
            if (project == null) {
                currentProjectCard1.setVisible(false);
                employeeInfoAssignToProjectButton.setVisible(true);
            } else {
                employeeInfoAssignToProjectButton.setVisible(false);
                currentProjectCard.setVisible(true);
                currentProjectCard1.setVisible(true);
                Contractor contractor = getContractorInfo(project.getContractorId());
                employeeInfoCurrentProjectName.setText(project.getCity() + " " + project.getProjType());
                employeeInfoCurrentProjectCity.setText("");
                employeeInfoCurrentProjectType.setText("");
                employeeInfoCurrentProjectId.setText(project.getProjectId());
                employeeInfoCurrentProjectStartDate.setText(projectDateFormat.format(project.getStartDate()));
                employeeInfoCurrentProjectDueDate.setText(projectDateFormat.format(project.getDueDate()));
                employeeInfoCurrentProjectStreet.setText(project.getCity() + (project.getStreet() == null ? "" : " - " + project.getStreet()));
                if (contractor == null) {
                    employeeInfoCurrentProjectContractor.setText("No contractor.");
                } else {
                    employeeInfoCurrentProjectContractor.setText("Cont. : " + contractor.getFname() + " " + contractor.getLname());

                }
            }



                    ArrayList<Project> recentFinishedProjects = getRecentFinishedProjects(employee.getEid());
                    recentFinishedProjects.forEach(recentProject -> {
                        addProjRow(recentProject.getProjectId(), recentProject.getCity() + " " + recentProject.getProjType(), recentProject.getCity() + (recentProject.getStreet() == null ? "" : " - " + recentProject.getStreet()));
                    });


        }else { // this employee can't work on projects
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
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {

                contractorInfoClose();
                new Thread(() -> Platform.runLater(() -> {
                    deleteContractor(contractorInfoContId.getText());
                    setupContractorsTable();
                })).start();

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

    /*                                      User info                                       */
    @FXML
    public void userInfoClicked() {
        new Thread(
                () -> {
                    Platform.runLater(() -> {
                        User user = getUserInfo((String) Navigator.getValue("eid"));
                        Employee emp = getEmployeeInfo((String) Navigator.getValue("eid"));
                        if(user != null){
                            userInfoEmpId.setText(user.getEid());
                            userInfoNickName.setText(user.getNickName());
                            userInfoRole.setText(user.getRole());
                            if (emp != null) userInfoEmpName.setText(emp.getFname() + " " + emp.getMname() + " " + emp.getLname());
                            else userInfoEmpName.setText(user.getNickName());
                        }
                    });
                }
        ).start();
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
        employeeInfoAssignToProjectButton.setVisible(false);
        employeeInfoAssignButton.setVisible(true);
        employeeInfoAssignProjectIdLabel.setVisible(true);
        employeeInfoAssignTextField.setVisible(true);
    }
    @FXML
    public void employeeInfoAssignButtonClicked() {

        try {
            Integer.valueOf(employeeInfoAssignTextField.getText());
            if(employeeInfoAssignTextField.getText().trim().length() != 4){
                System.out.println(employeeInfoAssignTextField.getText().trim().length());
                throw new Exception();
            }

            DBapi.assignEmployeeToProject(employeeInfoEmpId.getText(), employeeInfoAssignTextField.getText());
            employeeInfoClose();
            employeeDisplayClicked();
        }catch (Exception exception){
            Functions.showDialog("Please enter an valid Project ID", Functions.Errors.ERROR);

        }

    }
    public void removeEmployeeFromCurrentProject(){
        DBapi.removeEmployeeFromProject(employeeInfoEmpId.getText(), employeeInfoCurrentProjectId.getText());
        employeeInfoClose();
        employeeDisplayClicked();
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
    /*                         Supplier Info                  */
    @FXML
    public void supplierInfoEditButtonClicked() {
        if(!supplierInfoOnEdit) {
            supplierSwitchToEdit();
        } else {
            supplierSwitchFromEdit();
        }
    }
    @FXML
    public void supplierInfoClose() {
        supplierInfoPane.setVisible(false);
        homeNavBarVBox.setDisable(false);
        currentPane.setVisible(true);
        supplierSwitchFromEdit();
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
        Functions.showAddProjectPopup();
    }

    @FXML
    public void tableDisplayProjectClicked() {
        Project project = getProjectInfo(projectsTableViewFunctions.getSelectedRow().getProjectId()); //projectsTableViewFunctions.getSelectedRow();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if(project == null) return;
        new Thread(() -> Platform.runLater(() -> {
            Contractor contractor = getContractorInfo(project.getContractorId());
            if(contractor == null){
                contractorInfoPane.setVisible(false);
            }else{
                projectInfoContId.setText(contractor.getContractorId());
                projectInfoContName.setText(contractor.getFname() + " " + contractor.getMname() + " " + contractor.getLname());
                projectInfoContBirthdate.setText(dateFormat.format(contractor.getBirthdate()));
                projectInfoContType.setText(contractor.getContractorType());
                projectInfoContAge.setText(getAge(contractor.getBirthdate(), new Date()) + " yo");
            }
        })).start();
        projectInfoCity.setText(project.getCity());
        projectInfoStreet.setText(project.getStreet() == null  ? "" : project.getStreet());
        projectInfoStartDate.setText(dateFormat.format(project.getStartDate()));
        projectInfoDueDate.setText(dateFormat.format(project.getDueDate()));
        projectInfoProjAmount.setText(project.getAmount() + "$");
        projectInfoProjType.setText(project.getProjType());


        ArrayList<Product> products = DBapi.getProjectProducts(project.getProjectId());
        enhancedScrollPane.resetRows(partsUsedScrollPaneVbox);
        products.forEach(product -> {
            Warehouse warehouse = DBapi.getWarehouseInfo(product.getWarehouse_id());
            enhancedScrollPane.addRow(partsUsedScrollPaneVbox,product.getProductId(), product.getProductName(), warehouse.getCity(),86,143, 103, null, null);
        });







        projectsPane.setVisible(false);
        homeNavBarVBox.setDisable(true);
        projectInfoPane.setVisible(true);

    }

    /*                      Control Panel             */
    @FXML
    public void tableRemoveUserClicked() {
        if(usersTableViewFunctions.getSelectedRow() == null) return;
        User selectedUser = usersTableViewFunctions.getSelectedRow();
        Employee userEmp = getEmployeeInfo(selectedUser.getEid());
        Functions.showDialog("Are you sure you want to delete  \"" +  userEmp.getFname() + " " + userEmp.getLname() +"\" records?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                new Thread(() -> Platform.runLater(() -> {


                    // code here

                })).start();
            }

        }).start();
    }

    @FXML
    public void tableDisplayUserClicked() {
        controlPanelPane.setVisible(false);
        homeNavBarVBox.setDisable(true);
        userInfoPane.setVisible(true);
    }

    @FXML
    public void tableRemoveSupplierClicked() {
        if(suppliersTableViewFunctions.getSelectedRow() == null) return;
        Supplier selectedSupplier = suppliersTableViewFunctions.getSelectedRow();
        Functions.showDialog("Are you sure you want to delete  \"" + selectedSupplier.getCompanyName()  +"\" records?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                new Thread(() -> Platform.runLater(() -> {

                    // code here


                })).start();
            }
        }).start();
    }
    @FXML
    public void tableDisplaySupplierClicked() {
        controlPanelPane.setVisible(false);
        homeNavBarVBox.setDisable(true);
        supplierInfoPane.setVisible(true);
    }



    @FXML
    public void tableRemoveWarehouseClicked() {
        if(warehousesTableViewFunctions.getSelectedRow() == null) return;
        Warehouse selectedWarehouse = warehousesTableViewFunctions.getSelectedRow();
        Functions.showDialog("Are you sure you want to delete  \"" +  selectedWarehouse.getWarehouseId() + " " + " Warehouse" +"\" records?", Functions.Errors.CONFIRM_DIALOG);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            while(Functions.confirmFlag == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(Functions.confirmFlag) {
                new Thread(() -> Platform.runLater(() -> {


                    // code here


                })).start();
            }

        }).start();
    }
    @FXML
    public void tableDisplayWarehouseClicked() {
        controlPanelPane.setVisible(false);
        homeNavBarVBox.setDisable(true);
        warehouseInfoPane.setVisible(true);
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
        if(prevCurrentPane != null) {
            currentPane = prevCurrentPane;
            prevCurrentPane = null;
        }
    }

    /*                   Warehouse info                  */


    @FXML
    public void warehouseInfoClose() {
        Gauge warehouseInfoWCapacity = (Gauge) warehouseInfoPane.lookup("#warehouseInfoWCapacity");

        warehouseInfoWCapacity.setValue(0);
        currentPane.setVisible(true);
        homeNavBarVBox.setDisable(false);
        warehouseInfoPane.setVisible(false);
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


    public void switchToEdit() {
        Employee employee = DBapi.getEmployeeInfo(employeeInfoEmpId.getText());
        Label []labels = {employeeInfoEmpDistrict};
        TextField textField;
        textField = new TextField();
        textField.setLayoutX(employeeInfoEmpName.getLayoutX());
        textField.setLayoutY(employeeInfoEmpName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("First");
        textField.setId("editTextField" + "FirstName");
        textField.setText(employee.getFname());
        profileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(employeeInfoEmpName.getLayoutX() + 85);
        textField.setLayoutY(employeeInfoEmpName.getLayoutY());
        textField.setMaxWidth(60);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Middle");
        textField.setId("editTextField" + "MiddleName");
        textField.setText(employee.getMname());
        profileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(employeeInfoEmpName.getLayoutX() + 155);
        textField.setLayoutY(employeeInfoEmpName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Last");
        textField.setId("editTextField" + "LastName");
        textField.setText(employee.getLname());
        profileCard.getChildren().add(textField);

        employeeInfoEmpName.setVisible(false);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLayoutX(employeeInfoEmpJobPos.getLayoutX());
        comboBox.setLayoutY(employeeInfoEmpJobPos.getLayoutY());
        comboBox.setPrefWidth(employeeInfoEmpJobPos.getWidth());
        comboBox.setPromptText(employeeInfoEmpJobPos.getText());
        comboBox.getStyleClass().add("combo-box-ex");
        comboBox.setId("editTextField" + employeeInfoEmpJobPos.getId());

        ObservableList<String> comboBoxItems = FXCollections.observableArrayList(getJobPositions());
        comboBox.setItems(comboBoxItems);
        comboBox.setValue(employee.getJobPos());

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
        if (!employeeInfoOnEdit) return;

        TextField fNameTextField = (TextField) employeeInfoPane.lookup("#editTextFieldFirstName");
        TextField mNameTextField = (TextField) employeeInfoPane.lookup("#editTextFieldMiddleName");
        TextField lNameTextField = (TextField) employeeInfoPane.lookup("#editTextFieldLastName");

        TextField districtTextField = (TextField) employeeInfoPane.lookup("#editTextFieldemployeeInfoEmpDistrict");

        if(!fNameTextField.getText().isEmpty() && !mNameTextField.getText().isEmpty() && !lNameTextField.getText().isEmpty()) {

            System.out.println("Hurray");
            ComboBox<String> jobPositionComboBox = (ComboBox<String>) employeeInfoPane.lookup("#editTextFieldemployeeInfoEmpJobPos");
            new Thread(() -> Platform.runLater(() -> {
                updateEmployee(currentEmployeeProfilePage.getEid(), fNameTextField.getText(), mNameTextField.getText(), lNameTextField.getText(), jobPositionComboBox.getValue(), districtTextField.getText());
                employeesTableViewFunctions.initializeTableView(employeesTable);
            })).start();

            employeeInfoEmpName.setText(fNameTextField.getText() + " " + mNameTextField.getText() + " " + lNameTextField.getText());
            employeeInfoEmpDistrict.setText(districtTextField.getText());
            employeeInfoEmpJobPos.setText(jobPositionComboBox.getValue());


        }

        Label []labels = {employeeInfoEmpName,employeeInfoEmpJobPos,employeeInfoEmpDistrict};
        profileCard.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("editTextField"));
        for(Label label: labels) {
            label.setVisible(true);
        }

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
        TextField nicknameTextField = (TextField) userInfoPane.lookup("#editTextFielduserInfoNickName");
        if(userInfoChangePassword.isVisible()){

            updateEmployeeAccount(userInfoEmpId.getText(), nicknameTextField.getText());
        }else{
            TextField passwordTextField = (TextField) userInfoPane.lookup("#editTextFielduserInfoChangePassword");
            updateEmployeeAccount(userInfoEmpId.getText(), nicknameTextField.getText(), passwordTextField.getText());
        }
        userInfoNickName.setText(nicknameTextField.getText());

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
        textField.setText(enhancedScrollPane.currentContractorProfilePage.getFname());
        contractorProfileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(contractorInfoContName.getLayoutX() + 85);
        textField.setLayoutY(contractorInfoContName.getLayoutY());
        textField.setMaxWidth(60);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Middle");
        textField.setId("editTextField" + "MiddleName");
        textField.setText(enhancedScrollPane.currentContractorProfilePage.getMname());
        contractorProfileCard.getChildren().add(textField);
        textField = new TextField();
        textField.setLayoutX(contractorInfoContName.getLayoutX() + 155);
        textField.setLayoutY(contractorInfoContName.getLayoutY());
        textField.setMaxWidth(75);
        textField.getStyleClass().add("info-field");
        textField.setPromptText("Last");
        textField.setId("editTextField" + "LastName");
        textField.setText(enhancedScrollPane.currentContractorProfilePage.getLname());
        contractorProfileCard.getChildren().add(textField);

        contractorInfoContName.setVisible(false);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setLayoutX(contractorInfoContType.getLayoutX());
        comboBox.setLayoutY(contractorInfoContType.getLayoutY());
        comboBox.setPrefWidth(contractorInfoContType.getWidth());
        comboBox.setPromptText(contractorInfoContType.getText());
        comboBox.getStyleClass().add("combo-box-ex");
        comboBox.setId("editTextField" + contractorInfoContType.getId());
        ObservableList<String> comboBoxItems = FXCollections.observableArrayList(getContractorTypes());
        comboBox.setItems(comboBoxItems);
        comboBox.setValue(enhancedScrollPane.currentContractorProfilePage.getContractorType());

        contractorProfileCard.getChildren().add(comboBox);
        contractorInfoContType.setVisible(false);

        contractorInfoEditButton.setText("Submit");
        contractorInfoOnEdit=true;
    }
    public void contractorSwitchFromEdit() {
        if(!contractorInfoOnEdit) return;
        TextField fNameTextField = (TextField) contractorInfoPane.lookup("#editTextFieldFirstName");
        TextField mNameTextField = (TextField) contractorInfoPane.lookup("#editTextFieldMiddleName");
        TextField lNameTextField = (TextField) contractorInfoPane.lookup("#editTextFieldLastName");


        if(!fNameTextField.getText().isEmpty() && !mNameTextField.getText().isEmpty() && !lNameTextField.getText().isEmpty()) {


            ComboBox<String> contractorTypeComboBox = (ComboBox<String>) contractorInfoPane.lookup("#editTextFieldcontractorInfoContType");
            new Thread(() -> Platform.runLater(() -> {
                updateContractor(enhancedScrollPane.currentContractorProfilePage.getContractorId(), fNameTextField.getText(), mNameTextField.getText(), lNameTextField.getText(), contractorTypeComboBox.getValue());
                setupContractorsTable();
            })).start();


            contractorInfoContName.setText(fNameTextField.getText() + " " + mNameTextField.getText() + " " + lNameTextField.getText());
            contractorInfoContType.setText(contractorTypeComboBox.getValue());

        }


        Label []labels = {contractorInfoContName,contractorInfoContType};
        contractorProfileCard.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("editTextField"));
        for(Label label: labels) {
            label.setVisible(true);
        }

        contractorInfoEditButton.setText("Edit");
        contractorInfoOnEdit=false;
    }
    public void supplierSwitchToEdit() {
        TextField textField;
        textField = new TextField();
        textField.setLayoutX(supplierInfoCompanyName.getLayoutX());
        textField.setLayoutY(supplierInfoCompanyName.getLayoutY());
        textField.setPrefWidth(supplierInfoCompanyName.getWidth());
        textField.getStyleClass().add("info-field");
        textField.setText(supplierInfoCompanyName.getText());
        textField.setId("editTextField" + supplierInfoCompanyName.getId());
        supplierInfoProfileCard.getChildren().add(textField);
        supplierInfoCompanyName.setVisible(false);
        supplierInfoOnEdit = true;
        supplierInfoEditButton.setText("Submit");
    }
    public void supplierSwitchFromEdit() {
        if(!supplierInfoOnEdit) return;
        supplierInfoProfileCard.getChildren().removeIf(node -> node.getId() != null && node.getId().startsWith("editTextField"));
        supplierInfoCompanyName.setVisible(true);
        supplierInfoOnEdit = false;
        supplierInfoEditButton.setText("Edit");
    }





    private void addContRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(contractorListScrollPaneVbox,column1,column2,column3, 35, 150 ,85, Functions.ListType.CONT_LIST,currentPane,homeNavBarVBox,contractorInfoPane);
    }
    private void addWarehouseRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(warehouseListScrollPaneVbox,column1,column2,column3,40, 150 ,75, Functions.ListType.WAREHOUSE_LIST,projectsPane,homeNavBarVBox,warehouseInfoPane);
    }
    private void addProjRow(String column1,String column2, String column3) {
        enhancedScrollPane.addRow(lastProjectsScrollPaneVbox,column1,column2,column3, 32, 205, 98, Functions.ListType.LAST_PROJECTS_LIST);

    }
    private void addCurrentProjectsRow(String column1,String column2, String column3) {
        enhancedScrollPane.addRow(currentProjectsScrollPaneVbox,column1,column2,column3,32,190,90, Functions.ListType.CURRENT_PROJECTS_LIST,contractorInfoPane,projectInfoPane);
        prevCurrentPane = currentPane;
        currentPane = projectInfoPane;
    }


}

