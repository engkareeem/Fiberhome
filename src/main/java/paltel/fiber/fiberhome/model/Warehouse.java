package paltel.fiber.fiberhome.model;

import java.util.ArrayList;

public class Warehouse {


    private String warehouseId;
    private String city;

    private Integer capacity;

    private ArrayList<Project> projectList;

    public Warehouse() {
    }

    public Warehouse(String warehouseId, String city, Integer capacity, ArrayList<Project> projectList) {
        this.warehouseId = warehouseId;
        this.city = city;
        this.capacity = capacity;
        this.projectList = projectList;
    }

    public Warehouse(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Project> getProjectList() {
        return projectList;
    }
    public int getProjectsCount() {
        return projectList.size();
    }

    public void setProjectList(ArrayList<Project> projectList) {
        this.projectList = projectList;
    }

}
