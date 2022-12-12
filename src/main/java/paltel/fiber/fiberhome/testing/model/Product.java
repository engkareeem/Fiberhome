package paltel.fiber.fiberhome.testing.model;

public class Product {


    private String productId;
    private String productName;
    private String description;
    private String mesUnit;

    private String warehouse_id;

    private String supplier_id;

    private Integer cost;

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    private Integer available_count;



    public Integer getAvailable_count() {
        return available_count;
    }

    public void setAvailable_count(Integer available_count) {
        this.available_count = available_count;
    }

    public Product() {
    }

    public Product(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMesUnit() {
        return mesUnit;
    }

    public void setMesUnit(String mesUnit) {
        this.mesUnit = mesUnit;
    }

    public String getWarehouse_id() {
        return warehouse_id;
    }

    public void setWarehouse_id(String warehouse_id) {
        this.warehouse_id = warehouse_id;
    }
}
