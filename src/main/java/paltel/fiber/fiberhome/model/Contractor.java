
package paltel.fiber.fiberhome.model;


import java.util.ArrayList;
import java.util.Date;


public class Contractor {

    private String contractorId;

    private String idNumber;

    private String fname;
    private String mname;
    private String lname;
    private Date birthdate;
    private ArrayList<Project> projectList;

    private String contractorType;
    private char sex;

    public Contractor() {
    }

    public Contractor(String contractorId) {
        this.contractorId = contractorId;
    }

    public Contractor(String contractorId, String idNumber) {
        this.contractorId = contractorId;
        this.idNumber = idNumber;
    }

    public String getContractorId() {
        return contractorId;
    }

    public void setContractorId(String contractorId) {
        this.contractorId = contractorId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public ArrayList<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(ArrayList<Project> projectList) {
        this.projectList = projectList;
    }

    public String getContractorType() {
        return contractorType;
    }

    public void setContractorType(String contractorType) {
        this.contractorType = contractorType;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }




}
