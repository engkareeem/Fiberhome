package paltel.fiber.fiberhome.model;

import paltel.fiber.fiberhome.Functions;
import paltel.fiber.fiberhome.utils.DBapi;

import java.text.SimpleDateFormat;
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

    public String getFormattedLastLogin(){
        SimpleDateFormat lastLoginFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm aa");
        if (lastLogin == null) return "Never";
        return lastLoginFormat.format(lastLogin);
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
