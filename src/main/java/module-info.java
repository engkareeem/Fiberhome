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

    opens paltel.fiber.fiberhome.testing to javafx.fxml;
    exports paltel.fiber.fiberhome.testing;
    exports paltel.fiber.fiberhome.testing.model;
    exports paltel.fiber.fiberhome.testing.homecontroller;
    opens paltel.fiber.fiberhome.testing.homecontroller to javafx.fxml;
}