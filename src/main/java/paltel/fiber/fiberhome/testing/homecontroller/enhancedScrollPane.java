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
import paltel.fiber.fiberhome.testing.utils.DBapi;
import paltel.fiber.fiberhome.testing.Functions.*;
import paltel.fiber.fiberhome.testing.model.Contractor;
import paltel.fiber.fiberhome.testing.model.Product;
import paltel.fiber.fiberhome.testing.model.Project;
import paltel.fiber.fiberhome.testing.model.Warehouse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;

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
                               addRow(contractorCurrentProjectsVbox, project.getProjectId(), project.getProjType(), project.getCity() +  project.getStreet(), 32, 168, 134, null, null);

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
                Label warehouseLocation = (Label) infoPane.lookup("#warehouseInfoWLocation");
                Gauge warehouseInfoWCapacity = (Gauge) infoPane.lookup("#warehouseInfoWCapacity");
                VBox availableProductsVbox = (VBox) infoPane.lookup("#warehouseInfoPartsScrollPaneVbox");
                PieChart warehouseInfoWStorage = (PieChart) infoPane.lookup("#warehouseInfoWStorage");

                if(warehouse != null){
                    warehouseId.setText(warehouse.getWarehouseId());
                    warehouseLocation.setText(warehouse.getCity());

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

                    availableProducts.forEach(product -> {
                        resetRows(availableProductsVbox);
                        addRow(availableProductsVbox, product.getProductId(), product.getProductName(), String.valueOf(product.getAvailable_count()), 86, 143, 103, null, null);

                    });
                }
            });
        } else if(type == ListType.CURRENT_PROJECTS_LIST) {

            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[2].setVisible(true); // Project info pane

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
                // accept code
                System.out.println("accept user " + column1);
            });
            Label declineLabel = new Label();
            FontIcon declineIcon = new FontIcon();
            declineLabel.setPrefWidth(24);
            declineIcon.setIconLiteral("fltfal-dismiss-circle-24");
            declineIcon.getStyleClass().add("decline-icon-button");
            declineIcon.setIconSize(25);
            declineLabel.setGraphic(declineIcon);
            declineLabel.setOnMouseClicked(mouseEvent -> {
                // decline code
                System.out.println("decline user " + column1);
            });
            hBox.getChildren().addAll(acceptLabel,declineLabel);

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
