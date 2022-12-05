package paltel.fiber.fiberhome.testing;

import paltel.fiber.fiberhome.testing.model.Contractor;
import paltel.fiber.fiberhome.testing.model.Employee;
import paltel.fiber.fiberhome.testing.model.Project;
import paltel.fiber.fiberhome.testing.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBapi {
    public static Connection connection = testingMain.dbConnection;



    // Employees Page
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
        employee.setHasAccount(isEmployeeHasAccount(employee.getEid()));
        return employee;
    }

    private static User getUserFromRow(ResultSet res) throws SQLException {
        User user = new User();
        user.setEid(res.getString("eid"));
        user.setNickName(res.getString("nickname"));
        user.setRole(res.getString("role"));
        user.setPassword(res.getString("password"));
        user.setLastLogin(res.getDate("last_login"));
        return user;
    }

    private static Project getProjectFromRow(ResultSet res) throws SQLException {
        Project project = new Project();
        project.setProjectId(res.getString("Project_ID"));
        project.setCity(res.getString("city"));
        project.setStreet(res.getString("street"));
        project.setStartDate(res.getDate("start_date"));
        project.setDueDate(res.getDate("due_date"));
        project.setAmount(res.getInt("amount"));
        project.setContractorId(res.getString("contractor_id"));
        project.setProjType(res.getString("proj_type"));
        return project;
    }

    private static Contractor getContractorFromRow(ResultSet res) throws SQLException {
        Contractor contractor = new Contractor();
        contractor.setContractorId(res.getString("Contractor_id"));
        contractor.setIdNumber(res.getString("id_number"));
        contractor.setFname(res.getString("fname"));
        contractor.setMname(res.getString("mname"));
        contractor.setLname(res.getString("lname"));
        contractor.setBirthdate(res.getDate("birthdate"));
        contractor.setSex(res.getString("sex").charAt(0));
        contractor.setContractorType(res.getString("contractor_type"));
        return contractor;
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

    public static Employee getEmployeeInfo(String eid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from EMPLOYEE where EMPLOYEE_ID = " + eid);
            if(res.next()){
                return getEmployeeFromRow(res);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Employee();
    }

    public static User getUserInfo(String eid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from EMPLOYEE_ACCOUNT where EID = " + eid);
            if(res.next()){
                return getUserFromRow(res);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new User();
    }

    public static Contractor getContractorInfo(String contractor_id){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from CONTRACTOR where CONTRACTOR_ID = " + contractor_id);
            if(res.next()){
                return getContractorFromRow(res);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Contractor();

    }

    public static Project getCurrentProject(String eid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where PROJECT_ID in (select WORKS_AT.PROJECT_ID from WORKS_AT where WORKS_AT.EMPLOYEE_ID = " + eid + ") and PROJECT.DUE_DATE >= CURRENT_DATE order by PROJECT.DUE_DATE DESC");
            if(res.next()){
                return getProjectFromRow(res);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Project();
    }



    public static Integer getEmployeeCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) from EMPLOYEE_ACCOUNT");
            if(res.next()){
                return res.getInt("EID");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static Integer getContractorCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(Contractor.CONTRACTOR_ID) from CONTRACTOR where Contractor.CONTRACTOR_ID in (select PROJECT.CONTRACTOR_ID from PROJECT)");
            if(res.next()){
                return res.getInt("ID_number");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static Integer getWorkingEmployeesCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EMPLOYEE_ID) from EMPLOYEE where EMPLOYEE_ID in (select WORKS_AT.EMPLOYEE_ID from WORKS_AT)");
            if(res.next()){
                return res.getInt("Employee_ID");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public static ArrayList<Project> getRecentFinishedProjects(String eid){
        ArrayList<Project> recentProjects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where PROJECT_ID in (select WORKS_AT.PROJECT_ID from WORKS_AT where WORKS_AT.EMPLOYEE_ID = " + eid + ") and PROJECT.DUE_DATE <= CURRENT_DATE order by PROJECT.DUE_DATE DESC");
            while (res.next()){
                recentProjects.add(getProjectFromRow(res));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return recentProjects;
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
