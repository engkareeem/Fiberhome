module paltel.fiber.fiberhome.testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires com.jfoenix;


    opens paltel.fiber.fiberhome.testing to javafx.fxml;
    exports paltel.fiber.fiberhome.testing;
}