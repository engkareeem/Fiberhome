package paltel.fiber.fiberhome.testing;

import paltel.fiber.fiberhome.testing.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        if (project.getStreet() == null) project.setStreet(" - ");
        return project;
    }

    private static Warehouse getWarehouseFromRow(ResultSet res) throws SQLException {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId(res.getString("warehouse_id"));
        warehouse.setCity(res.getString("city"));
        warehouse.setCapacity(res.getInt("capacity"));
        return warehouse;
    }

    private static Product getProductFromRow(ResultSet res) throws  SQLException{
        Product product = new Product();
        product.setProductId(res.getString("product_id"));
        product.setProductName(res.getString("product_name"));
        product.setDescription(res.getString("description"));
        product.setMesUnit(res.getString("mes_unit"));
        return product;
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

    public static Supplier getSupplierFromRow(ResultSet res) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(res.getString("supplier_id"));
        supplier.setCompanyName(res.getString("company_name"));
        return supplier;
    }

    public static Contact getContactFromRow(ResultSet res) throws SQLException {
        Contact contact = new Contact();
        contact.setSupplierId(res.getString("supplier_id"));
        contact.setHasemail(res.getString("hasemail").charAt(0));
        contact.setHasfax(res.getString("hasfax").charAt(0));
        contact.setHasphone(res.getString("hasphone").charAt(0));
        if(contact.getHasphone().equals('1')) contact.setPhoneNumber(res.getString("company_name"));
        if(contact.getHasfax().equals('1')) contact.setFax(res.getString("fax"));
        if(contact.getHasemail().equals('1')) contact.setEmailAddress(res.getString("email_address"));
        return contact;
    }
    public static void addEmployee(String idNumber, String fName, String mName, String lName, Date birthDate, String district, Character sex, String jobPos){
        try {
            Statement statement = connection.createStatement();
            String eid = getNewEmployeeId();
            statement.executeUpdate("insert into Employee(EMPLOYEE_ID, ID_NUMBER, FNAME, MNAME, LNAME, BIRTHDATE, DISTRICT, SEX, JOB_POS)  values('" + eid + "','" + idNumber + "','" + fName + "','"  +  mName + "','"  + lName + "', TO_DATE('" + birthDate + " 0:00:00', 'yyyy-mm-dd HH24:mi:ss'),'" + district +  "','"  + sex + "','"  + jobPos + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmployee(String eid, String fName, String mName, String lName, String jobPos, String district){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update Employee set FNAME = '" + fName + "', MNAME = '" + mName + "', LNAME = '" + lName + "', JOB_POS = '" + jobPos + "', DISTRICT = '" + district + "' where EMPLOYEE_ID = " + eid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(String eid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from Employee where EMPLOYEE_ID = " + eid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addContractor(String cid, String idNumber, String fName, String mName, String lName, Date birthDate, Character sex, String contractorType){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into CONTRACTOR(CONTRACTOR_ID, ID_NUMBER, FNAME, MNAME, LNAME, BIRTHDATE, SEX, CONTRACTOR_TYPE)  values('" + cid + "','" + idNumber + "','" + fName + "','"  +  mName + "','"  + lName + "', TO_DATE('" + birthDate + " 0:00:00', 'yyyy-mm-dd HH24:mi:ss'),'" + + sex + "','"  + contractorType + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateContractor(String cid, String fName, String mName, String lName, String contractorType){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update CONTRACTOR set FNAME = '" + fName + "', MNAME = '" + mName + "', LNAME = '" + lName + "', CONTRACTOR_TYPE = '" + contractorType + "' where CONTRACTOR_ID = " + cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContractor(String cid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from CONTRACTOR where CONTRACTOR_ID = " + cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProduct(String pid, String productName, String description, String measurementUnit){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into PRODUCT(PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, MES_UNIT)  values('" + pid + "','" + productName + "','"  +  description + "','"  + measurementUnit + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(String pid, String productName, String description, String measurementUnit){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update PRODUCT set PRODUCT_NAME = '" + productName + "', DESCRIPTION = '" + description + "', MES_UNIT = '" + measurementUnit + "' where PRODUCT_ID = " + pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteProduct(String pid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from PRODUCT where PRODUCT_ID = " + pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSupplier(String sid, String companyName){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into SUPPLIER(SUPPLIER_ID, COMPANY_NAME)  values('" + sid + "','" + companyName + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateSupplier(String sid, String companyName){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update SUPPLIER set COMPANY_NAME = '" + companyName + "' where SUPPLIER_ID = " + sid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSupplier(String sid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from SUPPLIER where SUPPLIER_ID = " + sid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addWarehouse(String wid, String city, Integer capacity){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into WAREHOUSE(WAREHOUSE_ID, CITY, CAPACITY)  values('" + wid + "','" + city  + "','" + capacity + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateWarehouse(String wid, String city, String capacity){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update WAREHOUSE set CITY = '" + city + "', CAPACITY = '" + capacity +  "' where WAREHOUSE_ID = " + wid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteWarehouse(String wid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from WAREHOUSE where WAREHOUSE_ID = " + wid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void assignEmployeeToProject(String eid, String pid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into WORKS_AT(EMPLOYEE_ID, PROJECT_ID)  values('" + eid + "','" + pid + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    public static void removeEmployeeFromProject(String eid, String pid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from WORKS_AT where EMPLOYEE_ID = '" + eid + "' and PROJECT_ID = '" + pid + "')");
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
    }

    public static void supplierCanSupply(String sid, String pid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into CAN_SUPPLY(SUPPLIER_ID, PRODUCT_ID)  values('" + sid + "','" + pid + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void projectImportsFromWarehouse(String pid, String wid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into IMPORTS_FROM(PROJECT_ID, WAREHOUSE_ID)  values('" + pid + "','" + wid + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void projectUsesProduct(String projectId, String productId, Integer quantity){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into USES(PROJECT_ID, PRODUCT_ID, QUANTITY)  values('" + projectId + "','" + productId + "','" + quantity + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void warehouseStoresProduct(String wid, String pid, Integer quantity, Integer reserved){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into STORES(WAREHOUSE_ID, PRODUCT_ID, QUANTITY, RESERVED)  values('" + wid + "','" + pid + "','" + quantity + "','" + reserved + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void reserveProductFromWarehouse(String wid, String pid, Integer reserved){
        try {
            Statement statement = connection.createStatement();
            Integer numOfReservedProducts = getNumberOfReservedProductInWarehouse(wid, pid);
            Integer numOfProducts = getQuantityOfAProductInWarehouse(wid, pid);
            if(numOfReservedProducts + reserved > numOfProducts){ // when reserving quantity more than available
                return;
            }
            statement.executeUpdate("update STORES set RESERVED = '" + (numOfReservedProducts + reserved)  +  "' where WAREHOUSE_ID = " + wid + " and PRODUCT_ID = " + pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Integer getNumberOfReservedProductInWarehouse(String wid, String pid)
    {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("select RESERVED from STORES where WAREHOUSE_ID = " + wid + " and PRODUCT_ID = " + pid );
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public static Integer getQuantityOfAProductInWarehouse(String wid, String pid)
    {
        try {
            Statement statement = connection.createStatement();
            statement.executeQuery("select QUANTITY from STORES where WAREHOUSE_ID = " + wid + " and PRODUCT_ID = " + pid );
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public static void acceptEmployeeAccount(String eid)
    {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update EMPLOYEE_ACCOUNT set ROLE = 'Employee' where EID = " + eid );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmployeeAccount(String eid, String nickname){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update EMPLOYEE_ACCOUNT set NICKNAME = '" + nickname + "' where EID = " + eid );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmployeeAccount(String eid, String nickname, String password){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update EMPLOYEE_ACCOUNT set NICKNAME = '" + nickname + "', PASSWORD = '" + password + "' where EID = " + eid );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void denyEmployeeAccount(String eid){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from EMPLOYEE_ACCOUNT where EID = " + eid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }








    public static ArrayList<Employee> getAllEmployees(){
        ArrayList<Employee> employees = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * from Employee order by EMPLOYEE_ID");
            while (res.next()){
                employees.add(getEmployeeFromRow(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;

    }

    public static ArrayList<Contractor> getAllContractors(){
        ArrayList<Contractor> contractors = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet res =  statement.executeQuery("SELECT * from CONTRACTOR ORDER BY CONTRACTOR_ID");
            while (res.next()){
                contractors.add(getContractorFromRow(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contractors;

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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }

    public static User getUserInfo(String eid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from EMPLOYEE_ACCOUNT where EID = " + eid);
            if(res.next()){
                return getUserFromRow(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Contractor getContractorInfo(String contractor_id){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from CONTRACTOR where CONTRACTOR_ID = " + contractor_id);
            if(res.next()){
                return getContractorFromRow(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Project getCurrentProject(String eid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where PROJECT_ID in (select WORKS_AT.PROJECT_ID from WORKS_AT where WORKS_AT.EMPLOYEE_ID = " + eid + ") and PROJECT.DUE_DATE >= CURRENT_DATE order by PROJECT.DUE_DATE DESC");
            if(res.next()){
                return getProjectFromRow(res);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getUsersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) as numberOfUsers from EMPLOYEE_ACCOUNT");
            if(res.next()){
                return res.getInt("numberOfUsers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



    public static Integer getEmployeesCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EMPLOYEE_ID) as numberOfEmployees from EMPLOYEE");
            if(res.next()){
                return res.getInt("numberOfEmployees");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getContractorCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(Contractor.CONTRACTOR_ID) as numberOfContractors from CONTRACTOR where Contractor.CONTRACTOR_ID in (select PROJECT.CONTRACTOR_ID from PROJECT)");
            if(res.next()){
                return res.getInt("numberOfContractors");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getWorkingEmployeesCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EMPLOYEE_ID) as numberOfWorkingEmployees from EMPLOYEE where EMPLOYEE_ID in (select WORKS_AT.EMPLOYEE_ID from WORKS_AT)");
            if(res.next()){
                return res.getInt("numberOfWorkingEmployees");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Project> getRecentFinishedProjects(String eid){
        ArrayList<Project> recentProjects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where PROJECT_ID in (select WORKS_AT.PROJECT_ID from WORKS_AT where WORKS_AT.EMPLOYEE_ID = " + eid + ") and PROJECT.DUE_DATE < CURRENT_DATE order by PROJECT.DUE_DATE DESC");
            while (res.next()){
                recentProjects.add(getProjectFromRow(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentProjects;
    }


    public static ArrayList<String> getJobPositions(){
        ArrayList<String> jobPositions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select JOB_POS from JOB_POSITION");
            while (res.next()){
                jobPositions.add(res.getString("Job_Pos"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobPositions;
    }

    public static ArrayList<String> getAccountRoles(){
        ArrayList<String> roles = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select ROLE_NAME from ROLE");
            while (res.next()){
                roles.add(res.getString("Role_Name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public static ArrayList<String> getContractorTypes(){
        ArrayList<String> contractorTypes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select CONT_TYPE from CONTRACTOR_TYPE");
            while (res.next()){
                contractorTypes.add(res.getString("CONT_TYPE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contractorTypes;
    }


    // project Page

    public static Project getProjectInfo(String proj_id){
        Project project = new Project();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where PROJECT_ID = " + proj_id);
            if(res.next()){
                return getProjectFromRow(res);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Warehouse getWarehouseInfo(String warehouseId){

        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from WAREHOUSE where WAREHOUSE_ID = " + warehouseId);
            if(res.next()){
                return getWarehouseFromRow(res);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Project> getAllProjects() {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT order by PROJECT_ID");
            while (res.next()){
                projects.add(getProjectFromRow(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }
    public static ArrayList<String> getProjectTypes(){
        ArrayList<String> projectTypes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select PROJ_TYPE from PROJECT_TYPE");
            while (res.next()){
                projectTypes.add(res.getString("PROJ_TYPE"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectTypes;
    }

    public static ArrayList<Warehouse> getAllWarehouses(){
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from WAREHOUSE ORDER BY WAREHOUSE_ID");
            while (res.next()){
                warehouses.add(getWarehouseFromRow(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouses;
    }

    public static ArrayList<Product> getProjectProducts(String pid){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PRODUCT where PRODUCT_ID in (select USES.PROJECT_ID from USES) order by PRODUCT_ID");
            while (res.next()){
                products.add(getProductFromRow(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }




    // Utils

    public static ArrayList<Project> getCurrentContractorProjects(String cid) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where  CONTRACTOR_ID = '" + cid + "' and PROJECT.DUE_DATE > CURRENT_DATE");
            while (res.next()){
                projects.add(getProjectFromRow(res));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return projects;
    }

    public static String getNewEmployeeId() throws SQLException {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select EMPLOYEE_ID from EMPLOYEE order by EMPLOYEE_ID desc fetch first 1 row only");
            if(res.next()){
                String lastEmpId = res.getString("employee_id");
                Integer lastEmpIdNum = Integer.valueOf(lastEmpId);
                System.out.println(lastEmpIdNum);

                return String.format("%04d", lastEmpIdNum+1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "0000";
    }
    public static void updateLastLoginTime(String eid){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(Calendar.getInstance().getTime());
        new Thread(()->{
            try {

                Statement statement = connection.createStatement();
                statement.executeUpdate("update EMPLOYEE_ACCOUNT set LAST_LOGIN = TO_DATE('" + timeStamp + "', 'yyyy-mm-dd HH24:mi:ss') where EID = " + eid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }



}
