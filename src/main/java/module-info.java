module paltel.fiber.fiberhome.testing {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires MaterialFX;
    requires java.sql;
    requires com.jfoenix;
    requires ojdbc8;
    requires org.kordamp.ikonli.javafx;


    opens paltel.fiber.fiberhome.testing to javafx.fxml;
    exports paltel.fiber.fiberhome.testing;
}