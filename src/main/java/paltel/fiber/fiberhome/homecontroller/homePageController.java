package paltel.fiber.fiberhome.homecontroller;

import animatefx.animation.*;
import eu.hansolo.medusa.Gauge;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import paltel.fiber.fiberhome.model.*;
import paltel.fiber.fiberhome.Main;
import paltel.fiber.fiberhome.utils.DBapi;
import paltel.fiber.fiberhome.Functions;
import paltel.fiber.fiberhome.Navigator;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Calendar.*;
import static paltel.fiber.fiberhome.utils.DBapi.*;

public class homePageController implements Initializable {



    @FXML
    Label userNickNameLabel,userRoleLabel;
    @FXML
    Pane disableNavBar;

    @FXML
    AnchorPane ap;
    @FXML
    VBox homeNavBarVBox;
    @FXML
    ImageView backgroundImageView;
    @FXML
    Pane titleBar;
    @FXML
    Pane employeesPane,controlPanelPane,projectsPane;
    @FXML
    Button navButton4,navButton1,navButton2,navButton3;
    @FXML
    FontIcon navButton1FontIcon,navButton2FontIcon,navButton3FontIcon;
    @FXML
    MFXPaginatedTableView<Employee> employeesTable;

    @FXML
    Pane employeeInfoPane;

    Pane currentPane;
    public static Pane prevCurrentPane = null;

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
    Label controlPanelTotalWarehousesNumber, controlPanelTotalUsedMaterials,controlPanelTotalStoredParts,controlPanelTotalFreeSpace;

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
    /*                   Project manager project info           */
    @FXML
    Pane projectManagerProjectInfoPaneChild;
    @FXML
    Label projectManagerProjectInfoPaneDontWork;
    @FXML
    Label employeeInfoCloseLabel,employeeInfoRemoveFromProject,projManYouDontWork;
    @FXML
    Pane projectManagerProjectInfoPane;
    @FXML
    MFXScrollPane ProjManPartsUsedScrollPane,ProjManProjectEmployeesScrollPane;
    @FXML
    VBox ProjManPartsUsedScrollPaneVbox,ProjManProjectEmployeesScrollPaneVbox;
    @FXML
    Label ProjManProjectInfoProjId,ProjManProjectInfoProjType,ProjManProjectInfoProjAmount
            ,ProjManProjectInfoStartDate,ProjManProjectInfoDueDate,ProjManProjectInfoStreet,ProjManProjectInfoCity;

    /* ----------------------------------------------------------------------------- */


    Employee currentEmployeeProfilePage;

    boolean contractorInfoOnEdit = false;
    boolean supplierInfoOnEdit = false;

    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            addProjManagerProjectEmployeesRow("0003","Zobr","wepofk");
            addProjManagerProjectParts("0000000002","zobr kber","Nablus");
        });
        user = getUserInfo((String) Navigator.getValue("eid"));
        Employee employee = getEmployeeInfo((String) Navigator.getValue("eid"));

        userNickNameLabel.setText(user.getNickName());
        userRoleLabel.setText(employee.getJobPos());
        setupNavBar();
        warehouseInfoWStorage.setLegendSide(Side.LEFT);
        warehouseInfoWStorage.setLabelsVisible(false);


        employeesTableView = employeesTable;
        updateLastLoginTime((String) Navigator.getValue("eid"));
        stage = Navigator.primaryStage;

        ap.setOpacity(0);
        Platform.runLater(() -> {
            stage.centerOnScreen();
            playOpenAnimation();
        });
        Functions.move(stage,titleBar);
        Functions.optimizeImageView(backgroundImageView);





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

    private void setupPendingAccountsTable(){
        enhancedScrollPane.resetRows(controlPanelPendingUsersScrollPaneVbox);
        ArrayList<Employee> pendingUsers = getAllPendingAccounts();
        pendingUsers.forEach(employeeInstance -> {
            User userInstance = getUserInfo(employeeInstance.getEid());
            enhancedScrollPane.addRow(controlPanelPendingUsersScrollPaneVbox,employeeInstance.getEid(), employeeInstance.getFname() + " " + employeeInstance.getMname() + " " + employeeInstance.getLname() ,userInstance.getNickName() ,37,138,131, Functions.ListType.PENDING_LIST);
        });
    }

    private void setupWarehouseTable(){
        enhancedScrollPane.resetRows(warehouseListScrollPaneVbox);
        ArrayList<Warehouse> warehouses = getAllWarehouses();
        warehouses.forEach(warehouse -> {
            addWarehouseRow(warehouse.getWarehouseId(), warehouse.getCity(), String.valueOf(warehouse.getCapacity()));

        });
    }


    private void setUpCheapestSupplierTable(){
        enhancedScrollPane.resetRows(controlPanelCheapestProductScrollPaneVbox);
        ArrayList<Product> products = getAllProductsThatHasSuppliers();
        products.forEach(product -> {
            Supplier supplier = getCheapestProductSupplier(product.getProductId());
            if(supplier != null) {
                addCheapestProductsRow(product.getProductId(), product.getProductName(), supplier.getCompanyName());
            }
        });
    }


    private void setUpEmployeeStatisticsBlocks(){
        new Thread(() -> {
            Platform.runLater(() -> totalEmployeesCountLabel.setText(getEmployeesCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalUsersCountLabel.setText(getUsersCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalContractorCountLabel.setText(getContractorCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalWorkingEmployeesCountLabel.setText(getWorkingEmployeesCount().toString()));
            Thread.interrupted();

        }).start();
    }

    private void setUpControlPanelUsersBlocks(){
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalActiveUsers.setText(getActiveUsersCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalAdminAccounts.setText(getAdminUsersCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalEmployeeAccounts.setText(getEmployeeUsersCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalPendingAccounts.setText(getPendingUsersCount().toString()));
            Thread.interrupted();

        }).start();
    }
    private void setUpControlPanelSuppliersBlocks(){
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalSuppliers.setText(getTotalSuppliersCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalProducts.setText(getTotalProductsCount().toString()));
            Thread.interrupted();

        }).start();

    }

    private void setUpControlPanelWarehousesBlocks(){
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalWarehousesNumber.setText(getTotalWarehousesCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalStoredParts.setText(getTotalProductsStoredInWarehouses().toString()));
            Thread.interrupted();
        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalFreeSpace.setText(getTotalFreeSpaceInWarehouses().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> controlPanelTotalUsedMaterials.setText(getTotalUsedPartsInWarehouses().toString()));
            Thread.interrupted();

        }).start();
    }

    private void setUpProjectsStatisticsBlocks(){
        new Thread(() -> {
            Platform.runLater(() -> totalProjectsCountLabel.setText(getProjectsCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalWarehouseCountLabel.setText(getWarehousesCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalRunningProjectsCountLabel.setText(getRunningProjectsCount().toString()));
            Thread.interrupted();

        }).start();
        new Thread(() -> {
            Platform.runLater(() -> totalFinishedProjectsCountLabel.setText(getFinishedProjectsCount().toString()));
            Thread.interrupted();

        }).start();
    }
    private void playOpenAnimation(){
        ZoomIn openingAnimation = new ZoomIn(ap);
        openingAnimation.setOnFinished(event -> {});
        openingAnimation.play();
    }

    /*                   Nav bar stuff                    */
    @FXML
    public void navButton1Clicked() {
        if(user.getRole().equals("Admin")) {
            employeesPane.setVisible(false);
            controlPanelPane.setVisible(true);
            projectsPane.setVisible(false);
            currentPane = controlPanelPane;
        } else if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER) {
            currentPane = employeeInfoPane;
            employeeDisplay(false,true);
            projectManagerProjectInfoPane.setVisible(false);
        } else if(user.getJobPos() == Functions.JobPos.TECHNICIAN) {
            currentPane = employeesPane;
            employeeDisplay(false,true);
        } else if(user.getJobPos() == Functions.JobPos.ACCOUNTANT) {
            currentPane = employeesPane;
            employeeDisplay(false,true);
        }
        switchNavButton(navButton1);
        prevCurrentPane = null;
    }
    @FXML
    public void navButton2Clicked() {
        if(user.getRole().equals("Admin")) {
            employeesPane.setVisible(true);
            controlPanelPane.setVisible(false);
            projectsPane.setVisible(false);
            currentPane = employeesPane;

            // test
        } else if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER) {
            employeeInfoClose(false,true);
            projectManagerProjectInfoPane.setVisible(true);
            currentPane = projectManagerProjectInfoPane;
        } else if(user.getJobPos() == Functions.JobPos.TECHNICIAN) {
            employeeInfoClose(false,true);
        }
        switchNavButton(navButton2);
    }


    @FXML
    public void navButton4Clicked() {
        if(user.getRole().equals("Admin")) {
            employeesPane.setVisible(false);
            controlPanelPane.setVisible(false);
            projectsPane.setVisible(false);
            currentPane = projectsPane;
        }
        switchNavButton(navButton4);
        prevCurrentPane = null;
    }

    @FXML
    public void navButton3Clicked() {
        if(user.getRole().equals("Admin")) {
            employeesPane.setVisible(false);
            controlPanelPane.setVisible(false);
            projectsPane.setVisible(true);
            currentPane = projectsPane;
        }
        switchNavButton(navButton3);
        prevCurrentPane = null;
    }

    @FXML
    public void signOutClicked() {
        AnimationFX closeAnimation = new ZoomOut(ap);
        closeAnimation.setOnFinished((event) -> {
            Navigator.pushNamedReplacement("loginPageScene");
            new Thread(() -> {
                try {
                    connection.endRequest();
                    Main.connectToDatabase();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Thread.interrupted();

            }).start();

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

                    deleteEmployee(selectedEmployee.getEid());
                    employeesTableViewFunctions.initializeTableView(employeesTable);
                    setUpEmployeeStatisticsBlocks();


            }
            Thread.interrupted();

        }).start();
    }
    @FXML
    public void employeeInfoCloseClicked() {
        employeeInfoClose(false,false);
    }
    public void employeeInfoClose(boolean refresh,boolean primary) {
        if(primary) {
            employeeInfoPane.setVisible(false);
        }else if(!refresh) {
            Functions.infoOutAnimation(currentPane,employeeInfoPane);
        }
        else {
            employeeInfoPane.setVisible(false);
            currentPane.setVisible(true);
        }
        disableNavBar.setVisible(false);
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
                Platform.runLater(this::removeEmployeeFromCurrentProject);
            }
            Thread.interrupted();

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
        employeeDisplay(false,false);
    }
    public void employeeDisplay(boolean refresh,boolean primary) {
        Employee employee = null;
        if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER || user.getJobPos() == Functions.JobPos.TECHNICIAN || user.getJobPos() == Functions.JobPos.ACCOUNTANT) {
            employee = getEmployeeInfo(user.getEid());
        } else {
            employee = employeesTableViewFunctions.getSelectedRow();
            currentEmployeeProfilePage = employee;
        }
        if (employee == null) return;
        enhancedScrollPane.resetRows(lastProjectsScrollPaneVbox);
        SimpleDateFormat birthdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat projectDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat lastLoginFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm aa");

        Employee finalEmployee = employee;
        new Thread(() -> {
            Platform.runLater(() -> {
                User user = getUserInfo(finalEmployee.getEid());
                if (user == null || user.getLastLogin() == null) {
                    employeeInfoLastLogin.setText("Never");
                } else {
                    employeeInfoLastLogin.setText(lastLoginFormat.format(user.getLastLogin()));
                }
            });
            Thread.interrupted();


        }).start();

        if (employee.getJobPos().equals("Technician") || employee.getJobPos().equals("Project Monitor") || employee.getJobPos().equals("Project Manager")) {

            lastProjectsCard.setVisible(true);
            Project project = getCurrentProject(employee.getEid());
            if (project == null) {
                currentProjectCard1.setVisible(false);
                if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER || user.getJobPos() == Functions.JobPos.TECHNICIAN || user.getJobPos() == Functions.JobPos.ACCOUNTANT) {
                    projManYouDontWork.setVisible(true);
                }else {
                    employeeInfoAssignToProjectButton.setVisible(true);
                    projManYouDontWork.setVisible(false);
                }
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

            currentProjectCard.setVisible(true);
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

        if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER || user.getJobPos() == Functions.JobPos.TECHNICIAN || user.getJobPos() == Functions.JobPos.ACCOUNTANT) {
            employeeInfoCloseLabel.setVisible(false);
            employeeInfoRemoveFromProject.setVisible(false);
            employeeInfoAssignButton.setVisible(false);
            employeeInfoEditButton.setVisible(false);
        } else {
//            employeesPane.setVisible(false);
            disableNavBar.setVisible(true);
            employeeInfoCloseLabel.setVisible(true);

        }
        employeeInfoPane.setVisible(true);
        if(!refresh && !primary) {
            Functions.infoInAnimation(employeesPane,employeeInfoPane);
        }

        prevCurrentPane = employeeInfoPane;
    }

    /*               Contractor info          */
    @FXML
    public void contractorInfoEditButtonClicked() {
        if(!contractorInfoOnEdit) {
            contractorSwitchToEdit();
        } else {
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
                deleteContractor(contractorInfoContId.getText());
                setupContractorsTable();
                setUpEmployeeStatisticsBlocks();


            }
            Thread.interrupted();

        }).start();
    }
    @FXML
    public void contractorInfoClose() {
        Functions.infoOutAnimation(currentPane,contractorInfoPane);
//        contractorInfoPane.setVisible(false);
//        currentPane.setVisible(true);
        disableNavBar.setVisible(false);
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
                    Thread.interrupted();

                }
        ).start();
        userInfoPane.setVisible(true);
        Functions.infoInAnimation(currentPane,userInfoPane);
//        currentPane.setVisible(false);
        disableNavBar.setVisible(true);
    }
    @FXML
    public void userInfoClose() {
        Functions.infoOutAnimation(currentPane,userInfoPane);
//        userInfoPane.setVisible(false);
//        currentPane.setVisible(true);
        disableNavBar.setVisible(false);
        userSwitchFromEdit();
    }
    @FXML
    public void userInfoEditButtonClicked() {
        if(!userInfoOnEdit) {
            userSwitchToEdit();
        } else {
            userSwitchFromEdit();
        }
    }
    @FXML
    public void employeeInfoAssignToProjectButtonClicked() {
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
                throw new Exception();
            }

            DBapi.assignEmployeeToProject(employeeInfoEmpId.getText(), employeeInfoAssignTextField.getText());
            employeeInfoClose(true,false);
            employeeDisplay(true,false);
        }catch (Exception exception){
            Functions.showDialog("Please enter an valid Project ID", Functions.Errors.ERROR);

        }

    }
    public void removeEmployeeFromCurrentProject(){
        DBapi.removeEmployeeFromProject(employeeInfoEmpId.getText(), employeeInfoCurrentProjectId.getText());
        employeeInfoClose(true,false);
        employeeDisplay(true,false);
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
        Functions.infoOutAnimation(currentPane,supplierInfoPane);
//        supplierInfoPane.setVisible(false);
        disableNavBar.setVisible(false);
//        currentPane.setVisible(true);
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
                deleteProject(selectedProject.getProjectId());
                projectsTableViewFunctions.initializeTableView(projectsTable);
                setUpProjectsStatisticsBlocks();
            }
            Thread.interrupted();

        }).start();
    }
    @FXML
    public void tableAddProjectClicked() {
        Functions.showAddProjectPopup();
        projectsTableViewFunctions.initializeTableView(projectsTable);
        setUpProjectsStatisticsBlocks();
    }


    @FXML
    public void tableDisplayProjectClicked() {
        tableDisplayProject(false);
    }
    public void tableDisplayProject(boolean refresh) {
        if(projectsTableViewFunctions.getSelectedRow() == null) return;
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
            Thread.interrupted();

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







        disableNavBar.setVisible(true);
        projectInfoPane.setVisible(true);
        if(!refresh) {
            Functions.infoInAnimation(projectsPane,projectInfoPane);
        }

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
                Platform.runLater(() -> {
                    deleteEmployeeAccount(selectedUser.getEid());
                    usersTableViewFunctions.initializeTableView(controlPanelUsersTableView);
                    setUpControlPanelUsersBlocks();
                });
            }
            Thread.interrupted();

        }).start();
    }

    @FXML
    public void tableDisplayUserClicked() {
        User user = usersTableViewFunctions.getSelectedRow();
        if(user == null) return;
        Employee employee = DBapi.getEmployeeInfo(user.getEid());
        userInfoNickName.setText(user.getNickName());
        userInfoEmpId.setText(user.getEid());
        userInfoRole.setText(user.getRole());
        userInfoEmpName.setText(employee.getFname() + " " + employee.getMname() + " " + employee.getLname());

        userInfoPane.setVisible(true);
        Functions.infoInAnimation(currentPane,userInfoPane);
//        controlPanelPane.setVisible(false);
        disableNavBar.setVisible(true);
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
            Thread.interrupted();

        }).start();
    }
    @FXML
    public void tableDisplaySupplierClicked() {
        if(suppliersTableViewFunctions.getSelectedRow() == null) return;
        Supplier supplier = suppliersTableViewFunctions.getSelectedRow();
        supplierInfoCompanyName.setText(supplier.getCompanyName());
        supplierInfoSupplierId.setText(supplier.getSupplierId());

        ArrayList<Product> offeringProducts = getAllProductsThatSupplierCanSupply(supplier.getSupplierId());
        for (Product offeringProduct : offeringProducts) {

            addOffersProductsRow(offeringProduct.getProductId(), offeringProduct.getProductName(), offeringProduct.getCost() + "$");
        }

        supplierInfoPane.setVisible(true);
        Functions.infoInAnimation(currentPane,supplierInfoPane);
//        controlPanelPane.setVisible(false);
        disableNavBar.setVisible(true);
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
            Thread.interrupted();

        }).start();
    }
    @FXML
    public void tableViewWarehouseProjectsClicked() {
        // todo : warehouse table view Projects Table
        if(warehousesTableViewFunctions.getSelectedRow() == null) {
            enhancedScrollPane.resetRows(controlPanelWarehouseProjectsScrollPaneVbox);
            return;
        };
        enhancedScrollPane.resetRows(controlPanelWarehouseProjectsScrollPaneVbox);
        Warehouse warehouse = warehousesTableViewFunctions.getSelectedRow();
        for (Project project : getAllProjectsImportingFromAWarehouse(warehouse.getWarehouseId())) {
            Integer numberOfReservedProducts = DBapi.getProductsCountThatProjectUsesFromAWarehouse(warehouse.getWarehouseId(), project.getProjectId());
            Contractor contractor = getContractorInfo(project.getContractorId());
            addWarehouseProjectsRow(project.getProjectId(), project.getCity() + " " + project.getProjType(), contractor.getFname() + " " + contractor.getLname(), numberOfReservedProducts.toString());
        }
    }
    @FXML
    public void tableDisplayWarehouseClicked() {
        if(warehousesTableViewFunctions.getSelectedRow() == null) return;

        Warehouse warehouse = warehousesTableViewFunctions.getSelectedRow();
        if(warehouse != null){
            warehouseInfoWid.setText(warehouse.getWarehouseId());
            warehouseInfoWLocation.setText(warehouse.getCity());
            warehouseInfoWCapacityCount.setText(warehouse.getCapacity().toString());


            Double warehousePercentage = DBapi.getWarehousePercentage(warehouse.getWarehouseId());
            warehouseInfoWCapacity.setValue(warehousePercentage);


            ArrayList<Product> availableProducts = DBapi.getAvailableProductsInWarehouse(warehouse.getWarehouseId());
            Integer usedSpace = DBapi.getCapacityForAWarehouse(warehouse.getWarehouseId());

            int availableProductsCount = 0;
            for(Product product : availableProducts){
                availableProductsCount += product.getAvailable_count();
            }
            int availablePercentage = (int) (((float) availableProductsCount / warehouse.getCapacity()) * 100);
            int reservedPercentage =  Math.round(((float) (usedSpace - availableProductsCount)  / warehouse.getCapacity()) * 100);
            int freeSpacePercentage = Math.round(((float) (warehouse.getCapacity() - usedSpace)  / warehouse.getCapacity()) * 100);
            availablePercentage = availablePercentage + (100 - (reservedPercentage + freeSpacePercentage + availablePercentage));

            ObservableList<PieChart.Data> pieChartData =
                    FXCollections.observableArrayList(
                            new PieChart.Data("Available %" + availablePercentage, availableProductsCount),
                            new PieChart.Data("Reserved %" + reservedPercentage , usedSpace - availableProductsCount),
                            new PieChart.Data("Free Space %" +  freeSpacePercentage, warehouse.getCapacity() - usedSpace ));
            warehouseInfoWStorage.setData(pieChartData);
            warehouseInfoWStorage.setLegendSide(Side.LEFT);
            warehouseInfoWStorage.setLabelsVisible(false);
            warehousesTableViewFunctions.initializeTableView(controlPanelWarehousesTableView);
            availableProducts.forEach(product -> {
                addWarehouseRow( product.getProductId(), product.getProductName(), String.valueOf(product.getAvailable_count()));

            });
            warehouseInfoPane.setVisible(true);
            Functions.infoInAnimation(controlPanelPane,warehouseInfoPane);
//            controlPanelPane.setVisible(false);
            disableNavBar.setVisible(true);
        }


    }

    /*                  Project Info                */
    @FXML
    public void projectInfoEditButtonClicked() {

    }
    @FXML
    public void projectInfoCloseClicked() {
        projectInfoClose(false);
    }
    public void projectInfoClose(boolean refresh) {
        if(prevCurrentPane != null) {
            Functions.infoOutAnimation(prevCurrentPane,projectInfoPane);
        } else {
            Functions.infoOutAnimation(currentPane,projectInfoPane);
            disableNavBar.setVisible(false);
        }
    }

    /*                   Warehouse info                  */


    @FXML
    public void warehouseInfoClose() {
        Functions.infoOutAnimation(currentPane,warehouseInfoPane);
//        currentPane.setVisible(true);
        disableNavBar.setVisible(false);
//        warehouseInfoPane.setVisible(false);
    }
    /*                    Project manager Project info              */
    @FXML
    public void ProjManProjectInfoClose() {
        projectsPane.setVisible(true);
        disableNavBar.setVisible(false);
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
        if(button.getText().equals("Projects")){
            new Thread(() -> {
                Platform.runLater(() -> {
                    projectsTableViewFunctions.initializeTableView(projectsTable);
                    setupWarehouseTable();
                    setUpProjectsStatisticsBlocks();
                });
                Thread.interrupted();

            }).start();
        }else if(button.getText().equals("Employees")){
            new Thread(() -> {
                Platform.runLater(() -> {

                    employeesTableViewFunctions.initializeTableView(employeesTable);
                    setupContractorsTable();
                    setUpEmployeeStatisticsBlocks();
                    Thread.interrupted();

                });
            }).start();
        }else if(button.getText().equals("Control Panel")){
            // todo: Control Panel ROBLOX
            setUpControlPanelUsersBlocks();
            setupPendingAccountsTable();
            usersTableViewFunctions.initializeTableView(controlPanelUsersTableView);

            setUpControlPanelSuppliersBlocks();
            setUpCheapestSupplierTable();
            suppliersTableViewFunctions.initializeTableView(controlPanelSuppliersTableView);

            setUpControlPanelWarehousesBlocks();
            warehousesTableViewFunctions.initializeTableView(controlPanelWarehousesTableView);
        }

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
        if(employeeInfoPane.isVisible()) {
            if (!fNameTextField.getText().isEmpty() && !mNameTextField.getText().isEmpty() && !lNameTextField.getText().isEmpty()) {

                ComboBox<String> jobPositionComboBox = (ComboBox<String>) employeeInfoPane.lookup("#editTextFieldemployeeInfoEmpJobPos");
                new Thread(() -> Platform.runLater(() -> {
                    updateEmployee(currentEmployeeProfilePage.getEid(), fNameTextField.getText(), mNameTextField.getText(), lNameTextField.getText(), jobPositionComboBox.getValue(), districtTextField.getText());
                    employeesTableViewFunctions.initializeTableView(employeesTable);
                    Thread.interrupted();

                })).start();

                employeeInfoEmpName.setText(fNameTextField.getText() + " " + mNameTextField.getText() + " " + lNameTextField.getText());
                employeeInfoEmpDistrict.setText(districtTextField.getText());
                employeeInfoEmpJobPos.setText(jobPositionComboBox.getValue());


            }
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
        if(userInfoPane.isVisible()) {
            if (userInfoChangePassword.isVisible()) {

                updateEmployeeAccount(userInfoEmpId.getText(), nicknameTextField.getText());
            } else {
                TextField passwordTextField = (TextField) userInfoPane.lookup("#editTextFielduserInfoChangePassword");
                updateEmployeeAccount(userInfoEmpId.getText(), nicknameTextField.getText(), passwordTextField.getText());
            }
            userInfoNickName.setText(nicknameTextField.getText());
        }

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

        if(contractorInfoPane.isVisible()) {

            if (!fNameTextField.getText().isEmpty() && !mNameTextField.getText().isEmpty() && !lNameTextField.getText().isEmpty()) {


                ComboBox<String> contractorTypeComboBox = (ComboBox<String>) contractorInfoPane.lookup("#editTextFieldcontractorInfoContType");
                new Thread(() -> Platform.runLater(() -> {
                    updateContractor(enhancedScrollPane.currentContractorProfilePage.getContractorId(), fNameTextField.getText(), mNameTextField.getText(), lNameTextField.getText(), contractorTypeComboBox.getValue());
                    setupContractorsTable();
                    Thread.interrupted();

                })).start();


                contractorInfoContName.setText(fNameTextField.getText() + " " + mNameTextField.getText() + " " + lNameTextField.getText());
                contractorInfoContType.setText(contractorTypeComboBox.getValue());

            }
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




    private void addOfferingProduct(){
        //enhancedScrollPane.addRow();
        // todo addRow
    }

    private void addContRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(contractorListScrollPaneVbox,column1,column2,column3, 35, 150 ,85, Functions.ListType.CONT_LIST,currentPane,homeNavBarVBox,contractorInfoPane,projectInfoPane);
    }
    private void addWarehouseRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(warehouseListScrollPaneVbox,column1,column2,column3,40, 150 ,75, Functions.ListType.WAREHOUSE_LIST,projectsPane,homeNavBarVBox,warehouseInfoPane);
    }
    private void addProjRow(String column1,String column2, String column3) {
        enhancedScrollPane.addRow(lastProjectsScrollPaneVbox,column1,column2,column3, 32, 205, 98, Functions.ListType.LAST_PROJECTS_LIST,employeeInfoPane,projectInfoPane);

    }
    private void addCurrentProjectsRow(String column1,String column2, String column3) { // there is edit here
        enhancedScrollPane.addRow(currentProjectsScrollPaneVbox,column1,column2,column3,32,190,90, Functions.ListType.CURRENT_PROJECTS_LIST,contractorInfoPane,projectInfoPane);
    }
    private void addCheapestProductsRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(controlPanelCheapestProductScrollPaneVbox,column1,column2,column3,82,140,181, null);
    }
    private void addWarehouseProjectsRow(String column1,String column2,String column3,String column4) { // there is edit here
        enhancedScrollPane.addRow(controlPanelWarehouseProjectsScrollPaneVbox,column1,column2,column3,column4,37,115,146,94,null);
    }
    private void addProjManagerProjectEmployeesRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(ProjManProjectEmployeesScrollPaneVbox,column1,column2,column3,54,166,114, Functions.ListType.PROJ_MANAGER_PROJ_EMPLOYEES,projectManagerProjectInfoPane,homeNavBarVBox,employeeInfoPane);
    }
    private void addProjManagerProjectParts(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(ProjManPartsUsedScrollPaneVbox,column1, column2, column3,86,143, 103, null);
    }
    private void addOffersProductsRow(String column1,String column2,String column3) {
        enhancedScrollPane.addRow(offersProductsScrollPaneVbox,column1,column2,column3,32,190,90,null);
    }


    private void setupNavBar() {
        assert user != null;
        if(user.getRole().equals("Admin")) {
            navButton1.setText("Control Panel");
            navButton1.setVisible(true);
            navButton2.setText("Employees");
            navButton2.setVisible(true);
            navButton3.setText("Projects");
            navButton3.setVisible(true);

            navButton1FontIcon.setIconLiteral("fltfal-app-folder-24");
            navButton2FontIcon.setIconLiteral("fltfmz-person-accounts-24");
            navButton3FontIcon.setIconLiteral("fltfmz-wrench-16");
            navButton1Clicked();
        } else if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER) {
            navButton1.setText("My Profile");
            navButton2.setText("Project");
            navButton1.setVisible(true);
            navButton2.setVisible(true);

            navButton1FontIcon.setIconLiteral("fltfmz-person-24");
            navButton2FontIcon.setIconLiteral("fltfmz-wrench-16");
            navButton1Clicked();
        } else if(user.getJobPos() == Functions.JobPos.TECHNICIAN) {
            navButton1.setText("My Profile");
//            navButton2.setText("Project");
            navButton1.setVisible(true);
//            navButton2.setVisible(true);

            navButton1FontIcon.setIconLiteral("fltfmz-person-24");
//            navButton2FontIcon.setIconLiteral("fltfmz-wrench-16");
            navButton1Clicked();
        } else if(user.getJobPos() == Functions.JobPos.ACCOUNTANT) {
            navButton1.setText("My Profile");
            navButton1.setVisible(true);

            navButton1FontIcon.setIconLiteral("fltfmz-person-24");

            navButton1Clicked();

        }
    }

}

