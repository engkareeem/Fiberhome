package paltel.fiber.fiberhome.testing.model;

import java.util.Date;

public class Employee {
    private String eid;
    private String idNum;
    private String fname,mname,lname;
    private Date birthdate;
    private String district;
    private char sex;
    private String jobPos;
    private boolean hasAccount;
    public Employee() {
        eid=idNum=fname=mname=lname=district=jobPos="";
        birthdate=null;
        sex='N';
    }

    public Employee(String eid, String idNum, String fname, String mname, String lname, Date birthdate, String district, char sex, String jobPos,boolean hasAccount) {
        this.eid = eid;
        this.idNum = idNum;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.birthdate = birthdate;
        this.district = district;
        this.sex = sex;
        this.jobPos = jobPos;
        this.hasAccount=hasAccount;
    }


    public String isHasAccount() {
        return hasAccount ? "Yes":"No";
    }

    public String getEid() {
        return eid;
    }

    public String getIdNum() {
        return idNum;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getDistrict() {
        return district;
    }

    public char getSex() {
        return sex;
    }

    public String getJobPos() {
        return jobPos;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public void setJobPos(String jobPos) {
        this.jobPos = jobPos;
    }

    public void setHasAccount(boolean hasAccount) {
        this.hasAccount = hasAccount;
    }
}
