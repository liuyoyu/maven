package com.demo.vo;

/**
*  该类由自动代码生成
*  createDate：2021/11/14 21:00:40
*  表名: MENU
*/
public class MenuVO{
    /**
    * 
    */
    private java.math.BigDecimal id;
    /**
    * 
    */
    private String name;
    /**
    * 
    */
    private String url;
    /**
    * 
    */
    private java.math.BigDecimal parentid;
    /**
    * 
    */
    private java.util.Date createdate;
    /**
    * 
    */
    private java.math.BigDecimal seq;
    /**
    * 
    */
    private java.math.BigDecimal status;

    public java.math.BigDecimal getId(){
        return this.id;
    }

    public void setId(java.math.BigDecimal id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public java.math.BigDecimal getParentid(){
        return this.parentid;
    }

    public void setParentid(java.math.BigDecimal parentid){
        this.parentid = parentid;
    }

    public java.util.Date getCreatedate(){
        return this.createdate;
    }

    public void setCreatedate(java.util.Date createdate){
        this.createdate = createdate;
    }

    public java.math.BigDecimal getSeq(){
        return this.seq;
    }

    public void setSeq(java.math.BigDecimal seq){
        this.seq = seq;
    }

    public java.math.BigDecimal getStatus(){
        return this.status;
    }

    public void setStatus(java.math.BigDecimal status){
        this.status = status;
    }
}