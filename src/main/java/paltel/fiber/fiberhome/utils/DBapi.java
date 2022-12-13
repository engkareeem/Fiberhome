package paltel.fiber.fiberhome.utils;

import paltel.fiber.fiberhome.model.*;
import paltel.fiber.fiberhome.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBapi {
    public static Connection connection = Main.dbConnection;



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

    public static String addProduct( String productName, String description, String measurementUnit){
        String pid = getNewProjectId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into PRODUCT(PRODUCT_ID, PRODUCT_NAME, DESCRIPTION, MES_UNIT)  values('" + pid + "','" + productName + "','"  +  description + "','"  + measurementUnit + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pid;
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

    public static String addSupplier(String companyName){
        String sid = getNewSupplierId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into SUPPLIER(SUPPLIER_ID, COMPANY_NAME)  values('" + sid + "','" + companyName + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sid;
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

    public static String addWarehouse(String city, Integer capacity){
        String wid = getNewWarehouseId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into WAREHOUSE(WAREHOUSE_ID, CITY, CAPACITY)  values('" + wid + "','" + city  + "','" + capacity + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wid;
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


    public static String addProject(String city, String street, LocalDate dueDate, String projectType, String contractorId, Integer amount){
        String pid = getNewProjectId();
        try {
            Statement statement = connection.createStatement();
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd H:mm:ss").format(Calendar.getInstance().getTime());
            statement.executeUpdate("insert into PROJECT(PROJECT_ID, AMOUNT, START_DATE, DUE_DATE, CITY, STREET, CONTRACTOR_ID, PROJ_TYPE)  values('" + pid + "'," + amount + ", TO_DATE('" + timeStamp + "', 'yyyy-mm-dd HH24:mi:ss') , TO_DATE('" + dueDate + " 0:00:00', 'yyyy-mm-dd HH24:mi:ss'),'"  + city +  "','"  + street + "','"  + contractorId + "','"  + projectType + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pid;
    }

    public static void reserveProductForAProject(String productId, String projectId,String warehouseId, Integer quantity){
        try{
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false);
            statement.executeUpdate("insert into USES(PROJECT_ID, PRODUCT_ID, QUANTITY, WAREHOUSE_ID) VALUES('" + projectId + "','" + productId + "'," + quantity + ",'" + warehouseId + "')");
            Integer reservedCount = getNumberOfReservedProductInWarehouse(warehouseId, productId);
            statement.executeUpdate("update STORES set RESERVED = " + reservedCount + quantity + " where WAREHOUSE_ID = " + warehouseId + " and PRODUCT_ID = " + productId);
            connection.commit();
            connection.setAutoCommit(true);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void deleteProject(String pid){
        try {
            Statement statement = connection.createStatement();
            connection.setAutoCommit(false); // transaction
            statement.executeUpdate("delete from PROJECT where PROJECT_ID = " + pid);
            statement.executeUpdate("delete from WORKS_AT where PROJECT_ID = " + pid);
            statement.executeUpdate("delete from USES where PROJECT_ID = " + pid);

            connection.commit();
            connection.setAutoCommit(true);
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
            statement.executeUpdate("delete from WORKS_AT where EMPLOYEE_ID = '" + eid + "' and PROJECT_ID = '" + pid + "'");
        } catch (SQLException e) {
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
            Integer numOfProducts = getQuantityOfAProductInWarehouseForProject(wid, pid);
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

    public static Integer getQuantityOfAProductInWarehouseForProject(String wid, String pid)
    {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select sum(QUANTITY) as quantityOfProducts from STORES where WAREHOUSE_ID = " + wid + " and PRODUCT_ID = " + pid );
            if(res.next()){
                return res.getInt("quantityOfProducts");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public static Integer getQuantityOfAProductInWarehouse(String wid)
    {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select sum(QUANTITY) as quantityOfProducts from STORES where WAREHOUSE_ID = " + wid);
            if(res.next()){
                return res.getInt("quantityOfProducts");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
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



    public static ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        try {

            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * from Employee order by EMPLOYEE_ID");
            while (res.next()) {
                employees.add(getEmployeeFromRow(res));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

        public static ArrayList<User> getAllUsers(){
            ArrayList<User> users = new ArrayList<>();
            try {

                Statement statement = connection.createStatement();
                ResultSet res =  statement.executeQuery("SELECT * from EMPLOYEE_ACCOUNT where ROLE != 'Pending' order by EID");
                while (res.next()){
                    users.add(getUserFromRow(res));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


            return users;

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

    public static Integer getProjectsCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(PROJECT_ID) as numOfProjects from PROJECT");
            if(res.next()){
                return res.getInt("numOfProjects");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getRunningProjectsCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(PROJECT_ID) as numOfRunningProjects from PROJECT where  DUE_DATE > CURRENT_DATE");
            if(res.next()){
                return res.getInt("numOfRunningProjects");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getFinishedProjectsCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(PROJECT_ID) as numOfFinishedProjects from PROJECT where  DUE_DATE <= CURRENT_DATE");
            if(res.next()){
                return res.getInt("numOfFinishedProjects");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getWarehousesCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(WAREHOUSE_ID) as numOfWarehouses from WAREHOUSE");
            if(res.next()){
                return res.getInt("numOfWarehouses");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Product> getAvailableProductsInWarehouse(String wid){
        ArrayList<Product> availableProducts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PRODUCT where PRODUCT.PRODUCT_ID in (SELECT STORES.PRODUCT_ID from  STORES where STORES.WAREHOUSE_ID = '" + wid + "') order by PRODUCT_ID");
            while (res.next()){
                Product product = getProductFromRow(res);
                availableProducts.add(product);
            }
            res.close();
            for(Product product: availableProducts){
                res = statement.executeQuery("select * from STORES where WAREHOUSE_ID = " + wid + " and PRODUCT_ID = " + product.getProductId());
                if (res.next()){
                    product.setAvailable_count(res.getInt("quantity") - res.getInt("reserved"));
                    product.setSupplier_id(res.getString("supplier_id"));
                }

            }
            res.close();
            for (Product product: availableProducts){
                res = statement.executeQuery("select PRODUCT_COST from CAN_SUPPLY where SUPPLIER_ID = " + product.getSupplier_id() + " and PRODUCT_ID = " + product.getProductId());
                if (res.next()){
                    product.setCost(res.getInt("product_cost"));
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableProducts;
    }


    public static Integer getCapacityForAWarehouse(String wid){
        ArrayList<Product> availableProducts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select SUM(QUANTITY) as numOfStoredProducts from STORES where WAREHOUSE_ID = " + wid);
            if (res.next()){
                return res.getInt("numOfStoredProducts");
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

    public static ArrayList<Warehouse> getAllWarehousesWithProjectsCount(){
        ArrayList<Warehouse> warehouses = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from WAREHOUSE ORDER BY WAREHOUSE_ID");
            while (res.next()){
                warehouses.add(getWarehouseFromRow(res));
            }
            res.close();
            for (Warehouse warehouse : warehouses) {
                res = statement.executeQuery("select count(distinct PROJECT_ID) as numOfProjects from USES where WAREHOUSE_ID = " + warehouse.getWarehouseId());
                if(res.next()){
                    warehouse.setProjectCount(res.getInt("numOfProjects"));
                }
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
            ResultSet res = statement.executeQuery("select *  from PRODUCT where PRODUCT.PRODUCT_ID  in (select USES.PRODUCT_ID from USES where USES.PROJECT_ID = '" + pid +  "') order by Product.PRODUCT_ID");

            while (res.next()){
                products.add(getProductFromRow(res));
            }

            res.close();

            for (Product product : products) {
                res = statement.executeQuery("select WAREHOUSE_ID from USES where PROJECT_ID = " + pid + " and PRODUCT_ID = " + product.getProductId());
                if(res.next()){
                    product.setWarehouse_id(res.getString("warehouse_id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static Double getWarehousePercentage(String wid){
        try {
            Statement statement = connection.createStatement();
            Integer usedCapacity = getQuantityOfAProductInWarehouse(wid);
            ResultSet res = statement.executeQuery("select CAPACITY from WAREHOUSE where WAREHOUSE_ID = " + wid);

            if (res.next()){
                Integer capacity = res.getInt("capacity");
                return ((((double) usedCapacity )/ capacity) * 100);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }




    // Control PANEL USERS
    public static Integer getActiveUsersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) as numberOfUsers from EMPLOYEE_ACCOUNT where LAST_LOGIN > (sysdate-1)"); // last hour login 1/24
            if(res.next()){
                return res.getInt("numberOfUsers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Integer getUsersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) as numberOfUsers from EMPLOYEE_ACCOUNT where ROLE != 'Pending'");
            if(res.next()){
                return res.getInt("numberOfUsers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getEmployeeUsersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) as numberOfUsers from EMPLOYEE_ACCOUNT where ROLE = 'Employee'");
            if(res.next()){
                return res.getInt("numberOfUsers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Integer getAdminUsersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) as numberOfAdminUsers from EMPLOYEE_ACCOUNT where ROLE = 'Admin'"); // last hour login
            if(res.next()){
                return res.getInt("numberOfAdminUsers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getPendingUsersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(EID) as numberOfPendingUsers from EMPLOYEE_ACCOUNT where ROLE = 'Pending'"); // last hour login
            if(res.next()){
                return res.getInt("numberOfPendingUsers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public static void acceptEmployeeAccount(String eid)
    {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("update EMPLOYEE_ACCOUNT set ROLE = 'Employee' where EID = " + eid );
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

    public static void deleteEmployeeAccount(String eid){
        try{
            Statement statement = connection.createStatement();
            statement.executeUpdate("delete from EMPLOYEE_ACCOUNT where EID = " + eid);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    // Control Panel Suppliers

    public static ArrayList<Supplier> getAllSuppliers(){
        ArrayList<Supplier> suppliers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from SUPPLIER order by SUPPLIER_ID");
            while (res.next()){
                suppliers.add(getSupplierFromRow(res));
            }
            for (Supplier supplier : suppliers) {
                assignSupplierContacts(supplier);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return suppliers;
    }
    public static Integer getTotalSuppliersCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(SUPPLIER_ID) as numOfSuppliers from SUPPLIER");
            if(res.next()){
                return res.getInt("numOfSuppliers");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getTotalProductsCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(PRODUCT_ID) as numOfProducts from PRODUCT");
            if(res.next()){
                return res.getInt("numOfProducts");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Product> getAllProductsThatHasSuppliers(){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PRODUCT where PRODUCT.PRODUCT_ID in (select distinct CAN_SUPPLY.PRODUCT_ID from CAN_SUPPLY) order by PRODUCT.PRODUCT_ID");
            while (res.next()){
                products.add(getProductFromRow(res));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public static Supplier getCheapestProductSupplier(String pid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from SUPPLIER where SUPPLIER_ID in (SELECT CAN_SUPPLY.SUPPLIER_ID from  CAN_SUPPLY where PRODUCT_ID = " + pid + " order by PRODUCT_COST desc fetch first row only ) ");
            if(res.next()){
                return getSupplierFromRow(res);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getProductPriceFromSupplier(String sid, String pid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select PRODUCT_COST from CAN_SUPPLY where PRODUCT_ID = " + pid + " and SUPPLIER_ID = " + sid);
            if(res.next()){
                return res.getInt("PRODUCT_COST");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Product> getAllProductsThatSupplierCanSupply(String sid){
        ArrayList<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PRODUCT where PRODUCT.PRODUCT_ID in (select CAN_SUPPLY.PRODUCT_ID from CAN_SUPPLY where SUPPLIER_ID = " + sid + ") order by PRODUCT.PRODUCT_ID");
            while (res.next()){
                products.add(getProductFromRow(res));
            }

            for (Product product : products) {
                int productCost = getProductPriceFromSupplier(sid, product.getProductId());
                product.setCost(productCost);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public static void assignSupplierContacts(Supplier supplier){
        try {
            Statement statement = connection.createStatement();
            String sid = supplier.getSupplierId();
            ResultSet res = statement.executeQuery("select * from CONTACT where SUPPLIER_ID = " + supplier.getSupplierId());
            if(res.next()){
                Contact contact = new Contact();
                contact.setSupplierId(supplier.getSupplierId());
                contact.setHasfax(res.getString("hasfax").charAt(0));
                contact.setHasemail(res.getString("hasemail").charAt(0));
                contact.setHasphone(res.getString("hasphone").charAt(0));

                if(contact.getHasfax().equals('1')) {
                    contact.setFax(res.getString("fax"));
                }
                if(contact.getHasemail().equals('1')) {
                    contact.setEmailAddress(res.getString("email_address"));
                }
                if(contact.getHasphone().equals('1')) {
                    contact.setPhoneNumber(res.getString("phone_number"));
                }
                supplier.setContact(contact);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }






    // Control Panel Warehouses

    public static Integer getTotalWarehousesCount(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select count(WAREHOUSE_ID) as numOfWarehouses from WAREHOUSE");
            if(res.next()){
                return res.getInt("numOfWarehouses");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getTotalProductsStoredInWarehouses(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select sum(QUANTITY) as numOfProducts from STORES");
            if(res.next()){
                return res.getInt("numOfProducts");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Integer getTotalFreeSpaceInWarehouses(){
        Integer freeSpace = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select sum(CAPACITY) as totalCapacity from WAREHOUSE");
            if(res.next()){
                freeSpace = res.getInt("totalCapacity");
            }
            freeSpace -= getTotalProductsStoredInWarehouses();
            return freeSpace;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getTotalUsedPartsInWarehouses(){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select sum(RESERVED) as totalReserved from STORES");
            if(res.next()){
                return res.getInt("totalReserved");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static ArrayList<Project> getAllProjectsImportingFromAWarehouse(String wid) {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from PROJECT where PROJECT_ID in (select distinct USES.PROJECT_ID from USES where WAREHOUSE_ID = '" + wid + "') order by PROJECT_ID");
            while (res.next()){
                projects.add(getProjectFromRow(res));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static Integer getProductsCountThatProjectUsesFromAWarehouse(String wid, String pid){
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select sum(QUANTITY) as numOfProducts from  USES where WAREHOUSE_ID = " + wid + " and PROJECT_ID = " + pid);
            if (res.next()){
                return res.getInt("numOfProducts");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    public static String getNewEmployeeId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select EMPLOYEE_ID from EMPLOYEE order by EMPLOYEE_ID desc fetch first 1 row only");
            if(res.next()){
                String lastEmpId = res.getString("employee_id");
                int lastEmpIdNum = Integer.valueOf(lastEmpId);
                return String.format("%04d", lastEmpIdNum+1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "0000";
    }

    public static String getNewProjectId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select PROJECT_ID from PROJECT order by PROJECT_ID desc fetch first 1 row only");
            if(res.next()){
                String lastProjId = res.getString("project_id");
                int lastProjIdNum = Integer.parseInt(lastProjId);

                return String.format("%04d", lastProjIdNum+1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "0000";
    }

    public static String getNewSupplierId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select SUPPLIER_ID  from SUPPLIER order by SUPPLIER_ID desc fetch first 1 row only");
            if(res.next()){
                String lastSupId = res.getString("supplier_id");
                int lastSupIdNum = Integer.parseInt(lastSupId);

                return String.format("%04d", lastSupIdNum+1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "0000";
    }

    public static String getNewWarehouseId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select WAREHOUSE_ID  from WAREHOUSE order by WAREHOUSE_ID desc fetch first 1 row only");
            if(res.next()){
                String lastWarehouseId = res.getString("warehouse_id");
                int lastWarehouseIdNum = Integer.parseInt(lastWarehouseId);

                return String.format("%02d", lastWarehouseIdNum+1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "00";
    }

    public static String getNewProductId() {
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select PRODUCT_ID  from PRODUCT order by PRODUCT_ID desc fetch first 1 row only");
            if(res.next()){
                String lastProductId = res.getString("product_id");
                int lastProductIdNum = Integer.parseInt(lastProductId);

                return String.format("%010d", lastProductIdNum+1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return "0000000000";
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
