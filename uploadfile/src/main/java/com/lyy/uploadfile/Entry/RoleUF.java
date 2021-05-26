package com.lyy.uploadfile.Entry;

import com.lyy.uploadfile.Configture.Interface.PrimaryKey;

import java.util.Date;

public class RoleUF {

    @PrimaryKey
    private long id;

    private String roleName;

    private Date createDate;

    private int status = STATUS.USING.value;  // 0 - 可用； 1 - 禁用

    public enum STATUS{
        USING(0),
        BANNED(1);
        int value;
        STATUS(int status){
            this.value = status;
        }
        public int value(){return this.value;}
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
