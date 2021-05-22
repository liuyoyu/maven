package com.lyy.uploadfile.Entry;

import com.lyy.uploadfile.Configture.Interface.PrimaryKey;

import java.util.Date;

public class EmailStore {

    @PrimaryKey(strategy = TablePrimaryKey.IncreaseStrategy.DATE)
    private long id;

    private String account;

    private String toEmailAddr;

    private String froEmailAddr;

    private int code;

    private Date createDate;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getToEmailAddr() {
        return toEmailAddr;
    }

    public void setToEmailAddr(String toEmailAddr) {
        this.toEmailAddr = toEmailAddr;
    }

    public String getFroEmailAddr() {
        return froEmailAddr;
    }

    public void setFroEmailAddr(String froEmailAddr) {
        this.froEmailAddr = froEmailAddr;
    }
}
