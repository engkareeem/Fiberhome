package paltel.fiber.fiberhome.testing.objects;

import java.time.LocalDate;

public class Employee {
    private String eid;
    private String idNum;
    private String fname,mname,lname;
    private LocalDate birthdate;
    private String district;
    private char sex;
    private String jobPos;
    public Employee() {
        eid=idNum=fname=mname=lname=district=jobPos="";
        birthdate=LocalDate.now();
        sex='N';
    }

    public Employee(String eid, String idNum, String fname, String mname, String lname, LocalDate birthdate, String district, char sex, String jobPos) {
        this.eid = eid;
        this.idNum = idNum;
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.birthdate = birthdate;
        this.district = district;
        this.sex = sex;
        this.jobPos = jobPos;
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

    public LocalDate getBirthdate() {
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

    public void setBirthdate(LocalDate birthdate) {
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
}
