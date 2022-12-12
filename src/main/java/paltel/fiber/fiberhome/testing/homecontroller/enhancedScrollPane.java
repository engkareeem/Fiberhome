package paltel.fiber.fiberhome.testing.homecontroller;

import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;
import paltel.fiber.fiberhome.testing.Functions;
import paltel.fiber.fiberhome.testing.Navigator;
import paltel.fiber.fiberhome.testing.model.*;
import paltel.fiber.fiberhome.testing.utils.DBapi;
import paltel.fiber.fiberhome.testing.Functions.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;
import static paltel.fiber.fiberhome.testing.utils.DBapi.*;

public class enhancedScrollPane {
    public static Contractor currentContractorProfilePage;
    public static void addRow(VBox vbox, String column1, String column2, String column3,String column4, int width1, int width2, int width3,int width4, ListType type, Node...nodes) {
        Label col1 = new Label(column1);
        col1.setPrefSize(width1,40);

        Label col2 = new Label(column2);
        col2.setPrefSize(width2,40);

        Label col3 = new Label(column3);
        col3.setPrefSize(width3,40);

        Label col4 = new Label(column4);
        col4.setPrefSize(width4,40);

        HBox hBox = new HBox();

        hBox.setMinHeight(40);
        hBox.getChildren().addAll(col1,col2,col3,col4);
        hBox.setSpacing(20);

        hBox.getStyleClass().add("list-row");
        hBox.setId(column1);
        hBox.setAlignment(Pos.CENTER_LEFT);


        Separator separator = new Separator();
        separator.setOpacity(0.1);

        vbox.getChildren().addAll(hBox,separator);

    }
    public static void addRow(VBox vbox, String column1, String column2, String column3, int width1, int width2, int width3, ListType type, Node...nodes) {
        // We work as a super programmers
        // so assume we put in (column1) the id of row :3


        Label col1 = new Label(column1);
        col1.setPrefSize(width1,40);

        Label col2 = new Label(column2);
        col2.setPrefSize(width2,40);

        Label col3 = new Label(column3);
        col3.setPrefSize(width3,40);

        HBox hBox = new HBox();

        hBox.setMinHeight(40);
        hBox.getChildren().addAll(col1,col2,col3);
        hBox.setSpacing(20);

        hBox.getStyleClass().add("list-row");
        hBox.setId(column1);
        hBox.setAlignment(Pos.CENTER_LEFT);


        if(type == ListType.CONT_LIST) {
            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[1].setDisable(true); // nav bar pane
                nodes[2].setVisible(true); // Cont info pane


                    // user lookup here :3
                    // trust me bro
                    // nodes[2].lookup("Any id");
                    new Thread(() -> Platform.runLater(() -> {
                        Contractor contractor = DBapi.getContractorInfo(column1);
                        currentContractorProfilePage = contractor;
                        Pane infoPane = (Pane) nodes[2];

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        Label contractorInfoContId = (Label) infoPane.lookup("#contractorInfoContId");
                        Label contractorInfoContBirthdate = (Label) infoPane.lookup("#contractorInfoContBirthdate");
                        Label contractorInfoContName = (Label) infoPane.lookup("#contractorInfoContName");
                        Label contractorInfoContAge = (Label) infoPane.lookup("#contractorInfoContAge");
                        Label contractorInfoContType = (Label) infoPane.lookup("#contractorInfoContType");
                        VBox contractorCurrentProjectsVbox = (VBox) infoPane.lookup("#currentProjectsScrollPaneVbox");

                        if(contractor != null) {
                            contractorInfoContId.setText(contractor.getContractorId());
                            contractorInfoContBirthdate.setText(dateFormat.format(contractor.getBirthdate()));
                            contractorInfoContName.setText(contractor.getFname() + " " + contractor.getMname() + " " + contractor.getLname());
                            contractorInfoContAge.setText(getAge(contractor.getBirthdate(), new Date()) + " yo");
                            contractorInfoContType.setText(contractor.getContractorType());
                            ArrayList<Project> currentProjects = DBapi.getCurrentContractorProjects(contractor.getContractorId());
                           currentProjects.forEach(project -> {
                               resetRows(contractorCurrentProjectsVbox);
                               addRow(contractorCurrentProjectsVbox, project.getProjectId(), project.getProjType(), project.getCity() + " " +  project.getStreet(), 32, 168, 134, null, null);

                           });
                        }
                    })).start();
            });
        } else if(type == ListType.WAREHOUSE_LIST) {
            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[1].setDisable(true); // nav bar pane
                nodes[2].setVisible(true); // Warehouse info pane

                Warehouse warehouse = DBapi.getWarehouseInfo(column1);
                Pane infoPane = (Pane) nodes[2];

                Label warehouseId = (Label) infoPane.lookup("#warehouseInfoWid");
                Label warehouseCapacity = (Label) infoPane.lookup("#warehouseInfoWCapacityCount");
                Label warehouseLocation = (Label) infoPane.lookup("#warehouseInfoWLocation");
                Gauge warehouseInfoWCapacity = (Gauge) infoPane.lookup("#warehouseInfoWCapacity");
                VBox availableProductsVbox = (VBox) infoPane.lookup("#warehouseInfoPartsScrollPaneVbox");
                PieChart warehouseInfoWStorage = (PieChart) infoPane.lookup("#warehouseInfoWStorage");

                if(warehouse != null){
                    warehouseId.setText(warehouse.getWarehouseId());
                    warehouseLocation.setText(warehouse.getCity());
                    warehouseCapacity.setText(warehouse.getCapacity().toString());


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
                    resetRows(availableProductsVbox);
                    availableProducts.forEach(product -> {
                        addRow(availableProductsVbox, product.getProductId(), product.getProductName(), String.valueOf(product.getAvailable_count()), 86, 143, 103, null, null);

                    });
                }
            });
        } else if(type == ListType.CURRENT_PROJECTS_LIST) {

            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[1].setVisible(true); // Project info pane

            });
        } else if(type == ListType.PENDING_LIST) {
            hBox.getStyleClass().remove("list-row");
            hBox.getStyleClass().add("list-row-ex");
            Label acceptLabel = new Label();
            FontIcon acceptIcon = new FontIcon();
            acceptLabel.setPrefWidth(24);
            acceptIcon.setIconLiteral("fltfal-checkmark-circle-24");
            acceptIcon.getStyleClass().add("accept-icon-button");
            acceptIcon.setIconSize(25);
            acceptLabel.setGraphic(acceptIcon);
            acceptLabel.setOnMouseClicked(mouseEvent -> {
                DBapi.acceptEmployeeAccount(column1);
                enhancedScrollPane.resetRows((VBox) hBox.getParent());
                ArrayList<Employee> pendingUsers = getAllPendingAccounts();
                pendingUsers.forEach(employeeInstance -> {
                    User userInstance = getUserInfo(employeeInstance.getEid());
                    enhancedScrollPane.addRow((VBox) hBox.getParent(),employeeInstance.getEid(), employeeInstance.getFname() + " " + employeeInstance.getMname() + " " + employeeInstance.getLname() ,userInstance.getNickName() ,37,138,131, Functions.ListType.PENDING_LIST);
                });

            });
            Label declineLabel = new Label();
            FontIcon declineIcon = new FontIcon();
            declineLabel.setPrefWidth(24);
            declineIcon.setIconLiteral("fltfal-dismiss-circle-24");
            declineIcon.getStyleClass().add("decline-icon-button");
            declineIcon.setIconSize(25);
            declineLabel.setGraphic(declineIcon);
            declineLabel.setOnMouseClicked(mouseEvent -> {
                DBapi.denyEmployeeAccount(column1);
                enhancedScrollPane.resetRows((VBox) hBox.getParent());
                ArrayList<Employee> pendingUsers = getAllPendingAccounts();
                pendingUsers.forEach(employeeInstance -> {
                    User userInstance = getUserInfo(employeeInstance.getEid());
                    enhancedScrollPane.addRow((VBox) hBox.getParent(),employeeInstance.getEid(), employeeInstance.getFname() + " " + employeeInstance.getMname() + " " + employeeInstance.getLname() ,userInstance.getNickName() ,37,138,131, Functions.ListType.PENDING_LIST);
                });
            });
            hBox.getChildren().addAll(acceptLabel,declineLabel);

        } else if(type == ListType.PROJ_MANAGER_PROJ_EMPLOYEES) {
            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[1].setDisable(true); // navbar
                displayEmployeeInfo(column1,(Pane)nodes[2]); // nodes[2] = employeeInfoPage

            });
        }

        Separator separator = new Separator();
        separator.setOpacity(0.1);

        vbox.getChildren().addAll(hBox,separator);

    }
    private void loadContractorInfo(Contractor contractor){

    }
    public static void resetRows(VBox vbox) {
        vbox.getChildren().clear();
    }

    private static void displayEmployeeInfo(String eid,Pane employeeInfoPage) {
        Employee employee = null;
        User user = getUserInfo((String) Navigator.getValue("eid"));
        assert user != null;
        employee = getEmployeeInfo(eid);
        if (employee == null) return;
        resetRows((VBox) employeeInfoPage.lookup("#lastProjectsScrollPaneVbox"));
        SimpleDateFormat birthdateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat projectDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat lastLoginFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm aa");

        Employee finalEmployee = employee;
        new Thread(() -> {
            Platform.runLater(() -> {
                User _user = getUserInfo(finalEmployee.getEid());
                if (_user == null || _user.getLastLogin() == null) {
                    ((Label)employeeInfoPage.lookup("#employeeInfoLastLogin")).setText("Never");
                } else {
                    ((Label)employeeInfoPage.lookup("#employeeInfoLastLogin")).setText(lastLoginFormat.format(_user.getLastLogin()));
                }
            });

        }).start();

        if (employee.getJobPos().equals("Technician") || employee.getJobPos().equals("Project Monitor") || employee.getJobPos().equals("Project Manager")) {

            employeeInfoPage.lookup("#lastProjectsCard").setVisible(true);
            Project project = getCurrentProject(employee.getEid());
            if (project == null) {
                (employeeInfoPage.lookup("#currentProjectCard1")).setVisible(false);
                (employeeInfoPage.lookup("#projManYouDontWork")).setVisible(true);
            } else {
                (employeeInfoPage.lookup("#currentProjectCard")).setVisible(true);
                (employeeInfoPage.lookup("#currentProjectCard1")).setVisible(true);
                (employeeInfoPage.lookup("#projManYouDontWork")).setVisible(false);

                Contractor contractor = getContractorInfo(project.getContractorId());
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectName")).setText(project.getCity() + " " + project.getProjType());
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectCity")).setText("");
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectType")).setText("");
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectId")).setText(project.getProjectId());
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectStartDate")).setText(projectDateFormat.format(project.getStartDate()));
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectDueDate")).setText(projectDateFormat.format(project.getDueDate()));
                ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectStreet")).setText(project.getCity() + (project.getStreet() == null ? "" : " - " + project.getStreet()));
                if (contractor == null) {
                    ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectContractor")).setText("No contractor.");
                } else {
                    ((Label)employeeInfoPage.lookup("#employeeInfoCurrentProjectContractor")).setText("Cont. : " + contractor.getFname() + " " + contractor.getLname());
                }
            }



            ArrayList<Project> recentFinishedProjects = getRecentFinishedProjects(employee.getEid());
            recentFinishedProjects.forEach(recentProject -> {
                addRow((VBox)employeeInfoPage.lookup("#lastProjectsScrollPaneVbox"),recentProject.getProjectId(),recentProject.getCity() + " " + recentProject.getProjType(), recentProject.getCity() + (recentProject.getStreet() == null ? "" : " - " + recentProject.getStreet()), 32, 205, 98, Functions.ListType.LAST_PROJECTS_LIST);
            });


        }else { // this employee can't work on projects
            employeeInfoPage.lookup("#employeeInfoLastProjectsLabel").setVisible(false);
            employeeInfoPage.lookup("#employeeInfoAssignToProjectButton").setVisible(false);
            employeeInfoPage.lookup("#currentProjectCard").setVisible(false);
            employeeInfoPage.lookup("#lastProjectsCard").setVisible(false);
        }
        ((Label)employeeInfoPage.lookup("#employeeInfoEmpName")).setText(employee.getFname() + " " + employee.getMname() + " " + employee.getLname());
        ((Label)employeeInfoPage.lookup("#employeeInfoEmpId")).setText(employee.getEid());
        ((Label)employeeInfoPage.lookup("#employeeInfoEmpBirthdate")).setText(birthdateFormat.format(employee.getBirthdate()));
        ((Label)employeeInfoPage.lookup("#employeeInfoEmpJobPos")).setText(employee.getJobPos());
        ((Label)employeeInfoPage.lookup("#employeeInfoEmpAge")).setText(getAge(employee.getBirthdate(), new Date()) + " yo");
        ((Label)employeeInfoPage.lookup("#employeeInfoEmpDistrict")).setText(employee.getDistrict());

        if(user.getJobPos() == Functions.JobPos.PROJ_MANAGER) {
            employeeInfoPage.lookup("#employeeInfoRemoveFromProject").setVisible(false);
            employeeInfoPage.lookup("#employeeInfoAssignButton").setVisible(false);
            employeeInfoPage.lookup("#employeeInfoEditButton").setVisible(false);
        } else {
            employeeInfoPage.lookup("#employeesPane").setVisible(false);
            employeeInfoPage.lookup("#homeNavBarVBox").setDisable(true);

        }
        employeeInfoPage.lookup("#employeeInfoCloseLabel").setVisible(true);
        employeeInfoPage.lookup("#employeeInfoPane").setVisible(true);


    }
    static int getAge(Date firstDate, Date secondDate) {
        Calendar firstYear = getCalendar(firstDate);
        Calendar secondYear = getCalendar(secondDate);
        int numOfYears = secondYear.get(YEAR) - firstYear.get(YEAR);
        if (firstYear.get(MONTH) > secondYear.get(MONTH) ||
                (firstYear.get(MONTH) == secondYear.get(MONTH) && firstYear.get(DATE) > secondYear.get(DATE))) {
            numOfYears--;
        }
        return numOfYears;
    }


    static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(date);
        return calendar;
    }
}
