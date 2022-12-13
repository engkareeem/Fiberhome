module paltel.fiber.fiberhome.testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires MaterialFX;
    requires java.sql;
    requires com.jfoenix;
    requires com.oracle.database.jdbc;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.medusa;
    requires jasperreports;


    opens paltel.fiber.fiberhome to javafx.fxml;
    exports paltel.fiber.fiberhome;
    exports paltel.fiber.fiberhome.model;
    exports paltel.fiber.fiberhome.homecontroller;
    opens paltel.fiber.fiberhome.homecontroller to javafx.fxml;
    exports paltel.fiber.fiberhome.popupControllers;
    opens paltel.fiber.fiberhome.popupControllers to javafx.fxml;
    exports paltel.fiber.fiberhome.utils;
    opens paltel.fiber.fiberhome.utils to javafx.fxml;
}