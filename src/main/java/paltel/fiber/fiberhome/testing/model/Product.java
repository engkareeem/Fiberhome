package paltel.fiber.fiberhome.testing.model;

public class Product {


    private String productId;
    private String productName;
    private String description;
    private String mesUnit;

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



}
