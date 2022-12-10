package paltel.fiber.fiberhome.testing.homecontroller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paltel.fiber.fiberhome.testing.model.Supplier;

import java.util.Collection;
import java.util.Comparator;

public class suppliersTableViewFunctions {
    private static MFXTableView<Supplier> suppliersTable;
    public static void initializeTableView(MFXTableView<Supplier> tableview) {
        suppliersTable =tableview;

        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********

        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<Supplier> idColumn = new MFXTableColumn<>("SID", true, Comparator.comparing(Supplier::getSupplierId));
        MFXTableColumn<Supplier> companyNameColumn = new MFXTableColumn<>("Company name", true, Comparator.comparing(Supplier::getCompanyName));

        idColumn.setMinWidth(100);
        companyNameColumn.setMinWidth(120);

        ObservableList<Supplier> suppliers;

        suppliers = FXCollections.observableArrayList(
                new Supplier("7979","Sofyan company")
        );
//        ArrayList<Employee> employeeArrayList = DBapi.getAllEmployees();
//        project.addAll(employeeArrayList);

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Supplier::getSupplierId));
        companyNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Supplier::getCompanyName));

        tableview.getTableColumns().addAll(idColumn, companyNameColumn);
        tableview.getFilters().addAll(
                new StringFilter<>("SID", Supplier::getSupplierId),
                new StringFilter<>("Company Name", Supplier::getCompanyName)
        );
        tableview.setItems(suppliers);
    }
    public static Supplier getSelectedRow() {
        IMultipleSelectionModel<Supplier> selectionModel = suppliersTable.getSelectionModel();
        Collection<Supplier> selected = selectionModel.getSelection().values();
        if(selected.toArray().length == 0) return null;
        return (Supplier) selected.toArray()[0];
    }
}
