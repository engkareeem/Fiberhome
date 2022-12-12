
package paltel.fiber.fiberhome.model;

import java.util.ArrayList;
import java.util.Date;


public class Project {


    private String projectId;
    private Integer amount;
    private Date startDate;
    private Date dueDate;
    private String city;
    private String street;

    private ArrayList<Warehouse> warehouseList;
    private ArrayList<Employee> employeeList;
    private String contractorId;
    private String projType;

    public Project() {
        projectId = city = street = contractorId = projectId = "";
    }

    public Project(String projectId, Integer amount, Date startDate, Date dueDate, String city, String street, String contractorId, String projType) {
        this.projectId = projectId;
        this.amount = amount;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.city = city;
        this.street = street;
        this.warehouseList = warehouseList;
        this.employeeList = employeeList;
        this.contractorId = contractorId;
        this.projType = projType;
    }

    public Project(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public ArrayList<Warehouse> getWarehouseList() {
        return warehouseList;
    }

    public void setWarehouseList(ArrayList<Warehouse> warehouseList) {
        this.warehouseList = warehouseList;
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(ArrayList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }


}
