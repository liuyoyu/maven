package main.java.com.lyy.uploadfile.Entry;

import main.java.com.lyy.uploadfile.Configture.Interface.PrimaryKey;

import java.util.Date;

public class TaskFlow {

    @PrimaryKey
    private long id;

    private String taskName;

    private Date taskStartTime;//流程开始时的时间

    private Date taskEndTime;//流程结束时的时间

    private String reviseAccount;//审核人账号

    private String reviseName;

    private String userAccount;

    private String userName;

    private int status = STATUS.WAITING.getVal();

    private long fileId;

    public enum STATUS{
        PASS(0),
        REFUSE(1),
        CANCEL(2),
        WAITING(3);
        private int status;
        STATUS(int status){
            this.status = status;
        }
        public int getVal() {
            return status;
        }
    }

    private String fileName;

    private String fileSize;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Date taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Date getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Date taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getReviseAccount() {
        return reviseAccount;
    }

    public void setReviseAccount(String reviseAccount) {
        this.reviseAccount = reviseAccount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getFileId() {
        return fileId;
    }

    public void setFileId(long fileId) {
        this.fileId = fileId;
    }

    public String getReviseName() {
        return reviseName;
    }

    public void setReviseName(String reviseName) {
        this.reviseName = reviseName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
