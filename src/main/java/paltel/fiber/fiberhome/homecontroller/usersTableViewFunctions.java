package paltel.fiber.fiberhome.homecontroller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paltel.fiber.fiberhome.model.User;
import paltel.fiber.fiberhome.utils.DBapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class usersTableViewFunctions {
    private static MFXTableView<User> usersTable;
    public static void initializeTableView(MFXTableView<User> tableview) {
        usersTable =tableview;

        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********

        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<User> idColumn = new MFXTableColumn<>("EID", true, Comparator.comparing(User::getEid));
        MFXTableColumn<User> userNickNameColumn = new MFXTableColumn<>("NickName", true, Comparator.comparing(User::getNickName));
        MFXTableColumn<User> roleColumn = new MFXTableColumn<>("Role", true, Comparator.comparing(User::getRole));
        MFXTableColumn<User> lastLoginColumn = new MFXTableColumn<>("Last Login", true, Comparator.comparing(User::getLastLogin));

        idColumn.setMinWidth(100);
        userNickNameColumn.setMinWidth(120);
        roleColumn.setMinWidth(120);
        lastLoginColumn.setMinWidth(200);



        ObservableList<User> users;

        users = FXCollections.observableArrayList(
        );
        ArrayList<User> employeeArrayList = DBapi.getAllUsers();
        users.addAll(employeeArrayList);

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(User::getEid));
        userNickNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(User::getNickName));
        roleColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(User::getRole));
        lastLoginColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(User::getFormattedLastLogin));

        tableview.getTableColumns().clear();
        tableview.getTableColumns().addAll(idColumn, userNickNameColumn, roleColumn, lastLoginColumn);
        tableview.getFilters().addAll(
                new StringFilter<>("UID", User::getEid),
                new StringFilter<>("User Nickname", User::getNickName),
                new StringFilter<>("Role", User::getRole)
        );
        tableview.setItems(users);
    }
    public static User getSelectedRow() {
        IMultipleSelectionModel<User> selectionModel = usersTable.getSelectionModel();
        Collection<User> selected = selectionModel.getSelection().values();
        if(selected.toArray().length == 0) return null;
        return (User) selected.toArray()[0];
    }
}
