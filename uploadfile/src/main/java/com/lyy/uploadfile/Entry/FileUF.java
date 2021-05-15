package com.lyy.uploadfile.Entry;

import com.lyy.uploadfile.Configture.Interface.PrimaryKey;

import javax.validation.constraints.NotEmpty;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUF {

    @PrimaryKey
    private long id;

    @NotEmpty(message = "文件名称不能为空")
    private String fileName;

    private String storeName;

    @NotEmpty(message = "文件类型不能为空")
    private String fileType;

    private String fileSize;    //包含单位 KB、M、G

    private String uploadName;

    private String uploadAccount;

    private String reviseName = "-";

    private String reviseAccount;

    private Date uploadDate;

    private int status;     //状态：1 -可下载；0 -无法下载

    private String locatePath;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getReviseName() {
        return reviseName;
    }

    public void setReviseName(String reviseName) {
        this.reviseName = reviseName;
    }

    public String getReviseAccount() {
        return reviseAccount;
    }

    public void setReviseAccount(String reviseAccount) {
        this.reviseAccount = reviseAccount;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public String getUpDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        return sdf.format(uploadDate);
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getStatus() {
        return status;
    }

    public String getDownloadStatus() {
        return status == 0 ? "否" : "是";
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUploadAccount() {
        return uploadAccount;
    }

    public void setUploadAccount(String uploadAccount) {
        this.uploadAccount = uploadAccount;
    }

    public String getLocatePath() {
        return locatePath;
    }

    public void setLocatePath(String locatePath) {
        this.locatePath = locatePath;
    }
}
