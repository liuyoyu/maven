package com.demo.vo;

/**
*  该类由自动代码生成
*  createDate：2021/11/14 21:00:40
*  表名: EMAIL_STORE
*/
public class EmailStoreVO{
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
    private String froemailaddr;
    /**
    * 
    */
    private String toemailaddr;
    /**
    * 
    */
    private String contexthtml;
    /**
    * 
    */
    private String context;
    /**
    * 
    */
    private java.util.Date createdate;

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

    public String getFroemailaddr(){
        return this.froemailaddr;
    }

    public void setFroemailaddr(String froemailaddr){
        this.froemailaddr = froemailaddr;
    }

    public String getToemailaddr(){
        return this.toemailaddr;
    }

    public void setToemailaddr(String toemailaddr){
        this.toemailaddr = toemailaddr;
    }

    public String getContexthtml(){
        return this.contexthtml;
    }

    public void setContexthtml(String contexthtml){
        this.contexthtml = contexthtml;
    }

    public String getContext(){
        return this.context;
    }

    public void setContext(String context){
        this.context = context;
    }

    public java.util.Date getCreatedate(){
        return this.createdate;
    }

    public void setCreatedate(java.util.Date createdate){
        this.createdate = createdate;
    }
}