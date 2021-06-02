package com.lyy.uploadfile.Entry;

import com.lyy.uploadfile.Configture.Interface.PrimaryKey;
import com.lyy.uploadfile.Configture.SystemParameters;

import java.util.Date;

public class MenuRole {

    @PrimaryKey
    private long id;

    private long roleId;

    private String roleName;

    private long menuId;

    private long menuParentId = SystemParameters.MENUPARENTID;

    private String menuName;

    private String url;

    private double seq;

    private int status = STATUS.USING.value;

    private Date createDate;

    public enum STATUS{
        USING(0),  // 可用
        BANNED(1); // 禁用
        int value;
        STATUS(int value){this.value = value;}
        public int value(){return this.value;}
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(long menuParentId) {
        this.menuParentId = menuParentId;
    }

    public double getSeq() {
        return seq;
    }

    public void setSeq(double seq) {
        this.seq = seq;
    }

    public String getStatusName(){
        if (this.status == Menu.STATUS.USING.value()) {
            return "可用";
        }
        if (this.status == Menu.STATUS.BANNED.value()) {
            return "禁用";
        }
        return "";
    }
}
