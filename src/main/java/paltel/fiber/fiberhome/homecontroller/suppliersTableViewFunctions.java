package paltel.fiber.fiberhome.homecontroller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paltel.fiber.fiberhome.model.Supplier;
import paltel.fiber.fiberhome.utils.DBapi;

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
        MFXTableColumn<Supplier> supplierNumberColumn = new MFXTableColumn<>("Phone Number", true, Comparator.comparing(Supplier::getPhoneNumber));
        MFXTableColumn<Supplier> supplierEmailAddress = new MFXTableColumn<>("Email Address", true, Comparator.comparing(Supplier::getEmailAddress));
        MFXTableColumn<Supplier> supplierFax = new MFXTableColumn<>("Fax", true, Comparator.comparing(Supplier::getFax));

        idColumn.setMinWidth(100);
        companyNameColumn.setMinWidth(120);

        ObservableList<Supplier> suppliers;
        suppliers = FXCollections.observableArrayList(); // TODO: <--- Put the suppliers list here
//        ArrayList<Employee> employeeArrayList = DBapi.getAllEmployees();
//        project.addAll(employeeArrayList);

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Supplier::getSupplierId));
        companyNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Supplier::getCompanyName));

        tableview.getTableColumns().clear();
//        tableview.getTableColumns().addAll(idColumn, companyNameColumn,supplierNumberColumn,supplierEmailAddress,supplierFax);
        tableview.getFilters().addAll(
                new StringFilter<>("SID", Supplier::getSupplierId),
                new StringFilter<>("Company Name", Supplier::getCompanyName),
                new StringFilter<>("Phone Number", Supplier::getPhoneNumber),
                new StringFilter<>("Email Address", Supplier::getEmailAddress),
                new StringFilter<>("Fax", Supplier::getFax)
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
