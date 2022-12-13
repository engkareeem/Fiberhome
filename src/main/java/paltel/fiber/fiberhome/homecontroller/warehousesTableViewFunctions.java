package paltel.fiber.fiberhome.homecontroller;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paltel.fiber.fiberhome.model.Warehouse;
import paltel.fiber.fiberhome.utils.DBapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class warehousesTableViewFunctions {
    private static MFXTableView<Warehouse> warehousesTable;
    public static void initializeTableView(MFXTableView<Warehouse> tableview) {
        warehousesTable =tableview;

        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********

        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<Warehouse> idColumn = new MFXTableColumn<>("WID", true, Comparator.comparing(Warehouse::getWarehouseId));
        MFXTableColumn<Warehouse> locationColumn = new MFXTableColumn<>("Location", true, Comparator.comparing(Warehouse::getCity));
        MFXTableColumn<Warehouse> capacityColumn = new MFXTableColumn<>("Capacity", true, Comparator.comparing(Warehouse::getCapacity));
        MFXTableColumn<Warehouse> projectsCountColumn = new MFXTableColumn<>("Projects Imports from", true, Comparator.comparing(Warehouse::getProjectCount));

        idColumn.setMinWidth(100);
        locationColumn.setMinWidth(120);
        capacityColumn.setMinWidth(120);
        projectsCountColumn.setMinWidth(120);



        ObservableList<Warehouse> warehouses;

        warehouses = FXCollections.observableArrayList(

        );
        ArrayList<Warehouse> warehouseArrayList= DBapi.getAllWarehousesWithProjectsCount();
        warehouses.addAll(warehouseArrayList);

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Warehouse::getWarehouseId));
        locationColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Warehouse::getCity));
        capacityColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Warehouse::getCapacity));
        projectsCountColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Warehouse::getProjectCount));

        tableview.getTableColumns().clear();
        tableview.getTableColumns().addAll(idColumn, locationColumn, capacityColumn, projectsCountColumn);
        tableview.getFilters().addAll(
                new StringFilter<>("WID", Warehouse::getWarehouseId),
                new StringFilter<>("City", Warehouse::getCity),
                new IntegerFilter<>("Type", Warehouse::getCapacity),
                new IntegerFilter<>("Projects Count", Warehouse::getProjectCount)
        );
        tableview.setItems(warehouses);
    }
    public static Warehouse getSelectedRow() {
        IMultipleSelectionModel<Warehouse> selectionModel = warehousesTable.getSelectionModel();
        Collection<Warehouse> selected = selectionModel.getSelection().values();
        if(selected.toArray().length == 0) return null;
        return (Warehouse) selected.toArray()[0];
    }
}
