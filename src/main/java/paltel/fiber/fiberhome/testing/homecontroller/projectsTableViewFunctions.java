package paltel.fiber.fiberhome.testing.homecontroller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.selection.base.IMultipleSelectionModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import paltel.fiber.fiberhome.testing.DBapi;
import paltel.fiber.fiberhome.testing.model.Employee;
import paltel.fiber.fiberhome.testing.model.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;

public class projectsTableViewFunctions {
    private static MFXTableView<Project> projectsTable;
    public static void initializeTableView(MFXTableView<Project> tableview) {
        projectsTable=tableview;

        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********

        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<Project> idColumn = new MFXTableColumn<>("PID", true, Comparator.comparing(Project::getProjectId));
        MFXTableColumn<Project> contractorNameColumn = new MFXTableColumn<>("Contractor Name", true, Comparator.comparing(Project::getContractorId));
        MFXTableColumn<Project> typeColumn = new MFXTableColumn<>("Type", true, Comparator.comparing(Project::getProjType));
        MFXTableColumn<Project> totalAmountColumn = new MFXTableColumn<>("Total Budget", true, Comparator.comparing(Project::getAmount));
        MFXTableColumn<Project> cityColumn = new MFXTableColumn<>("City", true, Comparator.comparing(Project::getCity));
        MFXTableColumn<Project> streetColumn = new MFXTableColumn<>("Street", true, Comparator.comparing(Project::getStreet));

        idColumn.setMinWidth(100);
        contractorNameColumn.setMinWidth(120);
        typeColumn.setMinWidth(120);
        totalAmountColumn.setMinWidth(120);
        cityColumn.setMinWidth(100);
        streetColumn.setMinWidth(200);



        ObservableList<Project> projects;

        projects = FXCollections.observableArrayList(
                new Project("0001",15000, null,null,"Bidya","street","0001","Fiber")
        );
//        ArrayList<Employee> employeeArrayList = DBapi.getAllEmployees();
//        project.addAll(employeeArrayList);

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Project::getProjectId));
        contractorNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Project::getContractorId));
        typeColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Project::getProjType));
        totalAmountColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Project::getAmount));
        cityColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Project::getCity));
        streetColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Project::getStreet));

        tableview.getTableColumns().addAll(idColumn, contractorNameColumn, typeColumn, totalAmountColumn, cityColumn,streetColumn);
        tableview.getFilters().addAll(
                new StringFilter<>("PID", Project::getProjectId),
                new StringFilter<>("Contractor Name", Project::getContractorId),
                new StringFilter<>("Type", Project::getProjType),
                new IntegerFilter<>("Total Amount", Project::getAmount),
                new StringFilter<>("City", Project::getCity),
                new StringFilter<>("Street", Project::getStreet)
        );
        tableview.setItems(projects);
    }
    public static Project getSelectedRow() {
        IMultipleSelectionModel<Project> selectionModel = projectsTable.getSelectionModel();
        Collection<Project> selected = selectionModel.getSelection().values();
        if(selected.toArray().length == 0) return null;
        return (Project) selected.toArray()[0];
    }
}
