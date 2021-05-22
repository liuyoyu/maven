package com.lyy.uploadfile.Entry;

import java.util.Date;
import java.util.List;

public class Menu {
    private long id;

    private String name;

    private String url;

    private long parentId;

    private double seq;

    private int status;

    private Date createDate;

    public enum STATUS{
        USING(0),
        BANNED(1);
        private int value;
        STATUS(int value){
            this.value = value;
        }
        public int value(){
            return this.value;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public double getSeq() {
        return seq;
    }

    public void setSeq(double seq) {
        this.seq = seq;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
