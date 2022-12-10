package paltel.fiber.fiberhome.testing.homecontroller;

import com.jfoenix.controls.JFXBadge;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import paltel.fiber.fiberhome.testing.DBapi;
import paltel.fiber.fiberhome.testing.Functions.*;
import paltel.fiber.fiberhome.testing.model.Contractor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;

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
            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[1].setDisable(true); // nav bar pane
                nodes[2].setVisible(true); // Cont info pane


                    // user lookup here :3
                    // trust me bro
                    // nodes[2].lookup("Any id");
                    new Thread(() -> Platform.runLater(() -> {
                        Contractor contractor = DBapi.getContractorInfo(column1);
                        Pane infoPane = (Pane) nodes[2];

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                        Label contractorInfoContId = (Label) infoPane.lookup("#contractorInfoContId");
                        Label contractorInfoContBirthdate = (Label) infoPane.lookup("#contractorInfoContBirthdate");
                        Label contractorInfoContName = (Label) infoPane.lookup("#contractorInfoContName");
                        Label contractorInfoContAge = (Label) infoPane.lookup("#contractorInfoContAge");
                        Label contractorInfoContType = (Label) infoPane.lookup("#contractorInfoContType");
                        if(contractor != null) {
                            contractorInfoContId.setText(contractor.getContractorId());
                            contractorInfoContBirthdate.setText(dateFormat.format(contractor.getBirthdate()));
                            contractorInfoContName.setText(contractor.getFname() + " " + contractor.getMname() + " " + contractor.getLname());
                            contractorInfoContAge.setText(getAge(contractor.getBirthdate(), new Date()) + " yo");
                            contractorInfoContType.setText(contractor.getContractorType());
                        }
                    })).start();
                    // TODO: [Contractor info initialize]
                    // if you ask about contractor id
                    // its inside column1 :D
                }
                // user lookup here :3
                // trust me bro
                // nodes[2].lookup("Any id");
                // TODO: [Contractor info initialize]
                // if you ask about contractor id
                // its inside column1 :D
            });
        } else if(type == ListType.WAREHOUSE_LIST) {
            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[1].setDisable(true); // nav bar pane
                nodes[2].setVisible(true); // Warehouse info pane



                    // user lookup here :3
                    // trust me bro
                    // nodes[2].lookup("Any id");
                    // TODO: [Warehouse info initialize]
                    // if you ask about warehouse id
                    // its inside column1 :D
                }
                // user lookup here :3
                // trust me bro
                // nodes[2].lookup("Any id");
                // TODO: [Warehouse info initialize]
                // if you ask about warehouse id
                // its inside column1 :D
            });
        } else if(type == ListType.CURRENT_PROJECTS_LIST) {

            hBox.setOnMouseClicked(mouseEvent -> {
                nodes[0].setVisible(false); // current page pane
                nodes[2].setVisible(true); // Project info pane
                // TODO: [Project info initialize]
            });
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
