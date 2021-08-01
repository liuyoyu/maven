package com.demo.vo;

/**
*  该类由自动代码生成
*  生成时间：2021/08/01 17:04:49
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
    private java.lang.String name;
    /**
    * 
    */
    private java.lang.String url;
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

    public java.lang.String getName(){
        return this.name;
    }

    public void setName(java.lang.String name){
        this.name = name;
    }

    public java.lang.String getUrl(){
        return this.url;
    }

    public void setUrl(java.lang.String url){
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