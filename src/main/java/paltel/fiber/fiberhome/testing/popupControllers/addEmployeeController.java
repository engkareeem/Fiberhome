package paltel.fiber.fiberhome.testing.popupControllers;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import paltel.fiber.fiberhome.testing.utils.DBapi;
import paltel.fiber.fiberhome.testing.Functions;
import paltel.fiber.fiberhome.testing.Navigator;
import paltel.fiber.fiberhome.testing.homecontroller.employeesTableViewFunctions;
import paltel.fiber.fiberhome.testing.homecontroller.homePageController;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class addEmployeeController implements Initializable {
    Stage stage;
    @FXML
    Pane employeePane;
    @FXML
    AnchorPane ap;
    @FXML
    Label genderLabel,idNumberValidator,birthdateValidator;
    @FXML
    MFXDatePicker birthdateDatePicker;
    @FXML
    ComboBox<String> jobPosComboBox;
    @FXML
    TextField firstNameTf,middleNameTf,lastNameTf,idNumberTf,districtTf;

    boolean gender=true; // true for male, false for female
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Functions.emptyTextFieldListener(firstNameTf);
        Functions.emptyTextFieldListener(middleNameTf);
        Functions.emptyTextFieldListener(lastNameTf);
        Functions.emptyTextFieldListener(districtTf);
        Functions.idNumberListener(idNumberTf,idNumberValidator);
        Functions.birthdateListener(birthdateDatePicker,birthdateValidator);

        stage = Navigator.popupStage;
        ap.setOpacity(0);
        Platform.runLater(() -> {
            ZoomIn stageZoom = new ZoomIn(ap);
            stageZoom.setSpeed(2);
            stageZoom.play();
        });
        new Thread(() -> Platform.runLater(() -> {
            ArrayList<String> jobPositions = DBapi.getJobPositions();
            ObservableList<String> jobPositionsObservableList = FXCollections.observableArrayList(jobPositions);
            jobPosComboBox.setItems(jobPositionsObservableList);

        })).start();
        Functions.move(stage,employeePane);
    }
    @FXML
    public void genderClicked() {
        if(gender) {
            genderLabel.getStyleClass().removeAll("maleLabel");
            genderLabel.getStyleClass().add("femaleLabel");
        } else {
            genderLabel.getStyleClass().removeAll("femaleLabel");
            genderLabel.getStyleClass().add("maleLabel");
        }
        gender = !gender;
    }
    @FXML
    public void addEmployeeButtonClicked() {
        boolean valid = true;
        if(!Functions.validateEmptyTextField(firstNameTf)) {
            Functions.displayValidatingError(firstNameTf, null, null);
            valid = false;
        }
        if(!Functions.validateEmptyTextField(middleNameTf)) {
            Functions.displayValidatingError(middleNameTf, null, null);
            valid = false;
        }
        if(!Functions.validateEmptyTextField(lastNameTf)) {
            Functions.displayValidatingError(lastNameTf, null, null);
            valid = false;
        }
        if(!Functions.validateEmptyTextField(districtTf)) {
            Functions.displayValidatingError(districtTf, null, null);
            valid = false;
        }
        if(!Functions.validateIdNumber(idNumberTf,idNumberValidator)) {
            Functions.displayValidatingError(idNumberTf, null, null);
            valid = false;
        }
        if(!Functions.validateBirthdate(birthdateDatePicker,birthdateValidator)) {
            Functions.displayValidatingError(birthdateDatePicker,null,null);
            valid = false;
        }
        if(jobPosComboBox.getSelectionModel().getSelectedItem() == null) {
            Functions.displayValidatingComboBoxError(jobPosComboBox,null,null);
            valid = false;
        }
        if(!valid) return;

        DBapi.addEmployee(idNumberTf.getText(), firstNameTf.getText(), middleNameTf.getText(), lastNameTf.getText(),java.sql.Date.valueOf(birthdateDatePicker.getValue()), districtTf.getText(),( gender? 'M' : 'F') , jobPosComboBox.getValue() );
        closePopup();
        new Thread(() -> Platform.runLater(() -> employeesTableViewFunctions.initializeTableView(homePageController.employeesTableView))).start();

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
            Functions.closeAddEmployeePopup();
        });
        closeAnimation.play();
    }
}
