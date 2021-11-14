package com.demo.vo;

/**
*  该类由自动代码生成
*  createDate：2021/11/14 21:00:40
*  表名: ROLE_UF
*/
public class RoleUfVO{
    /**
    * 
    */
    private java.math.BigDecimal id;
    /**
    * 
    */
    private String rolename;
    /**
    * 
    */
    private java.util.Date createdate;
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

    public String getRolename(){
        return this.rolename;
    }

    public void setRolename(String rolename){
        this.rolename = rolename;
    }

    public java.util.Date getCreatedate(){
        return this.createdate;
    }

    public void setCreatedate(java.util.Date createdate){
        this.createdate = createdate;
    }

    public java.math.BigDecimal getStatus(){
        return this.status;
    }

    public void setStatus(java.math.BigDecimal status){
        this.status = status;
    }
}