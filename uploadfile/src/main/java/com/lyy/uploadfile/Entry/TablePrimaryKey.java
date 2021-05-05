package com.lyy.uploadfile.Entry;

import com.lyy.uploadfile.Configture.Interface.PrimaryKey;

import java.util.Date;

public class TablePrimaryKey {

    private String classReferenceName;

    private String currentTableID;

    private int strategy;   //增长策略

    private Date createDate;

    public enum IncreaseStrategy{
        AUTO("自增长", 1),
        DATE("日期增长", 2);

        private String type;
        private int typeNum;

        IncreaseStrategy(String type, int typeNum) {
            this.type = type;
            this.typeNum = typeNum;
        }

        public String getType() {
            return type;
        }

        public int getTypeNum() {
            return typeNum;
        }
    }

    public String getClassReferenceName() {
        return classReferenceName;
    }

    public void setClassReferenceName(String classReferenceName) {
        this.classReferenceName = classReferenceName;
    }

    public String getCurrentTableID() {
        return currentTableID;
    }

    public void setCurrentTableID(String currentTableID) {
        this.currentTableID = currentTableID;
    }

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public IncreaseStrategy getIncreaseStrategy(){
        for (IncreaseStrategy increaseStrategy : IncreaseStrategy.values()) {
            if (getStrategy() == increaseStrategy.getTypeNum()) {
                return increaseStrategy;
            }
        }
        return null;
    }
}
