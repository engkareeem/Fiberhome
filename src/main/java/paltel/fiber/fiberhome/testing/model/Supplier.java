package paltel.fiber.fiberhome.testing.model;

import java.util.ArrayList;

public class Supplier{

    private static final long serialVersionUID = 1L;
    private String supplierId;
    private String companyName;

    public Supplier() {
    }

    public Supplier(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }



}
