package com.lyy.uploadfile.VO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileListVO {
    private String name;
    private String createDate;
    private String reviseName;
    private String uploadName;

    public FileListVO() {
    }

    public FileListVO(String name, Date createDate, String reviseName, String uploadName) {
        this.name = name;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(createDate);
        this.createDate = date;
        this.reviseName = reviseName;
        this.uploadName = uploadName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(createDate);
        this.createDate = date;
    }

    public String getReviseName() {
        return reviseName;
    }

    public void setReviseName(String reviseName) {
        this.reviseName = reviseName;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }
}
