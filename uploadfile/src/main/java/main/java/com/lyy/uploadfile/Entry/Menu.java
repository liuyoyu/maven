package main.java.com.lyy.uploadfile.Entry;

import main.java.com.lyy.uploadfile.Configture.Interface.PrimaryKey;
import main.java.com.lyy.uploadfile.Configture.SystemParameters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Menu {

    @PrimaryKey
    private long id;

    private String name;

    private String url = "";

    private long parentId = SystemParameters.MENUPARENTID;  //父菜单 id = -1;

    private double seq;

    private int status = STATUS.USING.value;

    private Date createDate;

    private List<Menu> child;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    public Menu() {
    }

    public Menu(MenuRole menuRole) {
        this.id = menuRole.getMenuId();
        this.name = menuRole.getMenuName();
        this.url = menuRole.getUrl();
        this.parentId = menuRole.getMenuParentId();
        this.seq = menuRole.getSeq();
        this.status = menuRole.getStatus();
    }

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

    public List<Menu> getChild() {
        return child;
    }

    public void setChild(List<Menu> child) {
        this.child = child;
    }

    public String getStatusName(){
        if (this.status == STATUS.USING.value()) {
            return "可用";
        }
        if (this.status == STATUS.BANNED.value()) {
            return "禁用";
        }
        return "";
    }

    public String getCreateDateFormat(){
        if (this.createDate == null) {
            return "";
        }
        return sdf.format(this.createDate);
    }

    public String getParent(){
        if (this.parentId == -1) {
            return "无";
        }
        return "" + this.parentId;
    }
}
