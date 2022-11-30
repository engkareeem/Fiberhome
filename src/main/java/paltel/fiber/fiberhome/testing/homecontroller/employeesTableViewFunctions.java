package paltel.fiber.fiberhome.testing.homecontroller;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import paltel.fiber.fiberhome.testing.objects.Employee;

import java.time.LocalDate;
import java.util.Comparator;

public class employeesTableViewFunctions {
    public static void initializeTableView(MFXTableView<Employee> tableview) {
        // ***** IF YOU NEED TO EDIT ANYTHING, LEAVE COMMENT.. **********


        // Edit this if you need,
        // I will find solution for column width :3
        MFXTableColumn<Employee> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(Employee::getEid));
        MFXTableColumn<Employee> FNameColumn = new MFXTableColumn<>("FName", false, Comparator.comparing(Employee::getFname));
        MFXTableColumn<Employee> MNameColumn = new MFXTableColumn<>("MName", false, Comparator.comparing(Employee::getMname));
        MFXTableColumn<Employee> LNameColumn = new MFXTableColumn<>("LName", false, Comparator.comparing(Employee::getLname));
        MFXTableColumn<Employee> districtColumn = new MFXTableColumn<>("District", false, Comparator.comparing(Employee::getDistrict));
        MFXTableColumn<Employee> jobPositionColumn = new MFXTableColumn<>("Job Position", false, Comparator.comparing(Employee::getJobPos));

        ObservableList<Employee> employees;

        // TODO: Get employees data here
        //                               v
        employees = FXCollections.observableArrayList(
                new Employee("0","234879234","Ahmad","Majah","Salameh", LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Kareem","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Samaaneh","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Saadeh","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Hala","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Router","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Fridge","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Laptop","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Keyboard","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Toy","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin") ,
                new Employee("0","234879234","Suberb double terbo","Majah","Salameh",LocalDate.now(),"Doctor",'M',"Admin")
        );

        idColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getEid));
        FNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getFname));
        MNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getMname));
        LNameColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getLname));
        districtColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getDistrict));
        jobPositionColumn.setRowCellFactory(employee -> new MFXTableRowCell<>(Employee::getJobPos) {{
            setAlignment(Pos.CENTER_LEFT);
        }});

        tableview.getTableColumns().addAll(idColumn, FNameColumn, MNameColumn, LNameColumn, districtColumn,jobPositionColumn);
        tableview.getFilters().addAll(
                new StringFilter<>("ID", Employee::getEid),
                new StringFilter<>("FName", Employee::getFname),
                new StringFilter<>("MName", Employee::getMname),
                new StringFilter<>("LName", Employee::getLname),
                new StringFilter<>("District", Employee::getDistrict),
                new StringFilter<>("Job Position", Employee::getJobPos)
        );
        tableview.setItems(employees);
    }
}
