package paltel.fiber.fiberhome.testing.model;

import java.util.Date;

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
