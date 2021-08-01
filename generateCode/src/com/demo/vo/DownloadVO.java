package com.demo.vo;

/**
*  该类由自动代码生成
*  生成时间：2021/08/01 17:04:49
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
    private java.lang.String account;
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

    public java.lang.String getAccount(){
        return this.account;
    }

    public void setAccount(java.lang.String account){
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