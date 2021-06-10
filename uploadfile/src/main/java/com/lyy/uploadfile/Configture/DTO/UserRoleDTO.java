package com.lyy.uploadfile.Configture.DTO;

import com.lyy.uploadfile.Entry.RoleUF;
import com.lyy.uploadfile.Entry.UserRole;

public class UserRoleDTO {

    private long id;
    private String account;
    private String userName;
    private String sex;
    private String tel;
    private String email;
    private long roleId;
    private String roleName;
    private int roleStatus;  //用户角色状态

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getStatus() {
        return roleStatus;
    }

    public void setStatus(int status) {
        this.roleStatus = status;
    }

    public String getStatusName(){
        if (this.roleStatus == UserRole.STATUS.USING.val()) {
            return "正在使用";
        }
        if (this.roleStatus == UserRole.STATUS.UNUSED.val()) {
            return "拥有";
        }
        return "";
    }
}
