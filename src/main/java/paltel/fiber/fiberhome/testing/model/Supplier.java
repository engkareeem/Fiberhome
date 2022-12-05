package paltel.fiber.fiberhome.testing.model;

import java.util.ArrayList;

public class Supplier{

    private static final long serialVersionUID = 1L;
    private String supplierId;
    private String companyName;
    private ArrayList<Contact> contactList;

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

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<Contact> contactList) {
        this.contactList = contactList;
    }


}
