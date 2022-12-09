package paltel.fiber.fiberhome.testing;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
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

import java.net.URL;
import java.util.ResourceBundle;

public class addProjectController implements Initializable {
    @FXML
    AnchorPane ap;
    @FXML
    Pane projectPane;

    @FXML
    TextField cityTf,streetTf;
    @FXML
    Label endDateValidator;
    @FXML
    MFXDatePicker endDatePicker;
    @FXML
    ComboBox<String> projectContComboBox,typeComboBox;

    @FXML
    MFXScrollPane usedPartsScrollPane;
    @FXML
    VBox usedPartsScrollPaneVbox;


    Stage stage;



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

        addPartRow("0000000001","Fiber Optic Line","69000");
        addPartRow("0000000002","Copper Cable","34000");
        addPartRow("0000000003","Ethernet Cable","65000");
        addPartRow("0000000004","Twisted Cable","690");
        addPartRow("0000000005","Twisted Cable","690");
        addPartRow("0000000006","Twisted Cable","690");
        addPartRow("0000000007","Twisted Cable","690");
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


        // TODO: Add project here


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

    private void addPartRow(String PID, String PName,String price) {
        Label PIDLabel = new Label(PID);
        Label PNameLabel = new Label(PName);
        Label priceLabel = new Label(price);
        PIDLabel.getStyleClass().add("parts-table-cell");
        PIDLabel.setPrefWidth(95);

        PNameLabel.getStyleClass().add("parts-table-cell");
        PNameLabel.setPrefWidth(155);

        priceLabel.getStyleClass().add("parts-table-cell");
        priceLabel.setPrefWidth(65);


        ComboBox<String> warehousesComboBox = new ComboBox<>();
        warehousesComboBox.setPrefWidth(130);
        warehousesComboBox.setPromptText("Warehouse");

        warehousesComboBox.setId(PID + "ComboBox"); // Combobox id

        // TODO: Set warehouse combobox items here



        TextField amountTextField = new TextField();
        amountTextField.getStyleClass().add("informationTextFields");
        amountTextField.setAlignment(Pos.CENTER);

        amountTextField.setId(PID + "TextField"); // Text field id


        amountTextField.setText("0");
        amountTextField.setPrefWidth(90);

        HBox row = new HBox();
        row.getStyleClass().add("list-row");
        row.setMinHeight(40);
        row.getChildren().addAll(PIDLabel,PNameLabel,priceLabel,warehousesComboBox,amountTextField);
        row.setPrefWidth(645);
        Separator separator = new Separator();
        separator.prefWidth(645);
        separator.setOpacity(0.1);
        usedPartsScrollPaneVbox.getChildren().addAll(row,separator);
    }
}
