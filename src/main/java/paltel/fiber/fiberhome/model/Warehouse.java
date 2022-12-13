package paltel.fiber.fiberhome.model;

import java.util.ArrayList;

public class Warehouse {


    private String warehouseId;
    private String city;

    private Integer capacity;

    private Integer projectCount;

    public Warehouse() {
    }

    public Warehouse(String warehouseId, String city, Integer capacity, Integer projectCount) {
        this.warehouseId = warehouseId;
        this.city = city;
        this.capacity = capacity;
        this.projectCount = projectCount;
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

    public Integer getProjectCount() {
        return projectCount;
    }

    public void setProjectCount(Integer projectCount) {
        this.projectCount = projectCount;
    }
}
