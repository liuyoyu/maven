package com.demo.vo;

/**
*  该类由自动代码生成
*  createDate：2021/11/14 21:00:28
*  表名: DOWNLOAD
*/
public class DownloadVO{
    /**
    * 
    */
    private java.math.BigDecimal id;
    /**
    * 
    */
    private String account;
    /**
    * 
    */
    private java.math.BigDecimal fileid;
    /**
    * 
    */
    private java.util.Date downloaddate;

    public java.math.BigDecimal getId(){
        return this.id;
    }

    public void setId(java.math.BigDecimal id){
        this.id = id;
    }

    public String getAccount(){
        return this.account;
    }

    public void setAccount(String account){
        this.account = account;
    }

    public java.math.BigDecimal getFileid(){
        return this.fileid;
    }

    public void setFileid(java.math.BigDecimal fileid){
        this.fileid = fileid;
    }

    public java.util.Date getDownloaddate(){
        return this.downloaddate;
    }

    public void setDownloaddate(java.util.Date downloaddate){
        this.downloaddate = downloaddate;
    }
}