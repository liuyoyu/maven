package com.demo.vo;

/**
*  该类由自动代码生成
*  createDate：2021/11/14 21:00:40
*  表名: TASK_FLOW
*/
public class TaskFlowVO{
    /**
    * 
    */
    private java.math.BigDecimal id;
    /**
    * 
    */
    private String taskname;
    /**
    * 
    */
    private java.util.Date taskstarttime;
    /**
    * 
    */
    private java.util.Date taskendtime;
    /**
    * 
    */
    private String reviseaccount;
    /**
    * 
    */
    private String useraccount;
    /**
    * 
    */
    private java.math.BigDecimal status;
    /**
    * 
    */
    private java.math.BigDecimal fileid;

    public java.math.BigDecimal getId(){
        return this.id;
    }

    public void setId(java.math.BigDecimal id){
        this.id = id;
    }

    public String getTaskname(){
        return this.taskname;
    }

    public void setTaskname(String taskname){
        this.taskname = taskname;
    }

    public java.util.Date getTaskstarttime(){
        return this.taskstarttime;
    }

    public void setTaskstarttime(java.util.Date taskstarttime){
        this.taskstarttime = taskstarttime;
    }

    public java.util.Date getTaskendtime(){
        return this.taskendtime;
    }

    public void setTaskendtime(java.util.Date taskendtime){
        this.taskendtime = taskendtime;
    }

    public String getReviseaccount(){
        return this.reviseaccount;
    }

    public void setReviseaccount(String reviseaccount){
        this.reviseaccount = reviseaccount;
    }

    public String getUseraccount(){
        return this.useraccount;
    }

    public void setUseraccount(String useraccount){
        this.useraccount = useraccount;
    }

    public java.math.BigDecimal getStatus(){
        return this.status;
    }

    public void setStatus(java.math.BigDecimal status){
        this.status = status;
    }

    public java.math.BigDecimal getFileid(){
        return this.fileid;
    }

    public void setFileid(java.math.BigDecimal fileid){
        this.fileid = fileid;
    }
}