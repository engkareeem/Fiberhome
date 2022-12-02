package paltel.fiber.fiberhome.testing;

import paltel.fiber.fiberhome.testing.model.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBapi {
    public static Connection connection = testingMain.dbConnection;


    // TODO: Get User information by userID
    // TODO: Get Employee information by EID

    // Employees
    private static Employee getEmployeeFromRow(ResultSet res) throws SQLException {
        Employee employee = new Employee();
        employee.setEid(res.getString("employee_id"));
        employee.setIdNum(res.getString("id_number"));
        employee.setFname(res.getString("fname"));
        employee.setMname(res.getString("mname"));
        employee.setLname(res.getString("lname"));
        employee.setBirthdate(res.getDate("birthdate"));
        employee.setDistrict(res.getString("district"));
        employee.setSex(res.getString("sex").charAt(0));
        employee.setJobPos(res.getString("job_pos"));
        return employee;
    }
    public static ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * from Employee ");
            while (res.next()){
                employees.add(getEmployeeFromRow(res));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;

    }

    public static ArrayList<Employee> getAllPendingAccounts(){
        ArrayList<Employee> pendingEmployees = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from EMPLOYEE where EMPLOYEE_ID in (select EID from EMPLOYEE_ACCOUNT where ROLE = 'Pending')");
            while (res.next()){
                pendingEmployees.add(getEmployeeFromRow(res));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pendingEmployees;
    }

    public static boolean isEmployeeHasAccount(String eid){

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from EMPLOYEE_ACCOUNT where EID = " + eid + " and  role != 'Pending'");
            if (res.next()){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }


    //   ROLES
    public static ArrayList<String> getAccountRoles(){
        ArrayList<String> roles = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select ROLE_NAME from ROLE");
            while (res.next()){
                roles.add(res.getString("Role_Name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roles;
    }

}
