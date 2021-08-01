package com.demo.vo;

/**
*  该类由自动代码生成
*  生成时间：2021/08/01 17:04:49
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
    private java.lang.String taskname;
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
    private java.lang.String reviseaccount;
    /**
    * 
    */
    private java.lang.String useraccount;
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

    public java.lang.String getTaskname(){
        return this.taskname;
    }

    public void setTaskname(java.lang.String taskname){
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

    public java.lang.String getReviseaccount(){
        return this.reviseaccount;
    }

    public void setReviseaccount(java.lang.String reviseaccount){
        this.reviseaccount = reviseaccount;
    }

    public java.lang.String getUseraccount(){
        return this.useraccount;
    }

    public void setUseraccount(java.lang.String useraccount){
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