package paltel.fiber.fiberhome.testing.model;

import java.util.ArrayList;

public class Supplier{

    private static final long serialVersionUID = 1L;
    private String supplierId;
    private String companyName;

    private Contact contact;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Supplier(String supplierId, String companyName) {
        this.supplierId = supplierId;
        this.companyName = companyName;
    }

    public Supplier() {
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
