package com.lyy.Entity;

import java.util.List;

/**
 * author liuyongyu
 * date 2021/7/31
 */
public class TableEntity {
    private String name;//名称

    private String space;//空间

    private String schema;//模式

    private String catalog;//目录

    private String remark; //备注

    private List<PrimaryKey> pk;//主键信息

    private List<ColumnEntity> columns;//列信息

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public List<PrimaryKey> getPk() {
        return pk;
    }

    public void setPk(List<PrimaryKey> pk) {
        this.pk = pk;
    }

    public String getPrimaryKeyInfo() {
        StringBuilder sb = new StringBuilder();
        for (PrimaryKey primaryKey : pk) {
            sb.append("#").append(primaryKey.getColumnsName());
        }
        return sb.toString();
    }
}
