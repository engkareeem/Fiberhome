package paltel.fiber.fiberhome.testing.model;

import paltel.fiber.fiberhome.testing.Functions;
import paltel.fiber.fiberhome.testing.utils.DBapi;

import java.util.Date;
import java.util.Objects;

public class User {
    private String eid;
    private String password;
    private String role;
    private Date lastLogin;
    private String nickName;


    public User() {

    }

    public User(String eid, String password, String role, Date lastLogin, String nickName) {
        this.eid = eid;
        this.password = password;
        this.role = role;
        this.lastLogin = lastLogin;
        this.nickName = nickName;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    public Functions.JobPos getJobPos() {
        String pos = Objects.requireNonNull(DBapi.getEmployeeInfo(eid)).getJobPos();
        return switch (pos) {
            case "Department Manager" -> Functions.JobPos.DEP_MANAGER;
            case "Project Manager" -> Functions.JobPos.PROJ_MANAGER;
            case "Technician" -> Functions.JobPos.TECHNICIAN;
            case "Accountant" -> Functions.JobPos.ACCOUNTANT;
            default -> Functions.JobPos.OTHER;
        };
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
