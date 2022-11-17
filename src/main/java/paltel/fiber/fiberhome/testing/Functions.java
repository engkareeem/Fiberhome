package paltel.fiber.fiberhome.testing;

import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Functions {

/*                           Optimize design                                                             */

    public static void optimizeImageView(ImageView imageView) {

        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(29);
        clip.setArcHeight(29);
        imageView.setClip(clip);
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);
        imageView.setClip(null);
        imageView.setImage(image);
    }


/*                                                      Validators                                                   */

    public static void employeeNumberValidatorListener(TextField inputField,Label validatorLabel) {
        inputField.textProperty().addListener((obs, oldText, newText) -> {
            validateEmployeeNumber(inputField,validatorLabel);
        });
    }
    public static void passwordValidatorListener(TextField inputField,Label validatorLabel) {
        inputField.textProperty().addListener((obs, oldText, newText) -> {
            validatePassword(inputField,validatorLabel);
        });
    }
    public static void nickNameValidatorListener(TextField inputField,Label validatorLabel) {
        inputField.textProperty().addListener((obs, oldText, newText) -> {
            validateNickname(inputField,validatorLabel);
        });
    }

    public static boolean validateNickname(TextField inputField,Label validatorLabel) {
        String text = inputField.getText();
        if(text.length() >=2) {
            validatorLabel.setText("");
            inputField.getStyleClass().removeAll("informationTextFieldsError");
            inputField.getStyleClass().add("informationTextFields");
            return true;
        }
        else {
            validatorLabel.setText("Nickname must be 2 or more characters");
            inputField.getStyleClass().removeAll("informationTextFields");
            inputField.getStyleClass().add("informationTextFieldsError");
            return false;
        }
    }
    public static boolean validateEmployeeNumber(TextField inputField,Label validatorLabel) {
        String text = inputField.getText();
        if(text.length() == 4 && isNum(text)) {
            validatorLabel.setText("");
            inputField.getStyleClass().removeAll("informationTextFieldsError");
            inputField.getStyleClass().add("informationTextFields");
            return true;
        }
        else {
            validatorLabel.setText("Employee number must be 4 digits");
            inputField.getStyleClass().removeAll("informationTextFields");
            inputField.getStyleClass().add("informationTextFieldsError");
            return false;
        }
    }

    public static boolean validatePassword(TextField inputField,Label validatorLabel) {
        String text = inputField.getText();
        if(text.length() > 8) {
            validatorLabel.setText("");
            inputField.getStyleClass().removeAll("informationTextFieldsError");
            inputField.getStyleClass().add("informationTextFields");
            return true;
        } else {
            validatorLabel.setText("Password should be 8 or more characters");
            inputField.getStyleClass().removeAll("informationTextFields");
            inputField.getStyleClass().add("informationTextFieldsError");
            return false;
        }
    }
    /*                                                  CHECKERS                                                   */
    public static boolean isNum(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
