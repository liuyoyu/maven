package com.lyy.Entity;

/**
 * author liuyongyu
 * date 2021/8/1
 */
public class FieldEntity {
    private String name;//属性名

    private String type;//属性类型

    private String qualifier;//属性限定符

    private String remark;//备注

    private String upperCaseHeader;//首字母大写

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQualifier() {
        return qualifier;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpperCaseHeader() {
        return upperCaseHeader;
    }

    public void setUpperCaseHeader(String upperCaseHeader) {
        this.upperCaseHeader = upperCaseHeader;
    }
}
