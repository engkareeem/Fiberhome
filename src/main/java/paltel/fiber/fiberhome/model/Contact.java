/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package paltel.fiber.fiberhome.model;


public class Contact {

    private static final long serialVersionUID = 1L;
    private String contactId;

    private String phoneNumber;
    private String emailAddress;
    private String fax;
    private Character hasphone;
    private Character hasemail;
    private Character hasfax;

    private String supplierId;

    public Contact() {
    }

    public Contact(String contactId) {
        this.contactId = contactId;
    }

    public String getContactId() {

        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getPhoneNumber() {
        if(hasphone.equals('0')) return "none";
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        if(hasemail.equals('0')) return "none";
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFax() {
        if(hasfax.equals('0')) return "none";
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Character getHasphone() {
        return hasphone;
    }

    public void setHasphone(Character hasphone) {
        this.hasphone = hasphone;
    }

    public Character getHasemail() {
        return hasemail;
    }

    public void setHasemail(Character hasemail) {
        this.hasemail = hasemail;
    }

    public Character getHasfax() {
        return hasfax;
    }

    public void setHasfax(Character hasfax) {
        this.hasfax = hasfax;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }




}
