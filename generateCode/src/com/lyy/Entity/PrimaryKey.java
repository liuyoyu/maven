package com.lyy.Entity;

/**
 * author liuyongyu
 * date 2021/7/31
 */
public class PrimaryKey {
    private String name; //主键名称

    private String columnsName; //列名称

    private String keySeq;//主键序列

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnsName() {
        return columnsName;
    }

    public void setColumnsName(String columnsName) {
        this.columnsName = columnsName;
    }

    public String getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(String keySeq) {
        this.keySeq = keySeq;
    }
}
