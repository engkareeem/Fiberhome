package paltel.fiber.fiberhome.popupControllers;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import paltel.fiber.fiberhome.Functions;
import paltel.fiber.fiberhome.model.Contractor;
import paltel.fiber.fiberhome.model.Product;
import paltel.fiber.fiberhome.model.Warehouse;
import paltel.fiber.fiberhome.Navigator;
import paltel.fiber.fiberhome.utils.DBapi;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addProjectController implements Initializable {
    @FXML
    AnchorPane ap;
    @FXML
    Pane projectPane;

    @FXML
    TextField cityTf,streetTf;
    @FXML
    Label endDateValidator,totalBudgetLabel;
    @FXML
    MFXDatePicker endDatePicker;
    @FXML
    ComboBox<String> projectContComboBox,typeComboBox,materialsComboBox;

    @FXML
    MFXScrollPane usedPartsScrollPane;
    @FXML
    VBox usedPartsScrollPaneVbox;


    Stage stage;


    long totalBudget = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Functions.emptyTextFieldListener(cityTf);
        Functions.emptyTextFieldListener(streetTf);
        Functions.projectEndDateListener(endDatePicker,endDateValidator);


        stage = Navigator.popupStage;
        ap.setOpacity(0);
        Platform.runLater(() -> {
            ZoomIn stageZoom = new ZoomIn(ap);
            stageZoom.setSpeed(2);
            stageZoom.play();
        });
        Functions.move(stage,projectPane);


        // project types
        ArrayList<String> projectTypes = DBapi.getProjectTypes();
        typeComboBox.setItems(FXCollections.observableArrayList(projectTypes));

        // contractors
        ArrayList<Contractor> contractors = DBapi.getAllContractors();
        ArrayList<String>  contractorNames = new ArrayList<>();
        for(Contractor contractorInstance: contractors){
            contractorNames.add(contractorInstance.getContractorId() + " " + contractorInstance.getFname() + " " + contractorInstance.getMname() + " " + contractorInstance.getLname());
        }
        projectContComboBox.setItems(FXCollections.observableArrayList(contractorNames));

        // warehouse

        ArrayList<Warehouse> warehouses = DBapi.getAllWarehouses();
        ArrayList<String> warehousesNames = new ArrayList<>();
        for(Warehouse warehouseInstance: warehouses){
            warehousesNames.add(warehouseInstance.getWarehouseId() + " " + warehouseInstance.getCity());
        }
        materialsComboBox.setItems(FXCollections.observableArrayList(warehousesNames));






    }


    @FXML
    public void addProjectClicked() {
        boolean valid = true;
        if(!Functions.validateEmptyTextField(cityTf)) {
            Functions.displayValidatingError(cityTf,null,null);
            valid=false;
        }
        if(!Functions.validateEmptyTextField(streetTf)) {
            Functions.displayValidatingError(streetTf,null,null);
            valid = false;
        }
        if(!Functions.validateProjectEndDate(endDatePicker,endDateValidator)) {
            Functions.displayValidatingError(endDatePicker,null,null);
            valid = false;
        }
        if(projectContComboBox.getSelectionModel().getSelectedItem() == null) {
            Functions.displayValidatingComboBoxError(projectContComboBox,null,null);
            valid = false;
        }
        if(typeComboBox.getSelectionModel().getSelectedItem() == null) {
            Functions.displayValidatingComboBoxError(typeComboBox,null,null);
            valid = false;
        }


        if(!valid) return;



        String warehouseID = materialsComboBox.getValue().trim().split("\\s+")[0];


        String projectId = DBapi.addProject(cityTf.getText(), streetTf.getText(), endDatePicker.getValue(), typeComboBox.getValue(), projectContComboBox.getValue().split("\\s+")[0] , (int) totalBudget);
        ArrayList<Product> availableProductsInWarehouse = DBapi.getAvailableProductsInWarehouse(warehouseID);
        for (Product productInstance : availableProductsInWarehouse) {
            TextField amountTextField = (TextField) usedPartsScrollPane.lookup("#" + productInstance.getProductId() + "TextField");
            int productAmount = Integer.parseInt(amountTextField.getText());
            if(productAmount > 0){
                DBapi.reserveProductForAProject(productInstance.getProductId(),projectId, warehouseID, productAmount);
            }
        }


        closePopup();
    }

    @FXML
    public void close() {
        closePopup();
    }
    @FXML
    private void closePopup() {
        ZoomOut closeAnimation = new ZoomOut(ap);
        closeAnimation.setSpeed(3);
        closeAnimation.setOnFinished((event) -> {
            Functions.closeAddProjectPopup();
        });
        closeAnimation.play();
    }

    private void addPartRow(String PID, String PName,String price,int available) {
        Label PIDLabel = new Label(PID);
        Label PNameLabel = new Label(PName);
        Label priceLabel = new Label(price);
        Label availableLabel = new Label(String.valueOf(available));

        PIDLabel.getStyleClass().add("parts-table-cell");
        PIDLabel.setPrefWidth(95);

        PNameLabel.getStyleClass().add("parts-table-cell");
        PNameLabel.setPrefWidth(155);

        priceLabel.getStyleClass().add("parts-table-cell");
        priceLabel.setPrefWidth(65);

        availableLabel.getStyleClass().add("parts-table-cell");
        availableLabel.setPrefWidth(130);


//        ComboBox<String> warehousesComboBox = new ComboBox<>();
//        warehousesComboBox.setPrefWidth(130);
//        warehousesComboBox.setPromptText("Warehouse");
//        warehousesComboBox.getStyleClass().add("combo-box-ex");
//        warehousesComboBox.setId(PID + "ComboBox"); // Combobox id





        TextField amountTextField = new TextField();
        amountTextField.getStyleClass().add("informationTextFields");
        amountTextField.setAlignment(Pos.CENTER);
        amountTextField.setText("0");

        amountTextField.textProperty().addListener((obs, oldText, newText) -> {
            if(!Functions.isNum(newText) && !newText.equals("")) amountTextField.setText(oldText);
            else {
                if(oldText.equals("0")) {
                    if(!newText.equals("0")) amountTextField.setText(newText.replace("0",""));
                }
                if(newText.equals("")) {
                    amountTextField.setText("0");

                     totalBudget -= Integer.parseInt(priceLabel.getText()) * Integer.parseInt(oldText);
                } else if(!oldText.equals("")) {
                     totalBudget += (Integer.parseInt(priceLabel.getText()) * Integer.parseInt(newText)) - (Integer.parseInt(priceLabel.getText()) * Integer.parseInt(oldText));
                }
                totalBudgetLabel.setText(totalBudget + "$");
            }
        });
        
        
        amountTextField.setId(PID + "TextField"); // Text field id


        amountTextField.setPrefWidth(90);

        HBox row = new HBox();
        row.getStyleClass().add("list-row");
        row.setMinHeight(40);
        row.getChildren().addAll(PIDLabel,PNameLabel,priceLabel,availableLabel,amountTextField);
        row.setPrefWidth(645);
        Separator separator = new Separator();
        separator.prefWidth(645);
        separator.setOpacity(0.1);
        usedPartsScrollPaneVbox.getChildren().addAll(row,separator);
    }
    public void warehouseComboboxValueChanged() {
        if(materialsComboBox.getValue() != null && !materialsComboBox.getValue().isEmpty()) {
            resetRows(usedPartsScrollPaneVbox);
            String warehouseID = materialsComboBox.getValue().trim().split("\\s+")[0];
            ArrayList<Product> availableProductsInWarehouse = DBapi.getAvailableProductsInWarehouse(warehouseID);
            availableProductsInWarehouse.forEach(productInstance -> {
                addPartRow(productInstance.getProductId(), productInstance.getProductName(), productInstance.getCost().toString(), productInstance.getAvailable_count());

            });
        }
    }
    public static void resetRows(VBox vbox) {
        vbox.getChildren().clear();
    }
}
