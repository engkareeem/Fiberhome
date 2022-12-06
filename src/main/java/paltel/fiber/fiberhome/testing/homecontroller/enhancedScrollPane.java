package paltel.fiber.fiberhome.testing.homecontroller;

import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import paltel.fiber.fiberhome.testing.Functions.*;

public class enhancedScrollPane {
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

        if(type == ListType.CONT_LIST) {
            hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    nodes[0].setVisible(false); // current page pane
                    nodes[1].setDisable(true); // nav bar pane
                    nodes[2].setVisible(true); // Cont info pane

                    // user lookup here :3
                    // trust me bro
                    // nodes[2].lookup("Any id");
                    // TODO: [Contractor info initialize]
                    // if you ask about contractor id
                    // its inside column1 :D
                }
            });
        }

        Separator separator = new Separator();
        separator.setOpacity(0.1);

        vbox.getChildren().addAll(hBox,separator);

    }
    public static void resetRows(VBox vbox) {
        vbox.getChildren().clear();
    }
}
