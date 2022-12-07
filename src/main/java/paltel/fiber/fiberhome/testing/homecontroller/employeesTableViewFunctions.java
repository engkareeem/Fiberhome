package paltel.fiber.fiberhome.testing.homecontroller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paltel.fiber.fiberhome.testing.DBapi;
import paltel.fiber.fiberhome.testing.model.Employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;



public class employeesTableViewFunctions {
    static MFXTableView<Employee> employeesTable;

    public static void initializeTableView(MFXTableView<Employee> tableview) {
        employeesTable=tableview;

        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********





        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<Employee> idColumn = new MFXTableColumn<>("EID", true, Comparator.comparing(Employee::getEid));
        MFXTableColumn<Employee> FNameColumn = new MFXTableColumn<>("First Name", true, Comparator.comparing(Employee::getFname));
        MFXTableColumn<Employee> MNameColumn = new MFXTableColumn<>("Middle Name", true, Comparator.comparing(Employee::getMname));
        MFXTableColumn<Employee> LNameColumn = new MFXTableColumn<>("Last Name", true, Comparator.comparing(Employee::getLname));
        MFXTableColumn<Employee> districtColumn = new MFXTableColumn<>("District", true, Comparator.comparing(Employee::getDistrict));
        MFXTableColumn<Employee> jobPositionColumn = new MFXTableColumn<>("Job Position", true, Comparator.comparing(Employee::getJobPos));
        MFXTableColumn<Employee> isHasAccount = new MFXTableColumn<>("Has Account", true, Comparator.comparing(Employee::isHasAccount));

        idColumn.setMinWidth(100);
        FNameColumn.setMinWidth(120);
        MNameColumn.setMinWidth(120);
        LNameColumn.setMinWidth(120);
        districtColumn.setMinWidth(100);
        jobPositionColumn.setMinWidth(200);

        ObservableList<Employee> employees;


        employees = FXCollections.observableArrayList();
        ArrayList<Employee> employeeArrayList = DBapi.getAllEmployees();
        employees.addAll(employeeArrayList);

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getEid));
        FNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getFname));
        MNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getMname));
        LNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getLname));
        districtColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getDistrict));
        jobPositionColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getJobPos));
        isHasAccount.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::isHasAccount));

        tableview.getTableColumns().addAll(idColumn, FNameColumn, MNameColumn, LNameColumn, districtColumn,jobPositionColumn,isHasAccount);
        tableview.getFilters().addAll(
                new StringFilter<>("EID", Employee::getEid),
                new StringFilter<>("First Name", Employee::getFname),
                new StringFilter<>("Middle Name", Employee::getMname),
                new StringFilter<>("Last Name", Employee::getLname),
                new StringFilter<>("District", Employee::getDistrict),
                new StringFilter<>("Job Position", Employee::getJobPos),
                new StringFilter<>("Has Account", Employee::isHasAccount)
        );
        tableview.setItems(employees);
    }
    public static Employee getSelectedRow() {
        IMultipleSelectionModel<Employee> selectionModel = employeesTable.getSelectionModel();
        Collection<Employee> selected = selectionModel.getSelection().values();
        if(selected.toArray().length == 0) return null;
        return (Employee) selected.toArray()[0];
    }
}
